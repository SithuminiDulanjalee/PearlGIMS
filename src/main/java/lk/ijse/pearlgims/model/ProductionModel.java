package lk.ijse.pearlgims.model;

import javafx.scene.control.Alert;
import lk.ijse.pearlgims.dto.ProductionDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionModel {
    public ArrayList<ProductionDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT production.*,raw_material.material_name from production JOIN raw_material ON production.material_id = raw_material.material_id");
        ArrayList<ProductionDTO> productionDTOS= new ArrayList<>();

        while (resultSet.next()){
            ProductionDTO productionDTO = new ProductionDTO(
                    resultSet.getString("production_id"),
                    resultSet.getString("material_id"),
                    resultSet.getString("material_name"),
                    resultSet.getInt("qty")
            );
            productionDTOS.add(productionDTO);
        }

        return productionDTOS;
    }


    public boolean issue(String materialId, int quantity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO production VALUES(?,?,?)",getNextProductionId(),materialId,quantity);
    }

    public String getNextProductionId(){
        try {
            ResultSet resultSet = CrudUtil.execute("select MAX(production_id) from production order by production_id desc limit 1");
            String tableCharacter = "PR";
            if (resultSet.next()) {
                String lastId = resultSet.getString(1);
                String lastIdNumberString = lastId.substring(2);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
                return nextIdString;
            }
            return tableCharacter + "001";
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
        return null;
    }
}
