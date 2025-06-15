package ambiente;

/**
 * Enumera os tipos de obstáculos possíveis.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public enum TipoObstaculo {
  PEDRA(3, true),
  ARVORE(5, true),
  AGUA(0, true),
  BURACO(0, true);

  private final int peso;
  private final boolean bloqueia;

  TipoObstaculo(int peso, boolean bloqueia) {
    this.peso = peso;
    this.bloqueia = bloqueia;
  }

  public int getPeso() { return peso; }
  public boolean isBloqueia() { return bloqueia; }
}
