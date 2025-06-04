package lk.ijse.pearlgims.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RawMaterialTM {
    private String materialId;
    private String materialName;
    private double price;
    private int qty;
}
