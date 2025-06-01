package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderItemDTO {
    private String orderId;
    private String productId;
    private int qty;
    private double price;
}
