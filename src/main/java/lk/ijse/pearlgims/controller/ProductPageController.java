package lk.ijse.pearlgims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pearlgims.dto.ProductDTO;
import lk.ijse.pearlgims.dto.tm.ProductTM;
import lk.ijse.pearlgims.model.ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {
    public TextField txtSearch;
    public TableView<ProductTM> tblProduct;
    public TableColumn<ProductTM,String> colProductId;
    public TableColumn<ProductTM,String> colName;
    public TableColumn<ProductTM,Double> colPrice;
    public TableColumn<ProductTM,Integer> colQty;
    public TableColumn<ProductTM,String> colStatus;
    public TableColumn<ProductTM,String> colSize;
    public Label lblProductId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtQty;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReport;
    public Button btnReset;
    public ComboBox<String> cmbSize;
    public ComboBox<String> cmbStatus;

    private final ProductModel productModel = new ProductModel();

    public void txtSearchBarOnAction(KeyEvent keyEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String searchText = txtSearch.getText().trim();
        ObservableList<ProductTM> filteredList = FXCollections.observableArrayList();

        for (ProductTM product : tblProduct.getItems()) {
            if (product.getProductId().contains(searchText) || product.getName().contains(searchText)) {
                filteredList.add(product);
            }
        }

        tblProduct.setItems(filteredList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String name = txtName.getText().trim();
        String priceDouble = txtPrice.getText().trim();
        String quantity = txtQty.getText().trim();

        if (!name.matches("^[A-Za-z ]+$")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid product name..!").show();
            return;
        }

        if (!priceDouble.matches("\\d+(\\.\\d+)?")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid price..!").show();
            return;
        }

        if (!quantity.matches("\\d+")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid quantity..!").show();
            return;
        }

        String productId = lblProductId.getText();
        double price = Double.parseDouble(priceDouble);
        int qty = Integer.parseInt(quantity);
        String selectedStatus = cmbStatus.getSelectionModel().getSelectedItem();
        if(selectedStatus == null){
            new Alert(Alert.AlertType.WARNING, "Please select status..!").show();
            return;
        }
        String selectedSize = cmbSize.getSelectionModel().getSelectedItem();
        if(selectedSize == null){
            new Alert(Alert.AlertType.WARNING, "Please select size..!").show();
            return;
        }

        ProductDTO productDTO = new ProductDTO(
                productId,
                name,
                price,
                qty,
                selectedStatus,
                selectedSize
        );

        try {
            boolean isSaved = productModel.saveProduct(productDTO);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Product saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save product").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String nameUpdate = txtName.getText().trim();
        String priceDoubleUpdate = txtPrice.getText().trim();
        String quantityUpdate = txtQty.getText().trim();

        if (!nameUpdate.matches("^[A-Za-z ]+$")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid product name..!").show();
            return;
        }

        if (!priceDoubleUpdate.matches("\\d+(\\.\\d+)?")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid price..!").show();
            return;
        }

        if (!quantityUpdate.matches("\\d+")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid quantity..!").show();
            return;
        }

        String productIdUpdate = lblProductId.getText();
        double priceUpdate = Double.parseDouble(priceDoubleUpdate);
        int qtyUpdate = Integer.parseInt(quantityUpdate);
        String selectedStatusUpdate = cmbStatus.getSelectionModel().getSelectedItem();
        if(selectedStatusUpdate == null){
            new Alert(Alert.AlertType.WARNING, "Please select status..!").show();
            return;
        }
        String selectedSizeUpdate = cmbSize.getSelectionModel().getSelectedItem();
        if(selectedSizeUpdate == null){
            new Alert(Alert.AlertType.WARNING, "Please select size..!").show();
            return;
        }

        ProductDTO productDTO = new ProductDTO(
                productIdUpdate,
                nameUpdate,
                priceUpdate,
                qtyUpdate,
                selectedStatusUpdate,
                selectedSizeUpdate
        );
        try {
            boolean isUpdated = productModel.updateProduct(productDTO);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Product updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update product").show();
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
            String productID = lblProductId.getText();
            try {
                boolean isDeleted = productModel.deleteProduct(productID);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete product").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        }
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void cmbSizeOnAction(ActionEvent actionEvent) {
    }

    public void cmbStatusOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));

        try{
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    public void onClickProductTable(MouseEvent mouseEvent) {
        ProductTM selectedItem = tblProduct.getSelectionModel().getSelectedItem();

        if(selectedItem != null){
            lblProductId.setText(selectedItem.getProductId());
            txtName.setText(selectedItem.getName());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            txtQty.setText(String.valueOf(selectedItem.getQty()));
            cmbStatus.getSelectionModel().select(selectedItem.getStatus());
            cmbSize.getSelectionModel().select(selectedItem.getSize());
        }

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> productDTOArrayList = productModel.getAllProducts();
        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (ProductDTO productDTO : productDTOArrayList){
            ProductTM productTM = new ProductTM(
                    productDTO.getProductId(),
                    productDTO.getName(),
                    productDTO.getPrice(),
                    productDTO.getQty(),
                    productDTO.getStatus(),
                    productDTO.getSize());
            productTMS.add(productTM);
        }
        tblProduct.setItems(productTMS);
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = productModel.getNextProductId();
        lblProductId.setText(nextId);
    }

    private void resetPage(){
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtName.clear();
            txtPrice.clear();
            txtQty.clear();
            cmbStatus.getSelectionModel().clearSelection();
            cmbSize.getSelectionModel().clearSelection();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong..").show();
        }
    }
}