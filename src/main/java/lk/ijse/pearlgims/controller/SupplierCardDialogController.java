package lk.ijse.pearlgims.controller;

import javafx.scene.control.Label;
import lk.ijse.pearlgims.dto.SupplierDTO;

public class SupplierCardDialogController {
    public Label lblName;
    public Label lblEmail;
    public Label lblPhone;
    public Label lblAddress;

    public void setData(SupplierDTO supplier){
        lblName.setText(supplier.getName());
        lblEmail.setText(supplier.getEmail());
        lblPhone.setText(supplier.getContact());
        lblAddress.setText(supplier.getAddress());
    }
}
