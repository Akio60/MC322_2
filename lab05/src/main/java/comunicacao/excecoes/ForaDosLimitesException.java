package comunicacao.excecoes;

/**
 * Exceção lançada quando o robô sai dos limites do ambiente.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class ForaDosLimitesException extends Exception {
  public ForaDosLimitesException() {
    super("Fora dos limites do ambiente.");
  }
}
