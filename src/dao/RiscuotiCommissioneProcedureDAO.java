package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Corsa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class RiscuotiCommissioneProcedureDAO implements GenericProcedureDAO<Corsa, String> {

    @Override
    public String execute(Corsa corsa) throws DAOException {
        String procedureCall = "{ CALL RiscuotiCommissione(?, ?) }"; // Chiamata alla stored procedure
        String esito;

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(procedureCall)) {

            // Impostiamo i parametri
            cs.setInt(1, corsa.getIdRichiesta());  // Passiamo l'ID della corsa
            cs.registerOutParameter(2, java.sql.Types.VARCHAR);  // Out parametro per il risultato (esito)

            // Eseguiamo la chiamata
            cs.executeUpdate();

            // Recuperiamo il risultato
            esito = cs.getString(2);  // Recuperiamo il messaggio di esito

        } catch (SQLException e) {
            throw new DAOException("Errore durante la riscossione della commissione: " + e.getMessage());
        }

        return esito;  // Restituiamo il risultato della procedura
    }
}
