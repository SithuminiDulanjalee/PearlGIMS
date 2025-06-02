package lk.ijse.pearlgims.dto;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private String orderId;
    private Date orderDate;
    private String customerName;
    private int productCount;
    private int totalProductQuantity;
}
