package lk.ijse.pearlgims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.pearlgims.dto.CustomerDTO;
import lk.ijse.pearlgims.dto.OrderDetailDTO;
import lk.ijse.pearlgims.dto.tm.CustomerTM;
import lk.ijse.pearlgims.dto.tm.OrderDetailTM;
import lk.ijse.pearlgims.model.OrderDetailModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderDetailPageController implements Initializable {
    public TextField txtSearch;
    public TableView<OrderDetailTM> tblOrderDetails;
    public TableColumn<OrderDetailTM, String> colOrderId;
    public TableColumn<OrderDetailTM, Date> colOrderDate;
    public TableColumn<OrderDetailTM, String> colCustomerName;
    public TableColumn<OrderDetailTM, Integer> colProductCount;
    public TableColumn<OrderDetailTM, Integer> colTotalProductQuantity;
    public TableColumn<?,?> colAction;


    private final OrderDetailModel orderDetailModel = new OrderDetailModel();

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }

    public void txtSearchBarOnAction(KeyEvent keyEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!"+e.getMessage()).show();
        }

    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> orderDetailDTOArrayList = orderDetailModel.getAllOrderDetails();
        ObservableList<OrderDetailTM> orderDetailTMS = FXCollections.observableArrayList();

        for (OrderDetailDTO orderDetailDTO : orderDetailDTOArrayList){
            Button btnView = new Button("View");
            OrderDetailTM orderDetailTM = new OrderDetailTM(
                    orderDetailDTO.getOrderId(),
                    orderDetailDTO.getOrderDate(),
                    orderDetailDTO.getCustomerName(),
                    orderDetailDTO.getProductCount(),
                    orderDetailDTO.getTotalProductQuantity(),
                    btnView
            );
            orderDetailTMS.add(orderDetailTM);
        }
        tblOrderDetails.setItems(orderDetailTMS);
    }

    private void setCellValues() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colProductCount.setCellValueFactory(new PropertyValueFactory<>("productCount"));
        colTotalProductQuantity.setCellValueFactory(new PropertyValueFactory<>("totalProductQuantity"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnView"));
    }

}
