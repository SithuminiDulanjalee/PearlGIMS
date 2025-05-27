package lk.ijse.pearlgims.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.pearlgims.util.ReferenceUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField txtUsername;
    public AnchorPane ancMainContainer;
    public AnchorPane ancLogin;
    public TextField txtPassword;
    public Label lblInvalid;
    public StackPane dialogPane;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String inputUsername = txtUsername.getText().trim();
        String inputPassword = txtPassword.getText().trim();

        if ("admin".equals(inputUsername) && "1234".equals(inputPassword)) {
            navigateTo("/view/Dashboard.fxml");
        } else {
            lblInvalid.setVisible(true);
            lblInvalid.setText("Invalid username or password");
        }

        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            lblInvalid.setVisible(true);
            lblInvalid.setText("Please enter username and password");
        }

    }

    private void navigateTo(String path){
        try {
            ancLogin.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            ancLogin.prefWidthProperty().bind(ancMainContainer.widthProperty());
            ancLogin.prefHeightProperty().bind(ancMainContainer.heightProperty());

            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());

            ancLogin.getChildren().add(anchorPane);
            ReferenceUtil.stage.setResizable(true);
            ReferenceUtil.stage.setMaximized(true);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Page not found...!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReferenceUtil.dialogPane = dialogPane;
    }
}
