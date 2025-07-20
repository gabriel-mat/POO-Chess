package jogo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gerenciador {
    private Jogo jogo;

    public static void main(String[] args){
        Gerenciador g = new Gerenciador();
        g.executar();
    }

    public void executar() {
        Scanner s = new Scanner(System.in);
        boolean executando = true;
        while (executando) {
            System.out.println("\n --- Jogo de Xadrez ---");
            System.out.println("1. Nova partida");
            System.out.println("2. Carregar Partida");
            System.out.println("3. Encerrar");
            System.out.print("Escolha uma opção: ");

            int op;
            try {
                op = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Selecione um número entre 1 a 5 para realizar uma ação");
                continue;
            }
            switch (op) {
                case 1:
                    this.novaPartida();
                    break;
                case 2:
                    carregarPartida();
                    break;
                case 3:
                    executando = false;
                    salvarPartida();
                    System.out.println("Encerrando!");
                    break;
                default:
                    System.out.println("Opção inválida, escolha uma opção entre 1-5.");
            }

        }
        s.close();
    }

    private void novaPartida() {
        this.jogo = new Jogo();
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
        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível ler o arquivo: " + e.getMessage());
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
}
