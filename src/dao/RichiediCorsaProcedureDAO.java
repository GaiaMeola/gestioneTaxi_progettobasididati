package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Richiesta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class RichiediCorsaProcedureDAO implements GenericProcedureDAO<Richiesta, String> {

    @Override
    public String execute(Richiesta richiesta) throws DAOException {
        // Connessione al database e CallableStatement dichiarati all'interno del try-with-resources
        String esito;

        // Con try-with-resources per gestione automatica delle risorse
        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL RichiediCorsa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            // Impostiamo i parametri della stored procedure (rispettando l'ordine della query)
            stmt.setString(1, richiesta.getNumeroCliente());  // p_numero_cliente
            stmt.setString(2, richiesta.getProvinciaPartenza());  // p_provincia_partenza
            stmt.setString(3, richiesta.getCittaPartenza());  // p_citta_partenza
            stmt.setString(4, richiesta.getViaPartenza());  // p_via_partenza
            stmt.setString(5, richiesta.getNumeroCivicoPartenza());  // p_numero_civico_partenza
            stmt.setString(6, richiesta.getProvinciaDestinazione());  // p_provincia_destinazione
            stmt.setString(7, richiesta.getCittaDestinazione());  // p_citta_destinazione
            stmt.setString(8, richiesta.getViaDestinazione());  // p_via_destinazione
            stmt.setString(9, richiesta.getNumeroCivicoDestinazione());  // p_numero_civico_destinazione
            stmt.setInt(10, richiesta.getNumeroPostiRichiesti());  // p_numero_posti_richiesti

            // Registriamo il parametro OUT
            stmt.registerOutParameter(11, java.sql.Types.VARCHAR); // p_esito

            // Eseguiamo la stored procedure
            stmt.executeUpdate();

            // Recuperiamo il messaggio di esito restituito dalla stored procedure
            esito = stmt.getString(11);

        } catch (SQLException e) {
            // Gestiamo l'errore con un'eccezione personalizzata
            throw new DAOException(e.getMessage());
        }

        return esito;  // Restituiamo il messaggio di esito dalla stored procedure
    }
}
