package pecas;

public class Torre extends Peca{
    public Torre(String cor){
        super(cor);
    }

    public char getTipo(){
        return 'R';
    }

    public String desenho(){
        return "R";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD){
        return ((linhaO == linhaD) || (colunaO == colunaD));
    }

    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD){
        String retorno = "";

        if(!movimentoValido(linhaO, colunaO, linhaD, colunaD))
            return retorno;

        if(linhaO != linhaD){
            if(linhaO < linhaD)
                for(int i = linhaO; i <= linhaD; i++)
                    retorno += i + colunaO;
            else
                for(int i = linhaO; i >= linhaD; i--)
                    retorno += i + colunaO;
        }
        else{
            if(colunaO < colunaD)
                for(char i = colunaO; i <= colunaD; i++)
                    retorno += linhaO + i;
            else
                for(char i = colunaO; i >= colunaD; i--)
                    retorno += linhaO + i;
        }

        return retorno;
    }
}
