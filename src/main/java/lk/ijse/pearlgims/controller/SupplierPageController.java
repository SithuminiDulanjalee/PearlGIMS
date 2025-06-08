package lk.ijse.pearlgims.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lk.ijse.pearlgims.dto.SupplierDTO;
import lk.ijse.pearlgims.model.SupplierModel;
import lk.ijse.pearlgims.util.EmailUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierPageController implements Initializable {
    public ImageView supplierImage;
    public Label lblSupplierID;
    public TextField txtAddress;
    public GridPane gridPane;
    public TextField txtSearch;
    public Button btnSave;
    public Button btnEmail;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtName;
    public Button btnUpdate;

    SupplierModel supplierModel = new SupplierModel();

    public void iconAddSupplierOnAction(MouseEvent mouseEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String supplierId = lblSupplierID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();

        SupplierDTO supplierDTO = new SupplierDTO(supplierId, name, address, contact, email);

        try {
            boolean isUpdated = supplierModel.updateSupplier(supplierDTO);

            if(isUpdated){
                reload();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Supplier Updated Successfully");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Fail To Update Supplier");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerCardOnClick(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reload(); // Call  reload
    }

    private void loadSupplierCards() throws SQLException, ClassNotFoundException {
        double gridWidth = gridPane.getWidth(); // Get current width of gridPane

        String searchQuery = txtSearch.getText();

        ArrayList<SupplierDTO> suppliers = supplierModel.getAllSuppliers(searchQuery);

        gridPane.getChildren().clear(); // Clear old cards

        try {
            double cardWidth = 200; // Approximate width of one card
            int columns = Math.max((int)(gridWidth / (cardWidth + 20)), 1); // Ensure at least 1 column

            int i = 0;
            for (SupplierDTO supplier : suppliers) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/component/SuppliersCard.fxml"));
                AnchorPane card = loader.load();
                SupplierCardController supplierCardController = loader.getController();
                supplierCardController.load(supplier, this);

                int col = i % columns;
                int row = i / columns;

                gridPane.add(card, col, row);
                GridPane.setMargin(card, new Insets(10));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);

        lblSupplierID.setText("");
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();

        getNextSupplierId();

        Platform.runLater(() -> {
            try {
                loadSupplierCards(); // No parameters
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }
        });
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
        reload();
    }

    public void txtSearchBarOnAction(KeyEvent inputMethodEvent) {
        String searchQuery = txtSearch.getText();
        if (searchQuery.isEmpty()) {
            reload();
        }
    }

    public void loadUpdateData(SupplierDTO supplierDTO){
        lblSupplierID.setText(supplierDTO.getSupplierID());
        txtName.setText(supplierDTO.getName());
        txtContact.setText(supplierDTO.getContact());
        txtEmail.setText(supplierDTO.getEmail());
        txtAddress.setText(supplierDTO.getAddress());
        btnSave.setDisable(true);
//        btnEmail.setDisable(true);
        btnUpdate.setDisable(false);
    }

    public  void deleteSupplier(String supplierID) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Supplier");
        alert.setContentText("Are you sure you want to delete this supplier?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = supplierModel.deleteSupplier(supplierID);
            if (isDeleted) {
                reload();
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier").show();
            }
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = lblSupplierID.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        SupplierDTO supplierDTO = new SupplierDTO(id,name,contact,email,address);
        try {
            boolean isSaved = supplierModel.saveSupplier(supplierDTO);
            if (isSaved) {
                reload();
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save supplier").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail").show();
        }
    }

    public void getNextSupplierId(){
        try{
            String nextId = supplierModel.getNextSupplierId();
            lblSupplierID.setText(nextId);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void btnEmailOnAction(ActionEvent actionEvent) {
        EmailUtil.sendEmail("","","");
    }
}
