package qr.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import qr.system.entity.enums.TableStatus;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tables")
@Data
@EqualsAndHashCode (callSuper = true)
public class TableEntity extends BaseEntity {

    private String tableNumber;

    @Column(columnDefinition = "TEXT")
    private String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status = TableStatus.AVAILABLE;
}
