package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.dto.SupplierDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM supplier");
        ArrayList<SupplierDTO> supplierList = new ArrayList<>();
        while (result.next()) {
            SupplierDTO supplier = new SupplierDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            );
            supplierList.add(supplier);
        }
        return supplierList;
    }

}
