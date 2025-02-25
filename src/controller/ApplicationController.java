package controller;

import connection.ConnectionFactory;
import model.Credenziali;
import model.Ruolo;
import session.SessionManager;
import view.HomeView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationController implements Controller {

    @Override
    public void start() {
        boolean running = true;

        while (running) {
            int choice = HomeView.showMenu();

            switch (choice) {
                case 1:  // Login
                    handleLogin();
                    break;

                case 2:  // Registrazione
                    handleRegistration();
                    break;

                case 3:  // Esci
                    HomeView.showExitMessage();
                    running = false;
                    break;

                default:
                    HomeView.showInvalidChoiceMessage();
            }
        }

        closeConnection();
    }

    private void handleLogin() {
        LoginController loginController = new LoginController();
        loginController.start();
        Credenziali cred = loginController.getCredentials();

        if (cred != null) {
            // Imposta le credenziali nel SessionManager e cambia ruolo
            SessionManager.login(cred);
            if (changeRole(cred.getRole())) return;

            // Gestisci il controller in base al ruolo
            switch (cred.getRole()) {
                case CLIENTE:
                    new ClienteController().start();
                    break;
                case TASSISTA:
                    new TassistaController().start();
                    break;
                case GESTORE:
                    new GestoreController().start();
                    break;
                default:
                    HomeView.showErrorMessage("Ruolo non valido.");
                    break;
            }
        } else {
            ApplicationController controller = new ApplicationController();
            controller.start();
        }
    }

    private void handleRegistration() {
        RegistrazioneController registrazioneController = new RegistrazioneController();
        registrazioneController.start();

        Credenziali newUser = registrazioneController.getCredentials();

        if (newUser != null) {
            // Cambia ruolo prima di procedere con il login
            if (changeRole(newUser.getRole())) return;

            if (newUser.getRole() == Ruolo.CLIENTE || newUser.getRole() == Ruolo.TASSISTA) {
                LoginController loginAfterRegistration = new LoginController();
                loginAfterRegistration.start();
                Credenziali loggedInCred = loginAfterRegistration.getCredentials();

                if (loggedInCred != null) {
                    // Login dopo la registrazione
                    SessionManager.login(loggedInCred);

                    // Cambia ruolo
                    if (changeRole(loggedInCred.getRole())) return;

                    // Gestisci il controller in base al ruolo
                    switch (loggedInCred.getRole()) {
                        case CLIENTE:
                            new ClienteController().start();
                            break;
                        case TASSISTA:
                            new TassistaController().start();
                            break;
                        default:
                            HomeView.showErrorMessage("Ruolo non valido.");
                    }
                }
            } else {
                HomeView.showErrorMessage("Registrazione non valida.");
            }
        } else {
            ApplicationController controller = new ApplicationController();
            controller.start();
        }
    }

    private boolean changeRole(Ruolo role) {
        try {
            System.out.println("🔄 Tentativo di cambio ruolo a: " + role);

            // Cambia ruolo e forza la creazione di una nuova istanza
            ConnectionFactory.changeRole(role);
            System.out.println("✅ Connessione aggiornata con il ruolo: " + role);

            try (Statement stmt = ConnectionFactory.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT CURRENT_USER();")) {
                if (rs.next()) {
                    System.out.println("🔍 Utente autenticato su MySQL dopo cambio ruolo: " + rs.getString(1));
                }
            }


            return false;
        } catch (SQLException | IOException e) {
            HomeView.showErrorMessage("❌ Errore durante il cambio di ruolo: " + e.getMessage());
            return true;
        }
    }


    private void closeConnection() {
        try {
            Connection connection = ConnectionFactory.getConnection();
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Connessione chiusa con successo.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la chiusura della connessione", e);
        }
    }

}
