package model;

public enum Ruolo {
    GESTORE(1),
    CLIENTE(2),
    TASSISTA(3);

    private final int id;

    Ruolo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Ruolo fromInt(int i) {
        return switch (i) {
            case 1 -> GESTORE;
            case 2 -> CLIENTE;
            case 3 -> TASSISTA;
            default -> throw new IllegalArgumentException("Id ruolo non valido: " + i);
        };
    }
}

