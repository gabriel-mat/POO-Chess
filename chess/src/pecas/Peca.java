package pecas;

public abstract class Peca {
    private String cor;

    public Peca(String cor){
        this.cor = cor;
    }

    public String getCor(){
        return cor;
    }

    public abstract char getTipo();
    public abstract String desenho();
    public abstract boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD);
    public abstract String caminho(int linhaO, char colunaO, int linhaD, char colunaD);
}
