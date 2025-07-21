package pecas;

public class Peao extends Peca{
    public Peao(String cor) throws CorInvalidaException {
        super(cor);
    }

    public char getTipo(){
        return 'P';
    }

    public String desenho(){
        if(getCor().equals("Branco"))
            return "P";
        return "p";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (getCor().equals("Branco"))
            return ((colunaO == colunaD) && (linhaD == linhaO + 1)) ||
                    ((Math.abs(colunaD - colunaO) == 1) && (linhaD == linhaO + 1) ||
                            linhaO == 2 && colunaO == colunaD && linhaD == linhaO + 2);

        return ((colunaO == colunaD) && (linhaD == linhaO - 1)) ||
                ((Math.abs(colunaD - colunaO) == 1) && (linhaD == linhaO - 1) ||
                            linhaO == 7 && colunaO == colunaD && linhaD == linhaO - 2);

    }

    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD){
        String retorno = "";

        if(!movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return retorno;

        return retorno + linhaO + colunaO + linhaD + colunaD;
    }
}
