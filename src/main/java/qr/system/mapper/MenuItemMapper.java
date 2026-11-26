package qr.system.mapper;

import qr.system.entity.MenuItem;
import qr.system.request.MenuItemRequest;
import qr.system.response.MenuItemResponseDto;

public class MenuItemMapper {

    // Request DTO to Entity
    public static MenuItem toEntity(MenuItemRequest dto) {
        if (dto == null) {
            return null;
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setCategoryId(dto.getCategoryId());
        menuItem.setItemName(dto.getItemName());
        menuItem.setPrice(dto.getPrice());
        menuItem.setDescription(dto.getDescription());
        menuItem.setImageUrl(dto.getImageUrl());
        menuItem.setIsAvailable(true);
        return menuItem;
    }

    // Update existing entity using request DTO
    public static void updateEntity(MenuItem menuItem, MenuItemRequest dto) {
        if (dto == null || menuItem == null) {
            return;
        }

        if (dto.getItemName() != null) {
            menuItem.setItemName(dto.getItemName());
        }
        if (dto.getDescription() != null) {
            menuItem.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            menuItem.setPrice(dto.getPrice());
        }
        if (dto.getCategoryId() != null) {
            menuItem.setCategoryId(dto.getCategoryId());
        }
        if (dto.getImageUrl() != null) {
            menuItem.setImageUrl(dto.getImageUrl());
        }
    }

    // Convert Entity to Response DTO
    public static MenuItemResponseDto toResponseDto(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }
        return MenuItemResponseDto.builder()
                .id(menuItem.getId())
                .categoryId(menuItem.getCategoryId())
                .itemName(menuItem.getItemName())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .imageUrl(menuItem.getImageUrl())
                .isAvailable(menuItem.getIsAvailable())
                .build();
    }
}
