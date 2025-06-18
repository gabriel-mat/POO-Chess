package pecas;

import java.lang.Math;

public class Bispo extends Peca{
    public Bispo (String cor) {
        super(cor);
    }

    public char getTipo(){
        return 'B';
    }

    public String desenho(){
        if(getCor().equals("Branco"))
            return "B";
        return "b";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD){
        return (Math.abs(linhaD - linhaO) == Math.abs(colunaD - colunaO));
    }

    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD){
        String retorno = "";

        if(!movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return retorno;

        if(linhaD > linhaO){
            if(colunaD > colunaO)
                for(int i = linhaO; i <= linhaD; i++)
                    retorno += i + colunaO++;
            else
                for(int i = linhaO; i <= linhaD; i++)
                    retorno += i + colunaO--;
        }else{
            if(colunaD > colunaO)
                for(int i = linhaO; i >= linhaD; i--)
                    retorno += i + colunaO++;
            else
                for(int i = linhaO; i >= linhaD; i--)
                    retorno += i + colunaO--;
        }

        return retorno;
    }
}
