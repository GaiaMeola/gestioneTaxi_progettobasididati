package model;

import java.util.ArrayList;
import java.util.List;

public class TassistaReportList {

    private final List<TassistaReport> reportList;

    public TassistaReportList() {
        this.reportList = new ArrayList<>();
    }

    // Aggiungi un report alla lista
    public void aggiungiTassistaReport(TassistaReport report) {
        reportList.add(report);
    }

    // Restituisci la lista dei report
    public List<TassistaReport> getReportList() {
        return reportList;
    }

    // Ottieni un report per numero di patente
    public TassistaReport getReportByNumeroDiPatente(String numeroDiPatente) {
        for (TassistaReport report : reportList) {
            if (report.numeroDiPatente().equals(numeroDiPatente)) {
                return report;
            }
        }
        return null;
    }

    // Verifica se la lista è vuota
    public boolean isEmpty() {
        return reportList.isEmpty();
    }
}
