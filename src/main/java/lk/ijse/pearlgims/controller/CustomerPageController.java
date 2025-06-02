package lk.ijse.pearlgims.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pearlgims.dto.CustomerDTO;
import lk.ijse.pearlgims.dto.tm.CustomerTM;
import lk.ijse.pearlgims.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerPageController implements Initializable {
    public Label lblCustomerId;
    public TextField txtName;
    public Button btnSave;
    public TextField txtContact;
    public Button btnReset;
    public TextField txtEmail;
    public Button btnReport;
    public TextField txtAddress;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM,String> colCustomerId;
    public TableColumn<CustomerTM,String> colName;
    public TableColumn<CustomerTM,String> colContact;
    public TableColumn<CustomerTM,String> colEmail;
    public TableColumn<CustomerTM,String> colAddress;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String addressPattern = "^[A-Za-z ]+$";

    private final CustomerModel customerModel = new CustomerModel();
    public ImageView supplierImage;
    public TextField txtSearch;
    public Button btnEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try{
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerID = lblCustomerId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidAddress = address.matches(addressPattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle()+";-fx-border-color: #7367F0;");

        if(!isValidName)txtName.setStyle(txtName.getStyle()+"-fx-border-color: red;");
        if(!isValidContact)txtContact.setStyle(txtContact.getStyle()+"-fx-border-color: red;");
        if(!isValidEmail)txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: red;");
        if(!isValidAddress)txtAddress.setStyle(txtAddress.getStyle()+"-fx-border-color: red;");

        CustomerDTO customerDTO = new CustomerDTO(
                customerID,
                name,
                contact,
                email,
                address
        );

        if (isValidName && isValidContact && isValidEmail && isValidAddress) {
            try {
                boolean isSaved = customerModel.saveCustomer(customerDTO);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save customer").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
    }

    public void onClickCustomerTable(MouseEvent mouseEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if(selectedItem != null){
            lblCustomerId.setText(selectedItem.getCustomerID());
            txtName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContact());
            txtEmail.setText(selectedItem.getEmail());
            txtAddress.setText(selectedItem.getAddress());
        }

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOArrayList = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOArrayList){
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomerID(),
                    customerDTO.getName(),
                    customerDTO.getContact(),
                    customerDTO.getEmail(),
                    customerDTO.getAddress()
            );
            customerTMS.add(customerTM);
        }
        tblCustomer.setItems(customerTMS);

    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerID = lblCustomerId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        CustomerDTO customerDTO = new CustomerDTO(
                customerID,
                name,
                contact,
                email,
                address
        );

        try {
            boolean isUpdated = customerModel.updateCustomer(customerDTO);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String customerID = lblCustomerId.getText();
            try {
                boolean isDeleted = customerModel.deleteCustomer(customerID);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete customer").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        }
    }

    private void resetPage(){
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtName.clear();
            txtContact.clear();
            txtEmail.clear();
            txtAddress.clear();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Somthing went wrong..").show();
        }
    }


    public void reload() {
        Platform.runLater(() -> {
            try {
                loadTableData(); // No parameters
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }
        });

}

    public void txtSearchBarOnAction(KeyEvent keyEvent) {
        String searchQuery = txtSearch.getText();
        if (searchQuery.isEmpty()) {
            reload();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        reload();
    }

    public void btnEmailOnAction(ActionEvent actionEvent) {
    }
}
