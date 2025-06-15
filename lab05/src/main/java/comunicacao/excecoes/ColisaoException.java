package comunicacao.excecoes;

/**
 * Exceção lançada em caso de colisão.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class ColisaoException extends Exception {
  public ColisaoException() {
    super("Colisão detectada: destino ocupado.");
  }
}
