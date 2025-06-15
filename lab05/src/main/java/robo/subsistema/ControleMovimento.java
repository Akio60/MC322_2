package robo.subsistema;

import ambiente.Ambiente;
import comunicacao.excecoes.ColisaoException;
import comunicacao.excecoes.ForaDosLimitesException;
import comunicacao.excecoes.RoboDesligadoException;
import robo.Robo;

/**
 * Subsistema de controle de movimento do robô.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class ControleMovimento {
  private final Robo robo;

  public ControleMovimento(Robo robo) {
    this.robo = robo;
  }

  public void mover(int dx, int dy, int dz, Ambiente ambiente)
      throws ColisaoException, ForaDosLimitesException, RoboDesligadoException {
    if (!robo.isLigado()) throw new RoboDesligadoException();
    int[] pos = robo.getPosicao();
    int novoX = pos[0] + dx;
    int novoY = pos[1] + dy;
    int novoZ = pos[2] + dz;
    if (!ambiente.dentroLimites(novoX, novoY, novoZ)) {
      throw new ForaDosLimitesException();
    }
    if (!ambiente.estaLivre(novoX, novoY, novoZ)) {
      throw new ColisaoException();
    }
    ambiente.moverRobo(robo, novoX, novoY, novoZ);
  }
}
