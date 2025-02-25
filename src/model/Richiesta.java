package model;

public class Richiesta {

    private int id;
    private String numeroCliente; //riferimento a numero di telefono del cliente
    private final String provinciaPartenza;
    private final String cittaPartenza;
    private final String viaPartenza;
    private final String numeroCivicoPartenza;
    private final String provinciaDestinazione;
    private final String cittaDestinazione;
    private final String viaDestinazione;
    private final String numeroCivicoDestinazione;
    private final int numeroPostiRichiesti;
    private String numeroTassista; //riferimento a numero di patente del tassista

    public Richiesta(String numeroCliente, String provinciaPartenza, String cittaPartenza, String viaPartenza,
                     String numeroCivicoPartenza, String provinciaDestinazione, String cittaDestinazione,
                     String viaDestinazione, String numeroCivicoDestinazione, int numeroPostiRichiesti) {
        this.numeroCliente = numeroCliente;
        this.provinciaPartenza = provinciaPartenza;
        this.cittaPartenza = cittaPartenza;
        this.viaPartenza = viaPartenza;
        this.numeroCivicoPartenza = numeroCivicoPartenza;
        this.provinciaDestinazione = provinciaDestinazione;
        this.cittaDestinazione = cittaDestinazione;
        this.viaDestinazione = viaDestinazione;
        this.numeroCivicoDestinazione = numeroCivicoDestinazione;
        this.numeroPostiRichiesti = numeroPostiRichiesti;
    }

    public Richiesta(int id, String provinciaPartenza, String cittaPartenza, String viaPartenza,
                     String numeroCivicoPartenza, String provinciaDestinazione, String cittaDestinazione,
                     String viaDestinazione, String numeroCivicoDestinazione, int numeroPostiRichiesti) {
        this.id = id;
        this.provinciaPartenza = provinciaPartenza;
        this.cittaPartenza = cittaPartenza;
        this.viaPartenza = viaPartenza;
        this.numeroCivicoPartenza = numeroCivicoPartenza;
        this.provinciaDestinazione = provinciaDestinazione;
        this.cittaDestinazione = cittaDestinazione;
        this.viaDestinazione = viaDestinazione;
        this.numeroCivicoDestinazione = numeroCivicoDestinazione;
        this.numeroPostiRichiesti = numeroPostiRichiesti;
    }

    public int getId() {
        return id;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public String getProvinciaPartenza() {
        return provinciaPartenza;
    }

    public String getCittaPartenza() {
        return cittaPartenza;
    }

    public String getViaPartenza() {
        return viaPartenza;
    }

    public String getNumeroCivicoPartenza() {
        return numeroCivicoPartenza;
    }

    public String getProvinciaDestinazione() {
        return provinciaDestinazione;
    }

    public String getCittaDestinazione() {
        return cittaDestinazione;
    }

    public String getViaDestinazione() {
        return viaDestinazione;
    }

    public String getNumeroCivicoDestinazione() {
        return numeroCivicoDestinazione;
    }

    public int getNumeroPostiRichiesti() {
        return numeroPostiRichiesti;
    }

    public String getTassista() {
        return numeroTassista;
    }

    public void setNumeroTassista(String numeroTassista) {
        this.numeroTassista = numeroTassista;
    }

}
