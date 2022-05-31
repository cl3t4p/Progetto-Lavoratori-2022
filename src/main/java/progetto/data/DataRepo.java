package progetto.data;

import progetto.Dipendente;
import progetto.Lavoratore;

public class DataRepo {

    public DataRepo() {
    }

    Dipendente dipendente;

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
}
