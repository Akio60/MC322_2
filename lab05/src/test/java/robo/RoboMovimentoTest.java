package robo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

import ambiente.Ambiente;
import robo.terrestre.RoboTanque;

class RoboMovimentoTest {
  @Test
  void testMoverRobo() throws Exception {
    Ambiente a = new Ambiente(5, 5, 1);
    RoboTanque r = new RoboTanque("R2");
    a.adicionarRobo(r, 0, 0, 0);
    r.ligar();
    r.mover(1, 0, 0, a);
    assertArrayEquals(new int[]{1, 0, 0}, r.getPosicao());
  }

  @Test
  void testColisaoException() throws Exception {
    Ambiente a = new Ambiente(3, 3, 1);
    RoboTanque r1 = new RoboTanque("R3");
    RoboTanque r2 = new RoboTanque("R4");
    a.adicionarRobo(r1, 0, 0, 0);
    a.adicionarRobo(r2, 1, 0, 0);
    r1.ligar();
  }

  @Test
  void testForaDosLimitesException() throws Exception {
    Ambiente a = new Ambiente(2, 2, 1);
    RoboTanque r = new RoboTanque("R5");
    a.adicionarRobo(r, 0, 0, 0);
    r.ligar();
  }
}
