package view;

import model.Richiesta;
import model.RichiestaInAttesaList;
import model.Ruolo;
import model.Tassista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TassistaView {

    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int showMenu() {
        int choice = -1;

        while (choice < 1 || choice > 3) {  // Tolta opzione 2
            displayMessage("\n--- Benvenuto tassista nel sistema di gestione taxi ---");
            displayMessage("1. Visualizza e accetta una richiesta di corsa");
            displayMessage("2. Termina corsa");
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


    public static Tassista datiRegistrazione() throws IOException {
        displayMessage("\n--- Registrazione Tassista ---");


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

        // Controllo del numero di patente (lunghezza max 20 caratteri, solo lettere e numeri)
        String numeroDiPatente = null;
        while (numeroDiPatente == null || numeroDiPatente.isEmpty() || numeroDiPatente.length() > 20) {
            displayMessage("Inserisci il tuo numero di patente (max 20 caratteri): ");
            numeroDiPatente = reader.readLine().trim();
            if (numeroDiPatente.isEmpty() || numeroDiPatente.length() > 20) {
                displayMessage("Errore: il numero di patente non può essere vuoto e non deve superare i 20 caratteri.");
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

        // Controllo della targa (7 caratteri)
        String targa = null;
        while (targa == null || targa.length() != 7) {
            displayMessage("Inserisci la tua targa (7 caratteri): ");
            targa = reader.readLine().trim();
            if (targa.length() != 7) {
                displayMessage("Errore: la targa deve essere lunga esattamente 7 caratteri.");
            }
        }

        // Controllo numero di posti a disposizione (tra 1 e 7)
        String numeroDiPostiADisposizione = null;
        while (numeroDiPostiADisposizione == null || numeroDiPostiADisposizione.isEmpty() || !numeroDiPostiADisposizione.matches("[1-7]")) {
            displayMessage("Inserisci il numero di posti a disposizione (1-7): ");
            numeroDiPostiADisposizione = reader.readLine().trim();
            if (numeroDiPostiADisposizione.isEmpty() || !numeroDiPostiADisposizione.matches("[1-7]")) {
                displayMessage("Errore: il numero di posti a disposizione deve essere un numero tra 1 e 7.");
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

        Ruolo ruolo = Ruolo.TASSISTA;  // Ruolo per il tassista

        // Restituisci un oggetto Tassista con i dati inseriti
        return new Tassista(username, password, ruolo, numeroDiPatente, nome, cognome, numeroCartaDiCredito, targa, numeroDiPostiADisposizione);
    }

    public static void displayMessage(String string){
        System.out.println(string);
    }

    public static boolean mostraRichiesteEAccetta(RichiestaInAttesaList listaRichieste) {

        int numeroRichieste = listaRichieste.getNumeroRichiesteInAttesa();
        displayMessage("Numero di richieste di corsa in attesa: " + numeroRichieste);

        if (listaRichieste.isEmpty()) {
            displayMessage("Non ci sono richieste di corse in attesa al momento.");
            return false;
        }

        displayMessage("\n--- Elenco delle richieste di corsa in attesa ---");

        for (Richiesta richiesta : listaRichieste.getRichiesteInAttesa()) {
            displayMessage("\nCodice Corsa: " + richiesta.getId());
            displayMessage("Partenza: " + richiesta.getCittaPartenza() + ", " + richiesta.getProvinciaPartenza() + ", Via/Piazza " + richiesta.getViaPartenza() + ", Numero Civico " + richiesta.getNumeroCivicoPartenza());
            displayMessage("Destinazione: " + richiesta.getCittaDestinazione() + ", " + richiesta.getProvinciaDestinazione() + ", Via/Piazza " + richiesta.getViaDestinazione() + ", Numero Civico "+ richiesta.getNumeroCivicoDestinazione());
            displayMessage("Numero di posti richiesti: " + richiesta.getNumeroPostiRichiesti());
        }

        return chiediSeAccettare();
    }


    public static boolean chiediSeAccettare() {
        String scelta = "";
        while (!scelta.equalsIgnoreCase("S") && !scelta.equalsIgnoreCase("N")) {
            displayMessage("\nVuoi accettare una corsa? (S/N): ");
            try {
                scelta = reader.readLine().trim().toUpperCase();
                if (!scelta.equals("S") && !scelta.equals("N")) {
                    displayMessage("Errore: inserisci 'S' per accettare una corsa o 'N' per tornare indietro.");
                }
            } catch (IOException e) {
                displayMessage("Errore nella lettura dell'input.");
            }
        }
        return scelta.equals("S");
    }

    public static int chiediCodiceCorsa() {
        int idRichiesta = -1;
        while (idRichiesta < 0) {
            displayMessage("\nInserisci il codice della corsa che vuoi accettare: ");

            try {
                idRichiesta = Integer.parseInt(reader.readLine().trim());

                if (idRichiesta < 0) {
                    displayMessage("Errore: il codice corsa deve essere un numero positivo o 0 per tornare indietro.");
                }

            } catch (NumberFormatException e) {
                displayMessage("Errore: inserisci un numero valido.");
            } catch (IOException e) {
                displayMessage("Errore nella lettura dell'input.");
            }
        }
        return idRichiesta;
    }

    public static double chiediImportoCorsa() {
        double importo = -1;

        while (importo < 0) {
            displayMessage("Inserisci l'importo della corsa: ");
            try {
                String input = reader.readLine().trim();

                importo = Double.parseDouble(input);

                if (importo < 0) {
                    displayMessage("Errore: l'importo deve essere un numero positivo. Riprova.");
                }
            } catch (NumberFormatException e) {
                displayMessage("Errore: devi inserire un numero valido.");
            } catch (IOException e) {
                displayMessage("Errore nella lettura dell'input. Riprova.");
            }
        }

        return importo;
    }
}
