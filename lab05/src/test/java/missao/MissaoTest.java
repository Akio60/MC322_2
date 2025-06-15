package missao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import ambiente.Ambiente;
import robo.terrestre.RoboTanque;

class MissaoTest {
  @Test
  void testMissaoExplorar() {
    Ambiente a = new Ambiente(5, 5, 1);
    RoboTanque agente = new RoboTanque("Explorador");
    a.adicionarRobo(agente, 0, 0, 0);
    agente.ligar();
    MissaoExplorar m = new MissaoExplorar(3);
    agente.setMissao(m);
    assertFalse(m.executarPasso(agente, a));
    assertFalse(m.executarPasso(agente, a));
    assertTrue(m.executarPasso(agente, a));
  }

  @Test
  void testMissaoBuscarPonto() {
    Ambiente a = new Ambiente(5, 5, 1);
    RoboTanque agente = new RoboTanque("Buscador");
    a.adicionarRobo(agente, 0, 0, 0);
    agente.ligar();
    MissaoBuscarPonto m = new MissaoBuscarPonto(new int[]{1,1,0});
    agente.setMissao(m);
    assertTrue(m.executarPasso(agente, a));
  }
}