package tabuleiro;

public class Caminho {
    private Casa casaInicial;
    private Casa casaFinal;

    public Caminho(Casa casaInicial, Casa casaFinal){
        this.casaInicial = casaInicial;
        this.casaFinal = casaFinal;
    }

    public boolean estaLivre(Tabuleiro tabuleiro) {
        String caminho = casaInicial.getPeca().caminho(casaInicial.getLinha(), casaInicial.getColuna(), casaFinal.getLinha(), casaFinal.getColuna());
        int tamanho = caminho.length();

        for(int i = 0; i < tamanho ; i+=2){
            if(tabuleiro.casaOcupada(caminho.charAt(i), caminho.charAt(i + 1))){
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
