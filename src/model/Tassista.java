package model;

public class Tassista extends Credenziali{

    private final String numeroDiPatente;
    private final String nome;
    private final String cognome;
    private final String numeroCartaDiCredito;
    private final String targa;
    private final String numeroDiPostiADisposizione;

    public Tassista(String username, String password, Ruolo ruolo, String numeroDiPatente, String nome, String cognome, String numeroCartaDiCredito, String targa, String numeroDiPostiADisposizione) {
        super(username, password, ruolo);
        this.numeroDiPatente = numeroDiPatente;
        this.nome = nome;
        this.cognome = cognome;
        this.numeroCartaDiCredito = numeroCartaDiCredito;
        this.targa = targa;
        this.numeroDiPostiADisposizione = numeroDiPostiADisposizione;
    }


    public String getNumeroDiPatente() {
        return numeroDiPatente;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNumeroCartaDiCredito() {
        return numeroCartaDiCredito;
    }

    public String getTarga() {
        return targa;
    }

    public String getNumeroDiPostiADisposizione() {
        return numeroDiPostiADisposizione;
    }
}
