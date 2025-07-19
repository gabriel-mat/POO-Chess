package tabuleiro;

import java.util.ArrayList;

public class Caminho {
    private Tabuleiro tabuleiro;
    private ArrayList<Casa> casas;

    public Caminho(Casa casaInicial, Casa casaFinal, Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;
        casas = new ArrayList<>();
        construirCaminho(casaInicial, casaFinal);
    }

    private void construirCaminho(Casa inicio, Casa fim) {
        String c = inicio.getPeca().caminho(inicio.getLinha(), inicio.getColuna(), fim.getLinha(), fim.getColuna());
        for (int i = 0; i < c.length(); i+=2)
            casas.add(tabuleiro.getCasa(c.charAt(i) - '0', c.charAt(i + 1)));
    }

    public boolean estaLivre() {
        for(int i = 1; i < casas.size()-1; i++)
            if(!casas.get(i).casaVazia())
                return false;

        return true;
    }

    public Casa casaInicial() {
        return casas.getFirst();
    }

    public Casa casaFinal() {
        return casas.getLast();
    }
}
