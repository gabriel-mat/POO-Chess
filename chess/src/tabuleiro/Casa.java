package tabuleiro;

import pecas.Peca;

public class Casa {
    private String cor;
    private int linha;
    private char coluna;
    private Peca peca;

    public Casa(String cor, int linha, char coluna){
        this.cor = cor;
        this.linha = linha;
        this.coluna = coluna;
        this.peca = null;
    }

    public Casa(String cor, int linha, char coluna, Peca peca){
        this.cor = cor;
        this.linha = linha;
        this.coluna = coluna;
        this.peca = peca;
    }

    public String getCor(){
        return cor;
    }

    public int getLinha(){
        return linha;
    }

    public char getColuna(){
        return coluna;
    }

    public Peca getPeca(){
        return peca;
    }

    public void setPeca(Peca peca){
        this.peca = peca;
    }

    public boolean casaVazia(){
        return peca == null;
    }
}
