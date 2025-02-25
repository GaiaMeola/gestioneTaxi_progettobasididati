package model;

public class Cliente extends Credenziali{

    private final String telefono;
    private final String nome;
    private final String cognome;
    private final String cartaDiCredito;


    public Cliente(String username, String password, Ruolo ruolo, String telefono, String nome, String cognome, String cartaDiCredito) {
        super(username, password, ruolo);
        this.telefono = telefono;
        this.nome = nome;
        this.cognome = cognome;
        this.cartaDiCredito = cartaDiCredito;
    }


    public String getTelefono() {
        return telefono;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCartaDiCredito() {
        return cartaDiCredito;
    }

}
