package sensores;

import ambiente.Ambiente;
import java.util.Optional;
import robo.Robo;

/**
 * Interface base para sensores.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public interface Sensor {
  /**
   * Detecta eventos no ambiente simulado.
   * @param ambiente ambiente de simulação
   * @param robo robô associado
   * @return Optional<EventoSensor>
   */
  Optional<EventoSensor> detectar(Ambiente ambiente, Robo robo);
}
