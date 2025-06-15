package sensores;

import ambiente.Ambiente;
import java.util.Optional;
import robo.Robo;

/**
 * Sensor de terreno para robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class SensorTerreno implements Sensor {
  @Override
  public Optional<EventoSensor> detectar(Ambiente ambiente, Robo robo) {
    int[] pos = robo.getPosicao();
    // Simulação: sempre retorna um evento de leitura de terreno
    EventoSensor evento = new EventoSensor("Terreno detectado", pos[0], pos[1], pos[2]);
    return Optional.of(evento);
  }
}
