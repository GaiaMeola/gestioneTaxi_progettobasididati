package controller;

import connection.ConnectionFactory;
import dao.*;
import exceptions.DAOException;
import model.Corsa;
import model.Richiesta;
import model.RichiestaInAttesaList;
import session.SessionManager;
import view.TassistaView;

import java.io.IOException;
import java.sql.SQLException;


public class TassistaController implements Controller{
    @Override
    public void start() {

        while (true) {
            int choice;
            choice = TassistaView.showMenu();

            switch (choice) {
                case 1:
                    // Visualizza corse attive
                    try {
                        visualizzaCorseAttive();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    // Termina corsa
                    terminaCorsa();
                    break;
                case 3:
                    // Logout
                    try {
                        SessionManager.logout();
                        ConnectionFactory.logout();  // Esegui il logout
                        TassistaView.displayMessage("Logout effettuato con successo!");
                        return;  // Torna al menu principale o esci dal programma
                    } catch (SQLException | IOException e) {
                        TassistaView.displayMessage("Errore durante il logout: " + e.getMessage());
                    }
                    return;
                default:
                    break;
            }
        }
    }

    private void visualizzaCorseAttive() throws SQLException {
        TassistaView.displayMessage("Visualizza richieste di corsa in attesa...");

        try {
            // Chiamata alla DAO per ottenere le richieste in attesa
            VisualizzaRichiesteInAttesaProcedureDAO richiestaDAO = new VisualizzaRichiesteInAttesaProcedureDAO();
            RichiestaInAttesaList listaRichieste = richiestaDAO.execute(null);

            // Passa la lista alla view per la visualizzazione
            boolean accettata = TassistaView.mostraRichiesteEAccetta(listaRichieste);

            if (accettata) {
                int idRichiesta = TassistaView.chiediCodiceCorsa();  // La View si occupa di chiedere l'ID della richiesta da accettare

                // Verifica che l'ID della corsa sia valido (non negativo)
                if (idRichiesta <= 0) {
                    TassistaView.displayMessage("Codice corsa non valido.");
                    return;
                }

                // Ora otteniamo la richiesta specifica tramite l'ID
                Richiesta richiesta = listaRichieste.getRichiestaById(idRichiesta);

                if (richiesta != null) {
                    accettaCorsa(richiesta);  // Passiamo l'intero oggetto richiesta
                } else {
                    TassistaView.displayMessage("Corsa non trovata.");
                }
            } else {
                TassistaView.displayMessage("Tornando al menu principale...");
            }

        } catch (DAOException e) {
            TassistaView.displayMessage("Errore durante la visualizzazione delle corse attive: " + e.getMessage());
        }
    }

    private void accettaCorsa(Richiesta richiesta) throws SQLException {
        TassistaView.displayMessage("Accettazione in corso...");

        String usernameTassista = SessionManager.getUsername();  // Recuperiamo lo username del tassista dalla sessione
        String numeroPatente;

        // Recuperiamo il numero di patente tramite la DAO
        try {
            OttieniNumeroPatenteProcedureDAO patenteDAO = new OttieniNumeroPatenteProcedureDAO();
            numeroPatente = patenteDAO.execute(usernameTassista);  // Otteniamo il numero di patente tramite username

            // Impostiamo il numero di patente nell'oggetto richiesta
            richiesta.setNumeroTassista(numeroPatente);  // Impostiamo la patente del tassista nella richiesta

        } catch (DAOException e) {
            TassistaView.displayMessage("Errore durante il recupero del numero di patente.");
            return;  // Se c'è un errore nel recupero della patente, interrompiamo l'operazione
        }

        // Ora chiamiamo la stored procedure per accettare la corsa
        try {
            AccettaRichiestaProcedureDAO accettaDAO = new AccettaRichiestaProcedureDAO();
            String esito = accettaDAO.execute(richiesta);  // Passiamo l'oggetto richiesta completo
            TassistaView.displayMessage(esito);  // Mostriamo il risultato dell'operazione
        } catch (DAOException e) {
            TassistaView.displayMessage("Errore durante l'elaborazione dell'accettazione della corsa. Riprovare più tardi.");
        }
    }

    private void terminaCorsa() {
        TassistaView.displayMessage("Terminazione in corso...");

        String usernameTassista = SessionManager.getUsername();  // Recuperiamo lo username del tassista dalla sessione
        String numeroPatente;

        // Recuperiamo il numero di patente tramite la DAO
        try {
            OttieniNumeroPatenteProcedureDAO patenteDAO = new OttieniNumeroPatenteProcedureDAO();
            numeroPatente = patenteDAO.execute(usernameTassista);  // Otteniamo il numero di patente tramite username

        } catch (DAOException e) {
            TassistaView.displayMessage("Errore durante il recupero del numero di patente.");
            return;  // Se c'è un errore nel recupero della patente, interrompiamo l'operazione
        }

        // Recupera l'ID della corsa attuale per il tassista
        try {
            // Chiamata alla DAO per trovare la corsa attuale
            TrovaCorsaAttualeProcedureDAO trovaCorsaDAO = new TrovaCorsaAttualeProcedureDAO();
            Corsa corsaAttuale = trovaCorsaDAO.execute(numeroPatente);  // Recuperiamo la corsa attuale

            // Se non esiste una corsa attiva, interrompiamo
            if (corsaAttuale == null) {
                TassistaView.displayMessage("Nessuna corsa attiva trovata.");
                return;
            }

            // Chiediamo al tassista di inserire l'importo per terminare la corsa
            double importo = TassistaView.chiediImportoCorsa();

            // Impostiamo l'importo nella corsa
            corsaAttuale.setImporto(importo);

            // Ora chiamiamo la DAO per terminare la corsa passando l'oggetto corsa aggiornato
            TerminaCorsaProcedureDAO terminaCorsaDAO = new TerminaCorsaProcedureDAO();
            String esito = terminaCorsaDAO.execute(corsaAttuale);

            // Mostriamo il risultato della terminazione della corsa
            TassistaView.displayMessage(esito);

        } catch ( DAOException e) {
            TassistaView.displayMessage("Errore durante la terminazione della corsa: " + e.getMessage());
        }
    }
}

