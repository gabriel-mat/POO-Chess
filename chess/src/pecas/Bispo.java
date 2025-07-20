package pecas;

public class Bispo extends Peca {
    public Bispo(String cor) {
        super(cor);
    }

    public char getTipo() {
        return 'B';
    }

    public String desenho() {
        if (getCor().equals("Branco"))
            return "B";
        return "b";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        return (Math.abs(linhaD - linhaO) == Math.abs(colunaD - colunaO));
    }

    //    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD){
//        String retorno = "";
//
//        if(!movimentoValido(linhaO, colunaO, linhaD, colunaD))
//            return retorno;
//
//        if(linhaD > linhaO){
//            if(colunaD > colunaO)
//                for(int i = linhaO; i <= linhaD; i++)
//                    retorno += i + colunaO++;
//            else
//                for(int i = linhaO; i <= linhaD; i++)
//                    retorno += i + colunaO--;
//        }else{
//            if(colunaD > colunaO)
//                for(int i = linhaO; i >= linhaD; i--)
//                    retorno += i + colunaO++;
//            else
//                for(int i = linhaO; i >= linhaD; i--)
//                    retorno += i + colunaO--;
//        }
//
//        return retorno;
//    }
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        StringBuilder caminho = new StringBuilder();

        // Define a direção do movimento na diagonal
        int dirLinha = (linhaD > linhaO) ? 1 : -1;
        int dirColuna = (colunaD > colunaO) ? 1 : -1;

        int linhaAtual = linhaO + dirLinha;
        char colunaAtual = (char) (colunaO + dirColuna);

        while (linhaAtual != linhaD && colunaAtual != colunaD) {
            caminho.append(linhaAtual).append(colunaAtual);
            linhaAtual += dirLinha;
            colunaAtual = (char) (colunaAtual + dirColuna);
        }

        return caminho.toString();
    }
}
