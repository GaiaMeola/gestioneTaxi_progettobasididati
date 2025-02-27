package session;

import model.Credenziali;

public class SessionManager {
    // per tenere traccia dell'utente attualmente loggato

    private static Credenziali credenziali = null;

    public static void login(Credenziali credenziali){
        SessionManager.credenziali = credenziali;
    }

    //così da ottenere l'username dell'utente attualmente loggato
    public static String getUsername() {
        if (credenziali == null) {
            throw new IllegalStateException("Nessun utente loggato.");
        }
        return credenziali.getUsername();
    }

    public static void logout() {
        credenziali = null;
    }

}
