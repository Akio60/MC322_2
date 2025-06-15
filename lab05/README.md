# Simulador de Robôs – MC322

## Como compilar e executar

```bash
mvn clean package
mvn exec:java -Dexec.mainClass="main.SimuladorCLI"
```

## Como rodar os testes

```bash
mvn test
```

## Estrutura

- src/ambiente: Ambiente, obstáculos, tipos de entidade
- src/robo: Robôs, especializações, subsistemas
- src/sensores: Sensores
- src/missao: Missões
- src/comunicacao: Logger e exceções
- src/main: CLI
- test/: Testes JUnit 5
- docs/: Diagrama UML (PlantUML)

## Diagrama UML

Veja o arquivo `docs/diagrama.puml` para o diagrama de classes PlantUML.

## Autor

Vitor Akio Isawa  
@reviewer Laura Bianchi
