package pecas;

public class Dama extends Peca{
    public Dama(String cor) throws CorInvalidaException {
        super(cor);
    }

    public char getTipo(){
        return 'Q';
    }

    public String desenho(){
        if(getCor().equals("Branco"))
            return "Q";
        return "q";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD){
        return ((Math.abs(linhaD - linhaO) == Math.abs(colunaD - colunaO))
                || (!(linhaO == linhaD) && (colunaO == colunaD) || (linhaO == linhaD) && !(colunaO == colunaD))) ;
    }

    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD || colunaO == colunaD) {
            StringBuilder caminho = new StringBuilder();
            caminho.append(linhaO).append(colunaO);
            if (colunaO == colunaD) {
                int direcao = (linhaD > linhaO) ? 1 : -1;
                for (int i = linhaO + direcao; i != linhaD; i += direcao) {
                    caminho.append(i).append(colunaO);
                }
            } else {
                int direcao = (colunaD > colunaO) ? 1 : -1;
                for (char i = (char)(colunaO + direcao); i != colunaD; i = (char)(i + direcao)) {
                    caminho.append(linhaO).append(i);
                }
            }
            caminho.append(linhaD).append(colunaD);
            return caminho.toString();
        }
        else {
            StringBuilder caminho = new StringBuilder();
            caminho.append(linhaO).append(colunaO);
            int dirLinha = (linhaD > linhaO) ? 1 : -1;
            int dirColuna = (colunaD > colunaO) ? 1 : -1;
            int linhaAtual = linhaO + dirLinha;
            char colunaAtual = (char)(colunaO + dirColuna);
            while (linhaAtual != linhaD && colunaAtual != colunaD) {
                caminho.append(linhaAtual).append(colunaAtual);
                linhaAtual += dirLinha;
                colunaAtual = (char)(colunaAtual + dirColuna);
            }
            caminho.append(linhaD).append(colunaD);
            return caminho.toString();
        }
    }
}
