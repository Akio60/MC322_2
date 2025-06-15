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
import missao.AgenteInteligente;
import missao.Missao;
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

  private static void menu() {
    while (true) {
      System.out.println("\nMenu:");
      System.out.println("1. Adicionar robô");
      System.out.println("2. Adicionar obstáculo");
      System.out.println("3. Atribuir missão a robô");
      System.out.println("4. Executar passo");
      System.out.println("5. Executar missão completa");
      System.out.println("6. Exportar log");
      System.out.println("0. Sair");
      System.out.print("Escolha: ");
      String op = scanner.nextLine();
      try {
        switch (op) {
          case "1": adicionarRobo(); break;
          case "2": adicionarObstaculo(); break;
          case "3": atribuirMissao(); break;
          case "4": executarPasso(); break;
          case "5": executarMissaoCompleta(); break;
          case "6": exportarLog(); break;
          case "0": System.exit(0);
          default: System.out.println("Opção inválida.");
        }
      } catch (Exception e) {
        System.out.println("Erro: " + e.getMessage());
      }
    }
  }

  private static void adicionarRobo() {
    System.out.print("Nome do robô: ");
    String nome = scanner.nextLine();
    System.out.print("Tipo (1-Tanque, 2-Rápido, 3-Drone, 4-Carga): ");
    String tipo = scanner.nextLine();
    Robo robo;
    switch (tipo) {
      case "1": robo = new RoboTanque(nome); break;
      case "2": robo = new RoboRapido(nome); break;
      case "3": robo = new RoboDrone(nome); break;
      case "4": robo = new RoboCarga(nome); break;
      default: System.out.println("Tipo inválido."); return;
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
      case "1": t = TipoObstaculo.PEDRA; break;
      case "2": t = TipoObstaculo.ARVORE; break;
      case "3": t = TipoObstaculo.AGUA; break;
      case "4": t = TipoObstaculo.BURACO; break;
      default: System.out.println("Tipo inválido."); return;
    }
    System.out.print("Posição (x y z): ");
    int x = scanner.nextInt(), y = scanner.nextInt(), z = scanner.nextInt();
    scanner.nextLine();
    ambiente.adicionarObstaculo(new Obstaculo(t), x, y, z);
    System.out.println("Obstáculo adicionado.");
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
    if (!(robo instanceof AgenteInteligente)) {
      System.out.println("Robô não suporta missões inteligentes.");
      return;
    }
    AgenteInteligente agente = (AgenteInteligente) robo;
    System.out.println("Missão (1-Explorar, 2-Patrulhar, 3-Buscar ponto): ");
    String tipo = scanner.nextLine();
    switch (tipo) {
      case "1":
        System.out.print("Qtd passos: ");
        int passos = scanner.nextInt();
        scanner.nextLine();
        agente.setMissao(new MissaoExplorar(passos));
        break;
      case "2":
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
        agente.setMissao(new MissaoPatrulhar(pts));
        break;
      case "3":
        System.out.print("Destino (x y z): ");
        int[] dest = new int[3];
        dest[0] = scanner.nextInt();
        dest[1] = scanner.nextInt();
        dest[2] = scanner.nextInt();
        scanner.nextLine();
        agente.setMissao(new MissaoBuscarPonto(dest));
        break;
      default:
        System.out.println("Tipo inválido.");
    }
    System.out.println("Missão atribuída.");
  }

  private static void executarPasso() {
    Robo robo = escolherRobo();
    if (robo == null) return;
    if (!(robo instanceof AgenteInteligente)) {
      System.out.println("Robô não suporta missões inteligentes.");
      return;
    }
    AgenteInteligente agente = (AgenteInteligente) robo;
    if (agente.getMissao() == null) {
      System.out.println("Missão não atribuída.");
      return;
    }
    LoggerMissao logger = new LoggerMissao(agente.getNome());
    ((Missao) agente.getMissao()).executarPasso(agente, ambiente);
    logger.logar(agente, ambiente);
    logger.fechar();
    System.out.println("Passo executado e logado.");
  }

  private static void executarMissaoCompleta() {
    Robo robo = escolherRobo();
    if (robo == null) return;
    if (!(robo instanceof AgenteInteligente)) {
      System.out.println("Robô não suporta missões inteligentes.");
      return;
    }
    AgenteInteligente agente = (AgenteInteligente) robo;
    if (agente.getMissao() == null) {
      System.out.println("Missão não atribuída.");
      return;
    }
    LoggerMissao logger = new LoggerMissao(agente.getNome());
    agente.executarMissao(ambiente, logger);
    logger.fechar();
    System.out.println("Missão executada e log exportado.");
  }

  private static void exportarLog() {
    System.out.println("Logs estão na pasta logs/.");
  }
}
