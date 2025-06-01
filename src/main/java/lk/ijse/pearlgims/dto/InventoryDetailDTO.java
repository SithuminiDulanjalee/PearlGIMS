package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InventoryDetailDTO {
    private String inventoryDetailId;
    private String inventoryId;
    private String materialId;
    private int qty;
}
