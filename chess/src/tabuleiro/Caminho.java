package tabuleiro;

import java.util.ArrayList;

public class Caminho {
    private Tabuleiro tabuleiro;
    private ArrayList<Casa> casas;

    public Caminho(Casa casaInicial, Casa casaFinal, Tabuleiro tabuleiro) throws CasaDeOrigemVaziaException{
        if (casaInicial.casaVazia()) {
            throw new CasaDeOrigemVaziaException("Não é possível construir um caminho a partir de uma casa de origem vazia.");
        }
        this.tabuleiro = tabuleiro;
        casas = new ArrayList<>();
        construirCaminho(casaInicial, casaFinal);
    }

    private void construirCaminho(Casa inicio, Casa fim) {
        String c = inicio.getPeca().caminho(inicio.getLinha(), inicio.getColuna(), fim.getLinha(), fim.getColuna());
        for (int i = 0; i < c.length(); i+=2)
            casas.add(tabuleiro.getCasa(Character.getNumericValue(c.charAt(i)), c.charAt(i + 1)));
    }

    public boolean estaLivre() {
        if (casas.isEmpty()) {
            return true;
        }

        for (Casa casa : casas) {
            if (!casa.casaVazia()) {
                return false;
            }
        }

        return true;
    }

    public Casa casaInicial() {
        if (casas.isEmpty()) {
            return null;
        }
        return casas.getFirst();
    }

    public Casa casaFinal() {
        if (casas.isEmpty()) {
            return null;
        }
        return casas.getLast();
    }
}
