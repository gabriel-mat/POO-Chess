package tabuleiro;

import pecas.Peca;

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

    public boolean ehValida()  throws CasaDeOrigemVaziaException {
        Peca pecaOrigem = tabuleiro.getPeca(linhaO, colunaO);
        Peca pecaDestino = tabuleiro.getPeca(linhaD, colunaD);

        if (!Tabuleiro.noLimite(linhaO, colunaO) || !Tabuleiro.noLimite(linhaD, colunaD))
            return false;

        if (pecaOrigem == null)
            throw new CasaDeOrigemVaziaException("A casa de origem " + linhaO + colunaO + " está vazia.");

        if (!pecaOrigem.getCor().equals(jogador.getCor()))
            return false;

        if (pecaDestino != null && pecaDestino.getCor().equals(jogador.getCor()))
            return false;

        if (!pecaOrigem.movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return false;

        if (pecaOrigem.getTipo() != 'N' && !caminho.estaLivre())
            return false;

        if (pecaOrigem.getTipo() == 'P') {
            int deltaColuna = colunaD - colunaO;
            if (Math.abs(deltaColuna) == 1) {
                if (pecaDestino == null || pecaDestino.getCor().equals(jogador.getCor()))
                    return false;
            } else {
                if (pecaDestino != null)
                    return false;
            }
        }

        return true;
    }

    public boolean ehXeque(Jogador jogadorAlvo, Jogador jogadorOponente, Tabuleiro tabuleiro) {
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

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa casaAtual = this.tabuleiro.getCasa(i, j);
                if (casaAtual != null && !casaAtual.casaVazia() && casaAtual.getPeca().getCor().equals(jogadorOponente.getCor())) {
                    Peca pecaOponente = casaAtual.getPeca();

                    if (pecaOponente.movimentoValido(casaAtual.getLinha(), casaAtual.getColuna(), casaDoRei.getLinha(), casaDoRei.getColuna())) {
                        Caminho caminhoAtaque = new Caminho(casaAtual, casaDoRei, this.tabuleiro);
                        if (caminhoAtaque.estaLivre())
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean ehXequeMate(Jogador jogadorAlvo, Jogador jogadorOponente, Tabuleiro tabuleiro) {
        if (!ehXeque(jogadorAlvo, jogadorOponente, tabuleiro))
            return false;

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa origem = tabuleiro.getCasa(i, j);
                if (origem == null || origem.casaVazia()) continue;

                Peca pecaAtual = origem.getPeca();
                if (!pecaAtual.getCor().equals(jogadorAlvo.getCor())) continue;

                for (int k = 1; k <= 8; k++) {
                    for (char l = 'a'; l <= 'h'; l++) {

                        if (!pecaAtual.movimentoValido(i, j, k, l))
                            continue;

                        Peca pecaDestino = tabuleiro.getPeca(k, l);
                        if (pecaDestino != null && pecaDestino.getCor().equals(jogadorAlvo.getCor()))
                            continue;

                        Jogada tentativa = new Jogada(jogadorAlvo, tabuleiro, i, j, k, l);

                        if (tentativa.ehValida()) {

                            Peca destinoOriginal = tabuleiro.getPeca(k, l);
                            tabuleiro.colocarPeca(k, l, pecaAtual);
                            tabuleiro.colocarPeca(i, j, null);

                            boolean aindaEmXeque = ehXeque(jogadorAlvo, jogadorOponente, tabuleiro);

                            tabuleiro.colocarPeca(i, j, pecaAtual);
                            tabuleiro.colocarPeca(k, l, destinoOriginal);

                            if (!aindaEmXeque)
                                return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public String getNotacao() {
        return "" + linhaO + colunaO + linhaD + colunaD;
    }
}
