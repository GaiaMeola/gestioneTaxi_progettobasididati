package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class OttieniNumeroTelefonoProcedureDAO implements GenericProcedureDAO<String, String> {

    // dato lo username che otteniamo dalla sessione, ricaviamo il numero di telefono
    @Override
    public String execute(String username) throws DAOException {

        String numeroTelefono;

        // Usa try-with-resources per gestire automaticamente la chiusura delle risorse
        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL OttieniNumeroTelefono(?, ?)}")) {

            // Impostiamo i parametri della stored procedure
            stmt.setString(1, username);  // Parametro di ingresso: username
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);  // Parametro di output: numeroTelefono

            // Eseguiamo la stored procedure
            stmt.execute();

            // Recuperiamo il numero di telefono restituito dalla stored procedure
            numeroTelefono = stmt.getString(2);

            // Controlliamo se il numero di telefono è nullo
            if (numeroTelefono == null) {
                throw new DAOException("Nessun numero di telefono trovato per l'username: " + username);
            }

        } catch (SQLException e) {
            // Gestiamo l'errore con un'eccezione personalizzata
            throw new DAOException(e.getMessage());
        }

        return numeroTelefono;  // Restituiamo il numero di telefono ottenuto
    }
}
