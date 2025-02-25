package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.Corsa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class TrovaCorsaAttualeProcedureDAO implements GenericProcedureDAO<String, Corsa> {

    @Override
    public Corsa execute(String numeroPatenteTassista) throws DAOException {
        Corsa corsa;

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL TrovaCorsaAttuale(?, ?)}")) {  // SOLO 2 PARAMETRI!

            // Impostiamo i parametri della stored procedure
            stmt.setString(1, numeroPatenteTassista); // Username del tassista
            stmt.registerOutParameter(2, java.sql.Types.INTEGER); // Parametro di output (ID corsa)

            // Eseguiamo la stored procedure
            stmt.execute();

            // Recuperiamo l'ID della corsa attuale
            int idCorsa = stmt.getInt(2);

            // Se non c'è una corsa attiva, restituiamo null
            if (idCorsa == 0 || stmt.wasNull()) {
                return null;
            }

            // Creiamo un oggetto Corsa con solo l'ID (l'importo verrà impostato dopo)
            corsa = new Corsa(idCorsa, 0.0); // L'importo sarà settato successivamente

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return corsa;
    }
}
