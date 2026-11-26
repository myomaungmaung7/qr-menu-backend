package qr.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qr.system.entity.MenuItem;
import qr.system.exceptions.ResourceNotFoundException;
import qr.system.repository.MenuItemRepository;
import qr.system.request.MenuItemAvailabilityRequest;
import qr.system.request.MenuItemRequest;
import qr.system.response.ApiResponse;
import qr.system.response.MenuItemResponseDto;
import qr.system.service.MenuItemService;
import qr.system.utils.ResponseUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final MenuItemRepository menuItemRepository;

    @PostMapping("add-item")
    public ResponseEntity<ApiResponse<MenuItemResponseDto>> createMenuItem(
            @Valid @RequestBody MenuItemRequest request) {

        MenuItemResponseDto responseDto = menuItemService.createMenuItem(request);
        return ResponseUtil.created("Menu item created successfully.", responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItemResponseDto>> updateMenuItem(
            @PathVariable("id") Long itemId,
            @Valid @RequestBody MenuItemRequest request) {

        MenuItemResponseDto responseDto = menuItemService.updateMenuItem(itemId, request);
        return ResponseUtil.ok("Menu item updated successfully.", responseDto);
    }

    @PatchMapping("/{id}/available")
    public ResponseEntity<ApiResponse<MenuItemResponseDto>> updateAvailability(
            @PathVariable("id") Long itemId,
            @Valid @RequestBody MenuItemAvailabilityRequest request) {

        MenuItemResponseDto responseDto = menuItemService.updateAvailability(itemId, request.getIsAvailable());
        return ResponseUtil.ok("Menu item availability updated successfully.", responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<MenuItem>>> getMenuItems(
            @RequestParam(required = false) Long categoryId) {

        List<MenuItem> items;
        if (categoryId != null) {
            items = menuItemRepository.findByCategoryId(categoryId);
            if (items.isEmpty()) {
                throw new ResourceNotFoundException("No menu items found for category id: " + categoryId);
            }
        } else {
            items = menuItemRepository.findAll();
            if (items.isEmpty()) {
                throw new ResourceNotFoundException("No menu item available.");
            }
        }
        return ResponseUtil.ok("Menu item fetched successfully.", items);
    }

    @GetMapping("/{item-id}")
    public ResponseEntity<ApiResponse<MenuItem>> getMenuItemById (
            @PathVariable("item-id") Long itemId) {

        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id:" + itemId));
        return ResponseUtil.ok("Menu item detail fetched successfully.", item);
    }
}
