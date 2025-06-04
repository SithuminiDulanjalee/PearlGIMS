package lk.ijse.pearlgims.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pearlgims.dto.SupplierDTO;
import lk.ijse.pearlgims.util.DialogUtil;

import java.io.IOException;
import java.sql.SQLException;

public class SupplierCardController {
    public Label supplierName;
    public Label lblPhoneNumber;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnView;
    private SupplierDTO supplier;
    SupplierPageController supplierPageController;


    public void customerCardOnClick(MouseEvent mouseEvent) throws IOException {

    }

    public void load(SupplierDTO supplier , SupplierPageController supplierPageController) throws IOException {
        this.supplier = supplier;
        this.supplierPageController = supplierPageController;
        supplierName.setText(this.supplier.getName());
        lblPhoneNumber.setText(this.supplier.getContact());
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/component/SuppliersDialogCard.fxml"));
        AnchorPane pane = loader.load();
        SupplierCardDialogController supplierCardDialogController = loader.getController();
        supplierCardDialogController.setData(supplier);
        DialogUtil.showCustom("Supplier Details", "",pane , "Send Mail", "Cancel", ()->System.out.println("clock cansal"), ()->System.out.println("clock cansal"));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        supplierPageController.loadUpdateData(supplier);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        supplierPageController.deleteSupplier(supplier.getSupplierID());
    }
}
