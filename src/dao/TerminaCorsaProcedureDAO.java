package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Corsa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class TerminaCorsaProcedureDAO implements GenericProcedureDAO<Corsa, String> {

    @Override
    public String execute(Corsa corsa) throws DAOException {
        String esito;

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL TerminaCorsa(?, ?, ?)}")) {

            // Impostiamo i parametri della stored procedure
            stmt.setInt(1, corsa.getIdRichiesta()); // ID della corsa
            stmt.setDouble(2, corsa.getImporto());  // Importo della corsa
            stmt.registerOutParameter(3, java.sql.Types.VARCHAR); // Parametro di uscita: esito

            // Eseguiamo la stored procedure
            stmt.execute();

            // Recuperiamo l'esito
            esito = stmt.getString(3);

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return esito; // Restituiamo il risultato
    }
}
