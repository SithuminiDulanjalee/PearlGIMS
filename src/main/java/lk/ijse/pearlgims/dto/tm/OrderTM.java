package lk.ijse.pearlgims.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderTM {
    private String productId;
    private String productName;
    private int orderQty;
    private String size;
    private double price;
    private double total;
    private Button btnRemove;

}
