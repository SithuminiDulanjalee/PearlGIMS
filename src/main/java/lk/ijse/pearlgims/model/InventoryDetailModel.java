package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.dto.tm.InventoryDetailsTm;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDetailModel {
    public ArrayList<InventoryDetailsTm> getAllInventoryDetaills() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryDetailsTm> inventoryDetailsTms = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM inventory_detail");

        while (rs.next()) {
            inventoryDetailsTms.add(new InventoryDetailsTm(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
        }
        return inventoryDetailsTms;
    }

    public boolean addInventoryDetail(InventoryDetailsTm inventoryDetailsTm) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO inventory_detail VALUES (?,?,?,?)",
                inventoryDetailsTm.getInventoryId(),
                inventoryDetailsTm.getInventoryId(),
                inventoryDetailsTm.getMaterialId(),
                inventoryDetailsTm.getQty()
        );
    }

    public String getNextId(){
        try {
            ResultSet resultSet = CrudUtil.execute("select MAX(inventory_detail_id) from inventory_detail order by inventory_detail_id desc limit 1");
            String tableCharacter = "ID";
            if (resultSet.next()) {
                String lastId = resultSet.getString(1);
                String lastIdNumberString = lastId.substring(2);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                String nextIdString = String.format(tableCharacter + "%04d", nextIdNumber);
                return nextIdString;
            }
            return tableCharacter + "0001";
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
