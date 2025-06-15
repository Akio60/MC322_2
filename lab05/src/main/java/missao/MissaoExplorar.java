package missao;

import ambiente.Ambiente;
import robo.Robo;

/**
 * Missão de exploração para robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class MissaoExplorar implements Missao {
  private int passos;
  private int maxPassos;

  public MissaoExplorar(int maxPassos) {
    this.maxPassos = maxPassos;
    this.passos = 0;
  }

  @Override
  public boolean executarPasso(Robo robo, Ambiente ambiente) {
    if (passos >= maxPassos) return true;
    robo.moverAleatorio(ambiente);
    passos++;
    return passos >= maxPassos;
  }
}
