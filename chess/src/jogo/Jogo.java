package jogo;

import tabuleiro.*;
import pecas.*;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private Jogador j1, j2;
    private Peca[] pecas = new Peca[32];

    public Jogo(){
        int i = 0;
        this.tabuleiro = new Tabuleiro();
        j1 = j2 = null;
        iniciarPecas();
    }

    private void iniciarPecas(){
        int i = 0;

        while(i < 8) {
            pecas[i] = new Peao("Branco");
            tabuleiro.colocarPeca(2, (char)('a' + i), pecas[i]);
            i++;
        }
        while(i < 16) {
            pecas[i] = new Peao("Preto");
            tabuleiro.colocarPeca(7, (char)('a' + i - 8), pecas[i]);
            i++;
        }

        pecas[i++] = new Torre("Branco");
        tabuleiro.colocarPeca(1, 'a', pecas[i - 1]);

        pecas[i++] = new Torre("Branco");
        tabuleiro.colocarPeca(1, 'h', pecas[i - 1]);

        pecas[i++] = new Torre("Preto");
        tabuleiro.colocarPeca(8, 'a', pecas[i - 1]);

        pecas[i++] = new Torre("Preto");
        tabuleiro.colocarPeca(8, 'h', pecas[i - 1]);

        pecas[i++] = new Cavalo("Branco");
        tabuleiro.colocarPeca(1, 'b', pecas[i - 1]);

        pecas[i++] = new Cavalo("Branco");
        tabuleiro.colocarPeca(1, 'g', pecas[i - 1]);

        pecas[i++] = new Cavalo("Preto");
        tabuleiro.colocarPeca(8, 'b', pecas[i - 1]);

        pecas[i++] = new Cavalo("Preto");
        tabuleiro.colocarPeca(8, 'g', pecas[i - 1]);

        pecas[i++] = new Bispo("Branco");
        tabuleiro.colocarPeca(1, 'c', pecas[i - 1]);

        pecas[i++] = new Bispo("Branco");
        tabuleiro.colocarPeca(1, 'f', pecas[i - 1]);

        pecas[i++] = new Bispo("Preto");
        tabuleiro.colocarPeca(8, 'c', pecas[i - 1]);

        pecas[i++] = new Bispo("Preto");
        tabuleiro.colocarPeca(8, 'f', pecas[i - 1]);

        pecas[i++] = new Dama("Branco");
        tabuleiro.colocarPeca(1, 'd', pecas[i - 1]);

        pecas[i++] = new Dama("Preto");
        tabuleiro.colocarPeca(8, 'd', pecas[i - 1]);

        pecas[i++] = new Rei("Branco");
        tabuleiro.colocarPeca(1, 'e', pecas[i - 1]);

        pecas[i++] = new Rei("Preto");
        tabuleiro.colocarPeca(8, 'e', pecas[i - 1]);
    }
}
