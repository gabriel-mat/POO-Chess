package tabuleiro;
import pecas.*;

public class Jogada {
    private final Jogador jogador;
    private final Tabuleiro tabuleiro;
    private final int linhaO, linhaD;
    private final char colunaO, colunaD;
    private final Caminho caminho;

    public Jogada(Jogador jogador, Tabuleiro tabuleiro, int linhaO, char colunaO, int linhaD, char colunaD) {
        this.jogador = jogador;
        this.linhaO = linhaO;
        this.colunaO = colunaO;
        this.linhaD = linhaD;
        this.colunaD = colunaD;
        this.tabuleiro = tabuleiro;
        caminho = new Caminho(tabuleiro.getCasa(linhaO, colunaO), tabuleiro.getCasa(linhaD, colunaD), tabuleiro);
    }

    public boolean ehValida() {
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


    public boolean ehXeque(Jogador jogadorAlvo, Tabuleiro tabuleiro) {
        Casa casaDoRei = null;

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa casa = tabuleiro.getCasa(i, j);
                if (casa != null && !casa.casaVazia()) {
                    Peca peca = casa.getPeca();
                    if (peca.getCor().equals(jogadorAlvo.getCor()) && peca.getTipo() == 'K') {
                        casaDoRei = casa;
                        break;
                    }
                }
            }
            if (casaDoRei != null) break;
        }

        if (casaDoRei == null) return false;

        String corOponente = jogadorAlvo.getCor().equals("Branco") ? "Preto" : "Branco";

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa casa = tabuleiro.getCasa(i, j);
                if (casa != null && !casa.casaVazia()) {
                    Peca peca = casa.getPeca();
                    if (peca.getCor().equals(corOponente)) {
                        if (peca.movimentoValido(i, j, casaDoRei.getLinha(), casaDoRei.getColuna())) {
                            Caminho caminho = new Caminho(casa, casaDoRei, tabuleiro);
                            if (caminho.estaLivre()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean ehXequeMate(Jogador jogadorDaVez, Tabuleiro tabuleiro) {
        if (!ehXeque(jogadorDaVez, tabuleiro)) {
            return false;
        }

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa origem = tabuleiro.getCasa(i, j);
                if (origem != null && !origem.casaVazia()) {
                    Peca pecaAtual = origem.getPeca();
                    if (pecaAtual.getCor().equals(jogadorDaVez.getCor())) {

                        for (int k = 1; k <= 8; k++) {
                            for (char l = 'a'; l <= 'h'; l++) {
                                if (pecaAtual.movimentoValido(i, j, k, l)) {
                                    Peca destinoOriginal = tabuleiro.getPeca(k, l);

                                    // Simula o movimento
                                    tabuleiro.colocarPeca(k, l, pecaAtual);
                                    tabuleiro.colocarPeca(i, j, null);

                                    boolean aindaEmXeque = ehXeque(jogadorDaVez, tabuleiro);

                                    // Desfaz o movimento
                                    tabuleiro.colocarPeca(i, j, pecaAtual);
                                    tabuleiro.colocarPeca(k, l, destinoOriginal);

                                    if (!aindaEmXeque) {
                                        return false; // Existe jogada de escape
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; // Não existe jogada de escape -> xeque-mate
    }

    public String getNotacao() {
        return "" + linhaO + colunaO + linhaD + colunaD;
    }
}
