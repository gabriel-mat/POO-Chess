package pecas;

import java.lang.Math;

public class Cavalo extends Peca{
    public Cavalo(String cor){
        super(cor);
    }

    public char getTipo(){
        return 'N';
    }

    public String desenho(){
        if(getCor().equals("Branco"))
            return "N";
        return "n";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD){
        return ((Math.abs(linhaO - linhaD) == 1 && Math.abs(colunaO - colunaD) == 2)
                || (Math.abs(linhaO - linhaD) == 2 && Math.abs(colunaO - colunaD) == 1)) ;
    }

    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD){

        if (!movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "";
        }

        StringBuilder caminho = new StringBuilder();
        int deltaLinha = linhaD - linhaO;
        int deltaColuna = colunaD - colunaO;

        if (Math.abs(deltaLinha) == 2) {
            int dirLinha = deltaLinha > 0 ? 1 : -1;

            caminho.append(linhaO).append(colunaO);

            caminho.append(linhaO + dirLinha).append(colunaO);

            caminho.append(linhaD).append(colunaD);
        }

        else {
            int dirColuna = deltaColuna > 0 ? 1 : -1;

            caminho.append(linhaO).append(colunaO);

            caminho.append(linhaO).append((char)(colunaO + dirColuna));

            caminho.append(linhaD).append(colunaD);
        }

        return caminho.toString();
    }
}
