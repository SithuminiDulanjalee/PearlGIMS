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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierPageController implements Initializable {
    public ScrollPane supplierScrollPane1;
    public ScrollPane supplierScrollPane;
    public JFXButton btnUpdate;
    public ImageView supplierImage;
    public TextField txtCompany;
    public Label lblSupplierID;
    public JFXButton btnDelete;
    public TextField txtOwner;
    public TextField txtPhone;
    public TextField txtAddress;
    public JFXButton btnAdd;
    public ImageView supplierImg;
    public Label lblCompany;
    public Label lblPhoneNumber;
    public GridPane gridPane;
    public TextField txtSearch;
    public Button btnSave;

    SupplierModel supplierModel = new SupplierModel();

    public void iconAddSupplierOnAction(MouseEvent mouseEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
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
                supplierCardController.load(supplier);

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
        Platform.runLater(() -> {
            try {
                loadSupplierCards(); // No parameters
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }
        });
//        gridPane.widthProperty().addListener((obs, oldVal, newVal) -> {
//            try {
//                loadSupplierCards(); // No parameters
//            } catch (Exception e) {
//                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//                e.printStackTrace();
//            }
//        });
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

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }
}
