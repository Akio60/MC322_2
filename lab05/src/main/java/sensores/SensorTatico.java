package sensores;

import ambiente.Ambiente;
import java.util.Optional;
import robo.Robo;

/**
 * Sensor tático para robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class SensorTatico extends Sensor {
  @Override
  public Optional<EventoSensor> detectar(Ambiente ambiente, Robo robo) {
    int[] pos = robo.getPosicao();
    EventoSensor evento = new EventoSensor("Tático: área segura", pos[0], pos[1], pos[2]);
    return Optional.of(evento);
  }
}
