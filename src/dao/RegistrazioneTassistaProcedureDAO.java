package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Tassista;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrazioneTassistaProcedureDAO implements GenericProcedureDAO<Tassista, String> {

    @Override
    public String execute(Tassista param) throws DAOException {
        // La stored procedure ora accetta 8 parametri IN
        String procedureCall = "{ CALL RegistrazioneTassista(?, ?, ?, ?, ?, ?, ?, ?) }";
        String esito = "";

        try (Connection conn = ConnectionFactory.getConnection();  // Modificato qui
             CallableStatement cs = conn.prepareCall(procedureCall)) {

            cs.setString(1, param.getUsername());
            cs.setString(2, param.getPassword());
            cs.setString(3, param.getNumeroDiPatente());
            cs.setString(4, param.getNome());
            cs.setString(5, param.getCognome());
            cs.setString(6, param.getNumeroCartaDiCredito());
            cs.setString(7, param.getTarga());

            // Converti il numero di posti in intero (assumendo che getNumeroDiPostiADisposizione() ritorni una String)
            int numeroPosti = Integer.parseInt(param.getNumeroDiPostiADisposizione());
            cs.setInt(8, numeroPosti);

            // Esegui la stored procedure e ottieni il ResultSet contenente l'esito
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    esito = rs.getString("esito");
                }
            }

            if (esito == null) {
                throw new DAOException("Nessun esito restituito dalla stored procedure.");
            }

            if (!"Registrazione completata con successo!".equals(esito)) {
                throw new DAOException(esito);
            }

            return esito;

        } catch (SQLException e) {
            throw new DAOException("Errore durante la registrazione del tassista: " + e.getMessage());
        }
    }
}
