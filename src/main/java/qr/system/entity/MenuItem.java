package qr.system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_items")
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuItem extends BaseEntity {

    private Long categoryId;
    private String itemName;
    private String price;
    private String description;
    private String imageUrl;
    private Boolean isAvailable = true;

}
