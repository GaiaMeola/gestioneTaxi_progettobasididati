package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Corsa;
import model.CorsaList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostraCorseNonRiscosseDAO implements GenericProcedureDAO<Void, CorsaList> {

    @Override
    public CorsaList execute(Void param) throws DAOException {
        String procedureCall = "{ CALL MostraCorseNonRiscosse() }"; // Chiamata alla stored procedure

        // Inizializza la lista delle corse non riscosse
        CorsaList listaCorse = new CorsaList();

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(procedureCall);
             ResultSet rs = cs.executeQuery()) {

            // Itera sui risultati della query
            while (rs.next()) {
                // Crea un oggetto CorsaNonRiscossa per ogni risultato
                Corsa corsa = new Corsa(
                        rs.getInt("IDRichiesta"),
                        rs.getDouble("Importo")
                );
                // Aggiungi la corsa alla lista delle corse non riscosse
                listaCorse.aggiungiCorsa(corsa);
            }
        } catch( SQLException e){
            throw new DAOException("Errore nel recupero delle corse non riscosse: " + e.getMessage());
        }
        // Restituisce la lista delle corse non riscosse
        return listaCorse;
    }
}
