package progetto.data;

import progetto.Dipendente;
import progetto.Lavoratore;

public class DataRepo {


    Dipendente dipendente;
    int lavoratore_id;

    public DataRepo() {
    }


    public int getLavoratore_id() {
        //TODO return lavoratore_id
        return 58;
    }

    public void setLavoratore_id(int lavoratore_id) {
        this.lavoratore_id = lavoratore_id;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
}
