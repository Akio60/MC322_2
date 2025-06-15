package sensores;

import ambiente.Ambiente;
import java.util.Optional;
import robo.Robo;

/**
 * Classe abstrata base para sensores.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public abstract class Sensor {
  /**
   * Detecta eventos no ambiente para um robô.
   * @param ambiente ambiente de simulação
   * @param robo robô associado
   * @return Optional<EventoSensor>
   */
  public abstract Optional<EventoSensor> detectar(Ambiente ambiente, Robo robo);
}
