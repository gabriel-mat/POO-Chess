package pecas;

public class Dama extends Peca{
    public Dama(String cor){
        super(cor);
    }

    public char getTipo(){
        return 'Q';
    }

    public String desenho(){
        return "Q";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD){
        return ((Math.abs(linhaD - linhaO) == Math.abs(colunaD - colunaO))
                || ((linhaO == linhaD) || (colunaO == colunaD))) ;
    }

    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD){
        String retorno = "";

        if(!movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return retorno;

        if(colunaO == colunaD){
            if(linhaO < linhaD)
                for(int i = linhaO; i <= linhaD; i++)
                    retorno += i + colunaO;
            else
                for(int i = linhaO; i >= linhaD; i--)
                    retorno += i + colunaO;
        }
        else if (linhaO == linhaD){
            if(colunaO < colunaD)
                for(char i = colunaO; i <= colunaD; i++)
                    retorno += linhaO + i;
            else
                for(char i = colunaO; i >= colunaD; i--)
                    retorno += linhaO + i;
        }
        else if(linhaD > linhaO){
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

        retorno += linhaD + colunaD;
        return retorno;
    }
}
