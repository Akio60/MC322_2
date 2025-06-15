package robo.subsistema;

import ambiente.Ambiente;
import robo.Robo;
import sensores.Sensor;

/**
 * Gerencia os sensores do robô.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class GerenciadorSensores {
  @SuppressWarnings("FieldMayBeFinal")
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
