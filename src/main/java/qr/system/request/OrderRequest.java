package qr.system.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderRequest {

    @NotNull(message = "Table Id is required")
    private Long tableId;

    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemRequest> items;
}
