package jogo;

import pecas.*;
import tabuleiro.Jogada;
import tabuleiro.Jogador;
import tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private Jogador j1, j2;
    private Peca[] pecas = new Peca[32];
    private ArrayList<Jogada> historicoJogadas;
    private int nJogadas;

    public Jogo(String nome1, String nome2) throws CorInvalidaException {
        this.tabuleiro = new Tabuleiro();
        j1 = new Jogador("Branco", nome1);
        j2 = new Jogador("Preto", nome2);
        iniciarPecas();
        this.historicoJogadas = new ArrayList<>();
        nJogadas = 0;
    }

    public Jogo(String historico) throws FormatoArquivoInvalidoException, CorInvalidaException {
        this.tabuleiro = new Tabuleiro();

        // vetor de strings extraido a partir do histórico, seperando por quebras de linha
        String[] linhas = historico.split("\n");
        iniciarPecas();
        this.historicoJogadas = new ArrayList<>();

        if (linhas.length < 2) {
            throw new FormatoArquivoInvalidoException("Arquivo não contém os nomes dos dois jogadores.");
        }

        String[] dadosJ1 = linhas[0].split(" - ");
        this.j1 = new Jogador("Branco", dadosJ1[0]);

        String[] dadosJ2 = linhas[1].split(" - ");
        this.j2 = new Jogador("Preto", dadosJ2[0]);

        // refaz cada jogada do jogo anterior para restaurar o estado do tabuleiro
        for (int i = 2; i < linhas.length; i++) {
            String notacaoJogada = linhas[i];
            if (notacaoJogada.length() != 4) {
                if (!notacaoJogada.trim().isEmpty()) {
                    throw new FormatoArquivoInvalidoException("A jogada '" + notacaoJogada + "' registrada no histórico é inválida.");
                }
                continue;
            }
            int linhaO = Character.getNumericValue(notacaoJogada.charAt(0));
            char colunaO = notacaoJogada.charAt(1);
            int linhaD = Character.getNumericValue(notacaoJogada.charAt(2));
            char colunaD = notacaoJogada.charAt(3);

            realizarJogada(linhaO, colunaO, linhaD, colunaD);
        }
    }

    public boolean ehXeque() {
        Jogador jogadorAlvo = estaNaVez();
        Jogador jogadorOponente = jogadorAlvo.equals(j1) ? j2 : j1;

        return historicoJogadas.getLast().ehXeque(jogadorAlvo, jogadorOponente, tabuleiro);
    }

    public boolean ehXequeMate() {
        if (nJogadas == 0)
            return false;

        Jogador jogadorAlvo = estaNaVez();
        Jogador jogadorOponente = jogadorAlvo.equals(j1) ? j2 : j1;

        return historicoJogadas.getLast().ehXequeMate(jogadorAlvo, jogadorOponente, tabuleiro);
    }

    public int getnJogadas() {
        return nJogadas;
    }


    public void iniciarPartida(Scanner in) {
        String notacaoJogada = "";
        while (!ehXequeMate() && !notacaoJogada.equalsIgnoreCase("parar")) {
            imprimir();
            System.out.println("É a vez de " + estaNaVez().getNome());
            notacaoJogada = estaNaVez().informaJogada();

            if (notacaoJogada.equalsIgnoreCase("parar")) break;

            if (notacaoJogada.length() == 4) {
                try {
                    int linhaO = Character.getNumericValue(notacaoJogada.charAt(0));
                    char colunaO = notacaoJogada.charAt(1);
                    int linhaD = Character.getNumericValue(notacaoJogada.charAt(2));
                    char colunaD = notacaoJogada.charAt(3);

                    realizarJogada(linhaO, colunaO, linhaD, colunaD);
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Erro: " + e.getMessage());
                    System.out.println("Por favor, tente novamente.");
                } catch (Exception e) { // Captura outros possíveis erros de parsing
                    System.out.println("Formato de jogada inválido. Use o formato '1a3a'.");
                }
            } else {
                System.out.println("Formato de jogada inválido. Use o formato '1a3a'.");
            }
        }
        if (ehXequeMate()) {
            System.out.println("Xeque Mate!");
        }
        imprimir();
    }

    public void imprimir() {
        System.out.println("\nJogador 1: " + j1.getNome());
        System.out.println("Peças capturadas: " + j1.pecasCapturadas());

        System.out.println(tabuleiro.desenho());

        System.out.println("Jogador 2: " + j2.getNome());
        System.out.println("Peças capturadas: " + j2.pecasCapturadas() + "\n");
    }

    public boolean jogadaValida(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (!Tabuleiro.noLimite(linhaO, colunaO) || !Tabuleiro.noLimite(linhaD, colunaD)) {
            return false;
        }

        Peca pecaOrigem = tabuleiro.getPeca(linhaO, colunaO);
        if (pecaOrigem == null) {
            return false;
        }

        Jogador jogadorDaVez = estaNaVez();
        Jogador jogadorOponente = jogadorDaVez.equals(j1) ? j2 : j1;

        Jogada tentativa = new Jogada(jogadorDaVez, tabuleiro, linhaO, colunaO, linhaD, colunaD);
        if (!tentativa.ehValida()) {
            return false;
        }

        Peca pecaDestinoOriginal = tabuleiro.getPeca(linhaD, colunaD);

        tabuleiro.colocarPeca(linhaD, colunaD, pecaOrigem);
        tabuleiro.colocarPeca(linhaO, colunaO, null);

        boolean ficouEmXeque = tentativa.ehXeque(jogadorDaVez, jogadorOponente, this.tabuleiro);

        tabuleiro.colocarPeca(linhaO, colunaO, pecaOrigem);
        tabuleiro.colocarPeca(linhaD, colunaD, pecaDestinoOriginal);

        if (ficouEmXeque) {
            return false;
        }

        return true;
    }

    public void realizarJogada(int linhaO, char colunaO, int linhaD, char colunaD)  throws MovimentoInvalidoException{
        if (!jogadaValida(linhaO, colunaO, linhaD, colunaD)) {
            throw new MovimentoInvalidoException("A jogada de " + linhaO + colunaO + " para " + linhaD + colunaD + " não é permitida.");
        }

        Jogador jogador = estaNaVez();
        Jogada novaJogada = new Jogada(jogador, tabuleiro, linhaO, colunaO, linhaD, colunaD);

        Peca pecaMovida = tabuleiro.getPeca(linhaO, colunaO);
        Peca pecaCapturada = tabuleiro.getPeca(linhaD, colunaD);

        if (pecaCapturada != null)
            jogador.adicionarCapturada(pecaCapturada.getTipo());

        tabuleiro.colocarPeca(linhaO, colunaO, null);
        tabuleiro.colocarPeca(linhaD, colunaD, pecaMovida);

        this.historicoJogadas.add(novaJogada);
        this.nJogadas++;
    }

    public String registroJogo() {
        StringBuilder registro = new StringBuilder();

        registro.append(j1.getNome()).append("\n");
        registro.append(j2.getNome()).append("\n");

        for (Jogada jogada : this.historicoJogadas)
            registro.append(jogada.getNotacao()).append("\n");

        return registro.toString();
    }

    public Jogador estaNaVez() {
        if (nJogadas % 2 == 0) return j1;
        else return j2;
    }

    private void iniciarPecas() throws CorInvalidaException{
        int i = 0;

        while (i < 8) {
            pecas[i] = new Peao("Branco");
            tabuleiro.colocarPeca(2, (char) ('a' + i), pecas[i]);
            i++;
        }
        while (i < 16) {
            pecas[i] = new Peao("Preto");
            tabuleiro.colocarPeca(7, (char) ('a' + i - 8), pecas[i]);
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

    public Jogador getJ1() {
        return j1;
    }

    public Jogador getJ2() {
        return j2;
    }
}
