package missao;

/**
 * Interface funcional para comunicação de robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
@FunctionalInterface
public interface Comunicavel {
  void comunicar(String mensagem);
}
