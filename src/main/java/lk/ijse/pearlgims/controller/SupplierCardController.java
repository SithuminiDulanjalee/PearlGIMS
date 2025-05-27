package lk.ijse.pearlgims.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pearlgims.dto.SupplierDTO;
import lk.ijse.pearlgims.util.DialogUtil;

import java.io.IOException;

public class SupplierCardController {
    public Label supplierName;
    public Label lblPhoneNumber;
    private SupplierDTO supplier;


    public void customerCardOnClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/component/SuppliersDialogCard.fxml"));
        AnchorPane pane = loader.load();
        SupplierCardDialogController supplierCardDialogController = loader.getController();
        supplierCardDialogController.setData(supplier);
        DialogUtil.showCustom("Supplier Details", "",pane , null);
    }

    public void load(SupplierDTO supplier){
        this.supplier = supplier;
        supplierName.setText(this.supplier.getName());
        lblPhoneNumber.setText(this.supplier.getContact());
    }
}
