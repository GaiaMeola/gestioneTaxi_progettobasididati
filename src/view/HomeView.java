package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeView {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int showMenu() {
        int choice = -1;

        while (choice < 1 || choice > 3) {
            displayMessage("\n--- Benvenuto nel sistema di gestione taxi ---");
            displayMessage("1. Login");
            displayMessage("2. Registrazione");
            displayMessage("3. Esci");
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

        // Restituisci la scelta valida
        return choice;
    }

    public static void showExitMessage() {
        System.out.println("Grazie per aver usato il sistema. Arrivederci!");
    }

    public static void showInvalidChoiceMessage() {
        System.out.println("Scelta non valida. Riprova.");
    }

    public static void showErrorMessage(String message) {
        System.out.println("Errore: " + message);
    }

    //Tipo di registrazione
    public static int showRegistrationChoice() throws IOException {
        int choice = -1;
        while (choice < 1 || choice > 2) {
            displayMessage("\nCome desideri registrarti?");
            displayMessage("1. Come Cliente");
            displayMessage("2. Come Tassista");
            displayMessage("Seleziona un'opzione: ");
            try {
                choice = Integer.parseInt(reader.readLine().trim());

                if (choice < 1 || choice > 2) {
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

    public static void displayMessage(String s) {
        System.out.println(s);
    }
}
