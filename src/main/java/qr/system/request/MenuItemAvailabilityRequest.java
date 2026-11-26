package qr.system.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MenuItemAvailabilityRequest {

    @NotNull(message = "isAvailable must not be null.")
    private Boolean isAvailable;
}
