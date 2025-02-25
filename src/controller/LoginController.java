package controller;

import dao.LoginProcedureDAO;
import exceptions.DAOException;
import model.Credenziali;
import view.LoginView;

import java.io.IOException;

public class LoginController implements Controller {

    Credenziali credenziali;

    @Override
    public void start() {
        try {
            credenziali = LoginView.authenticate();
        } catch (IOException e) {
            LoginView.displayMessage("Errore durante la lettura delle credenziali. Riprova.");
        }

        try {
            // Esegui la stored procedure per autenticare l'utente
            credenziali = new LoginProcedureDAO().execute(credenziali);
        } catch (DAOException e) {
            // Gestione dell'errore se le credenziali non sono valide
            credenziali = null;
            LoginView.displayMessage("Credenziali non valide. Riprova");
        } catch (Exception e) {
            credenziali = null;
            // Gestione di altre eccezioni generali
            LoginView.displayMessage("Si è verificato un errore imprevisto. Riprova più tardi.");
        }
    }

    public Credenziali getCredentials() {
        return credenziali;
    }
}
