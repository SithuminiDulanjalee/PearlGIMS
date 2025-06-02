package lk.ijse.pearlgims.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.pearlgims.db.DBConnection;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportPageController implements Initializable {
    public TextField txtSearch;
    public JFXButton btnOrderReport;

    public void txtSearchBarOnAction(KeyEvent keyEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnOrderReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport report = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/view/report/OrderDetails.jrxml")
            );
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> params = new HashMap<>();
            params.put("P_DATE", LocalDate.now().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report,
                    params,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            reload();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong"+e.getMessage()).show();
        }
    }

    private void reload() {
    }
}
