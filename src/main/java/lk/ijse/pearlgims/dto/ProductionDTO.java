package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductionDTO {
    private String productionId;
    private String materialId;
    private String materialName;
    private int qty;
}
