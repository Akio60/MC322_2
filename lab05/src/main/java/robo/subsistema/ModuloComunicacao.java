package robo.subsistema;

import robo.Robo;

/**
 * Subsistema de comunicação do robô.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class ModuloComunicacao {
  private final Robo robo;

  public ModuloComunicacao(Robo robo) {
    this.robo = robo;
  }

  public Robo getRobo() {
    return this.robo;
  }

  // Métodos de comunicação serão implementados posteriormente
}
