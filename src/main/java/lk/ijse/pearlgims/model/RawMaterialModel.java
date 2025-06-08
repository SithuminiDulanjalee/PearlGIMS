package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.dto.InventoryDetailDTO;
import lk.ijse.pearlgims.dto.OrderItemDTO;
import lk.ijse.pearlgims.dto.ProductDTO;
import lk.ijse.pearlgims.dto.RawMaterialDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RawMaterialModel {
    public ArrayList<String> getAllRawMaterialIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select material_id from raw_material");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }


    public RawMaterialDTO findById(String selectedRawMaterialId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute(
                "select * from raw_material where material_id=?",
                selectedRawMaterialId
        );
        if (rst.next()) {
            return new RawMaterialDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
        }
        return null;
    }

    public boolean reduceQty(InventoryDetailDTO inventoryDetailDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update raw_material set qty = qty - ? where material_id = ?",
                inventoryDetailDTO.getQty(),
                inventoryDetailDTO.getMaterialId()
        );
    }


    public String getNextRawMaterialId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select material_id from raw_material order by material_id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber+1;

            String nextIdString = String.format("R%03d",nextIdNumber);
            return nextIdString;
        }
        return "R001";
    }

    public boolean saveRawMaterial(RawMaterialDTO rawMaterialDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into raw_material (material_id, material_name, price, qty) values (?,?,?,?)",
                rawMaterialDTO.getMaterialId(),
                rawMaterialDTO.getMaterialName(),
                rawMaterialDTO.getPrice(),
                rawMaterialDTO.getQty()
        );
    }

    public ArrayList<RawMaterialDTO> getAllRawMaterials() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from raw_material");

        ArrayList<RawMaterialDTO> rawMaterialDTOArrayList = new ArrayList<>();
        while (resultSet.next()){
            RawMaterialDTO rawMaterialDTO = new RawMaterialDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4));
            rawMaterialDTOArrayList.add(rawMaterialDTO);
        }

        return rawMaterialDTOArrayList;
    }

    public boolean updateRawMaterial(RawMaterialDTO rawMaterialDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update raw_material set material_name=?, price=?, qty=? where material_id=?",
                rawMaterialDTO.getMaterialName(),
                rawMaterialDTO.getPrice(),
                rawMaterialDTO.getQty(),
                rawMaterialDTO.getMaterialId()
        );
    }

    public boolean deleteRawMaterial(String rawMaterialId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from raw_material where material_id=?",rawMaterialId);
    }

    public ArrayList<RawMaterialDTO> loadRawMaterials() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT material_id,material_name FROM raw_material");
        ArrayList<RawMaterialDTO> rawMaterialList = new ArrayList<>();
        while (result.next()) {
            RawMaterialDTO rawMaterial = new RawMaterialDTO(
                    result.getString(1),
                    result.getString(2)
            );
            rawMaterialList.add(rawMaterial);
        }
        return rawMaterialList;
    }
}
