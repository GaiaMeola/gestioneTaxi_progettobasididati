package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Credenziali;
import model.Ruolo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginProcedureDAO implements GenericProcedureDAO<Credenziali, Credenziali> {

    @Override
    public Credenziali execute(Credenziali param) throws DAOException {
        String procedureCall = "{ CALL Login(?, ?, ?) }";

        // Usa la nuova connessione ottenuta tramite ConnectionFactory.getConnection()
        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(procedureCall)) {

            // Imposta i parametri per la stored procedure
            cs.setString(1, param.getUsername());
            cs.setString(2, param.getPassword());

            cs.registerOutParameter(3, Types.TINYINT);

            // Esecuzione della stored procedure
            cs.execute();

            // Recupera il risultato del ruolo
            int roleInt = cs.getInt(3);

            // Se il ruolo è null, significa che le credenziali non sono valide
            if (cs.wasNull()) {
                throw new DAOException("Credenziali non valide.");
            }

            // Mappa il risultato numerico al tipo di Ruolo
            Ruolo ruolo = switch (roleInt) {
                case 1 -> Ruolo.CLIENTE;
                case 2 -> Ruolo.TASSISTA;
                case 3 -> Ruolo.GESTORE;
                default -> throw new DAOException("Ruolo non valido.");
            };

            // Imposta il ruolo nelle credenziali
            param.setRuolo(ruolo);
            // Restituisce le credenziali con il ruolo assegnato
            return param;
        }  catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
