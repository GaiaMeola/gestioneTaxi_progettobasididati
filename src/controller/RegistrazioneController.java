package controller;

import dao.RegistrazioneClienteProcedureDAO;
import dao.RegistrazioneTassistaProcedureDAO;
import exceptions.DAOException;
import model.Cliente;
import model.Credenziali;
import model.Tassista;
import view.ClienteView;
import view.HomeView;
import view.TassistaView;

import java.io.IOException;

public class RegistrazioneController implements Controller {

    private Credenziali credenziali;

    @Override
    public void start() {
        boolean registered = false;
        while (!registered) {
            int choice;
            try {
                choice = HomeView.showRegistrationChoice();
            } catch (IOException e) {
                throw new RuntimeException("Errore durante la visualizzazione del menu di registrazione", e);
            }
            switch (choice) {
                case 1:
                    // Registrazione come Cliente
                   HomeView.displayMessage("Registrazione come Cliente...");
                    registraCliente();
                    registered = true;
                    break;
                case 2:
                    // Registrazione come Tassista
                    HomeView.displayMessage("Registrazione come Tassista...");
                    registraTassista();
                    registered = true;
                    break;
                default:
                    HomeView.displayMessage("Scelta non valida, riprova.");
                    break;
            }
        }
    }

    public void registraCliente() {
        Cliente cliente;
        try {
            cliente = ClienteView.datiRegistrazione();
        } catch (IOException e) {
            ClienteView.displayMessage("Errore durante la lettura dei dati del cliente. Riprova.");
            return;
        }

        try {
            String exitus = new RegistrazioneClienteProcedureDAO().execute(cliente);
            ClienteView.displayMessage(exitus);
        } catch (DAOException e) {
            cliente = null;
            ClienteView.displayMessage(e.getMessage());
        }
        // Salva le credenziali per effettuare il login
        credenziali = cliente;
    }

    public void registraTassista() {
        Tassista tassista;
        try {
            tassista = TassistaView.datiRegistrazione();
        } catch (IOException e) {
            TassistaView.displayMessage("Errore durante la lettura dei dati del tassista. Riprova.");
            return;
        }

        try {
            String exitus = new RegistrazioneTassistaProcedureDAO().execute(tassista);
            TassistaView.displayMessage(exitus);
        } catch (DAOException e) {
            tassista = null;
            TassistaView.displayMessage(e.getMessage());
        }
        // Salva le credenziali per effettuare il login
        credenziali = tassista;
    }

    public Credenziali getCredentials() {
        return credenziali;
    }
}
