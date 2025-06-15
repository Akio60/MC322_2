package robo;

import sensores.Sensor;
import robo.subsistema.ControleMovimento;
import robo.subsistema.GerenciadorSensores;
import robo.subsistema.ModuloComunicacao;
import ambiente.Ambiente;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe abstrata base para todos os robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public abstract class Robo {
  protected String nome;
  protected int x, y, z;
  protected boolean ligado;
  protected List<Sensor> sensores;
  protected ControleMovimento controleMovimento;
  protected GerenciadorSensores gerenciadorSensores;
  protected ModuloComunicacao moduloComunicacao;

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
}
