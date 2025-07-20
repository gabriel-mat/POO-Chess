package tabuleiro;
import pecas.*;
import jogo.Jogo;

public class Jogada {
    private Jogo jogo;

    private final Jogador jogador;
    private final Tabuleiro tabuleiro;
    private final int linhaO, linhaD;
    private final char colunaO, colunaD;
    private final Caminho caminho;

    public Jogada(Jogador jogador, Jogo jogo, Tabuleiro tabuleiro, int linhaO, char colunaO, int linhaD, char colunaD) {
        this.jogador = jogador;
        this.linhaO = linhaO;
        this.colunaO = colunaO;
        this.linhaD = linhaD;
        this.colunaD = colunaD;
        this.jogo = jogo;
        this.tabuleiro = tabuleiro;
        caminho = new Caminho(tabuleiro.getCasa(linhaO, colunaO), tabuleiro.getCasa(linhaD, colunaD), tabuleiro);
    }

    public boolean ehValida() throws CasaDeOrigemVaziaException {
        if (tabuleiro.getPeca(linhaO, colunaO) == null) {
            throw new CasaDeOrigemVaziaException("A casa de origem " + linhaO + colunaO + " está vazia.");
        }

        if (!Tabuleiro.noLimite(linhaO, colunaO) || !Tabuleiro.noLimite(linhaD, colunaD))
            return false;

        if (!tabuleiro.getPeca(linhaO, colunaO).getCor().equals(jogador.getCor()))
            return false;

        if (tabuleiro.casaOcupada(linhaD, colunaD) && tabuleiro.getPeca(linhaD, colunaD).getCor().equals(jogador.getCor()))
            return false;

        if (tabuleiro.getPeca(linhaO, colunaO).getTipo() != 'N' && !caminho.estaLivre())
            return false;

        if (!tabuleiro.getPeca(linhaO, colunaO).movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return false;
        //Não pode ser xeque tbm no próprio rei
        return true;
    }

    /**
     * Verifica se o rei de um determinado jogador está sob ataque (em xeque).
     * A implementação está em Jogada.java conforme solicitado.
     * @param jogadorAlvo O jogador cujo rei será verificado.
     * @return true se o rei do jogador estiver em xeque, false caso contrário.
     */
    public boolean ehXeque(Jogador jogadorAlvo) {
        Casa casaDoRei = null;

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa casa = this.tabuleiro.getCasa(i, j);
                if (casa != null && !casa.casaVazia() && casa.getPeca().getCor().equals(jogadorAlvo.getCor()) && casa.getPeca().getTipo() == 'K') {
                    casaDoRei = casa;
                    break;
                }
            }
            if (casaDoRei != null) break;
        }

        if (casaDoRei == null) return false;

        Jogador oponente = (jogadorAlvo == jogo.getJ1()) ? jogo.getJ2() : jogo.getJ1();
        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa casaAtual = this.tabuleiro.getCasa(i, j);
                if (casaAtual != null && !casaAtual.casaVazia() && casaAtual.getPeca().getCor().equals(oponente.getCor())) {
                    Peca pecaOponente = casaAtual.getPeca();

                    if (pecaOponente.movimentoValido(casaAtual.getLinha(), casaAtual.getColuna(), casaDoRei.getLinha(), casaDoRei.getColuna())) {
                        Caminho caminhoAtaque = new Caminho(casaAtual, casaDoRei, this.tabuleiro);
                        if (caminhoAtaque.estaLivre()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o jogador da vez está em xeque-mate.
     * A implementação está em Jogada.java conforme solicitado.
     */
    public boolean ehXequeMate() {
        Jogador jogadorDaVez = jogo.estaNaVez();
        if (!this.ehXeque(jogadorDaVez)) {
            return false;
        }

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa casaOrigem = this.tabuleiro.getCasa(i, j);
                if (casaOrigem != null && !casaOrigem.casaVazia() && casaOrigem.getPeca().getCor().equals(jogadorDaVez.getCor())) {
                    Peca pecaAtual = casaOrigem.getPeca();

                    for (int k = 1; k <= 8; k++) {
                        for (char l = 'a'; l <= 'h'; l++) {

                            if (jogo.jogadaValida(i, j, k, l)) {

                                Peca pecaDestinoOriginal = this.tabuleiro.getPeca(k, l);
                                this.tabuleiro.colocarPeca(k, l, pecaAtual);
                                this.tabuleiro.colocarPeca(i, j, null);

                                boolean aindaEmXeque = this.ehXeque(jogadorDaVez);

                                this.tabuleiro.colocarPeca(i, j, pecaAtual);
                                this.tabuleiro.colocarPeca(k, l, pecaDestinoOriginal);

                                if (!aindaEmXeque) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Retorna a notação da jogada no formato para arquivo (ex: "1a3b").
     *
     * @return A string da jogada formatada para salvamento.
     */
    public String getNotacao() {
        return "" + linhaO + colunaO + linhaD + colunaD;
    }
}
