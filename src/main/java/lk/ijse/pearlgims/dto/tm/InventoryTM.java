package lk.ijse.pearlgims.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTM {
    private String inventoryId; // VARCHAR(10) PRIMARY KEY
    private String name;        // VARCHAR(50) NOT NULL
    private String supplierId;  // VARCHAR(10)
    private Date date;

    public InventoryTM(String inventoryId, String name) {
        this.inventoryId = inventoryId;
        this.name = name;
    }
}