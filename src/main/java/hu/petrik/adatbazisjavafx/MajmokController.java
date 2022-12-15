package hu.petrik.adatbazisjavafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    private int kivalasztottMajomId;
    @FXML
    private Button megseButton;

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

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
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

        try {
            if (elkuldButton.getText().equals("Elküld")) {
                Majom majom = new Majom(fajta, max_iq, szereti_banant);
                db.majomHozzaadasa(majom);
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
            } else {
                Majom majom = new Majom(kivalasztottMajomId, fajta, max_iq, szereti_banant);
                if (db.majomModositasa(majom)) {
                    alert(Alert.AlertType.WARNING, "Sikeres módosítás", "");
                } else {
                    alert(Alert.AlertType.WARNING, "Sikertelen módosítás", "");
                }
            }
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
        elkuldButton.setText("Elküld");

        majmokTablazat.setDisable(false);
        modositButton.setDisable(false);
        torlesButton.setDisable(false);
    }

    @FXML
    public void modositClick(ActionEvent actionEvent) {
        Majom kivalasztottMajom = getKivalasztottMajom();
        if (kivalasztottMajom == null) return;

        kivalasztottMajomId = kivalasztottMajom.getId();
        fajtaInput.setText(kivalasztottMajom.getFajta());
        max_iqInput.getValueFactory().setValue(kivalasztottMajom.getMax_iq());
        szereti_banantCheckbox.setSelected(kivalasztottMajom.isSzereti_banant());
        elkuldButton.setText("Módosít");
        majmokTablazat.setDisable(true);
        modositButton.setDisable(true);
        torlesButton.setDisable(true);
    }

    @FXML
    public void torlesClick(ActionEvent actionEvent) {
        Majom kivalasztottMajom = getKivalasztottMajom();
        if (kivalasztottMajom == null) return;

        Optional<ButtonType> felhasznaloValasztasa = alert(Alert.AlertType.CONFIRMATION,
                "Biztos szeretné törölni az alábbi majmot?", kivalasztottMajom.getFajta());
        if (felhasznaloValasztasa.isEmpty() ||
                (!felhasznaloValasztasa.get().equals(ButtonType.OK) &&
                !felhasznaloValasztasa.get().equals(ButtonType.YES) )) {
            return;
        }

        try {
            if (db.majomTorlese(kivalasztottMajom)) {
                alert(Alert.AlertType.WARNING, "Sikeres törlés", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen törlés", "");
            }
            majmokListazasa();
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Adatbazis hiba", e.getMessage());
        }
    }

    private Majom getKivalasztottMajom() {
        int selectedIndex = majmokTablazat.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert(Alert.AlertType.WARNING, "Előbb válasszon a táblázatból", "");
            return null;
        }
        return majmokTablazat.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void megseClick(ActionEvent actionEvent) {
        urlapAlaphelyzetbe();
    }
}