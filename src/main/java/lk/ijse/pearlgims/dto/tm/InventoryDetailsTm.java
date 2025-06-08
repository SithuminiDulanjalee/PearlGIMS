package lk.ijse.pearlgims.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryDetailsTm {
    private String inventoryDetailId;
    private String inventoryId;
    private String materialId;
    private int qty;
}