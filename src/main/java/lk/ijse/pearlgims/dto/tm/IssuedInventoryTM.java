package lk.ijse.pearlgims.dto.tm;

import javafx.scene.layout.HBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssuedInventoryTM {
    private String productionId;
    private String productionOrderId;
    private String productionMaterialId;
    private String productionMatrialName;
    private int productionQuentity;
    private HBox action;
}