package tabuleiro;

public class Jogada {
    private final Jogador jogador;
    private final Tabuleiro tabuleiro;
    private final int linhaO, linhaD;
    private final char colunaO, colunaD;

    public Jogada(Jogador jogador, Tabuleiro tabuleiro, int linhaO, char colunaO, int linhaD, char colunaD){
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.linhaO = linhaO;
        this.colunaO = colunaO;
        this.linhaD = linhaD;
        this.colunaD = colunaD;
    }

    public boolean ehValida(){
        if(!Tabuleiro.noLimite(linhaO, colunaO) || !Tabuleiro.noLimite(linhaD, colunaD))
            return false;

        if(!tabuleiro.getPeca(linhaO, colunaO).getCor().equals(jogador.getCor()))
            return false;

        if(tabuleiro.casaOcupada(linhaD, colunaD) && tabuleiro.getPeca(linhaD, colunaD).getCor().equals(jogador.getCor()))
            return false;

        // verificar se caminho está livre, se não for um cavalo

        if(!tabuleiro.getPeca(linhaO, colunaO).movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return false;

        return true;
    }
}
