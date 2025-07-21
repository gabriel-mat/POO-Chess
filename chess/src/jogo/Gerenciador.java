package jogo;

import pecas.*;

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
        //testes();
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
                    if(novaPartida(in)) jogo.iniciarPartida(in);
                    break;
                case 2:
                    if (carregarPartida(in)) jogo.iniciarPartida(in);
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

    private boolean novaPartida(Scanner in) {
        try {
            System.out.println("Digite o nome do Jogador 1 (peças brancas): ");
            String nome1 = in.nextLine();

            System.out.println("Digite o nome do Jogador 2 (peças pretas): ");
            String nome2 = in.nextLine();

            jogo = new Jogo(nome1, nome2);
            System.out.println("Nova partida iniciada!");
            return true;
        } catch (CorInvalidaException e) {
            System.out.println("ERRO CRÍTICO: " + e.getMessage());

            this.jogo = null;
            return false;
        }
    }

    private boolean carregarPartida(Scanner in) {
        String nomeArquivo;

        while (true) {
            System.out.println("Digite o nome do arquivo (ou 'parar' para voltar ao menu): ");
            nomeArquivo = in.nextLine();

            if (nomeArquivo.equalsIgnoreCase("parar"))
                return false;
            if (Files.exists(Paths.get(nomeArquivo)))
                break;

            System.out.println("Arquivo não encontrado. Tente novamente ou 'parar' para voltar ao menu.\n");
        }

        try {
            String historico = new String(Files.readAllBytes(Paths.get(nomeArquivo)));
            jogo = new Jogo(historico);
            System.out.println("Jogo carregado com sucesso");
            return true;
        } catch (java.nio.file.InvalidPathException e) {
            System.out.println("Erro. O nome do arquivo digitado contém caracteres inválidos.");
        } catch (FileNotFoundException e) {
            System.out.println("Erro. Arquivo não encontrado: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro. Não foi possível ler o arquivo: " + e.getMessage());
        } catch (FormatoArquivoInvalidoException e) {
            System.out.println("ERRO: O arquivo de save está corrompido. " + e.getMessage());
        } catch (CorInvalidaException e) {
            System.out.println("ERRO CRÍTICO: Dados de cor inválidos no sistema. " + e.getMessage());
        }
        jogo = null;
        return false;
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

//    private void testes() throws CorInvalidaException{
//        // Teste Peão
//        Peao p1 = new Peao("Branco");
//        Peao p2 = new Peao("Preto");
//
//        // Teste getTipo
//        System.out.println(p1.getTipo()); // deve retornar 'P'
//        System.out.println(p2.getTipo()); // deve retornar 'P'
//
//        // Teste getCor
//        System.out.println(p1.getCor()); // deve retornar Branco
//        System.out.println(p2.getCor()); // deve retornar Preto
//
//        // Teste desenho
//        System.out.println(p1.desenho()); // deve retornar P
//        System.out.println(p2.desenho()); // deve retornar p
//
//        // Teste caminho
//        System.out.println(p1.caminho(2, 'e', 3, 'e')); // deve retornar 2e3e
//        System.out.println(p1.caminho(2, 'e', 7, 'f')); // deve retornar vazio
//        System.out.println(p2.caminho(7, 'e', 6, 'e')); // deve retornar 7e6e
//        System.out.println(p2.caminho(3, 'e', 9, 'e')); // deve retornar vazio
//
//        // Teste movimento válido
//        System.out.println(p1.movimentoValido(2, 'e', 3, 'e')); // true
//        System.out.println(p1.movimentoValido(2, 'e', 7, 'f')); // false
//        System.out.println(p2.movimentoValido(7, 'e', 6, 'e')); // true
//        System.out.println(p2.movimentoValido(3, 'e', 9, 'e')); // false
//
//        // Teste peça viva
//        System.out.println(p1.estaViva()); // true
//        p1.setViva(false);
//        System.out.println(p1.estaViva()); // false
//
//        // Teste Torre
//        Torre t1 = new Torre("Branco");
//        Torre t2 = new Torre("Preto");
//
//        // getTipo
//        System.out.println(t1.getTipo()); // deve retornar 'T'
//        System.out.println(t2.getTipo()); // deve retornar 'T'
//
//        // getCor
//        System.out.println(t1.getCor()); // deve retornar Branco
//        System.out.println(t2.getCor()); // deve retornar Preto
//
//        // desenho
//        System.out.println(t1.desenho()); // deve retornar T
//        System.out.println(t2.desenho()); // deve retornar t
//
//        // caminho
//        System.out.println(t1.caminho(1, 'a', 1, 'h')); // deve retornar 1a1h
//        System.out.println(t1.caminho(1, 'a', 8, 'a')); // deve retornar 1a8a
//        System.out.println(t1.caminho(1, 'a', 3, 'c')); // deve retornar vazio
//        System.out.println(t2.caminho(8, 'h', 5, 'h')); // deve retornar 8h5h
//        System.out.println(t2.caminho(8, 'h', 7, 'g')); // deve retornar vazio
//
//        // movimentos válidos
//        System.out.println(t1.movimentoValido(1, 'a', 1, 'h')); // true
//        System.out.println(t1.movimentoValido(1, 'a', 3, 'c')); // false
//
//        // Teste Bispo
//        Bispo b1 = new Bispo("Branco");
//        Bispo b2 = new Bispo("Preto");
//
//        // getTipo
//        System.out.println(b1.getTipo()); // deve retornar 'B'
//        System.out.println(b2.getTipo()); // deve retornar 'B'
//
//        // getCor
//        System.out.println(b1.getCor()); // deve retornar Branco
//        System.out.println(b2.getCor()); // deve retornar Preto
//
//        // desenho
//        System.out.println(b1.desenho()); // deve retornar B
//        System.out.println(b2.desenho()); // deve retornar b
//
//        // caminho
//        System.out.println(b1.caminho(1, 'c', 3, 'e')); // deve retornar 1c3e
//        System.out.println(b1.caminho(1, 'c', 1, 'd')); // deve retornar vazio
//        System.out.println(b2.caminho(8, 'f', 6, 'd')); // deve retornar 8f6d
//        System.out.println(b2.caminho(8, 'f', 7, 'f')); // deve retornar vazio
//
//        // movimento válido
//        System.out.println(b1.movimentoValido(1, 'c', 3, 'e')); // true
//        System.out.println(b1.movimentoValido(1, 'c', 1, 'd')); // false
//
//        // Teste Dama
//        Dama d1 = new Dama("Branco");
//        Dama d2 = new Dama("Preto");
//
//        // getTipo
//        System.out.println(d1.getTipo()); // deve retornar 'D'
//        System.out.println(d2.getTipo()); // deve retornar 'D'
//
//        // getCor
//        System.out.println(d1.getCor()); // deve retornar Branco
//        System.out.println(d2.getCor()); // deve retornar Preto
//
//        // desenho
//        System.out.println(d1.desenho()); // deve retornar D
//        System.out.println(d2.desenho()); // deve retornar d
//
//        // caminho
//        System.out.println(d1.caminho(1, 'd', 1, 'h')); // deve retrornar 1d1h
//        System.out.println(d1.caminho(1, 'd', 5, 'd')); // deve retornar 1d5d
//        System.out.println(d1.caminho(1, 'd', 4, 'g')); // deve retornar 1d4g
//        System.out.println(d1.caminho(1, 'd', 3, 'e')); // deve retornar vazio
//
//        System.out.println(d2.caminho(8, 'e', 5, 'b')); // deve retornar 8e5b
//        System.out.println(d2.caminho(8, 'e', 8, 'e')); // deve retornar vazio
//
//        // movimento válido
//        System.out.println(d1.movimentoValido(1, 'd', 5, 'd')); // true
//        System.out.println(d1.movimentoValido(1, 'd', 3, 'e')); // false
//
//        // Teste rei
//        Rei r1 = new Rei("Branco");
//        Rei r2 = new Rei("Preto");
//
//        // getTipo
//        System.out.println(r1.getTipo()); // deve retornar 'K'
//        System.out.println(r2.getTipo()); // deve retornar 'K'
//
//        // getCor
//        System.out.println(r1.getCor()); // deve retornar Branco
//        System.out.println(r2.getCor()); // deve retornar Preto
//
//        // desenho
//        System.out.println(r1.desenho()); // deve retornar K
//        System.out.println(r2.desenho()); // deve retoranr k
//
//        // caminho
//        System.out.println(r1.caminho(1, 'e', 1, 'f')); // deve retornar 1e1f
//        System.out.println(r1.caminho(1, 'e', 2, 'f')); // deve retornar 1e2f
//        System.out.println(r1.caminho(1, 'e', 3, 'e')); // deve retornar vazio
//        System.out.println(r2.caminho(8, 'e', 7, 'e')); // deve retornar 8e7e
//        System.out.println(r2.caminho(8, 'e', 6, 'e')); // deve retornar vazio
//
//        // movimento válido
//        System.out.println(r1.movimentoValido(1, 'e', 2, 'f')); // true
//        System.out.println(r1.movimentoValido(1, 'e', 3, 'e')); // false
//
//        // Teste jogador
//        Jogador j1 = new Jogador("Branco", "Yuri Alberto");
//        Jogador j2 = new Jogador("Preto", "Garro");
//
//        // Teste getCor()
//        System.out.println(j1.getCor()); // deve retornar Branco
//        System.out.println(j2.getCor()); // deve retornar Preto
//
//        // Teste getNome()
//        System.out.println(j1.getNome()); // deve retornar Yuri Alberto
//        System.out.println(j2.getNome()); // deve retornar Garro
//
//        // Teste adicionaPeca(Peca p)
//        for(int i = 0; i < 16; i++){
//            Peao pecaAdd1 = new Peao("Branco");
//            System.out.println(j1.adicionarPeca(pecaAdd1)); // devem retornar true
//        }
//        Peao pecaAdd2 = new Peao("Branco");
//        System.out.println(j1.adicionarPeca(pecaAdd2)); // deve retornar false
//
//        // Teste adicionarCapturada(char tipo)
//        for(int i = 0; i < 16; i++){
//            System.out.println(j1.adicionarCapturada('B')); // devem retornar true
//        }
//        System.out.println(j1.adicionarCapturada('B')); // deve retornar false
//
//        // Teste getNumPecas()
//        System.out.println(j1.getNumPecas()); // deve retornar 16
//        System.out.println(j2.getNumPecas()); // deve retornar 0
//
//        // Teste pecasCapturadas()
//        System.out.println(j1.pecasCapturadas()); // deve retornar BBBBBBBBBBBBBBBB
//        System.out.println(j2.pecasCapturadas()); // deve retornar vazio
//
//        // Teste casa
//        Casa c1 = new Casa("Branco", 1, 'b');
//        Casa c2 = new Casa("Preto", 3, 'd');
//
//        // Teste getCor()
//        System.out.println(c1.getCor()); // deve retornar Branco
//        System.out.println(c2.getCor()); // deve retornar Preto
//
//        // Teste getLinha()
//        System.out.println(c1.getLinha());
//        System.out.println(c2.getLinha());
//
//        // Teste getColuna()
//        System.out.println(c1.getColuna());
//        System.out.println(c2.getColuna());
//
//        // Teste getPeca()
//        System.out.println(c1.getPeca());
//        System.out.println(c2.getPeca());
//
//        // Teste setPeca(Peca peca)
//        Peao peaoTeste = new Peao("Branco");
//        c1.setPeca(peaoTeste);
//        System.out.println(c1.getPeca().getTipo()); // deve retornar P
//
//        // Teste casaVazia()
//        System.out.println(c1.casaVazia()); // false
//        System.out.println(c2.casaVazia()); // true
//
//        // Teste tabuleiro
//        Tabuleiro tab1 = new Tabuleiro();
//
//        // desenho()
//        tab1.desenho();
//
//        // colocarPeça()
//        Peao pt = new Peao("Branco");
//        tab1.colocarPeca(1, 'a', pt);
//        tab1.colocarPeca(-1, 'a', pt);
//        System.out.println(tab1.getPeca(1, 'a'));//deve retornar P
//
//        // casaOcupada()
//        System.out.println(tab1.casaOcupada(1, 'a'));//true
//        System.out.println(tab1.casaOcupada(4, 'e'));//false
//
//        //getPeca()
//        System.out.println(tab1.getPeca(1, 'a').getTipo());// deve retornar P
//
//        //Teste caminho
//        Tabuleiro tab2 = new Tabuleiro();
//        Peca pc = new Peao("Branco");
//        tab2.colocarPeca(4, 'e', pc);
//        Caminho cam1 = new Caminho(tab2.getCasa(1, 'e'), tab2.getCasa(8, 'e'), tab2);
//        Caminho cam2 = new Caminho(tab2.getCasa(1, 'd'), tab2.getCasa(8, 'd'), tab2);
//
//        // estaLivre()
//        System.out.println(cam1.estaLivre());// false
//        System.out.println(cam2.estaLivre());// true
//
//        // casaInicial()
//        System.out.println(cam1.casaInicial().getLinha() + cam1.casaInicial().getColuna());// 1e
//        System.out.println(cam2.casaInicial().getLinha() + cam2.casaInicial().getColuna());// 1d
//
//        // casaFinal()
//        System.out.println(cam1.casaFinal().getLinha() + cam1.casaFinal().getColuna());// 8e
//        System.out.println(cam2.casaFinal().getLinha() + cam2.casaFinal().getColuna());// 8d
//
//        // Teste Jogada
//        Tabuleiro tab = new Tabuleiro();
//        Jogador jogadorBranco = new Jogador("Branco", "BrancoTest");
//        Jogador jogadorPreto = new Jogador("Preto", "PretoTest");
//        Peao peaoJogada = new Peao("Branco");
//
//        tab.colocarPeca(2, 'e', peaoJogada); // Coloca o peão na posição inicial
//        Jogada jogada1 = new Jogada(jogadorBranco, tab, 2, 'e', 3, 'e');
//
//        try {
//            System.out.println(jogada1.ehValida()); // true
//        } catch (CasaDeOrigemVaziaException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(jogada1.getNotacao()); // Deve retornar 2e3e
//
//        // Teste ehXeque
//        Rei reiAlvo = new Rei("Branco");
//        tab.colocarPeca(1, 'e', reiAlvo);
//        Torre torreInimiga = new Torre("Preto");
//        tab.colocarPeca(8, 'e', torreInimiga);
//
//        Jogada jogada2 = new Jogada(jogadorPreto, tab, 8, 'e', 1, 'e');
//        System.out.println(jogada2.ehXeque(jogadorBranco, jogadorPreto, tab)); // true
//
//        // Teste ehXequeMate
//        // O rei branco está encurralado sem casas livres
//        tab.colocarPeca(2, 'd', new Torre("Preto"));
//        tab.colocarPeca(2, 'f', new Torre("Preto"));
//        Jogada jogada3 = new Jogada(jogadorPreto, tab, 8, 'e', 1, 'e');
//        System.out.println(jogada3.ehXequeMate(jogadorBranco, jogadorPreto, tab)); // true
//
//        // Teste Jogo
//        try {
//            Jogo jogo = new Jogo("Alice", "Bob");
//
//            System.out.println(jogo.getJ1().getNome()); // Alice
//            System.out.println(jogo.getJ2().getNome()); // Bob
//            System.out.println(jogo.estaNaVez().getNome()); // Alice
//
//            // Jogada válida
//            jogo.realizarJogada(2, 'e', 3, 'e');
//            System.out.println(jogo.getnJogadas()); // 1
//
//            // Jogada inválida
//            try {
//                jogo.realizarJogada(1, 'e', 3, 'e'); // rei se move 2 casas
//            } catch (MovimentoInvalidoException e) {
//                System.out.println("Erro esperado: " + e.getMessage());
//            }
//
//            // Registro do jogo
//            System.out.println(jogo.registroJogo()); // Deve mostrar histórico
//        } catch (Exception e) {
//            System.out.println("Erro durante teste de Jogo: " + e.getMessage());
//        }
//    }
}