package pecas;

public class Bispo extends Peca {
    public Bispo(String cor) throws CorInvalidaException {
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


    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        StringBuilder caminho = new StringBuilder();
        caminho.append(linhaO).append(colunaO);

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
        caminho.append(linhaD).append(colunaD);

        return caminho.toString();
    }
}
