package view;

import model.Corsa;
import model.CorsaList;
import model.TassistaReport;
import model.TassistaReportList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GestoreView {

    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int showMenu() {
        int choice = -1;

        while (choice < 1 || choice > 3) {
            displayMessage("\n--- Benvenuto gestore nel sistema di gestione taxi ---");
            displayMessage("1. Visualizza corse non riscosse");
            displayMessage("2. Genera report sui tassisti");
            displayMessage("3. Logout");
            displayMessage("Seleziona un'opzione: ");

            try {
                choice = Integer.parseInt(reader.readLine().trim());

                if (choice < 1 || choice > 3) {
                    displayMessage("Opzione non valida. Per favore, seleziona un numero da 1 a 3.");
                }

            } catch (NumberFormatException e) {
                displayMessage("Input non valido. Inserisci un numero intero.");
            } catch (IOException e) {
                displayMessage("Errore nella lettura dell'input. Riprova.");
            }
        }
        return choice;
    }

    public static void showCorseNonRiscosse(CorsaList corsaList) {
        displayMessage("\n--- Elenco corse non riscosse ---");

        if (corsaList.isEmpty()) {
            displayMessage("Non ci sono corse non riscosse.");
            return;
        }

        for (Corsa corsa : corsaList.getCorseNonRiscosse()) {
            displayMessage("Codice: " + corsa.getIdRichiesta() + " | Importo: " + corsa.getImporto() + "€");
        }

        displayMessage("\nSeleziona il codice della corsa da riscuotere (0 per tornare al menu): ");
    }

    public static int chiediIdCorsa() {
        int idScelto;

        try {
            idScelto = Integer.parseInt(reader.readLine().trim());

            if (idScelto == 0) {
                return -1; // Segnale per annullare l'operazione
            }

            return idScelto;

        } catch (NumberFormatException | IOException e) {
            displayMessage("Errore nell'inserimento del codice. Ritorno al menu.");
            return -1;
        }
    }

    public static boolean confermaRiscossione(int idCorsa) {
        displayMessage("Sei sicuro di voler riscattare la corsa con codice " + idCorsa + "?");
        displayMessage("1. Sì\n2. No\nScegli un'opzione: ");

        try {
            int scelta = Integer.parseInt(reader.readLine().trim());
            return scelta == 1;

        } catch (IOException | NumberFormatException e) {
            displayMessage("Errore nell'input. Operazione annullata.");
            return false;
        }
    }

    public static void displayMessage(String s) {
        System.out.println(s);
    }

    public static void showReportTassisti(TassistaReportList tassistaReportList) {
        displayMessage("\n--- Report Tassisti ---");

        if (tassistaReportList.isEmpty()) {
            displayMessage("Non ci sono dati per i tassisti.");
            return;
        }

        // Itera sulla lista dei report e stampa le informazioni per ogni tassista
        for (TassistaReport report : tassistaReportList.getReportList()) {
            displayMessage("Numero di Patente: " + report.numeroDiPatente() +
                    " | Nome: " + report.nome() +
                    " | Cognome: " + report.cognome() +
                    " | Numero Corse: " + report.numeroCorse() +
                    " | Guadagno Totale: " + report.guadagnoTotale() + "€" +
                    " | Commissione Totale: " + report.commissioneTotale() + "€");
        }

        // Chiedi se l'utente vuole visualizzare il dettaglio di un tassista
        String numeroDiPatenteScelto = chiediNumeroDiPatente();

        if (numeroDiPatenteScelto != null && !numeroDiPatenteScelto.isEmpty()) {
            // Mostra i dettagli del tassista
            TassistaReport report = tassistaReportList.getReportByNumeroDiPatente(numeroDiPatenteScelto);
            if (report != null) {
                showDettagliTassista(report);
            } else {
                displayMessage("Tassista con numero di patente " + numeroDiPatenteScelto + " non trovato.");
            }
        }

        displayMessage("\nFine del report.");
    }

    // Metodo per chiedere il numero di patente (ora restituisce una String)
    public static String chiediNumeroDiPatente() {
        String numeroDiPatente = null;

        try {
            displayMessage("\nInserisci il numero di patente del tassista per vedere i dettagli (0 per tornare al menu): ");
            numeroDiPatente = reader.readLine().trim();

            if (numeroDiPatente.equals("0")) {
                return null; // Segnale per tornare al menu
            }

        } catch (IOException e) {
            displayMessage("Errore nell'inserimento del numero di patente. Ritorno al menu.");
        }

        return numeroDiPatente;
    }

    // Metodo per mostrare i dettagli di un tassista
    public static void showDettagliTassista(TassistaReport report) {
        displayMessage("\n--- Dettaglio Tassista ---");
        displayMessage("Numero di Patente: " + report.numeroDiPatente());
        displayMessage("Nome: " + report.nome());
        displayMessage("Cognome: " + report.cognome());
        displayMessage("Numero di Corse: " + report.numeroCorse());
        displayMessage("Guadagno Totale: " + report.guadagnoTotale() + "€");
        displayMessage("Commissione Totale: " + report.commissioneTotale() + "€");
    }
}
