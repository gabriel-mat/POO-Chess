package pecas;

public abstract class Peca {
    private final String cor;
    private boolean viva;

    public Peca(String cor){
        this.cor = cor;
        this.viva = true;
    }

    public String getCor(){
        return cor;
    }

    public boolean estaViva() {
        return viva;
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }

    public abstract char getTipo();
    public abstract String desenho();
    public abstract boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD);
    public abstract String caminho(int linhaO, char colunaO, int linhaD, char colunaD);
}
