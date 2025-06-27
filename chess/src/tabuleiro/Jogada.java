package tabuleiro;

public class Jogada {
    private final Jogador jogador;
    private final Tabuleiro tabuleiro;
    private final int linhaO, linhaD;
    private final char colunaO, colunaD;
    private final Caminho caminho;

    public Jogada(Jogador jogador, Tabuleiro tabuleiro, int linhaO, char colunaO, int linhaD, char colunaD){
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.linhaO = linhaO;
        this.colunaO = colunaO;
        this.linhaD = linhaD;
        this.colunaD = colunaD;
        caminho = new Caminho(tabuleiro.getCasa(linhaO, colunaO), tabuleiro.getCasa(linhaD, colunaD));
    }

    public boolean ehValida(){
        if(!Tabuleiro.noLimite(linhaO, colunaO) || !Tabuleiro.noLimite(linhaD, colunaD))
            return false;

        if(!tabuleiro.getPeca(linhaO, colunaO).getCor().equals(jogador.getCor()))
            return false;

        if(tabuleiro.casaOcupada(linhaD, colunaD) && tabuleiro.getPeca(linhaD, colunaD).getCor().equals(jogador.getCor()))
            return false;

        if(tabuleiro.getPeca(linhaO, colunaO).getTipo() != 'N' && !caminho.estaLivre(tabuleiro))
            return false;

        if(!tabuleiro.getPeca(linhaO, colunaO).movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return false;

        return true;
    }
}
