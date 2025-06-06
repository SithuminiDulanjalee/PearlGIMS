package lk.ijse.pearlgims.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InventoryDTO {
    private String inventoryId;
    private String name;
    private String supplierId;
    private Date date;
    private ArrayList<InventoryDetailDTO> inventoryDetails;

}
