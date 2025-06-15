package missao;

import ambiente.Ambiente;
import robo.Robo;

/**
 * Missão de busca de ponto para robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class MissaoBuscarPonto implements Missao {
  @SuppressWarnings("FieldMayBeFinal")
  private int[] destino;

  public MissaoBuscarPonto(int[] destino) {
    this.destino = destino;
  }

  @Override
  public boolean executarPasso(Robo robo, Ambiente ambiente) {
    return robo.moverPara(destino, ambiente);
  }
}
