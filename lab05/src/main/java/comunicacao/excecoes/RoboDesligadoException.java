package comunicacao.excecoes;

/**
 * Exceção lançada quando o robô está desligado.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class RoboDesligadoException extends Exception {
  public RoboDesligadoException() {
    super("O robô está desligado.");
  }
}
