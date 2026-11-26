package qr.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MenuItemRequest {
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Item Name is required")
    private String itemName;

    @NotBlank(message = "Price is required")
    private String price;

    private String description;

    private String imageUrl;
}
