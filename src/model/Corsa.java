package model;

public class Corsa {
    private final int idRichiesta;
    private double importo;

    //Informazioni necessarie per terminare la corsa
    public Corsa(int idRichiesta, double importo) {
        this.idRichiesta = idRichiesta;
        this.importo = importo;
    }

    // Getter e Setter
    public int getIdRichiesta() { return idRichiesta; }


    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }
}
