package qr.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qr.system.entity.MenuItem;
import qr.system.entity.Order;
import qr.system.entity.OrderItem;
import qr.system.entity.TableEntity;
import qr.system.entity.enums.OrderStatus;
import qr.system.exceptions.ItemUnavailableException;
import qr.system.exceptions.ResourceNotFoundException;
import qr.system.repository.MenuItemRepository;
import qr.system.repository.OrderItemRepository;
import qr.system.repository.OrderRepository;
import qr.system.repository.TableRepository;
import qr.system.request.OrderItemRequest;
import qr.system.request.OrderRequest;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final TableRepository tableRepository;

    @Transactional
    public Order createOrder(@Valid OrderRequest request) {
        log.info("Processing new order creation for table ID {}", request.getTableId());

        TableEntity table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with ID: " + request.getTableId()));

        Order order = new Order();
        order.setTableId(table.getId());
        order.setOrderStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + itemReq.getItemId()));

            // Validate order availability
            if (menuItem.getIsAvailable() == null || !menuItem.getIsAvailable()) {
                log.warn("Attempted to order unavailable item ID: {}", menuItem.getId());
                throw new ItemUnavailableException("Menu item '" + menuItem.getItemName() + "' is currently unavailable.");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setItemId(menuItem.getId());
            orderItem.setQuantity(String.valueOf(itemReq.getQuantity()));
            orderItem.setPrice(menuItem.getPrice());

            orderItemRepository.save(orderItem);

            BigDecimal itemPrice = new BigDecimal(menuItem.getPrice());
            BigDecimal qty = new BigDecimal(itemReq.getQuantity());
            totalAmount = totalAmount.add(itemPrice.multiply(qty));
        }

        savedOrder.setTotalAmount(totalAmount.toString());
        log.info("Order successfully created with ID: {} and Total Amount: {}", savedOrder.getId(), totalAmount);
        return orderRepository.save(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByTable(Long tableId) {
        log.info("Fetching orders for table ID: {}", tableId);

        if (!tableRepository.existsById(tableId)) {
            throw new ResourceNotFoundException("Table not found with ID: " + tableId);
        }

        List<Order> orders = orderRepository.findByTableId(tableId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for table ID: " + tableId);
        }
        return orders;
    }
}
