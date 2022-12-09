package hu.petrik.adatbazisjavafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    private TextField fajtaInput;
    @FXML
    private Spinner<Integer> max_iqInput;
    @FXML
    private CheckBox szereti_banantCheckbox;
    @FXML
    private Button elkuldButton;

    public void initialize() {
        fajtaOszlop.setCellValueFactory(new PropertyValueFactory<>("fajta"));
        max_iqOszlop.setCellValueFactory(new PropertyValueFactory<>("max_iq"));
        szereti_banantOszlop.setCellValueFactory(new PropertyValueFactory<>("szereti_banantDisplay"));
        max_iqInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255, 50));
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

    @FXML
    public void elkuldClick(ActionEvent actionEvent) {
    }
}