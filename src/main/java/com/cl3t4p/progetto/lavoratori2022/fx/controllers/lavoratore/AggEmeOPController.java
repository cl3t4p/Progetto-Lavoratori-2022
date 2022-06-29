package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.TableData;
import com.cl3t4p.progetto.lavoratori2022.data.checks.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.data.type.Emergenza;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.NumberField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class AggEmeOPController implements Initializable {



    private final PostDriver postDriver = Main.getPostDriver();
    @FXML
    private TableData eme_view;
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


        ButtonColumn buttonColumn = new ButtonColumn("", (key -> {
            if(!postDriver.delEmergenzeByID(lavoratore_id,key))
                JavaFXError.DB_ERROR.printContent("Errore nella cancellazione dell'emergenza");
            eme_view.refreshData();
            return null;
        }));
        buttonColumn.setMsgError("Deve esistere almeno un contatto di emergenza");

        //tableData = new TableData(eme_view, buttonColumn,()-> TableData.toMap(postDriver.getEmergenze(lavoratore_id)));
        eme_view.setSupplier(()-> TableData.toMap(postDriver.getEmergenze(lavoratore_id)));
        eme_view.setButtonColumn(buttonColumn);

        eme_view.setupColumn(col_nome, "nome");
        eme_view.setupColumn(col_cognome, "cognome");
        eme_view.setupColumn(col_telefono, "telefono");
        eme_view.setupColumn(col_email, "email");
        eme_view.refreshData();

    }



    public void addEmergenza(ActionEvent event) {
        try {
            Emergenza emergenza = getEmergenza();
            if (!postDriver.addEmergenza(emergenza, lavoratore_id)) throw new JavaFXDataError("Il contatto esiste già");
            eme_view.refreshData();
        } catch (JavaFXDataError e) {
            e.printFX();
        } catch (SQLException e) {
            JavaFXError.DB_ERROR.show();
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
        Main.getLoader().loadView("MODIFICA_AGG_LAVORATORE");
    }
}
