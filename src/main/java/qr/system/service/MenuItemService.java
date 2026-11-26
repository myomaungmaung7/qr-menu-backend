package qr.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qr.system.entity.MenuItem;
import qr.system.exceptions.ResourceNotFoundException;
import qr.system.mapper.MenuItemMapper;
import qr.system.repository.MenuItemRepository;
import qr.system.request.MenuItemRequest;
import qr.system.response.MenuItemResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Transactional
    public MenuItemResponseDto createMenuItem(@Valid MenuItemRequest request) {
        log.info("Creating new menu item: {}", request.getItemName());

        MenuItem menuItem = MenuItemMapper.toEntity(request);
        MenuItem savedItem = menuItemRepository.save(menuItem);

        log.info("Menu item created successfully with ID: {}", savedItem.getId());
        return MenuItemMapper.toResponseDto(savedItem);
    }

    @Transactional
    public MenuItemResponseDto updateMenuItem(Long itemId, @Valid MenuItemRequest request) {
        log.info("Updating menu item with ID: {}", itemId);

        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found - No menu item found with ID: " + itemId + "."));

        MenuItemMapper.updateEntity(menuItem, request);
        MenuItem updatedItem = menuItemRepository.save(menuItem);

        log.info("Menu item updated successfully with ID: {}", updatedItem.getId());
        return MenuItemMapper.toResponseDto(updatedItem);
    }

    @Transactional
    public MenuItemResponseDto updateAvailability(Long itemId, Boolean isAvailable) {
        log.info("Updating availability for menu item with ID: {} to {}", itemId, isAvailable);

        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found - No menu item found with ID: " + itemId + "."));

        menuItem.setIsAvailable(isAvailable);
        MenuItem updatedItem = menuItemRepository.save(menuItem);

        log.info("Menu item availability updated successfully for ID: {}", updatedItem.getId());
        return MenuItemMapper.toResponseDto(updatedItem);
    }
}
