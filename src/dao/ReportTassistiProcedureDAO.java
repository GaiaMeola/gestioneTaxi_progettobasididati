package dao;

import connection.ConnectionFactory;
import exceptions.DAOException;
import model.TassistaReport;
import model.TassistaReportList;

import java.sql.*;

public class ReportTassistiProcedureDAO implements GenericProcedureDAO<Void, TassistaReportList> {

    @Override
    public TassistaReportList execute(Void param) throws  DAOException {
        String procedureCall = "{ CALL ReportTassisti() }";  // Chiamata alla stored procedure

        // Crea una lista per memorizzare i risultati
        TassistaReportList reportList = new TassistaReportList();

        // Usa la connessione ottenuta tramite ConnectionFactory.getConnection()
        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(procedureCall);
             ResultSet rs = cs.executeQuery()) {

            // Elenco dei risultati della stored procedure
            while (rs.next()) {
                // Recuperiamo i dati dalla query
                String numeroDiPatente = rs.getString("NumeroDiPatente");
                String nome = rs.getString("Nome");
                String cognome = rs.getString("Cognome");
                int numeroCorse = rs.getInt("NumeroCorse");
                double guadagnoTotale = rs.getDouble("GuadagnoTotale");
                double commissioneTotale = rs.getDouble("CommissioneTotale");

                // Creiamo un oggetto report per ogni tassista
                TassistaReport report = new TassistaReport(numeroDiPatente, nome, cognome, numeroCorse, guadagnoTotale, commissioneTotale);

                // Aggiungiamo il report alla lista
                reportList.aggiungiTassistaReport(report);
            }

        } catch (SQLException e) {
            throw new DAOException("Errore nell'esecuzione della stored procedure ReportTassisti: " + e.getMessage());
        }

        // Restituiamo la lista dei report
        return reportList;
    }
}
