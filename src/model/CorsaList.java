package model;

import java.util.ArrayList;
import java.util.List;

public class CorsaList {

    private final List<Corsa> corseNonRiscosse;

    public CorsaList() {
        this.corseNonRiscosse = new ArrayList<>();
    }

    public void aggiungiCorsa(Corsa corsa) {
        corseNonRiscosse.add(corsa);
    }

    public List<Corsa> getCorseNonRiscosse() {
        return corseNonRiscosse;
    }

    public Corsa getCorsaById(int id) {
        for (Corsa corsa : corseNonRiscosse) {
            if (corsa.getIdRichiesta() == id) {
                return corsa;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return corseNonRiscosse.isEmpty();
    }
}
