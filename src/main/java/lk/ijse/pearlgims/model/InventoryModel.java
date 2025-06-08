package lk.ijse.pearlgims.model;

import javafx.scene.control.Alert;
import lk.ijse.pearlgims.dto.tm.InventoryTM;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryModel {
    public ArrayList<InventoryTM> getAllInventory() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryTM> inventoryTMArrayList = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("select * from inventory");

        while (rs.next()) {
            inventoryTMArrayList.add(
                    new InventoryTM(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            new Date(rs.getDate(4).getTime())
                    ));
        }
        return inventoryTMArrayList;
    }

    public boolean saveInventory(InventoryTM inventoryTM) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into inventory values (?,?,?,?)",
                inventoryTM.getInventoryId(),
                inventoryTM.getName(),
                inventoryTM.getSupplierId(),
                inventoryTM.getDate()
        );
    }

    public String getNextId(){
        try {
            ResultSet resultSet = CrudUtil.execute("select MAX(inventory_id) from inventory order by inventory_id desc limit 1");
            char tableCharacter = 'I';
            if (resultSet.next()) {
                String lastId = resultSet.getString(1);
                String lastIdNumberString = lastId.substring(1);
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

    public ArrayList<InventoryTM> loadInventory() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryTM> inventoryTMArrayList = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("select inventory_id,name from inventory");

        while (rs.next()) {
            inventoryTMArrayList.add(new InventoryTM(rs.getString(1),rs.getString(2)));
        }
        return inventoryTMArrayList;


    }
}
