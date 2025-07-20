package jogo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Gerenciador {
    private Jogo jogo;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Gerenciador g = new Gerenciador();
        g.iniciar(s);
    }

    public void iniciar(Scanner s) {
        System.out.println("\n --- Jogo de Xadrez ---");
        System.out.println("1. Nova partida");
        System.out.println("2. Carregar Partida");
        System.out.println("3. Encerrar");
        System.out.print("Escolha uma opção: ");

        int op = 0;
        while (op < 1 || op > 3) {
            try {
                op = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Selecione um número entre 1 a 5 para realizar uma ação");
            }
            switch (op) {
                case 1:
                    this.novaPartida();
                    iniciarPartida(s);
                    break;
                case 2:
                    carregarPartida();
                    iniciarPartida(s);
                    break;
                case 3:
                    this.salvarPartida();
                    System.out.println("Encerrando!");
                    return;
                default:
                    System.out.println("Opção inválida, escolha uma opção entre 1-3.");
            }
        }
    }


    private void novaPartida() {
        this.jogo = new Jogo("Sandro", "Carlos");
        System.out.println("Nova partida iniciada!");
    }

    private void carregarPartida() {
        String arquivo = "historico.txt";
        try {
            String historico = new String(Files.readAllBytes(Paths.get(arquivo)));

            this.jogo = new Jogo(historico);
            System.out.println("Jogo carregado com sucesso");
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Arquivo não encontrado: " + arquivo);
            this.jogo = null;
        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível ler o arquivo: " + e.getMessage());
            this.jogo = null;
        }

    }

    private void salvarPartida() {
        if (this.jogo == null) {
            return;
        }

        String historico = jogo.registroJogo();

        try (PrintWriter writer = new PrintWriter(new File("historico.txt"))) {
            writer.print(historico);
            System.out.println("Jogo salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível salvar o jogo no arquivo: " + e.getMessage());
        }
    }

    private void iniciarPartida(Scanner s) {
        if (this.jogo == null) return;

        String notacaoJogada = "";
        while (!this.jogo.ehXequeMate() && !notacaoJogada.equalsIgnoreCase("Sair")) {
            this.jogo.imprimir();
            System.out.println("É a vez de " + this.jogo.estaNaVez().getNome());
            System.out.println("Digite sua jogada ou 'Sair' para terminar: ");

            notacaoJogada = s.nextLine();

            if (notacaoJogada.equalsIgnoreCase("Sair")) break;

            if (notacaoJogada.length() == 4) {
                int linhaO = Character.getNumericValue(notacaoJogada.charAt(0));
                char colunaO = notacaoJogada.charAt(1);
                int linhaD = Character.getNumericValue(notacaoJogada.charAt(2));
                char colunaD = notacaoJogada.charAt(3);

                System.out.println(String.format("%d%c%d%c", linhaO, colunaO, linhaD, colunaD));

                this.jogo.realizarJogada(linhaO, colunaO, linhaD, colunaD);
            }
        }
        salvarPartida();
    }
}
