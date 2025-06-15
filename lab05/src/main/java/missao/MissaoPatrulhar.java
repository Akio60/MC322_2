package missao;

import ambiente.Ambiente;
import robo.Robo;

/**
 * Missão de patrulha para robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class MissaoPatrulhar implements Missao {
  @SuppressWarnings("FieldMayBeFinal")
  private int[][] pontos;
  private int indice;

  public MissaoPatrulhar(int[][] pontos) {
    this.pontos = pontos;
    this.indice = 0;
  }

  @Override
  public boolean executarPasso(Robo robo, Ambiente ambiente) {
    if (indice >= pontos.length) indice = 0;
    boolean chegou = robo.moverPara(pontos[indice], ambiente);
    if (chegou) indice++;
    return false;
  }
}
