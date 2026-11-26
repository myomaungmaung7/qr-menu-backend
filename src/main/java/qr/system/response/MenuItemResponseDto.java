package qr.system.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItemResponseDto {
    private Long id;
    private Long categoryId;
    private String itemName;
    private String price;
    private String description;
    private String imageUrl;
    private Boolean isAvailable;
}
