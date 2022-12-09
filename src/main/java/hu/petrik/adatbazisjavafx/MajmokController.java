package hu.petrik.adatbazisjavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MajmokController {

    @FXML
    private TableView<Majom> majmokTablazat;
    @FXML
    private TableColumn<Majom, String> fajtaOszlop;
    @FXML
    private TableColumn<Majom, Integer> max_iqOszlop;
    @FXML
    private TableColumn<Majom, String> szereti_banantOszlop;
    private MajomDB db;

    public void initialize() {
        fajtaOszlop.setCellValueFactory(new PropertyValueFactory<>("fajta"));
        max_iqOszlop.setCellValueFactory(new PropertyValueFactory<>("max_iq"));
        szereti_banantOszlop.setCellValueFactory(new PropertyValueFactory<>("szereti_banantDisplay"));
        Platform.runLater(() -> {
            try {
                db = new MajomDB();
                majmokListazasa();
            } catch (SQLException e) {
                alert(Alert.AlertType.ERROR, "Adatbázis hiba", e.getMessage());
                Platform.exit();
            }
        });
    }

    private void majmokListazasa() throws SQLException {
        List<Majom> majmok = db.majmokListazasa();
        majmokTablazat.getItems().clear();
        majmokTablazat.getItems().addAll(majmok);
    }

    private void alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}