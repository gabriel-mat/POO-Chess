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
        System.out.println("3. Salvar partida e Encerrar");
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
                    salvarPartida(in);
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
        String nomeArquivo;

        while (true) {
            System.out.println("Digite o nome do arquivo (ou 'parar' para voltar ao menu): ");
            nomeArquivo = in.nextLine();

            if (nomeArquivo.equalsIgnoreCase("parar"))
                return;
            if (Files.exists(Paths.get(nomeArquivo)))
                break;

            System.out.println("Arquivo não encontrado. Tente novamente ou 'parar' para voltar ao menu.\n");
        }

        try {
            String historico = new String(Files.readAllBytes(Paths.get(nomeArquivo)));
            jogo = new Jogo(historico);
            System.out.println("Jogo carregado com sucesso");
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

    private void salvarPartida(Scanner in) {
        if (jogo == null)
            return;

        System.out.print("Digite o nome do arquivo para salvar (ex: jogo1.txt): ");
        String nomeArquivo = in.nextLine().trim();

        if (nomeArquivo.isEmpty()) {
            System.out.println("Nome de arquivo inválido.");
            return;
        }

        if (!nomeArquivo.endsWith(".txt"))
            nomeArquivo += ".txt";

        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            System.out.print("Arquivo já existe. Deseja sobrescrevê-lo? (s/n): ");
            if (!in.nextLine().equalsIgnoreCase("s")) {
                System.out.println("Salvamento cancelado.");
                return;
            }
        }

        String historico = jogo.registroJogo();

        try (PrintWriter writer = new PrintWriter(arquivo)) {
            writer.print(historico);
            System.out.println("Jogo salvo com sucesso em \"" + nomeArquivo + "\"!");
        } catch (IOException e) {
            System.out.println("Erro. Não foi possível salvar o jogo no arquivo: " + e.getMessage());
        }
    }

}
