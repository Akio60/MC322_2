package ambiente;

import ambiente.mapa.TipoEntidade;
import robo.Robo;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o ambiente 3D do simulador de robôs.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class Ambiente {
  private final int largura;
  private final int altura;
  private final int profundidade;
  private TipoEntidade[][][] entidades;
  private List<Robo> robos;
  private List<Obstaculo> obstaculos;

  public Ambiente(int largura, int altura, int profundidade) {
    this.largura = largura;
    this.altura = altura;
    this.profundidade = profundidade;
    this.entidades = new TipoEntidade[largura][altura][profundidade];
    this.robos = new ArrayList<>();
    this.obstaculos = new ArrayList<>();
    for (int x = 0; x < largura; x++) {
      for (int y = 0; y < altura; y++) {
        for (int z = 0; z < profundidade; z++) {
          entidades[x][y][z] = TipoEntidade.LIVRE;
        }
      }
    }
  }

  public boolean estaLivre(int x, int y, int z) {
    if (!dentroLimites(x, y, z)) return false;
    return entidades[x][y][z] == TipoEntidade.LIVRE;
  }

  public boolean dentroLimites(int x, int y, int z) {
    return x >= 0 && x < largura && y >= 0 && y < altura && z >= 0 && z < profundidade;
  }

  public void adicionarRobo(Robo robo, int x, int y, int z) {
    if (!estaLivre(x, y, z)) throw new IllegalArgumentException("Posição ocupada.");
    entidades[x][y][z] = TipoEntidade.ROBO;
    robo.setPosicao(x, y, z);
    robos.add(robo);
  }

  public void adicionarObstaculo(Obstaculo obstaculo, int x, int y, int z) {
    if (!estaLivre(x, y, z)) throw new IllegalArgumentException("Posição ocupada.");
    entidades[x][y][z] = TipoEntidade.OBSTACULO;
    obstaculo.setPosicao(x, y, z);
    obstaculos.add(obstaculo);
  }

  public void moverRobo(Robo robo, int novoX, int novoY, int novoZ) {
    int[] pos = robo.getPosicao();
    entidades[pos[0]][pos[1]][pos[2]] = TipoEntidade.LIVRE;
    entidades[novoX][novoY][novoZ] = TipoEntidade.ROBO;
    robo.setPosicao(novoX, novoY, novoZ);
  }

  public int getLargura() { return largura; }
  public int getAltura() { return altura; }
  public int getProfundidade() { return profundidade; }
  public List<Robo> getRobos() { return robos; }
  public List<Obstaculo> getObstaculos() { return obstaculos; }
}
