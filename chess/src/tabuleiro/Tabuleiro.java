package tabuleiro;

import pecas.Peca;

public class Tabuleiro {
    private static final int DIMENSAO = 8;
    Casa[][] casas = new Casa[DIMENSAO][DIMENSAO];

    public Tabuleiro(){
        for(int i = 1; i <= DIMENSAO; i++) {
            for (char j = 'a'; j < 'a' + DIMENSAO; j++) {
                if (i % 2 == 0) {
                    if ((j - 'a') % 2 == 0)
                        casas[i - 1][j - 'a'] = new Casa("Branco", i, j);
                    else
                        casas[i - 1][j - 'a'] = new Casa("Preto", i, j);
                } else {
                    if ((j - 'a') % 2 == 0)
                        casas[i - 1][j - 'a'] = new Casa("Preto", i, j);
                    else
                        casas[i - 1][j - 'a'] = new Casa("Branco", i, j);
                }
            }
        }
    }

    public static boolean noLimite(int linha, char coluna){
        return (linha >= 1 && linha <= DIMENSAO && coluna >= 'a' && coluna < 'a' + DIMENSAO);
    }

    public String desenho(){
        StringBuilder tabuleiro = new StringBuilder();

        for(int i = 0; i < DIMENSAO; i++) {
            for (int j = 0; j < DIMENSAO; j++) {
                if (casas[i][j].getPeca() == null)
                    tabuleiro.append(String.format("%2s ", "."));
                else
                    tabuleiro.append(String.format("%2s ", casas[i][j].getPeca().desenho()));
            }
            tabuleiro.append("\n");
        }

        return tabuleiro.toString();
    }

    public boolean casaOcupada(int linha, char coluna){
        return (casas[linha - 1][coluna - 'a'].getPeca() != null);
    }

    public Peca getPeca(int linha, int coluna){
        return casas[linha - 1][coluna - 'a'].getPeca();
    }

    public boolean colocarPeca(int linha, char coluna, Peca peca){
        if(!noLimite(linha, coluna))
            return false;

        casas[linha - 1][coluna - 'a'].setPeca(peca);
        return true;
    }
}
