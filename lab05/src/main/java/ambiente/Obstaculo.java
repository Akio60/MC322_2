package ambiente;

/**
 * Representa um obstáculo no ambiente.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class Obstaculo {
  private TipoObstaculo tipo;
  private int x, y, z;

  public Obstaculo(TipoObstaculo tipo) {
    this.tipo = tipo;
  }

  public TipoObstaculo getTipo() {
    return tipo;
  }

  public void setPosicao(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public int[] getPosicao() {
    return new int[]{x, y, z};
  }
}
