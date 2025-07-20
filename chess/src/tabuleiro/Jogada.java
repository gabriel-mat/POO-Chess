package tabuleiro;

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
        //Não pode ser xeque tbm no próprio rei
        return true;
    }

    public boolean ehXeque() {

        if (jogador.getCor().equals("Branco")) {
            for (int i = 1; i <= 8; i++) {
                for (char c = 'a'; c <= 'h'; c++) {
                    if (tabuleiro.getPeca(i, c).getTipo() == 'K' && tabuleiro.getPeca(i, c).getCor().equals("Preto")) {
                        Casa reiPreto = tabuleiro.getCasa(i, c);
                        if (!tabuleiro.getPeca(linhaO, colunaO).movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
                            return false;
                        }
                        Caminho caminho = new Caminho(tabuleiro.getCasa(linhaD, colunaD), reiPreto, tabuleiro);
                        if (caminho.estaLivre()) {
                            return true;
                        }
                        return false;

                        for (int i = 1; i <= 8; i++)
                            for (char c = 'a'; c <= 'h'; c++)
                                if (tabuleiro.getPeca(i, c).getTipo() == 'K' && tabuleiro.getPeca(i, c).getCor().equals("Branco"))
                                    reiBranco = tabuleiro.getCasa(i, c);
                                else if (tabuleiro.getPeca(i, c).getTipo() == 'k' && tabuleiro.getPeca(i, c).getCor().equals("Preto"))
                                    reiPreto = tabuleiro.getCasa(i, c);

                        for (int i = 1; i <= 8; i++) {
                            for (char c = 'a'; c <= 'h'; c++) {
                                if (tabuleiro.getCasa(i, c) != reiBranco && tabuleiro.getPeca(i, c).getCor().equals("Branco")) {
                                    caminho = new Caminho(tabuleiro.getCasa(i, c), reiPreto, tabuleiro);
                                    if (tabuleiro.getPeca(i, c).movimentoValido(i, c, reiPreto.getLinha(), reiPreto.getColuna())
                                            && caminho.estaLivre())
                                        return true;
                                }
                            }
                        }
                    } else {
                        for (int i = 1; i <= 8; i++) {
                            for (char c = 'a'; c <= 'h'; c++) {
                                if (tabuleiro.getPeca(i, c).getTipo() == 'K' && tabuleiro.getPeca(i, c).getCor().equals("Branco")) {
                                    Casa reiBranco = tabuleiro.getCasa(i, c);
                                    if (!tabuleiro.getPeca(linhaO, colunaO).movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
                                        return false;
                                    }
                                    Caminho caminho = new Caminho(tabuleiro.getCasa(linhaD, colunaD), reiBranco, tabuleiro);
                                    if (caminho.estaLivre()) {
                                        return true;
                                    }
                                    return false;

                                }
                            }
                        }
                    }
                    return false;
                }
            }
        }
    }

    /**
     * Retorna a notação da jogada no formato para arquivo (ex: "1a3b").
     * @return A string da jogada formatada para salvamento.
     */
    public String getNotacao() {
        return "" + linhaO + colunaO + linhaD + colunaD;
    }
}
