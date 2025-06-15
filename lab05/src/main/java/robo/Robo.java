package robo;

import java.util.ArrayList;
import java.util.List;

import ambiente.Ambiente;
import comunicacao.LoggerMissao;
import missao.AgenteInteligente;
import missao.Missao;
import robo.subsistema.ControleMovimento;
import robo.subsistema.GerenciadorSensores;
import robo.subsistema.ModuloComunicacao;
import sensores.Sensor;

/**
 * Classe base para todos os robôs, agora capaz de receber missões.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class Robo implements AgenteInteligente {
  protected String nome;
  protected int x, y, z;
  protected boolean ligado;
  protected List<Sensor> sensores;
  protected ControleMovimento controleMovimento;
  protected GerenciadorSensores gerenciadorSensores;
  protected ModuloComunicacao moduloComunicacao;
  protected Missao missao;

  public Robo(String nome) {
    this.nome = nome;
    this.ligado = false;
    this.sensores = new ArrayList<>();
    this.controleMovimento = new ControleMovimento(this);
    this.gerenciadorSensores = new GerenciadorSensores(this);
    this.moduloComunicacao = new ModuloComunicacao(this);
  }

  public String getNome() { return nome; }
  public boolean isLigado() { return ligado; }
  public int[] getPosicao() { return new int[]{x, y, z}; }
  public void setPosicao(int x, int y, int z) {
    this.x = x; this.y = y; this.z = z;
  }
  public List<Sensor> getSensores() { return sensores; }
  public void adicionarSensor(Sensor sensor) { sensores.add(sensor); }

  public void ligar() { ligado = true; }
  public void desligar() { ligado = false; }

  public void usarSensores(Ambiente ambiente) {
    gerenciadorSensores.usarSensores(ambiente);
  }

  public void mover(int dx, int dy, int dz, Ambiente ambiente)
      throws Exception {
    controleMovimento.mover(dx, dy, dz, ambiente);
  }

  @Override
  public void setMissao(Missao missao) {
    this.missao = missao;
  }

  @Override
  public Missao getMissao() {
    return this.missao;
  }

  @Override
  public void executarMissao(Ambiente ambiente, LoggerMissao logger) {
    boolean concluida = false;
    while (!concluida) {
      concluida = missao.executarPasso(this, ambiente);
      logger.logar(this, ambiente);
    }
  }

  @Override
  public void moverAleatorio(Ambiente ambiente) {
    // Exemplo simples: tenta mover para uma direção aleatória válida
    int[][] dirs = {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
    for (int[] d : dirs) {
      int[] pos = getPosicao();
      int nx = pos[0]+d[0], ny = pos[1]+d[1], nz = pos[2]+d[2];
      if (ambiente.dentroLimites(nx,ny,nz) && ambiente.estaLivre(nx,ny,nz)) {
        try { mover(d[0], d[1], d[2], ambiente); } catch(Exception e) {}
        break;
      }
    }
  }

  @Override
  public boolean moverPara(int[] destino, Ambiente ambiente) {
    int[] pos = getPosicao();
    int dx = Integer.compare(destino[0], pos[0]);
    int dy = Integer.compare(destino[1], pos[1]);
    int dz = Integer.compare(destino[2], pos[2]);
    if (dx == 0 && dy == 0 && dz == 0) return true;
    try {
      mover(dx, dy, dz, ambiente);
    } catch(Exception e) {
      return false;
    }
    return getPosicao()[0] == destino[0] && getPosicao()[1] == destino[1] && getPosicao()[2] == destino[2];
  }
}
