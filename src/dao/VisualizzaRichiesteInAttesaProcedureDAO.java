package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Richiesta;
import model.RichiestaInAttesaList;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizzaRichiesteInAttesaProcedureDAO implements GenericProcedureDAO<Void, RichiestaInAttesaList> {

    @Override
    public RichiestaInAttesaList execute(Void param) throws DAOException {
        String procedureCall = "{ CALL VisualizzaRichiesteInAttesa() }"; // Chiamata alla stored procedure

        // Inizializza la lista delle richieste in attesa
        RichiestaInAttesaList listaRichieste = new RichiestaInAttesaList();

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(procedureCall);
             ResultSet rs = cs.executeQuery()) {

            // Itera sui risultati della query
            while (rs.next()) {
                // Crea un oggetto Richiesta per ogni risultato
                Richiesta richiesta = new Richiesta(
                        rs.getInt("ID"),
                        rs.getString("ProvinciaPartenza"),
                        rs.getString("CittàPartenza"),
                        rs.getString("ViaPartenza"),
                        rs.getString("NumeroCivicoPartenza"),
                        rs.getString("ProvinciaDestinazione"),
                        rs.getString("CittàDestinazione"),
                        rs.getString("ViaDestinazione"),
                        rs.getString("NumeroCivicoDestinazione"),
                        rs.getInt("NumeroPostiRichiesti")
                );
                // Aggiungi la richiesta alla lista delle richieste in attesa
                listaRichieste.aggiungiRichiesta(richiesta);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        // Restituisce la lista delle richieste in attesa
        return listaRichieste;
    }
}
