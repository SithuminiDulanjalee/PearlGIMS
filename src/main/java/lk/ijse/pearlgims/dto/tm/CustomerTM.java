package lk.ijse.pearlgims.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CustomerTM {
    private String customerID;
    private String name;
    private String contact;
    private String email;
    private String address;
    
}
