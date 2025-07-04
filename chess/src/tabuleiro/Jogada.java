package tabuleiro;

import pecas.*;

public class Jogada {
    private final Jogador jogador;
    private final Tabuleiro tabuleiro;
    private final int linhaO, linhaD;
    private final char colunaO, colunaD;
    private final Caminho caminho;

    public Jogada(Jogador jogador, Tabuleiro tabuleiro, int linhaO, char colunaO, int linhaD, char colunaD){
        this.jogador = jogador;
        this.linhaO = linhaO;
        this.colunaO = colunaO;
        this.linhaD = linhaD;
        this.colunaD = colunaD;
        this.tabuleiro = tabuleiro;
        caminho = new Caminho(tabuleiro.getCasa(linhaO, colunaO), tabuleiro.getCasa(linhaD, colunaD), tabuleiro);
    }

    public boolean ehValida(){
        if(!Tabuleiro.noLimite(linhaO, colunaO) || !Tabuleiro.noLimite(linhaD, colunaD))
            return false;

        if(!tabuleiro.getPeca(linhaO, colunaO).getCor().equals(jogador.getCor()))
            return false;

        if(tabuleiro.casaOcupada(linhaD, colunaD) && tabuleiro.getPeca(linhaD, colunaD).getCor().equals(jogador.getCor()))
            return false;

        if(tabuleiro.getPeca(linhaO, colunaO).getTipo() != 'N' && !caminho.estaLivre())
            return false;

        if(!tabuleiro.getPeca(linhaO, colunaO).movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return false;

        return true;
    }

    public boolean ehXeque(){
        Caminho caminho;
        Casa reiPreto, reiBranco;

        caminho = null;
        reiPreto = reiBranco = null;

        for(int i = 1; i <= 8; i++)
            for(char c = 'a'; c <= 'h'; c++)
                if(tabuleiro.getPeca(i, c).getTipo() == 'K' && tabuleiro.getPeca(i, c).getCor().equals("Branco"))
                    reiBranco = tabuleiro.getCasa(i, c);
                else if (tabuleiro.getPeca(i, c).getTipo() == 'K' && tabuleiro.getPeca(i, c).getCor().equals("Preto"))
                    reiPreto = tabuleiro.getCasa(i, c);


        for(int i = 1; i <= 8; i++){
            for(char c = 'a'; c <= 'h'; c++){
                if(tabuleiro.getCasa(i, c) != reiBranco && tabuleiro.getPeca(i, c).getCor().equals("Branco")){
                    caminho = new Caminho(tabuleiro.getCasa(i, c), reiPreto, tabuleiro);
                    if(tabuleiro.getPeca(i, c).movimentoValido(i, c ,reiPreto.getLinha(), reiPreto.getColuna())
                        && caminho.estaLivre())
                        return true;
                }
                else if(tabuleiro.getCasa(i, c) != reiBranco && tabuleiro.getPeca(i, c).getCor().equals("Branco")){
                    //
                }
            }
        }

        return false;
    }
}
