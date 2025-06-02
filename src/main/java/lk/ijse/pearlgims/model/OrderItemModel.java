package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.dto.OrderItemDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemModel {
    private final ProductModel productModel = new ProductModel();

    public boolean saveOrderDetailsList(ArrayList<OrderItemDTO> orderItemList) throws SQLException, ClassNotFoundException {
        for (OrderItemDTO orderItemDTO : orderItemList) {
            boolean isOrderDetailsSaved = saveOrderDetail(orderItemDTO);
            if (!isOrderDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = productModel.reduceQty(orderItemDTO);
            if (!isItemUpdated) {
                return false;
            }


        }
        return true;
    }

    private boolean saveOrderDetail(OrderItemDTO orderItemDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into order_item values (?,?,?,?)",
                orderItemDTO.getOrderId(),
                orderItemDTO.getProductId(),
                orderItemDTO.getQty(),
                orderItemDTO.getPrice()
        );
    }
}
