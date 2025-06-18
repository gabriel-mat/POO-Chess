package pecas;

public class Rei extends Peca{
    public Rei(String cor){
        super(cor);
    }

    public char getTipo(){
        return 'K';
    }

    public String desenho(){
        if(getCor().equals("Branco"))
            return "K";
        return "k";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD){
        return ((linhaO == linhaD && Math.abs(colunaO - colunaD) == 1)
                || (colunaO == colunaD && Math.abs(linhaO - linhaD) == 1)
                || (Math.abs(linhaO - linhaD) == 1) && (Math.abs(colunaO - colunaD) == 1));
    }

    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD){
        String retorno = "";

        if(!movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return retorno;

        return retorno + linhaO + colunaO + linhaD + colunaD;
    }
}
