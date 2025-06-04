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
import lk.ijse.pearlgims.dto.RawMaterialDTO;
import lk.ijse.pearlgims.dto.tm.ProductTM;
import lk.ijse.pearlgims.dto.tm.RawMaterialTM;
import lk.ijse.pearlgims.model.RawMaterialModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RawMaterialPageController implements Initializable {
    public TextField txtSearch;
    public TableView<RawMaterialTM> tblRawMaterial;
    public TableColumn<RawMaterialTM, String> colMaterialId;
    public TableColumn<RawMaterialTM, String> colName;
    public TableColumn<RawMaterialTM, Double> colPrice;
    public TableColumn<RawMaterialTM, Integer> colQty;
    public Label lblMaterialId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtQty;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReport;
    public Button btnReset;

    private final RawMaterialModel rawMaterialModel = new RawMaterialModel();

    public void txtSearchBarOnAction(KeyEvent keyEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }

    public void onClickRawMaterialTable(MouseEvent mouseEvent) {
        RawMaterialTM selectedItem = tblRawMaterial.getSelectionModel().getSelectedItem();

        if(selectedItem != null){
            lblMaterialId.setText(selectedItem.getMaterialId());
            txtName.setText(selectedItem.getMaterialName());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            txtQty.setText(String.valueOf(selectedItem.getQty()));
        }

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String materialId = lblMaterialId.getText();
        String name = txtName.getText();
        String priceDouble = txtPrice.getText();
        double price = Double.parseDouble(priceDouble);
        String quantity = txtQty.getText();
        int qty = Integer.parseInt(quantity);

        RawMaterialDTO rawMaterialDTO = new RawMaterialDTO(
                materialId,
                name,
                price,
                qty
        );

        try {
            boolean isSaved = rawMaterialModel.saveRawMaterial(rawMaterialDTO);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Raw material saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save raw material").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String materialIdUpdate = lblMaterialId.getText();
        String nameUpdate = txtName.getText();
        String priceDoubleUpdate = txtPrice.getText();
        double priceUpdate = Double.parseDouble(priceDoubleUpdate);
        String quantityUpdate = txtQty.getText();
        int qtyUpdate = Integer.parseInt(quantityUpdate);

        RawMaterialDTO rawMaterialDTO = new RawMaterialDTO(
                materialIdUpdate,
                nameUpdate,
                priceUpdate,
                qtyUpdate
        );
        try {
            boolean isUpdated = rawMaterialModel.updateRawMaterial(rawMaterialDTO);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Raw material updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update raw material").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail"+e.getMessage()).show();
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
            String materialID = lblMaterialId.getText();
            try {
                boolean isDeleted = rawMaterialModel.deleteRawMaterial(materialID);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Raw material deleted successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete raw material").show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try{
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<RawMaterialDTO> rawMaterialDTOArrayList = rawMaterialModel.getAllRawMaterials();
        ObservableList<RawMaterialTM> rawMaterialTMS = FXCollections.observableArrayList();

        for (RawMaterialDTO rawMaterialDTO : rawMaterialDTOArrayList) {
            RawMaterialTM rawMaterialTM = new RawMaterialTM(
                    rawMaterialDTO.getMaterialId(),
                    rawMaterialDTO.getMaterialName(),
                    rawMaterialDTO.getPrice(),
                    rawMaterialDTO.getQty());
            rawMaterialTMS.add(rawMaterialTM);
        }
        tblRawMaterial.setItems(rawMaterialTMS);

    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = rawMaterialModel.getNextRawMaterialId();
        lblMaterialId.setText(nextId);
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

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Somthing went wrong.."+e.getMessage()).show();
        }
    }
}
