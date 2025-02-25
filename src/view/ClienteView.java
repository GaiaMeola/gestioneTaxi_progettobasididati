package view;

import model.Cliente;
import model.Richiesta;
import model.Ruolo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClienteView {

    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int showMenu() {
        int choice = -1;

        while (choice < 1 || choice > 2) {
            displayMessage("\n--- Benvenuto cliente nel sistema di gestione taxi ---");
            displayMessage("1. Richiedi corsa");
            displayMessage("2. Logout");
            displayMessage("Seleziona un'opzione: ");

            try {
                choice = Integer.parseInt(reader.readLine().trim());

                if (choice < 1 || choice > 2) {
                    displayMessage("Opzione non valida. Per favore, seleziona un numero da 1 a 2.");
                }

            } catch (NumberFormatException e) {
                displayMessage("Input non valido. Inserisci un numero intero.");
            } catch (IOException e) {
                displayMessage("Errore nella lettura dell'input. Riprova.");
            }
        }

        return choice;
    }

    public static Cliente datiRegistrazione() throws IOException {
        System.out.println("\n--- Registrazione Cliente ---");

        String nome = null;
        while (nome == null || nome.isEmpty() || !nome.matches("[a-zA-Z]+") || nome.length() > 50) {
            displayMessage("Inserisci il tuo nome (max 50 caratteri): ");
            nome = reader.readLine().trim();
            if (nome.isEmpty() || !nome.matches("[a-zA-Z]+") || nome.length() > 50) {
                displayMessage("Errore: il nome deve contenere solo lettere e non può superare i 50 caratteri.");
            }
        }

        // Controllo del cognome (lunghezza max 50 e solo lettere)
        String cognome = null;
        while (cognome == null || cognome.isEmpty() || !cognome.matches("[a-zA-Z]+") || cognome.length() > 50) {
            displayMessage("Inserisci il tuo cognome (max 50 caratteri): ");
            cognome = reader.readLine().trim();
            if (cognome.isEmpty() || !cognome.matches("[a-zA-Z]+") || cognome.length() > 50) {
                displayMessage("Errore: il cognome deve contenere solo lettere e non può superare i 50 caratteri.");
            }
        }

        // Controllo del numero di telefono (lunghezza max 15 caratteri e solo numeri)
        String numeroDiTelefono = null;
        while (numeroDiTelefono == null || numeroDiTelefono.isEmpty() || !numeroDiTelefono.matches("\\d{10,15}")) {
            displayMessage("Inserisci il tuo numero di telefono (max 15 numeri): ");
            numeroDiTelefono = reader.readLine().trim();
            if (numeroDiTelefono.isEmpty() || !numeroDiTelefono.matches("\\d{10,15}")) {
                displayMessage("Errore: il numero di telefono deve contenere solo numeri e deve essere lungo tra 10 e 15 caratteri.");
            }
        }

        // Controllo del numero di carta di credito (19 cifre)
        String numeroCartaDiCredito = null;
        while (numeroCartaDiCredito == null || numeroCartaDiCredito.isEmpty() || !numeroCartaDiCredito.matches("\\d{19}")) {
            displayMessage("Inserisci il tuo numero di carta di credito (19 cifre): ");
            numeroCartaDiCredito = reader.readLine().trim();
            if (numeroCartaDiCredito.isEmpty() || !numeroCartaDiCredito.matches("\\d{19}")) {
                displayMessage("Errore: la carta di credito deve essere composta da 19 numeri.");
            }
        }


        // Controllo dello username (lunghezza max 50)
        String username = null;
        while (username == null || username.isEmpty() || username.length() > 50) {
            displayMessage("Inserisci il tuo username (max 50 caratteri): ");
            username = reader.readLine().trim();
            if (username.isEmpty() || username.length() > 50) {
                displayMessage("Errore: lo username non può essere vuoto e non deve superare i 50 caratteri.");
            }
        }


        // Controllo della password (almeno 6 caratteri)
        String password = null;
        while (password == null || password.length() < 6) {
            displayMessage("Inserisci la tua password (almeno 6 caratteri): ");
            password = reader.readLine().trim();
            if (password.length() < 6) {
                displayMessage("Errore: la password deve essere lunga almeno 6 caratteri.");
            }
        }

        Ruolo ruolo = Ruolo.CLIENTE;
        return new Cliente(username, password, ruolo, numeroDiTelefono, nome, cognome, numeroCartaDiCredito);
    }


    public static Richiesta richiediCorsa(String numeroTelefono) throws IOException {
        displayMessage("\n--- RICHIESTA DI CORSA ---");

        // Raccogli informazioni sulla partenza
        String provinciaPartenza = null;
        while (provinciaPartenza == null || provinciaPartenza.isEmpty() || !provinciaPartenza.matches("[A-Z]{2}")) {
            displayMessage("Inserisci la provincia di partenza (due lettere, es. RM, ML): ");
            provinciaPartenza = reader.readLine().trim();
            if (provinciaPartenza.isEmpty() || !provinciaPartenza.matches("[A-Z]{2}")) {
                displayMessage("Errore: la provincia deve essere composta da due lettere maiuscole.");
            }
        }

        String cittaPartenza = null;
        while (cittaPartenza == null || cittaPartenza.isEmpty()) {
            displayMessage("Inserisci la città di partenza: ");
            cittaPartenza = reader.readLine().trim();
            if (cittaPartenza.isEmpty()) {
                displayMessage("La città di partenza non può essere vuota.");
            }
        }

        String viaPartenza = null;
        while (viaPartenza == null || viaPartenza.isEmpty()) {
            displayMessage("Inserisci la via/piazza di partenza: ");
            viaPartenza = reader.readLine().trim();
            if (viaPartenza.isEmpty()) {
                displayMessage("La via di partenza non può essere vuota.");
            }
        }

        String numeroCivicoPartenza = null;
        while (numeroCivicoPartenza == null || numeroCivicoPartenza.isEmpty()) {
            displayMessage("Inserisci il numero civico di partenza: ");
            numeroCivicoPartenza = reader.readLine().trim();
            if (numeroCivicoPartenza.isEmpty()) {
                displayMessage("Il numero civico di partenza non può essere vuoto.");
            }
        }

        // Raccogli informazioni sulla destinazione
        String provinciaDestinazione = null;
        while (provinciaDestinazione == null || provinciaDestinazione.isEmpty() || !provinciaDestinazione.matches("[A-Z]{2}")) {
            displayMessage("Inserisci la provincia di destinazione (due lettere, es. RM, ML): ");
            provinciaDestinazione = reader.readLine().trim();
            if (provinciaDestinazione.isEmpty() || !provinciaDestinazione.matches("[A-Z]{2}")) {
                displayMessage("Errore: la provincia deve essere composta da due lettere maiuscole.");
            }
        }

        String cittaDestinazione = null;
        while (cittaDestinazione == null || cittaDestinazione.isEmpty()) {
            displayMessage("Inserisci la città di destinazione: ");
            cittaDestinazione = reader.readLine().trim();
            if (cittaDestinazione.isEmpty()) {
                displayMessage("La città di destinazione non può essere vuota.");
            }
        }

        String viaDestinazione = null;
        while (viaDestinazione == null || viaDestinazione.isEmpty()) {
            displayMessage("Inserisci la via/piazza di destinazione: ");
            viaDestinazione = reader.readLine().trim();
            if (viaDestinazione.isEmpty()) {
                displayMessage("La via di destinazione non può essere vuota.");
            }
        }

        String numeroCivicoDestinazione = null;
        while (numeroCivicoDestinazione == null || numeroCivicoDestinazione.isEmpty()) {
            displayMessage("Inserisci il numero civico di destinazione: ");
            numeroCivicoDestinazione = reader.readLine().trim();
            if (numeroCivicoDestinazione.isEmpty()) {
                displayMessage("Il numero civico di destinazione non può essere vuoto.");
            }
        }

        // Numero di posti richiesti
        int numeroPostiRichiesti = -1;
        while (numeroPostiRichiesti < 1) {
            displayMessage("Inserisci il numero di posti richiesti: ");
            try {
                numeroPostiRichiesti = Integer.parseInt(reader.readLine().trim());
                if (numeroPostiRichiesti < 1) {
                    displayMessage("Il numero di posti richiesti deve essere almeno 1.");
                }
            } catch (NumberFormatException e) {
                    displayMessage("Errore: inserisci un numero valido.");
            }
        }

        // Creazione dell'oggetto Richiesta
        return new Richiesta(
                numeroTelefono,
                provinciaPartenza, cittaPartenza, viaPartenza, numeroCivicoPartenza,
                provinciaDestinazione, cittaDestinazione, viaDestinazione, numeroCivicoDestinazione,
                numeroPostiRichiesti
        );
    }

    public static void displayMessage(String string){
        System.out.println(string);
    }

}
