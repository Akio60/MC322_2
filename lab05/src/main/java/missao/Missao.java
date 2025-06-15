package missao;

import ambiente.Ambiente;

/**
 * Interface para missões de robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public interface Missao {
  /**
   * Executa um passo da missão.
   * @param agente agente inteligente
   * @param ambiente ambiente de simulação
   * @return true se missão concluída
   */
  boolean executarPasso(AgenteInteligente agente, Ambiente ambiente);
}
