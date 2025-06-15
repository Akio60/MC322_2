package missao;

import ambiente.Ambiente;
import comunicacao.LoggerMissao;
import robo.Robo;

/**
 * Classe abstrata para agentes inteligentes.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public abstract class AgenteInteligente extends Robo {
  protected Missao missao;

  public AgenteInteligente(String nome) {
    super(nome);
  }

  public void setMissao(Missao missao) {
    this.missao = missao;
  }

  public void executarMissao(Ambiente ambiente, LoggerMissao logger) {
    boolean concluida = false;
    while (!concluida) {
      concluida = missao.executarPasso(this, ambiente);
      logger.logar(this, ambiente);
    }
  }

  public abstract void moverAleatorio(Ambiente ambiente);
  public abstract boolean moverPara(int[] destino, Ambiente ambiente);

    public Object getMissao() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
