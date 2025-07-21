package tabuleiro;

import java.util.Scanner;
import pecas.Peca;

public class Jogador {
    private int numPecas;
    private int numCapturadas;
    private final String cor, nome;
    private Peca[] pecas = new Peca[16];
    private char[] capturadas = new char[16];

    public Jogador(String cor, String nome){
        this.cor = cor;
        this.nome = nome;
        numCapturadas = numPecas = 0;

        for(int i = 0; i < 16; i++) {
            pecas[i] = null;
            capturadas[i] = '\0';
        }
    }

    public String getCor(){
        return cor;
    }

    public String getNome(){
        return nome;
    }

    public String informaJogada(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira sua jogada (ou 'parar'):");
        return scanner.nextLine();
    }

    public boolean adicionarPeca(Peca p){
        if(numPecas >= 16)
            return false;

        pecas[numPecas++] = p;
        return true;
    }

    public boolean adicionarCapturada(char tipo){
        if(numCapturadas >= 16)
            return false;

        capturadas[numCapturadas++] = tipo;
        return true;
    }

    public int getNumPecas(){
        return numPecas;
    }

    public int getNumCapturadas(){
        return numCapturadas;
    }

    public String pecasCapturadas(){
        String s = "";

        for(int i = 0; i < numCapturadas; i++)
            s += capturadas[i] + " ";

        return s == "" ? "-" : s.substring(0, s.length() - 1);
    }
}
