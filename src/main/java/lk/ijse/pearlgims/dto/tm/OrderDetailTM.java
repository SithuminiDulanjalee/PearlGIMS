package lk.ijse.pearlgims.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailTM {
    private String orderId;
    private Date orderDate;
    private String customerName;
    private int productCount;
    private int totalProductQuantity;
    private Button btnView;
}
