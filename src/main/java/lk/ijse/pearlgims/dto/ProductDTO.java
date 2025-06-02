package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {
    private String productId;
    private String name;
    private double price;
    private int qty;
    private String status;
    private String size;

}
