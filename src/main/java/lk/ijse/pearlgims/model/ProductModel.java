package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.dto.OrderItemDTO;
import lk.ijse.pearlgims.dto.ProductDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModel {
    public ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select product_id from product");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    public ArrayList<String> getAllProductSizes() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select size from product");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String size = rst.getString(1);
            list.add(size);
        }
        return list;
    }

    public ProductDTO findById(String selectedProductId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute(
                "select * from product where product_id=?",
                selectedProductId
        );
        if (rst.next()) {
            return new ProductDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    public boolean reduceQty(OrderItemDTO orderItemDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update product set quantity = quantity - ? where product_id = ?",
                orderItemDTO.getQty(),
                orderItemDTO.getProductId()
        );
    }

    public ProductDTO findSizeById(String selectedItem) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute(
                "select * from product where size=?",
                selectedItem
        );
        if (rst.next()) {
            return new ProductDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
