package comunicacao;

import robo.Robo;
import ambiente.Ambiente;
import sensores.Sensor;
import sensores.EventoSensor;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Logger de missão em formato JSON.
 * @author  Vitor Akio Isawa
 * @version 1.0
 * @since   2025-06
 * @reviewer Laura Bianchi
 */
public class LoggerMissao {
  private BufferedWriter writer;
  private String nomeArquivo;

  public LoggerMissao(String nomeRobo) {
    try {
      String data = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
      Files.createDirectories(Paths.get("logs"));
      nomeArquivo = "logs/" + nomeRobo + "-" + data + ".jsonl";
      writer = new BufferedWriter(new FileWriter(nomeArquivo, true));
    } catch (IOException e) {
      throw new RuntimeException("Erro ao criar arquivo de log", e);
    }
  }

  public void logar(Robo robo, Ambiente ambiente) {
    try {
      Map<String, Object> log = new LinkedHashMap<>();
      log.put("tempo", System.currentTimeMillis());
      log.put("robo", robo.getNome());
      int[] pos = robo.getPosicao();
      log.put("x", pos[0]);
      log.put("y", pos[1]);
      log.put("z", pos[2]);
      List<String> eventos = new ArrayList<>();
      for (Sensor s : robo.getSensores()) {
        Optional<EventoSensor> ev = s.detectar(ambiente, robo);
        ev.ifPresent(e -> eventos.add(e.getDescricao() + " (" + e.getX() + "," + e.getY() + "," + e.getZ() + ")"));
      }
      log.put("eventos", eventos);
      writer.write(toJson(log));
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      System.err.println("Erro ao escrever log: " + e.getMessage());
    }
  }

  private String toJson(Map<String, Object> map) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, Object> entry = it.next();
      sb.append("\"").append(entry.getKey()).append("\":");
      if (entry.getValue() instanceof String) {
        sb.append("\"").append(entry.getValue()).append("\"");
      } else if (entry.getValue() instanceof List) {
        sb.append("[");
        List<?> list = (List<?>) entry.getValue();
        for (int i = 0; i < list.size(); i++) {
          sb.append("\"").append(list.get(i)).append("\"");
          if (i < list.size() - 1) sb.append(",");
        }
        sb.append("]");
      } else {
        sb.append(entry.getValue());
      }
      if (it.hasNext()) sb.append(",");
    }
    sb.append("}");
    return sb.toString();
  }

  public void fechar() {
    try {
      if (writer != null) writer.close();
    } catch (IOException e) {
      // Ignorar
    }
  }

  public String getNomeArquivo() {
    return nomeArquivo;
  }
}
