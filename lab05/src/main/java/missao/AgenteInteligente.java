package missao;

import ambiente.Ambiente;
import comunicacao.LoggerMissao;

/**
 * Contrato para agentes inteligentes capazes de executar missões.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public interface AgenteInteligente {
    void setMissao(Missao missao);
    Missao getMissao();
    void executarMissao(Ambiente ambiente, LoggerMissao logger);
    void moverAleatorio(Ambiente ambiente);
    boolean moverPara(int[] destino, Ambiente ambiente);
}
