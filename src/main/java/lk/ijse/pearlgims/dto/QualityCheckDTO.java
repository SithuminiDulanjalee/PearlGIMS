package lk.ijse.pearlgims.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QualityCheckDTO {
    private String qualityId;
    private String productionId;
    private String status;
}
