package jogo;

import tabuleiro.*;
import pecas.*;

import java.util.List;
import java.util.ArrayList;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private Jogador j1, j2;
    private Peca[] pecas = new Peca[32];
    private List<Jogada> historicoJogadas;
    private int nJogadas;

    public Jogo(String nome1, String nome2){
        int i = 0;
        this.tabuleiro = new Tabuleiro();
        j1 = new Jogador("Branco", nome1);
        j2 = new Jogador("Preto", nome2);
        iniciarPecas();
        this.historicoJogadas = new ArrayList<>();
        nJogadas = 0;

    }
    public Jogo(String historico) {
        // chama o construtor padrão da classe
        this();

        // vetor de strings extraido a partir do histórico, seperando por quebras de linha
        String[] linhas = historico.split("\n");

        // Refaz cada jogada do jogo anterior para restaurar o estado do tabuleiro
        for (int i = 2; i < linhas.length; i++){
            String notacaoJogada = linhas[i];
            if (notacaoJogada.length() == 4) {
                int linhaO = Character.getNumericValue(notacaoJogada.charAt(0));
                char colunaO = notacaoJogada.charAt(1);
                int linhaD = Character.getNumericValue(notacaoJogada.charAt(2));
                char colunaD = notacaoJogada.charAt(3);

                this.realizarJogada(linhaO, colunaO, linhaD, colunaD);
            }
        }

    }

    public void imprimir() {
        System.out.println("Jogador 1: " + j1.getNome());
        System.out.println("Peças capturadas: " + j1.pecasCapturadas());

        System.out.println(tabuleiro.desenho());

        System.out.println("Jogador 2: " + j2.getNome());
        System.out.println("Peças capturadas: " + j2.pecasCapturadas());
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
    /**
     * Verifica se uma jogada é válida de acordo com as regras básicas do xadrez.
     * Esta função checa os limites do tabuleiro, a posse da peça, se o destino é válido
     * e se o padrão de movimento da peça está correto.
     *
     * @param linhaO  A linha de origem da peça (ex: 1 a 8).
     * @param colunaO A coluna de origem da peça (ex: 'a' a 'h').
     * @param linhaD  A linha de destino da peça (ex: 1 a 8).
     * @param colunaD A coluna de destino da peça (ex: 'a' a 'h').
     * @return {@code true} se a jogada for válida, {@code false} caso contrário.
     */
    public boolean jogadaValida(int linhaO, char colunaO, int linhaD, char colunaD){
        if(!Tabuleiro.noLimite(linhaO, colunaO) || !Tabuleiro.noLimite(linhaD, colunaD))
            return false;
        Peca pecaOrigem = tabuleiro.getPeca( linhaO, colunaO );

        // Checa se existe uma peça na casa de origem, para prevenir NullPointerException
        if (pecaOrigem == null) {
            return false;
        }

        // Delega para a própria peça a validação do seu padrão de movimento.
        if (!pecaOrigem.movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return false;
        }

        // Determina o jogador da vez.
        Jogador jogador;
        if (nJogadas % 2 == 0) jogador = j1;
        else jogador = j2;

        // Checa se a peça movida pertence ao jogador da vez.
        if(!pecaOrigem.getCor().equals(jogador.getCor()))
            return false;

        Peca pecaDestino = tabuleiro.getPeca(linhaD, colunaD);

        // Checa se a casa de destino não está ocupada por uma peça amiga.
        if(pecaDestino != null && pecaDestino.getCor().equals(jogador.getCor()))
            return false;

        return true;

    }
    /**
     * Executa uma jogada, caso ela seja válida.
     * Este método atualiza o estado do tabuleiro, gera a notação algébrica da jogada,
     * adiciona a notação ao histórico e incrementa o contador de jogadas.
     *
     * @param linhaO  A linha de origem da peça.
     * @param colunaO A coluna de origem da peça.
     * @param linhaD  A linha de destino da peça.
     * @param colunaD A coluna de destino da peça.
     */
    public void realizarJogada(int linhaO, char colunaO, int linhaD, char colunaD){
        if (!jogadaValida(linhaO, colunaO, linhaD, colunaD)) return;

        Jogador jogador;
        if (nJogadas % 2 == 0) jogador = j1;
        else jogador = j2;

        Peca pecaMovida = tabuleiro.getPeca(linhaO, colunaO);
        Peca pecaCapturada = tabuleiro.getPeca(linhaD, colunaD);

        tabuleiro.colocarPeca(linhaO, colunaO, null);
        tabuleiro.colocarPeca(linhaD, colunaD, pecaMovida);

        // Cria o objeto Jogada e o armazena
        Jogada novaJogada = new Jogada(jogador, tabuleiro, linhaO, colunaO, linhaD, colunaD);
        this.historicoJogadas.add(novaJogada);

        this.nJogadas++;

    }
    /**
     * Constrói e retorna uma string formatada com o histórico completo das jogadas.
     * O formato segue o padrão PGN (ex: "1. e4 e5 \n 2. Cf3 Cc6").
     *
     * @return Uma string contendo todo o histórico de jogadas formatado.
     */
    public String registroJogo(){
        // Adiciona um título e uma quebra de linha para melhor formatação.
        StringBuilder registro = new StringBuilder();

        // Adiciona os nomes dos jogadores
        registro.append(j1.getNome()).append("\n");
        registro.append(j2.getNome()).append("\n");

        // Adiciona o historico de jogadas
        for (Jogada jogada: this.historicoJogadas) {
            registro.append(jogada.getNotacao()).append("\n");
        }

        return registro.toString();
    }
}
