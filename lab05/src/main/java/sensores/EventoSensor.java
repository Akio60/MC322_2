package sensores;

/**
 * Evento gerado por sensores de robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class EventoSensor {
  private String descricao;
  private int x, y, z;

  public EventoSensor(String descricao, int x, int y, int z) {
    this.descricao = descricao;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public String getDescricao() { return descricao; }
  public int getX() { return x; }
  public int getY() { return y; }
  public int getZ() { return z; }
}
