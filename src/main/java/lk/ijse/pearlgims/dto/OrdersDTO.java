package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrdersDTO {
    private String orderId;
    private String customerId;
    private String userId;
    private String orderDate;
    private String orderStatus;
    private String paymentStatus;
}
