package lk.ijse.pearlgims.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductTM {
    private String productId;
    private String name;
    private double price;
    private int qty;
    private String status;
    private String size;

}
