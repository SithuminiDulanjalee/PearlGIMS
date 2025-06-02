package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.db.DBConnection;
import lk.ijse.pearlgims.dto.OrdersDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

            if (isOrderSave) {
                boolean isOrderDetailsSaved = orderItemModel.saveOrderDetailsList(ordersDTO.getOrderItems());
                if (isOrderDetailsSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
