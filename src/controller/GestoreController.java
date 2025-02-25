package controller;

import dao.MostraCorseNonRiscosseDAO;
import dao.ReportTassistiProcedureDAO;
import dao.RiscuotiCommissioneProcedureDAO;
import exceptions.DAOException;
import model.Corsa;
import model.CorsaList;
import model.TassistaReportList;
import session.SessionManager;
import view.GestoreView;
import connection.ConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;

public class GestoreController implements Controller {

    @Override
    public void start() {
        while (true) {
            int choice = GestoreView.showMenu();

            switch (choice) {
                case 1:
                    visualizzaCorseNonRiscosse();
                    break;
                case 2:
                    generaReportTassisti();
                    break;
                case 3:
                    logout();
                    return;
                default:
                    GestoreView.displayMessage("Scelta non valida. Riprova.");
            }
        }
    }

    // Metodo per visualizzare le corse non riscosse
    private void visualizzaCorseNonRiscosse() {
        MostraCorseNonRiscosseDAO mostraCorseDAO = new MostraCorseNonRiscosseDAO();

        try {
            // Ottieni la lista delle corse non riscosse dal database
            CorsaList corseNonRiscosse = mostraCorseDAO.execute(null);

            if (corseNonRiscosse.isEmpty()) {
                GestoreView.displayMessage("Non ci sono corse non riscosse.");
                return; // Torna al menu principale
            }

            // Mostra le corse non riscosse all'utente
            GestoreView.showCorseNonRiscosse(corseNonRiscosse);

            // Chiede all'utente se vuole riscuotere una corsa o tornare al menu
            int idCorsaSelezionata = GestoreView.chiediIdCorsa();

            if (idCorsaSelezionata <= 0) {
                GestoreView.displayMessage("Operazione annullata. Ritorno al menu.");
                return;
            }

            Corsa corsa = corseNonRiscosse.getCorsaById(idCorsaSelezionata);
            if (corsa == null) {
                GestoreView.displayMessage("Errore: corsa non trovata.");
                return;
            }

            // Chiedi conferma prima della riscossione
            boolean confermato = GestoreView.confermaRiscossione(idCorsaSelezionata);
            if (!confermato) {
                GestoreView.displayMessage("Operazione annullata. Ritorno al menu.");
                return;
            }

            riscuotiCommissione(corsa);

        } catch (DAOException e) {
            GestoreView.displayMessage("Errore nel recupero delle corse: " + e.getMessage());
        }
    }

    // Metodo per riscattare una commissione per una corsa
    private void riscuotiCommissione(Corsa corsa) {
        RiscuotiCommissioneProcedureDAO riscuotiCommissioneDAO = new RiscuotiCommissioneProcedureDAO();

        try {
            String esito = riscuotiCommissioneDAO.execute(corsa);
            GestoreView.displayMessage(esito);
        } catch ( DAOException e) {
            GestoreView.displayMessage("Errore nella riscossione della commissione: " + e.getMessage());
        }
    }

    private void generaReportTassisti() {
        ReportTassistiProcedureDAO reportTassistiDAO = new ReportTassistiProcedureDAO(); // Crea l'istanza del DAO

        try {
            // Ottieni il report dei tassisti dal DAO
            TassistaReportList tassistaReportList = reportTassistiDAO.execute(null);

            if (tassistaReportList.isEmpty()) {
                GestoreView.displayMessage("Non ci sono dati per i tassisti.");
                return;
            }

            // Passa la lista alla View per la visualizzazione
            GestoreView.showReportTassisti(tassistaReportList);

        } catch (DAOException e) {
            GestoreView.displayMessage("Errore nel recupero dei report dei tassisti: " + e.getMessage());
        }
    }

    // Metodo per effettuare il logout
    private void logout() {
        try {
            SessionManager.logout();
            ConnectionFactory.logout();
            GestoreView.displayMessage("Logout effettuato con successo!");
        } catch (SQLException | IOException e) {
            GestoreView.displayMessage("Errore durante il logout: " + e.getMessage());
        }
    }
}
