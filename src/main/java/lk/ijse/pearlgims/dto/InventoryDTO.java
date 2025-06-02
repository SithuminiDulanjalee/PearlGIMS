package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InventoryDTO {
    private String inventoryId;
    private String name;
    private String supplierId;
    private String date;

}
