package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.db.DBConnection;
import lk.ijse.pearlgims.dto.OrderItemDTO;
import lk.ijse.pearlgims.dto.OrdersDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OrdersModel {
    private final OrderItemModel orderItemModel = new OrderItemModel();

    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");
        char tableCharacter = 'O';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    public boolean placeOrder(OrdersDTO ordersDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderSave = CrudUtil.execute(
                    "insert into orders values (?,?,?)",
                    ordersDTO.getOrderId(),
                    ordersDTO.getCustomerId(),
                    ordersDTO.getOrderDate()
            );
            System.out.println("isOrderSave = " + isOrderSave);

            if (isOrderSave) {
                boolean isOrderDetailsSaved = orderItemModel.saveOrderDetailsList(ordersDTO.getOrderItems());
                System.out.println("isOrderDetailsSaved = " + isOrderDetailsSaved);
                System.out.println("isOrderSave = " + isOrderSave);
                if (isOrderDetailsSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean changeOrderStatus(String orderId, String status) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update orders set status=? where order_id=?", status, orderId);
    }

    public OrdersDTO getOrderById(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from orders where order_id=?", orderId);
        ResultSet orderItem = CrudUtil.execute("select * from order_item where order_id=?", orderId);
        OrdersDTO order = new OrdersDTO();



        if (resultSet.next()) {
            ArrayList<OrderItemDTO> orderItems = new ArrayList<>();
            while (orderItem.next()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO(
                        orderItem.getString("orderId"),
                        orderItem.getString("productId"),
                        orderItem.getInt("qty"),
                        orderItem.getDouble("price")
                );
                orderItems.add(orderItemDTO);
            }

            order = new OrdersDTO(
                    resultSet.getString("order_id"),
                    resultSet.getString("customer_id"),
                    new Date(resultSet.getDate("order_date").getTime()),
                    orderItems
            );
        }
        return order;
    }
}
