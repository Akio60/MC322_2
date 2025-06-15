package missao;

import ambiente.Ambiente;

/**
 * Missão de patrulha para robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class MissaoPatrulhar implements Missao {
  private int[][] pontos;
  private int indice;

  public MissaoPatrulhar(int[][] pontos) {
    this.pontos = pontos;
    this.indice = 0;
  }

  @Override
  public boolean executarPasso(AgenteInteligente agente, Ambiente ambiente) {
    if (indice >= pontos.length) indice = 0;
    boolean chegou = agente.moverPara(pontos[indice], ambiente);
    if (chegou) indice++;
    return false;
  }
}
