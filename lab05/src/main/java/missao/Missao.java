package missao;

import ambiente.Ambiente;
import robo.Robo;

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
   * @param robo robô executando a missão
   * @param ambiente ambiente de simulação
   * @return true se missão concluída
   */
  boolean executarPasso(Robo robo, Ambiente ambiente);
}
