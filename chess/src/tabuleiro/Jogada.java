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

        // Checa se existe uma peça na casa de origem, para prevenir NullPointerException
        if (pecaOrigem == null) {
            throw new CasaDeOrigemVaziaException("A casa de origem " + linhaO + colunaO + " está vazia.");
        }

        // Delega para a própria peça a validação do seu padrão de movimento.
        if (!pecaOrigem.movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return false;
        }

        // Regra específica do peão: se movimento for diagonal, só pode se for captura
        if (pecaOrigem.getTipo() == 'P') {
            int deltaColuna = colunaD - colunaO;
            if (Math.abs(deltaColuna) == 1) {
                if (pecaDestino == null || pecaDestino.getCor().equals(jogador.getCor())) {
                    return false; // tentou capturar no vazio ou peça amiga
                }
            } else {
                if (pecaDestino != null) {
                    return false; // tentou andar pra frente mas tinha peça no caminho
                }
            }
        }

        // Checa se a peça movida pertence ao jogador da vez.
        if (!pecaOrigem.getCor().equals(jogador.getCor()))
            return false;

        // Checa se a casa de destino não está ocupada por uma peça amiga.
        if (pecaDestino != null && pecaDestino.getCor().equals(jogador.getCor()))
            return false;

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
                        if (caminhoAtaque.estaLivre()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean ehXequeMate(Jogador jogadorAlvo, Jogador jogadorOponente, Tabuleiro tabuleiro) {
        if (!ehXeque(jogadorAlvo, jogadorOponente, tabuleiro)) {
            return false;
        }

        for (int i = 1; i <= 8; i++) {
            for (char j = 'a'; j <= 'h'; j++) {
                Casa origem = tabuleiro.getCasa(i, j);
                if (origem == null || origem.casaVazia()) continue;

                Peca pecaAtual = origem.getPeca();
                if (!pecaAtual.getCor().equals(jogadorAlvo.getCor())) continue;

                for (int k = 1; k <= 8; k++) {
                    for (char l = 'a'; l <= 'h'; l++) {

                        if (!pecaAtual.movimentoValido(i, j, k, l)) {
                            continue;
                        }

                        Peca pecaDestino = tabuleiro.getPeca(k, l);
                        if (pecaDestino != null && pecaDestino.getCor().equals(jogadorAlvo.getCor())) {
                            continue;
                        }

                        Jogada tentativa = new Jogada(jogadorAlvo, tabuleiro, i, j, k, l);

                        if (tentativa.ehValida()) {

                            Peca destinoOriginal = tabuleiro.getPeca(k, l);
                            tabuleiro.colocarPeca(k, l, pecaAtual);
                            tabuleiro.colocarPeca(i, j, null);

                            boolean aindaEmXeque = ehXeque(jogadorAlvo, jogadorOponente, tabuleiro);

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

        return true; // Não existe jogada de escape -> xeque-mate
    }

    public String getNotacao() {
        return "" + linhaO + colunaO + linhaD + colunaD;
    }
}
