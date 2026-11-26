package qr.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import qr.system.entity.enums.ItemStatus;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity{

    private Long orderId;
    private Long itemId;
    private String quantity;
    private String price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus itemStatus = ItemStatus.SENT_TO_KITCHEN;
}
