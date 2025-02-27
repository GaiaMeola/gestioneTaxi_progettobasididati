package connection;

import exceptions.DAOException;
import model.Ruolo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Connection connection;

    private ConnectionFactory() {
        //no istanza
    }

    public static Connection getConnection() throws SQLException {
        // Se la connessione non esiste o è chiusa, creane una nuova
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection()  {
        try (InputStream input = new FileInputStream("src/resources/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String connection_url = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty("LOGIN_USER");
            String pass = properties.getProperty("LOGIN_PASS");

            // Crea una nuova connessione
            connection = DriverManager.getConnection(connection_url, user, pass);
            /*System.out.println("Connessione al database stabilita con successo.");*/
        } catch (IOException | SQLException e) {
            // Gestione delle eccezioni
            throw new DAOException("Errore nel creare una nuova connessione al database");
        }
    }

    public static void changeRole(Ruolo role) throws SQLException, IOException {
        // Chiusura della connessione precedente se esiste
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }

        // Riprova a creare una nuova connessione con il nuovo ruolo
        try (InputStream input = new FileInputStream("src/resources/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String connection_url = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty(role.name() + "_USER");
            String pass = properties.getProperty(role.name() + "_PASS");

            // Crea la connessione con il nuovo ruolo
            connection = DriverManager.getConnection(connection_url, user, pass);
            /*System.out.println("Connessione aggiornata con il ruolo:" + role);*/
        } catch (SQLException | IOException e) {
            // Gestione delle eccezioni
            throw new DAOException("Errore durante il cambio di ruolo o la connessione al database");
        }
    }

    public static void logout() throws SQLException, IOException {
        // Chiudi la connessione attuale, se è aperta
        if (connection != null && !connection.isClosed()) {
            System.out.println("Chiudendo la connessione...");
            connection.close();
        }

        // Ricarica le credenziali di login dal file di configurazione
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/resources/db.properties")) {
            properties.load(fis);
        }
        // Carica le credenziali di login
        String url = properties.getProperty("CONNECTION_URL");
        String user = properties.getProperty("LOGIN_USER");
        String password = properties.getProperty("LOGIN_PASS");

        // Crea una nuova connessione con le credenziali di login
        connection = DriverManager.getConnection(url, user, password);
        /*System.out.println("Connessione riaperta come utente di login:" + user);*/
    }
}
