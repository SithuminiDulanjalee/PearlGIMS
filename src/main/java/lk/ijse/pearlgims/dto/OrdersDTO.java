package lk.ijse.pearlgims.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrdersDTO {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private ArrayList<OrderItemDTO> orderItems;
}
