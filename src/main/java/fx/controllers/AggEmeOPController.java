package fx.controllers;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.checks.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.data.type.Emergenza;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import fx.components.ButtonColumn;
import fx.components.NumberField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AggEmeOPController implements Initializable {


    private final PostDriver postDriver = Main.getPostDriver();
    private final ObservableList<Map<String, String>> emergenze_list = FXCollections.observableArrayList();
    @FXML
    private TableView<Map<String, String>> eme_view;
    @FXML
    private Label lav_id;
    @FXML
    private TableColumn<Map, String> col_nome, col_cognome, col_telefono, col_email;
    @FXML
    private TextField em_nome, em_cognome, em_email;
    @FXML
    private NumberField em_telefono;
    private int lavoratore_id;


    @Override
    public void initialize(URL url, ResourceBundle resource) {
        lavoratore_id = Main.getDataRepo().getLavoratore_id();
        lav_id.setText(lav_id.getText() + lavoratore_id);
        setupEmergenze();

        ButtonColumn<Integer, Map<String, String>> buttonColumn = new ButtonColumn<>("", lavoratore_id, ((lav_id, key) -> {
            postDriver.delEmergenzeByID(lav_id, key);
            refreshEmergenze();
        }), "Deve esistere almeno un contatto di emergenza");

        setupColumn(col_nome, "nome", buttonColumn);
        setupColumn(col_cognome, "cognome", buttonColumn);
        setupColumn(col_email, "email", buttonColumn);
        setupColumn(col_telefono, "telefono", buttonColumn);

        eme_view.getColumns().add(buttonColumn);

    }

    private void setupColumn(TableColumn<Map, String> column, String name, ButtonColumn buttonColumn) {
        column.setEditable(false);
        column.setResizable(false);
        column.setReorderable(false);
        column.setCellValueFactory(new MapValueFactory<>(name));

        column.prefWidthProperty().bind(eme_view.widthProperty().subtract(buttonColumn.widthProperty()).subtract(2).divide(4));
    }

    private void refreshEmergenze() throws SQLException {
        emergenze_list.clear();
        emergenze_list.addAll(postDriver.getEmergenze(lavoratore_id).stream().map(Emergenza::toMap).collect(Collectors.toSet()));
    }

    private void setupEmergenze() {
        try {
            refreshEmergenze();
            eme_view.setItems(emergenze_list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addEmergenza(ActionEvent event) {
        try {
            Emergenza emergenza = getEmergenza();
            if (!postDriver.addEmergenza(emergenza, lavoratore_id)) throw new JavaFXDataError("Il contatto esiste già");
            refreshEmergenze();
        } catch (JavaFXDataError e) {
            e.printFX();
        } catch (SQLException e) {
            JavaFXError.DB_ERROR.fxMSG();
        }

    }

    private Emergenza getEmergenza() throws JavaFXDataError {
        Emergenza emergenza = new Emergenza();
        emergenza.setNome(em_nome.getText());
        emergenza.setCognome(em_cognome.getText());
        if (!RegexChecker.EMAIL.validate(em_email.getText())) {
            throw new JavaFXDataError("Email emergenza non valida!");
        }
        emergenza.setEmail(em_email.getText());

        if (em_telefono.getValue() == null || !RegexChecker.TEL_NUMBER.validate(em_telefono.getText())) {
            throw new JavaFXDataError("Telefono emergenza non valido!");
        }
        emergenza.setTelefono(em_telefono.getValue());
        if (!emergenza.validate()) {
            throw new JavaFXDataError("Campi emergenza non compilati o errati");
        }
        return emergenza;
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU_LAVORATORE");
    }
}
