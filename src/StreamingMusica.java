import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static class Musica {
        String nome;
        String artista;
        String duracao;
        String genero;
    }

    static ArrayList<Musica> musicas = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    static String[] generosValidos = {"pop", "rock", "jazz", "eletronica", "hiphop", "classica"};

    public static void main(String[] args) {

        adicionarMusicasTeste();

        int opcao;
        do {
            limparTela();
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\n🎵 Até logo! 🎵");
        scanner.close();
    }

    public static void limparTela() {
        for (int i = 0; i < 40; i++) System.out.println();
    }

    public static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    public static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE STREAMING ===");
        System.out.println("1. Cadastrar música");
        System.out.println("2. Listar músicas");
        System.out.println("3. Buscar por título");
        System.out.println("4. Buscar músicas por artista");
        System.out.println("5. Buscar músicas por gênero");
        System.out.println("6. Exibir estatísticas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void processarOpcao(int opcao) {

        switch (opcao) {
            case 1 -> cadastrarMusica();
            case 2 -> listarMusicas();
            case 3 -> buscarPorTitulo();
            case 4 -> buscarPorArtista();
            case 5 -> buscarPorGenero();
            case 6 -> estatisticas();
            case 0 -> System.out.println("Encerrando Programa...");
            default -> System.out.println("Opção inválida!");
        }
        pausar();
    }

    public static void cadastrarMusica() {

        Musica m = new Musica();

        System.out.println("\n--- CADASTRAR MÚSICA ---");

        while (true) {
            System.out.print("Nome: ");
            m.nome = scanner.nextLine().trim();
            if (!m.nome.isEmpty()) break;
            System.out.println("Não pode ser vazio!");
        }

        while (true) {
            System.out.print("Artista: ");
            m.artista = scanner.nextLine().trim();
            if (!m.artista.isEmpty()) break;
            System.out.println("Não pode ser vazio!");
        }

        int segundos;
        while (true) {
            try {
                System.out.print("Duração (segundos): ");
                segundos = Integer.parseInt(scanner.nextLine());
                if (segundos > 0) break;
            } catch (Exception ignored) {}
            System.out.println("Valor inválido!");
        }

        m.duracao = formatarDuracao(segundos);

        while (true) {
            System.out.print("Gênero: ");
            m.genero = scanner.nextLine().toLowerCase();

            boolean valido = false;
            for (String g : generosValidos) {
                if (g.equals(m.genero)) {
                    valido = true;
                    break;
                }
            }

            if (valido) break;
            System.out.println("Gênero inválido!");
        }

        musicas.add(m);
        System.out.println("Música cadastrada!");
    }

    public static void listarMusicas() {
        System.out.println("\n--- MÚSICAS ---");

        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música cadastrada!");
            return;
        }

        for (int i = 0; i < musicas.size(); i++) {
            Musica m = musicas.get(i);
            System.out.println((i + 1) + ". " + m.nome + " | " + m.artista + " | " + m.duracao + " | " + m.genero);
        }
    }

    public static void buscarPorTitulo() {
        System.out.print("Título: ");
        String busca = scanner.nextLine().toLowerCase();

        boolean encontrou = false;

        for (Musica m : musicas) {
            if (m.nome.toLowerCase().contains(busca)) {
                System.out.println(m.nome + " | " + m.artista + " | " + m.duracao + " | " + m.genero);
                encontrou = true;
            }
        }

        if (!encontrou) System.out.println("Não encontrada!");
    }

    public static void buscarPorArtista() {
        System.out.print("Artista: ");
        String busca = scanner.nextLine().toLowerCase();

        boolean encontrou = false;

        for (Musica m : musicas) {
            if (m.artista.toLowerCase().contains(busca)) {
                System.out.println(m.nome + " | " + m.duracao + " | " + m.genero);
                encontrou = true;
            }
        }

        if (!encontrou) System.out.println("Não encontrado!");
    }

    public static void buscarPorGenero() {
        System.out.print("Gênero: ");
        String busca = scanner.nextLine().toLowerCase();

        boolean encontrou = false;

        for (Musica m : musicas) {
            if (m.genero.equals(busca)) {
                System.out.println(m.nome + " | " + m.artista + " | " + m.duracao);
                encontrou = true;
            }
        }

        if (!encontrou) System.out.println("Não encontrado!");
    }

    public static void estatisticas() {
        System.out.println("\n--- ESTATÍSTICAS ---");

        if (musicas.isEmpty()) {
            System.out.println("Sem dados!");
            return;
        }

        int totalSegundos = 0;

        for (Musica m : musicas) {
            String[] p = m.duracao.split(":");
            totalSegundos += Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
        }

        System.out.println("Total músicas: " + musicas.size());
        System.out.println("Duração total: " + formatarDuracao(totalSegundos));
        System.out.println("Média: " + formatarDuracao(totalSegundos / musicas.size()));
    }

    public static String formatarDuracao(int segundos) {
        return (segundos / 60) + ":" + String.format("%02d", segundos % 60);
    }

    public static void adicionarMusicasTeste() {
        Musica m1 = new Musica();
        m1.nome = "Bohemian Rhapsody";
        m1.artista = "Queen";
        m1.duracao = formatarDuracao(354);
        m1.genero = "rock";

        Musica m2 = new Musica();
        m2.nome = "Billie Jean";
        m2.artista = "Michael Jackson";
        m2.duracao = formatarDuracao(293);
        m2.genero = "pop";

        musicas.add(m1);
        musicas.add(m2);
    }
}