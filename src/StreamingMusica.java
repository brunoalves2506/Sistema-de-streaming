import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static class Musica {
        private String nome;
        private String artista;
        private String duracao;
        private String genero;

        public Musica(String nome, String artista, int duracaoSegundos, String genero) {
            setNome(nome);
            setArtista(artista);
            setDuracao(duracaoSegundos);
            setGenero(genero);
        }

        public String getNome() { return nome; }
        public String getArtista() { return artista; }
        public String getDuracao() { return duracao; }
        public String getGenero() { return genero; }

        public void setNome(String nome) {
            if (nome != null && !nome.isEmpty()) this.nome = nome;
            else System.out.println("Nome inválido!");
        }

        public void setArtista(String artista) {
            if (artista != null && !artista.isEmpty()) this.artista = artista;
            else System.out.println("Artista inválido!");
        }

        public void setDuracao(int segundos) {
            if (segundos > 0) this.duracao = formatarDuracao(segundos);
            else System.out.println("Duração inválida!");
        }

        public void setGenero(String genero) {
            if (genero != null) {
                genero = genero.toLowerCase();
                for (String g : generosValidos) {
                    if (g.equals(genero)) {
                        this.genero = genero;
                        return;
                    }
                }
            }
            System.out.println("Gênero inválido!");
        }
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

    public static void cadastrarMusica() {

        System.out.println("\n--- CADASTRAR MÚSICA ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Artista: ");
        String artista = scanner.nextLine();

        int segundos;
        while (true) {
            try {
                System.out.print("Duração (segundos): ");
                segundos = Integer.parseInt(scanner.nextLine());
                if (segundos > 0) break;
            } catch (Exception ignored) {}
            System.out.println("Valor inválido!");
        }

        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        Musica m = new Musica(nome, artista, segundos, genero);

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
            System.out.println((i + 1) + ". " +
                    m.getNome() + " | " +
                    m.getArtista() + " | " +
                    m.getDuracao() + " | " +
                    m.getGenero());
        }
    }

    public static void limparTela() {
        for (int i = 0; i < 40; i++) System.out.println();
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
        } catch (Exception e) {
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
            case 0 -> System.out.println("Encerrando...");
            default -> System.out.println("Opção inválida!");
        }
    }

    public static void buscarPorTitulo() {
        System.out.print("Título: ");
        String busca = scanner.nextLine().toLowerCase();

        boolean encontrou = false;

        for (Musica m : musicas) {
            if (m.getNome().toLowerCase().contains(busca)) {
                System.out.println(m.getNome() + " | " + m.getArtista() + " | " + m.getDuracao() + " | " + m.getGenero());
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
            if (m.getArtista().toLowerCase().contains(busca)) {
                System.out.println(m.getNome() + " | " + m.getDuracao() + " | " + m.getGenero());
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
            if (m.getGenero().equals(busca)) {
                System.out.println(m.getNome() + " | " + m.getArtista() + " | " + m.getDuracao());
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
            String[] p = m.getDuracao().split(":");
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
        musicas.add(new Musica("Bohemian Rhapsody", "Queen", 354, "rock"));
        musicas.add(new Musica("Billie Jean", "Michael Jackson", 293, "pop"));
    }
}