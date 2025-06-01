package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {
    private String paymentId;
    private String orderId;
    private double total;
    private double paid;
    private String status;
}
