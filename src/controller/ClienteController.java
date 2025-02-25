package controller;

import connection.ConnectionFactory;
import dao.OttieniNumeroTelefonoProcedureDAO;
import dao.RichiediCorsaProcedureDAO;
import exceptions.DAOException;
import model.Richiesta;
import session.SessionManager;
import view.ClienteView;

import java.io.IOException;
import java.sql.SQLException;


public class ClienteController implements Controller {

    @Override
    public void start() {

        while (true) {
            int choice;
            choice = ClienteView.showMenu();

            switch (choice) {
                case 1:
                    // Richiedere una corsa
                    richiediCorsa();
                    break;
                case 2:
                    // Logout
                    try {
                        SessionManager.logout();
                        ConnectionFactory.logout();  // Esegui il logout
                        ClienteView.displayMessage("Logout effettuato con successo!");
                        return;  // Torna al menu principale o esci dal programma
                    } catch (SQLException | IOException e) {
                        ClienteView.displayMessage("Errore durante il logout: " + e.getMessage());
                    }
                    return;
                default:
                    break;
            }
        }
    }

    private void richiediCorsa() {

        // procedura per richiedere una corsa
        Richiesta richiesta;
        String numeroTelefono;

        String usernameCliente = SessionManager.getUsername(); // recuperiamo lo username dalla sessione

        // Recuperiamo il numero di telefono usando la stored procedure
        try {
            OttieniNumeroTelefonoProcedureDAO telefonoDAO = new OttieniNumeroTelefonoProcedureDAO();
            numeroTelefono = telefonoDAO.execute(usernameCliente); // recupera il numero di telefono tramite username
        } catch (DAOException e) {
            ClienteView.displayMessage("Errore durante il recupero del numero di telefono.");
            return; // se c'è un errore nel recupero del numero, interrompiamo l'operazione
        }

        // Passiamo il numero di telefono alla view invece dello username
        try {
            richiesta = ClienteView.richiediCorsa(numeroTelefono);  // Passiamo il numero di telefono alla view
        } catch (IOException e) {
            ClienteView.displayMessage("Errore durante l'acquisizione dell'input per la richiesta di corsa.");
            return;
        }

        // Inviamo la richiesta di corsa al DAO
        try {
            String exitus;
            exitus = new RichiediCorsaProcedureDAO().execute(richiesta);  // Eseguiamo la stored procedure per registrare la corsa
            ClienteView.displayMessage(exitus);  // Mostriamo il risultato
        } catch (DAOException e) {
            ClienteView.displayMessage("Errore durante l'elaborazione della richiesta di corsa: " + e.getMessage());
        }
    }
}
