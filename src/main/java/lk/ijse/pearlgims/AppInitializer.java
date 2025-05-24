package lk.ijse.pearlgims;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.pearlgims.db.DBConnection;
import lk.ijse.pearlgims.util.ReferenceUtil;

import java.sql.SQLException;

public class AppInitializer extends Application {
    public static void main(String[]args) throws ClassNotFoundException, SQLException {
        launch(args);
        DBConnection.getInstance().getConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(load);
        ReferenceUtil.stage=stage;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Pearl Garment");
        stage.show();
    }
}

