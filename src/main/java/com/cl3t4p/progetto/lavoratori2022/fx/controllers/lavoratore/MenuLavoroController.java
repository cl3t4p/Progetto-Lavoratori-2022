package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoro;
import com.cl3t4p.progetto.lavoratori2022.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.ButtonColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.NumberField;
import com.cl3t4p.progetto.lavoratori2022.fx.components.TableData;
import com.cl3t4p.progetto.lavoratori2022.model.LavoroRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuLavoroController implements Initializable {


    private final LavoroRepo lavoroRepo = Main.getRepo().getLavoroRepo();

    private final DataRepo dataRepo = Main.getDataRepo();
    @FXML
    private TableData lav_view;

    @FXML
    private DatePicker ini_per, fine_per;

    @FXML
    private TableColumn<Map, String> col_nome, col_mansione, col_luogo, col_retri, col_ini, col_fine, col_id;

    @FXML
    private Label lav_id;

    @FXML
    private TextField nome, luogo, mansione;

    @FXML
    private NumberField retri;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (dataRepo.getLavoratore_id() != null) {
            lav_id.setText(lav_id.getText() + dataRepo.getLavoratore_id());
            lav_id.setVisible(true);
        }

        ButtonColumn buttonColumn = new ButtonColumn("", (key) -> {
            if (!lavoroRepo.deleteLavoroByID(Integer.valueOf(key.get("id"))))
                JavaFXError.DB_ERROR.printContent("Errore nella cancellazione dell'lavoro");
            lav_view.refreshData();
            return null;
        });


        //tableData = new TableData(lav_view,buttonColumn,()-> TableData.toMap(postDriver.getLavoroByLavID(dataRepo.getLavoratore_id())));

        lav_view.setSupplier(() -> TableData.toMap(lavoroRepo.getLavoroByLavID(dataRepo.getLavoratore_id())));
        lav_view.setButtonColumn(buttonColumn);

        lav_view.setupColumn(col_id, "id", 30);
        lav_view.setupColumn(col_nome, "nome_azienda");
        lav_view.setupColumn(col_mansione, "mansione");
        lav_view.setupColumn(col_luogo, "luogo");
        lav_view.setupColumn(col_retri, "retribuzione");
        lav_view.setupColumn(col_ini, "inizio_periodo");
        lav_view.setupColumn(col_fine, "fine_periodo");
    }


    public void back(ActionEvent event) {
        Main.getDataRepo().setLavoratore_id(null);
        Main.getLoader().loadView("MENU");
    }

    public void addLavoro(ActionEvent event) {
        try {
            Lavoro lavoro = getLavoro();
            LocalDate start = LocalDate.ofEpochDay(lavoro.getFine_periodo().getTime());
            LocalDate now = LocalDate.now();
            if (Period.between(start, now).getYears() > 5) {
                JavaFXError.INVALID_DATE.printContent("La data di fine periodo deve essere inferiore ai 5 anni");
                return;
            }
            if (lavoroRepo.addLavoro(getLavoro()) == -1) {
                throw new JavaFXDataError();
            }
        } catch (SQLException | JavaFXDataError e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.printContent("Errore nell'inserimento del lavoro");
        }
        lav_view.refreshData();
    }


    private Lavoro getLavoro() {
        Lavoro lavoro = new Lavoro();
        lavoro.setLuogo(luogo.getText());
        lavoro.setMansione(mansione.getText());
        lavoro.setNome_azienda(nome.getText());
        lavoro.setRetribuzione(retri.getValue().intValue());
        lavoro.setInizio_periodo(Date.valueOf(ini_per.getValue()));
        lavoro.setFine_periodo(Date.valueOf(fine_per.getValue()));
        lavoro.setId_lavoratore(dataRepo.getLavoratore_id());
        return lavoro;
    }
}