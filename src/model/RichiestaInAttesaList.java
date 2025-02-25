package model;

import java.util.ArrayList;
import java.util.List;

public class RichiestaInAttesaList {

    private final List<Richiesta> richiesteInAttesa;

    // Costruttore
    public RichiestaInAttesaList() {
        this.richiesteInAttesa = new ArrayList<>();
    }

    // Metodo per aggiungere una richiesta alla lista
    public void aggiungiRichiesta(Richiesta richiesta) {
        richiesteInAttesa.add(richiesta);
    }

    // Metodo per ottenere tutte le richieste in attesa
    public List<Richiesta> getRichiesteInAttesa() {
        return richiesteInAttesa;
    }

    // Metodo per ottenere una richiesta specifica (basato su ID)
    public Richiesta getRichiestaById(int id) {
        for (Richiesta richiesta : richiesteInAttesa) {
            if (richiesta.getId() == id) {
                return richiesta;
            }
        }
        return null; // Se non trovata
    }

    // Metodo per ottenere il numero totale delle richieste in attesa
    public int getNumeroRichiesteInAttesa() {
        return richiesteInAttesa.size();
    }

    public boolean isEmpty() {
        return richiesteInAttesa.isEmpty();  // Restituisce true se la lista è vuota, false altrimenti
    }
}
