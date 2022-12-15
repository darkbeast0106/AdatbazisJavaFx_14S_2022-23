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
    @FXML
    private Button modositButton;
    @FXML
    private Button torlesButton;

    public void initialize() {
        fajtaOszlop.setCellValueFactory(new PropertyValueFactory<>("fajta"));
        max_iqOszlop.setCellValueFactory(new PropertyValueFactory<>("max_iq"));
        szereti_banantOszlop.setCellValueFactory(new PropertyValueFactory<>("szereti_banantDisplay"));
        max_iqInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 50));
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
        String fajta = fajtaInput.getText().trim();
        int max_iq = max_iqInput.getValue();
        boolean szereti_banant = szereti_banantCheckbox.isSelected();
        if (fajta.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Fajta megadása kötelező", "");
            return;
        }
        Majom majom = new Majom(fajta, max_iq, szereti_banant);
        try {
            db.majomHozzaadasa(majom);
            alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
            majmokListazasa();
            urlapAlaphelyzetbe();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                alert(Alert.AlertType.WARNING, "Ilyen fajtájú majom már van felvéve", "");
            } else {
                alert(Alert.AlertType.ERROR, "Adatbazis hiba", e.getMessage());
            }
        }
    }

    private void urlapAlaphelyzetbe() {
        fajtaInput.setText("");
        max_iqInput.getValueFactory().setValue(50);
        szereti_banantCheckbox.setSelected(false);
    }

    @FXML
    public void modositClick(ActionEvent actionEvent) {
    }

    @FXML
    public void torlesClick(ActionEvent actionEvent) {
    }
}