package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Richiesta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AccettaRichiestaProcedureDAO implements GenericProcedureDAO<Richiesta, String> {

    @Override
    public String execute(Richiesta richiesta) throws SQLException, DAOException {
        String esito;

        // Usa try-with-resources per gestire automaticamente la chiusura delle risorse
        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL AccettaRichiesta(?, ?, ?)}")) {

            // Impostiamo i parametri della stored procedure
            stmt.setString(1, richiesta.getTassista());  // Numero di patente del tassista
            stmt.setInt(2, richiesta.getId());  // ID della richiesta
            stmt.registerOutParameter(3, java.sql.Types.VARCHAR);  // Parametro di uscita: esito

            // Eseguiamo la stored procedure
            stmt.execute();

            // Recuperiamo il risultato dell'esito dalla stored procedure
            esito = stmt.getString(3);

        } catch (SQLException e) {
            // Gestiamo l'errore con un'eccezione personalizzata
            throw new DAOException(e.getMessage());
        }

        return esito;  // Restituiamo l'esito della procedura
    }
}
