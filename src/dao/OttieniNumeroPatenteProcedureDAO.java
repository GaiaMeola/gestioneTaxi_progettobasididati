package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class OttieniNumeroPatenteProcedureDAO implements GenericProcedureDAO<String, String> {

    // dato lo username che otteniamo dalla sessione, ricaviamo il numero di patente
    @Override
    public String execute(String username) throws DAOException {

        String numeroPatente;

        // Usa try-with-resources per gestire automaticamente la chiusura delle risorse
        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL OttieniNumeroPatente(?, ?)}")) {

            // Impostiamo i parametri della stored procedure
            stmt.setString(1, username);  // Parametro di ingresso: username
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);  // Parametro di output: numeroPatente

            // Eseguiamo la stored procedure
            stmt.execute();

            // Recuperiamo il numero di patente restituito dalla stored procedure
            numeroPatente = stmt.getString(2);

            // Controlliamo se il numero di patente è nullo
            if (numeroPatente == null || numeroPatente.equals("Tassista non trovato")) {
                throw new DAOException("Nessun numero di patente trovato per l'username: " + username);
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return numeroPatente;  // Restituiamo il numero di patente ottenuto
    }
}
