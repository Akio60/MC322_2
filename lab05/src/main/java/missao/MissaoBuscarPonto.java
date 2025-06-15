package missao;

import ambiente.Ambiente;

/**
 * Missão de busca de ponto para robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class MissaoBuscarPonto implements Missao {
  private int[] destino;

  public MissaoBuscarPonto(int[] destino) {
    this.destino = destino;
  }

  @Override
  public boolean executarPasso(AgenteInteligente agente, Ambiente ambiente) {
    return agente.moverPara(destino, ambiente);
  }
}
