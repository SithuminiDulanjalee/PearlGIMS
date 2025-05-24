package lk.ijse.pearlgims.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public AnchorPane ancMainContainer;

    public void btnHomePageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/HomePage.fxml");
    }

    public void btnCustomerPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/CustomerPage.fxml");
    }

    public void btnSupplierPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/SupplierPage.fxml");
    }

    public void btnInventoryPageOnAction(ActionEvent actionEvent) {
        
    }

    public void btnOrderPageOnAction(ActionEvent actionEvent) {
    }

    private void navigateTo(String path){
        try {
            ancMainContainer.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());
            ancMainContainer.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Page not found...!").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        navigateTo("/view/HomePage.fxml");
    }

    public void btnProductPageOnAction(ActionEvent actionEvent) {
    }

    public void btnQualityCheckPageOnAction(ActionEvent actionEvent) {
    }

    public void btnPaymentPageOnAction(ActionEvent actionEvent) {
    }

    public void btnReportPageOnAction(ActionEvent actionEvent) {
    }
}
