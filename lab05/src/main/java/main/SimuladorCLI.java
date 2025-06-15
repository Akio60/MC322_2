package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ambiente.Ambiente;
import ambiente.Obstaculo;
import ambiente.TipoObstaculo;
import comunicacao.LoggerMissao;
import missao.MissaoBuscarPonto;
import missao.MissaoExplorar;
import missao.MissaoPatrulhar;
import robo.Robo;
import robo.aereo.RoboCarga;
import robo.aereo.RoboDrone;
import robo.terrestre.RoboRapido;
import robo.terrestre.RoboTanque;
import sensores.SensorNavegacao;
import sensores.SensorTatico;
import sensores.SensorTerreno;

/**
 * CLI do simulador de robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class SimuladorCLI {
    private static Ambiente ambiente;
    private static final List<Robo> robos = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean mostrarMapa = true;
    
    public static void main(String[] args) {
        exibirBanner();
        carregarConfig();
        menu();
    }
    
    private static void exibirBanner() {
        System.out.println("====== Simulador de Robôs – MC322 ======");
    }
    
    private static void carregarConfig() {
        int largura = 10, altura = 10, profundidade = 3;
        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            String linha = br.readLine();
            if (linha != null) {
                String[] partes = linha.split(",");
                largura = Integer.parseInt(partes[0]);
                altura = Integer.parseInt(partes[1]);
                profundidade = Integer.parseInt(partes[2]);
            }
        } catch (Exception e) {
            // Usa padrão se não existir config.txt
        }
        ambiente = new Ambiente(largura, altura, profundidade);
        System.out.println("Ambiente criado: " + largura + "x" + altura + "x" + profundidade);
    }
    
    // New method to clear the terminal and display an improved header.
    private static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("________________________________");
        System.out.println("       Simulador de Robôs       ");
        System.out.println("________________________________\n");
    }
    
    // Main menu with options starting at 1, in the desired order.
    private static void menu() {
        while (true) {
            limparTerminal();
            if(mostrarMapa) {
                visualizarMapa();
            }
            System.out.println("\nMenu Principal:");
            System.out.println("1. Listar Robôs");
            System.out.println("2. Definir Ações");
            System.out.println("3. Executar Missões");
            System.out.println("4. Habilitar/Desabilitar Mapa (atual: " + (mostrarMapa ? "Habilitado" : "Desabilitado") + ")");
            System.out.println("5. Configurações");
            System.out.println("6. Sair");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();
            try {
                switch (op) {
                    case "1":
                        listarRobos();
                        pause();
                        break;
                    case "2":
                        submenuAcoes();
                        break;
                    case "3":
                        executarMissaoCompleta();
                        pause();
                        break;
                    case "4":
                        mostrarMapa = !mostrarMapa;
                        System.out.println("Mapa " + (mostrarMapa ? "Habilitado" : "Desabilitado") + ".");
                        pause();
                        break;
                    case "5":
                        submenuConfiguracoes();
                        break;
                    case "6":
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida.");
                        pause();
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                pause();
            }
        }
    }
    
    // Submenu for "Definir Ações" (options renumbered starting at 1).
    private static void submenuAcoes() {
        while (true) {
            limparTerminal();
            System.out.println("Submenu: Definir Ações");
            System.out.println("1. Atribuir Missão");
            System.out.println("2. Executar Passo (Mover)");
            System.out.println("3. Utilizar Sensor");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();
            switch(op) {
                case "1":
                    atribuirMissao();
                    pause();
                    break;
                case "2":
                    executarPasso();
                    pause();
                    break;
                case "3":
                    utilizarSensor();
                    pause();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Opção inválida.");
                    pause();
            }
        }
    }
    
    // Submenu for "Configurações" (options start at 1).
    private static void submenuConfiguracoes() {
        while (true) {
            limparTerminal();
            System.out.println("Submenu: Configurações");
            System.out.println("1. Adicionar Robô");
            System.out.println("2. Adicionar Obstáculo");
            System.out.println("3. Exportar Logs");
            System.out.println("4. Gerar Teste");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();
            switch(op) {
                case "1" -> {
                    adicionarRobo();
                    pause();
                }
                case "2" -> {
                    adicionarObstaculo();
                    pause();
                }
                case "3" -> {
                    exportarLog();
                    pause();
                }
                case "4" -> {
                    gerarTeste();
                    pause();
                }
                case "5" -> {
                    return;
                }
                default -> {
                    System.out.println("Opção inválida.");
                    pause();
                }
            }
        }
    }
    
    // Reverted map visualization with a single space between cell items.
    private static void visualizarMapa() {
        int largura = ambiente.getLargura();
        int altura = ambiente.getAltura();
        int profundidade = ambiente.getProfundidade();
        List<Robo> robosLocal = ambiente.getRobos();
        List<Obstaculo> obstaculos = ambiente.getObstaculos();
        
        System.out.println("Mapa:");
        for (int y = 0; y < altura; y++) {
            StringBuilder[] linhas = new StringBuilder[profundidade];
            for (int z = 0; z < profundidade; z++) {
                linhas[z] = new StringBuilder();
                for (int x = 0; x < largura; x++) {
                    boolean impresso = false;
                    for (Robo r : robosLocal) {
                        int[] pos = r.getPosicao();
                        if (pos[0] == x && pos[1] == y && pos[2] == z) {
                            linhas[z].append(r.getNome().toUpperCase().charAt(0)).append(" ");
                            impresso = true;
                            break;
                        }
                    }
                    if (impresso) continue;
                    for (Obstaculo o : obstaculos) {
                        int[] pos = o.getPosicao();
                        if (pos[0] == x && pos[1] == y && pos[2] == z) {
                            linhas[z].append("# ").append("");
                            impresso = true;
                            break;
                        }
                    }
                    if (!impresso) {
                        linhas[z].append(". ").append("");
                    }
                }
            }
            for (int z = 0; z < profundidade; z++) {
                System.out.print(linhas[z].toString());
                if(z < profundidade - 1)
                    System.out.print("   ");
            }
            System.out.println();
        }
        for (int z = 0; z < profundidade; z++) {
            System.out.print("Z=" + z);
            int espacos = ambiente.getLargura() - 2;
            for (int e = 0; e < espacos; e++)
                System.out.print(" ");
            if(z < profundidade - 1)
                System.out.print("   ");
        }
        System.out.println();
    }
    
    private static void adicionarRobo() {
        System.out.print("Nome do robô: ");
        String nome = scanner.nextLine();
        System.out.print("Tipo (1-Tanque, 2-Rápido, 3-Drone, 4-Carga): ");
        String tipo = scanner.nextLine();
        Robo robo;
        switch (tipo) {
            case "1" -> robo = new RoboTanque(nome);
            case "2" -> robo = new RoboRapido(nome);
            case "3" -> robo = new RoboDrone(nome);
            case "4" -> robo = new RoboCarga(nome);
            default -> {
                System.out.println("Tipo inválido.");
                return;
            }
        }
        System.out.print("Posição inicial (x y z): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int z = scanner.nextInt();
        scanner.nextLine();
        ambiente.adicionarRobo(robo, x, y, z);
        robo.ligar();
        robo.adicionarSensor(new SensorTerreno());
        robo.adicionarSensor(new SensorNavegacao());
        robo.adicionarSensor(new SensorTatico());
        robos.add(robo);
        System.out.println("Robô adicionado e ligado.");
    }

    private static void adicionarObstaculo() {
        System.out.print("Tipo (1-Pedra, 2-Árvore, 3-Água, 4-Buraco): ");
        String tipo = scanner.nextLine();
        TipoObstaculo t;
        switch (tipo) {
            case "1" -> t = TipoObstaculo.PEDRA;
            case "2" -> t = TipoObstaculo.ARVORE;
            case "3" -> t = TipoObstaculo.AGUA;
            case "4" -> t = TipoObstaculo.BURACO;
            default -> {
                System.out.println("Tipo inválido.");
                return;
            }
        }
        System.out.print("Posição (x y z): ");
        int x = scanner.nextInt(), y = scanner.nextInt(), z = scanner.nextInt();
        scanner.nextLine();
        ambiente.adicionarObstaculo(new Obstaculo(t), x, y, z);
        System.out.println("Obstáculo adicionado.");
    }

    private static void exportarLog() {
        System.out.println("Logs estão na pasta logs/.");
    }

    private static Robo escolherRobo() {
        if (robos.isEmpty()) {
            System.out.println("Nenhum robô disponível.");
            return null;
        }
        for (int i = 0; i < robos.size(); i++)
            System.out.println(i + ": " + robos.get(i).getNome());
        System.out.print("Escolha o robô: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        if (idx < 0 || idx >= robos.size()) {
            System.out.println("Índice inválido.");
            return null;
        }
        return robos.get(idx);
    }

    private static void atribuirMissao() {
        Robo robo = escolherRobo();
        if (robo == null) return;
        System.out.println("Missão (1-Explorar, 2-Patrulhar, 3-Buscar ponto): ");
        String tipo = scanner.nextLine();
        switch (tipo) {
            case "1" -> {
                System.out.print("Qtd passos: ");
                int passos = scanner.nextInt();
                scanner.nextLine();
                robo.setMissao(new MissaoExplorar(passos));
            }
            case "2" -> {
                System.out.print("Qtd pontos: ");
                int qtd = scanner.nextInt();
                int[][] pts = new int[qtd][3];
                for (int i = 0; i < qtd; i++) {
                    System.out.print("Ponto " + (i+1) + " (x y z): ");
                    pts[i][0] = scanner.nextInt();
                    pts[i][1] = scanner.nextInt();
                    pts[i][2] = scanner.nextInt();
                }
                scanner.nextLine();
                robo.setMissao(new MissaoPatrulhar(pts));
            }
            case "3" -> {
                System.out.print("Destino (x y z): ");
                int[] dest = new int[3];
                dest[0] = scanner.nextInt();
                dest[1] = scanner.nextInt();
                dest[2] = scanner.nextInt();
                scanner.nextLine();
                robo.setMissao(new MissaoBuscarPonto(dest));
            }
            default -> System.out.println("Tipo inválido.");
        }
        System.out.println("Missão atribuída.");
    }

    private static void executarPasso() {
        Robo robo = escolherRobo();
        if (robo == null) return;
        if (robo.getMissao() == null) {
            System.out.println("Missão não atribuída.");
            return;
        }
        LoggerMissao logger = new LoggerMissao(robo.getNome());
        robo.getMissao().executarPasso(robo, ambiente);
        logger.logar(robo, ambiente);
        logger.fechar();
        System.out.println("Passo executado e logado.");
    }

    private static void executarMissaoCompleta() {
        Robo robo = escolherRobo();
        if (robo == null) return;
        if (robo.getMissao() == null) {
            System.out.println("Missão não atribuída.");
            return;
        }
        LoggerMissao logger = new LoggerMissao(robo.getNome());
        robo.executarMissao(ambiente, logger);
        logger.fechar();
        System.out.println("Missão executada e log exportado.");
    }

    private static void listarRobos() {
        List<Robo> robosLocal = ambiente.getRobos();
        if (robosLocal.isEmpty()) {
            System.out.println("Nenhum robô no ambiente.");
            return;
        }
        System.out.println("Robôs no ambiente:");
        for (Robo r : robosLocal) {
            int[] pos = r.getPosicao();
            System.out.printf("- %s em (%d, %d, %d)%n", r.getNome(), pos[0], pos[1], pos[2]);
        }
    }

    // Utility method to pause before returning to menu
    private static void pause() {
        System.out.println("Pressione Enter para continuar...");
        scanner.nextLine();
    }

    // Novo método para gerar robôs e obstáculos aleatórios
    private static void gerarTeste() {
        System.out.println("Gerando robôs e obstáculos de teste...");
        int largura = ambiente.getLargura();
        int altura = ambiente.getAltura();
        int profundidade = ambiente.getProfundidade();
        java.util.Random rand = new java.util.Random();

        // Gerar um robô de cada tipo
        String[] nomes = {"TanqueTeste", "RapidoTeste", "DroneTeste", "CargaTeste"};
        Robo[] robosTeste = {
            new RoboTanque(nomes[0]),
            new RoboRapido(nomes[1]),
            new RoboDrone(nomes[2]),
            new RoboCarga(nomes[3])
        };

        for (Robo robo : robosTeste) {
            boolean inserido = false;
            for (int tent = 0; tent < 100 && !inserido; tent++) {
                int x = rand.nextInt(largura);
                int y = rand.nextInt(altura);
                int z = rand.nextInt(profundidade);
                if (ambiente.estaLivre(x, y, z)) {
                    ambiente.adicionarRobo(robo, x, y, z);
                    robo.ligar();
                    robo.adicionarSensor(new SensorTerreno());
                    robo.adicionarSensor(new SensorNavegacao());
                    robo.adicionarSensor(new SensorTatico());
                    robos.add(robo);
                    inserido = true;
                }
            }
            if (!inserido) {
                System.out.println("Não foi possível inserir o robô " + robo.getNome() + " em posição livre.");
            }
        }

        // Gerar 6 obstáculos simples (sem tipo)
        for (int i = 0; i < 6; i++) {
            boolean inserido = false;
            for (int tent = 0; tent < 100 && !inserido; tent++) {
                int x = rand.nextInt(largura);
                int y = rand.nextInt(altura);
                int z = rand.nextInt(profundidade);
                if (ambiente.estaLivre(x, y, z)) {
                    ambiente.adicionarObstaculo(new Obstaculo(null), x, y, z);
                    inserido = true;
                }
            }
            if (!inserido) {
                System.out.println("Não foi possível inserir obstáculo " + (i+1) + " em posição livre.");
            }
        }
        System.out.println("Teste gerado com sucesso!");
    }

    private static void utilizarSensor() {
        if (robos.isEmpty()) {
            System.out.println("Nenhum robô disponível.");
            return;
        }
        System.out.println("Robôs disponíveis:");
        for (int i = 0; i < robos.size(); i++) {
            System.out.println((i + 1) + ": " + robos.get(i).getNome());
        }
        System.out.print("Escolha o robô: ");
        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }
        if (idx < 0 || idx >= robos.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        Robo robo = robos.get(idx);
        List<sensores.Sensor> sensores = robo.getSensores();
        if (sensores.isEmpty()) {
            System.out.println("Robô não possui sensores.");
            return;
        }
        System.out.println("Sensores disponíveis:");
        for (int i = 0; i < sensores.size(); i++) {
            System.out.println((i + 1) + ": " + sensores.get(i).getClass().getSimpleName());
        }
        System.out.print("Escolha o sensor: ");
        int sensorIdx;
        try {
            sensorIdx = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }
        if (sensorIdx < 0 || sensorIdx >= sensores.size()) {
            System.out.println("Índice de sensor inválido.");
            return;
        }
        sensores.Sensor sensor = sensores.get(sensorIdx);
        var evento = sensor.detectar(ambiente, robo);
        if (evento.isPresent()) {
            System.out.println("Evento detectado: " + evento.get().getDescricao() +
                " (" + evento.get().getX() + "," + evento.get().getY() + "," + evento.get().getZ() + ")");
        } else {
            System.out.println("Nenhum evento detectado.");
        }
    }
}
