package view;

import model.Credenziali;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginView {

    public static Credenziali authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        displayMessage("\nInserisci username e password per effettuare il login: ");
        //read from stdin
        displayMessage("Username: ");
        String username = reader.readLine();
        displayMessage("Password: ");
        String password = reader.readLine();

        return new Credenziali(username, password, null);
        // then, call the login controller
    }

    public static void displayMessage(String s) {
        System.out.println(s);
    }
}
