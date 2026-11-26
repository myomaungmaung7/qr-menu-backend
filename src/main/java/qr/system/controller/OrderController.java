package qr.system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qr.system.entity.Order;
import qr.system.request.OrderRequest;
import qr.system.response.ApiResponse;
import qr.system.service.OrderService;
import qr.system.utils.ResponseUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Order>> createOrder(
            @Valid @RequestBody OrderRequest request) {

        log.info("REST request to create order for table ID: {}", request.getTableId());
        Order createOrder = orderService.createOrder(request);
        return ResponseUtil.created("Order placed successfully.", createOrder);
    }

    @GetMapping("/table/{table-id}")
    public ResponseEntity<ApiResponse<List<Order>>> getTableOrders(
            @PathVariable("table_id") Long tableId) {

        log.info("REST request to fetch orders for table ID: {}", tableId);
        List<Order> orders = orderService.getOrdersByTable(tableId);
        return ResponseUtil.ok("Table orders fetched successfully.", orders);
    }
}
