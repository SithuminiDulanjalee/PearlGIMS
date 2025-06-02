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
        ResultSet rst = CrudUtil.execute("select distinct size from product");
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
                "update product set qty = qty - ? where product_id = ?",
                orderItemDTO.getQty(),
                orderItemDTO.getProductId()
        );
    }


    public String getNextProductId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select product_id from product order by product_id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber+1;

            String nextIdString = String.format("P%03d",nextIdNumber);
            return nextIdString;
        }
        return "P001";
    }

    public boolean saveProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into product (product_id, name, price, qty, status, size) values (?,?,?,?,?,?)",
                productDTO.getProductId(),
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getQty(),
                productDTO.getStatus(),
                productDTO.getSize()
        );
    }

    public ArrayList<ProductDTO> getAllProducts() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from product");

        ArrayList<ProductDTO> productDTOArrayList = new ArrayList<>();
        while (resultSet.next()){
            ProductDTO productDTO = new ProductDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6));
            productDTOArrayList.add(productDTO);
        }

        return productDTOArrayList;
    }

    public boolean updateProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update product set name=?, price=?, qty=?, status=?, size=? where product_id=?",
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getQty(),
                productDTO.getStatus(),
                productDTO.getSize(),
                productDTO.getProductId()
        );
    }

    public boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from product where product_id=?",productId);
    }


}