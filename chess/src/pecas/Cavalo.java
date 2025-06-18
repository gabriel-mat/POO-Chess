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
        String retorno = "";

        if(!movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return retorno;

        if(Math.abs(linhaO - linhaD) == 1 && Math.abs(colunaO - colunaD) == 2){
            if(colunaD > colunaO)
                for(char i = colunaO; i <= colunaD; i++)
                    retorno += linhaO + i;
            else
                for(char i = colunaO; i >= colunaD; i--)
                    retorno += linhaO + i;
        }
        else{
            if(linhaD > linhaO)
                for(int i = linhaO; i <= linhaD; i++)
                    retorno += i + colunaO;
            else
                for(int i = linhaO; i >= linhaD; i--)
                    retorno += i + colunaO;
        }

        retorno += linhaD + colunaD;
        return retorno;
    }
}
