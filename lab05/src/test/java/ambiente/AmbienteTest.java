package ambiente;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ambiente.*;
import robo.terrestre.RoboTanque;

class AmbienteTest {
  @Test
  void testAdicionarRoboEObstaculo() {
    Ambiente a = new Ambiente(5, 5, 2);
    RoboTanque r = new RoboTanque("R1");
    a.adicionarRobo(r, 1, 1, 0);
    assertFalse(a.estaLivre(1, 1, 0));
    Obstaculo o = new Obstaculo(TipoObstaculo.PEDRA);
    a.adicionarObstaculo(o, 2, 2, 0);
    assertFalse(a.estaLivre(2, 2, 0));
  }

  @Test
  void testLimites() {
    Ambiente a = new Ambiente(3, 3, 1);
    assertTrue(a.dentroLimites(2, 2, 0));
    assertFalse(a.dentroLimites(3, 0, 0));
    assertFalse(a.dentroLimites(0, 3, 0));
    assertFalse(a.dentroLimites(0, 0, 1));
  }
}
