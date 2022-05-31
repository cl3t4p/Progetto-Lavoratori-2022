package progetto;


import progetto.data.CheckData;
import progetto.data.FieldChecker;
import progetto.database.annotation.SQLDInfo;

import java.sql.SQLException;
import java.util.Date;

//@Data
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lavoro implements CheckData {

    public Lavoro() {
    }

    int id;
    Date inizio_periodo;
    Date fine_periodo;
    String nome_azienda;
    @SQLDInfo(sql_name = "mansione_svolta")
    String mansione;
    @FieldChecker
    @SQLDInfo(sql_name = "luogo_lavoro")
    String luogo;
    @SQLDInfo(sql_name = "retribuzione_lorda_giornaliera")
    int retribuzione;


    public boolean validate() {
        if(!CheckData.super.validate())
            return false;
        try {
            if(Main.getPostDriver().getComuniByName(luogo) == null)
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getInizio_periodo() {
        return inizio_periodo;
    }

    public void setInizio_periodo(Date inizio_periodo) {
        this.inizio_periodo = inizio_periodo;
    }

    public Date getFine_periodo() {
        return fine_periodo;
    }

    public void setFine_periodo(Date fine_periodo) {
        this.fine_periodo = fine_periodo;
    }

    public String getNome_azienda() {
        return nome_azienda;
    }

    public void setNome_azienda(String nome_azienda) {
        this.nome_azienda = nome_azienda;
    }

    public String getMansione() {
        return mansione;
    }

    public void setMansione(String mansione) {
        this.mansione = mansione;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getRetribuzione() {
        return retribuzione;
    }

    public void setRetribuzione(int retribuzione) {
        this.retribuzione = retribuzione;
    }
}
