package robo.subsistema;

import robo.Robo;
import sensores.Sensor;
import ambiente.Ambiente;

/**
 * Gerencia os sensores do robô.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class GerenciadorSensores {
  private Robo robo;

  public GerenciadorSensores(Robo robo) {
    this.robo = robo;
  }

  public void usarSensores(Ambiente ambiente) {
    for (Sensor s : robo.getSensores()) {
      s.detectar(ambiente, robo);
    }
  }
}
