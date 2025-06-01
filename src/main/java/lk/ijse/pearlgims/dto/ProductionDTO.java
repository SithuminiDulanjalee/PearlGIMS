package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductionDTO {
    private String productionId;
    private String productId;
    private String materialId;
    private int qty;
}
