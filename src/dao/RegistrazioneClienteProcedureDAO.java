package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Cliente;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrazioneClienteProcedureDAO implements GenericProcedureDAO<Cliente, String> {

    @Override
    public String execute(Cliente param) throws DAOException {
        // La stored procedure ora accetta 6 parametri IN
        String procedureCall = "{ CALL RegistrazioneCliente(?, ?, ?, ?, ?, ?) }";
        String esito = "";

        // Usa la nuova connessione ottenuta tramite ConnectionFactory.getConnection()
        try (Connection conn = ConnectionFactory.getConnection();  // Sostituito getInstance().getConnection()
             CallableStatement cs = conn.prepareCall(procedureCall)) {

            // Imposta i parametri IN secondo l'ordine definito nella stored procedure
            cs.setString(1, param.getUsername());
            cs.setString(2, param.getPassword());
            cs.setString(3, param.getTelefono());
            cs.setString(4, param.getNome());
            cs.setString(5, param.getCognome());
            cs.setString(6, param.getCartaDiCredito());

            // Esegui la stored procedure e ottieni il ResultSet contenente il messaggio di esito
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
            throw new DAOException("Errore durante la registrazione del cliente: " + e.getMessage());
        }
    }
}
