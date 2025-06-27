package tabuleiro;

public class Caminho {
    private Tabuleiro tabuleiro;
    private Casa casaInicial, casaFinal;

    public Caminho(Casa casaInicial, Casa casaFinal, Tabuleiro tabuleiro){
        this.casaInicial = casaInicial;
        this.casaFinal = casaFinal;
        this.tabuleiro = tabuleiro;
    }

    public boolean estaLivre() {
        String caminho = casaInicial.getPeca().caminho(casaInicial.getLinha(), casaInicial.getColuna(), casaFinal.getLinha(), casaFinal.getColuna());
        int tamanho = caminho.length();

        for(int i = 2; i < tamanho - 2 ; i+=2){
            if(tabuleiro.casaOcupada(caminho.charAt(i) - '0', caminho.charAt(i + 1))){
                return false;
            }
        }
        return true;
    }

    public Casa casaInicial() {
        return casaInicial;
    }

    public Casa casaFinal() {
        return casaFinal;
    }
}
