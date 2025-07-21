package jogo;

import pecas.CorInvalidaException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Gerenciador {
    private Jogo jogo;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Gerenciador g = new Gerenciador();
        g.iniciar(in);
    }

    private void mostrarMenu() {
        System.out.println("1. Nova partida");
        System.out.println("2. Carregar Partida");
        System.out.println("3. Encerrar");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerInt(Scanner in) {
        int dado;
        while (true) {
            try {
                dado = in.nextInt();
                in.nextLine();
                return dado;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.\n");
                in.nextLine();
            }
        }
    }

    public void iniciar(Scanner in) {
        System.out.println("\n ------ Jogo de Xadrez ------");

        while (true) {
            mostrarMenu();
            int op = lerInt(in);

            switch (op) {
                case 1:
                    novaPartida(in);
                    jogo.iniciarPartida(in);
                    break;
                case 2:
                    carregarPartida(in);
                    jogo.iniciarPartida(in);
                    break;
                case 3:
                    salvarPartida();
                    System.out.println("Encerrando...");
                    return;
                default:
                    System.out.println("Opção inválida. Escolha uma nova opção.");
            }
        }
    }


    private void novaPartida(Scanner in) {
        try {
            System.out.println("Digite o nome do Jogador 1 (peças brancas): ");
            String nome1 = in.nextLine();

            System.out.println("Digite o nome do Jogador 2 (peças pretas): ");
            String nome2 = in.nextLine();

            jogo = new Jogo(nome1, nome2);
            System.out.println("Nova partida iniciada!");
        } catch (CorInvalidaException e) {
            System.out.println("ERRO CRÍTICO: " + e.getMessage());

            this.jogo = null;
        }
    }

    private void carregarPartida(Scanner in) {
        System.out.println("Digite o nome do arquivo: ");
        String nomeArquivo = in.nextLine();

        try {
            String historico = new String(Files.readAllBytes(Paths.get(nomeArquivo)));
            jogo = new Jogo(historico);
            System.out.println("Jogo carregado com sucesso");
        } catch (java.nio.file.InvalidPathException e) {
            System.out.println("Erro. O nome do arquivo digitado contém caracteres inválidos.");
            jogo = null;
        } catch (FileNotFoundException e) {
            System.out.println("Erro. Arquivo não encontrado: " + nomeArquivo);
            jogo = null;
        } catch (IOException e) {
            System.out.println("Erro. Não foi possível ler o arquivo: " + e.getMessage());
            jogo = null;
        } catch (FormatoArquivoInvalidoException e) {
            System.out.println("ERRO: O arquivo de save está corrompido. " + e.getMessage());
            this.jogo = null;
        } catch (CorInvalidaException e) {
            System.out.println("ERRO CRÍTICO: Dados de cor inválidos no sistema. " + e.getMessage());
            this.jogo = null;

        }
    }

    private void salvarPartida() {
        if (jogo == null)
            return;

        String historico = jogo.registroJogo();

        try (PrintWriter writer = new PrintWriter(new File("historico.txt"))) {
            writer.print(historico);
            System.out.println("Jogo salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro. Não foi possível salvar o jogo no arquivo: " + e.getMessage());
        }
    }

}
