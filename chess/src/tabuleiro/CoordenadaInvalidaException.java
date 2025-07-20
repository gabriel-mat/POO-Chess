package tabuleiro;

public class CoordenadaInvalidaException extends RuntimeException {
    public CoordenadaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
