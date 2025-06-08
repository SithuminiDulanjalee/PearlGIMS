package lk.ijse.pearlgims.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierDTO {
    private String supplierID;
    private String name;
    private String contact;
    private String email;
    private String address;

    public SupplierDTO(String supplierID, String name) {
        this.supplierID = supplierID;
        this.name = name;
    }
}
