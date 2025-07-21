package pecas;

public class Torre extends Peca{
    public Torre(String cor) throws CorInvalidaException {
        super(cor);
    }

    public char getTipo(){
        return 'R';
    }

    public String desenho(){
        if(getCor().equals("Branco"))
            return "R";
        return "r";
    }

    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD){
        return (!(linhaO == linhaD) && (colunaO == colunaD) || (linhaO == linhaD) && !(colunaO == colunaD));
    }

    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        StringBuilder caminho = new StringBuilder();

        // Movimento Vertical
        if (colunaO == colunaD) {
            int direcao = (linhaD > linhaO) ? 1 : -1; // Define se está subindo (1) ou descendo (-1)
            for (int i = linhaO + direcao; i != linhaD; i += direcao) {
                caminho.append(i).append(colunaO);
            }
        }
        // Movimento Horizontal
        else if (linhaO == linhaD) {
            int direcao = (colunaD > colunaO) ? 1 : -1; // Define se está indo para a direita (1) ou esquerda (-1)
            for (char i = (char)(colunaO + direcao); i != colunaD; i = (char)(i + direcao)) {
                caminho.append(linhaO).append(i);
            }
        }
        return caminho.toString();
    }
}
