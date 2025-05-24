package lk.ijse.pearlgims.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pearlgims.util.ReferenceUtil;

import java.io.IOException;

public class LoginController {
    public TextField txtUsername;
    public AnchorPane ancLogin;
    public TextField txtPassword;
    public Label lblInvalid;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String inputUsername = txtUsername.getText().trim();
        String inputPassword = txtPassword.getText().trim();

        if ("admin".equals(inputUsername) && "1234".equals(inputPassword)) {
            ancLogin.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            ancLogin.getChildren().add(load);
            ReferenceUtil.stage.setResizable(true);
            ReferenceUtil.stage.setMaximized(true);

        } else {
            lblInvalid.setVisible(true);
            lblInvalid.setText("Invalid username or password");
        }

        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            lblInvalid.setVisible(true);
            lblInvalid.setText("Please enter username and password");
        }

    }
}
