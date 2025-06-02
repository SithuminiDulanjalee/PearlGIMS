package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.dto.OrderDetailDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailModel {
    public ArrayList<OrderDetailDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT orders.order_id,orders.order_date,customer.name AS customer_name,COUNT(order_item.order_id) AS item_count,SUM(order_item.qty) AS total_item_quantity,SUM(order_item.price) AS total_price FROM orders JOIN customer ON orders.customer_id = customer.customer_id JOIN order_item ON orders.order_id = order_item.order_id GROUP BY orders.order_id");

        ArrayList<OrderDetailDTO> orderDetailDTOArrayList = new ArrayList<>();
        while (resultSet.next()){
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(resultSet.getString(1),resultSet.getDate(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5));
            orderDetailDTOArrayList.add(orderDetailDTO);
        }

        return orderDetailDTOArrayList;
    }
}
