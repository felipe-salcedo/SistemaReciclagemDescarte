package leitura.gravacao_arquivos;

import java.util.*;

import javax.swing.JOptionPane;

public class Grafo {
    private Map<String, List<Aresta>> grafo = new HashMap<>();

    public static class Aresta {
        String destino;
        int peso;

        public Aresta(String destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    // Adicionar uma rota (aresta)
    public void adicionarRota(String origem, String destino, int peso) {
        grafo.putIfAbsent(origem, new ArrayList<>());
        grafo.putIfAbsent(destino, new ArrayList<>());
        grafo.get(origem).add(new Aresta(destino, peso));
        grafo.get(destino).add(new Aresta(origem, peso));
    }

    // Remover uma rota (aresta)
    public void removerRota(String origem, String destino) {
        grafo.getOrDefault(origem, new ArrayList<>())
                .removeIf(aresta -> aresta.destino.equals(destino));
        grafo.getOrDefault(destino, new ArrayList<>())
                .removeIf(aresta -> aresta.destino.equals(origem));
    }

    // Exibir estrutura do grafo
    public void exibirGrafo() {
        StringBuilder estrutura = new StringBuilder("Estrutura do Grafo:\n");
        for (String ponto : grafo.keySet()) {
            estrutura.append("Ponto: ").append(ponto).append("\n");
            for (Aresta aresta : grafo.get(ponto)) {
                estrutura.append("  -> ").append(aresta.destino)
                        .append(" (Distância: ").append(aresta.peso).append(")\n");
            }
        }
        JOptionPane.showMessageDialog(null, estrutura.toString(), "Estrutura do Grafo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Algoritmo de Dijkstra para encontrar o menor caminho
    public List<String> dijkstra(String origem, String destino) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecessores = new HashMap<>();
        PriorityQueue<String> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        Set<String> visitados = new HashSet<>();

        // Inicialização
        for (String ponto : grafo.keySet()) {
            distancias.put(ponto, Integer.MAX_VALUE);
        }
        distancias.put(origem, 0);
        fila.add(origem);

        while (!fila.isEmpty()) {
            String atual = fila.poll();
            if (!visitados.add(atual)) {
                continue;
            }

            for (Aresta aresta : grafo.getOrDefault(atual, new ArrayList<>())) {
                if (!visitados.contains(aresta.destino)) {
                    int novaDistancia = distancias.get(atual) + aresta.peso;
                    if (novaDistancia < distancias.get(aresta.destino)) {
                        distancias.put(aresta.destino, novaDistancia);
                        predecessores.put(aresta.destino, atual);
                        fila.add(aresta.destino);
                    }
                }
            }
        }

        // Reconstruir o caminho
        List<String> caminho = new ArrayList<>();
        String passo = destino;
        while (passo != null) {
            caminho.add(0, passo);
            passo = predecessores.get(passo);
        }

        return caminho.isEmpty() || !caminho.get(0).equals(origem) ? null : caminho;
    }

    // Novo método: Retorna os pontos (nós) do grafo
    public Set<String> getPontos() {
        return grafo.keySet();
    }

    // Novo método: Retorna as arestas conectadas a um ponto
    public List<Aresta> getArestas(String ponto) {
        return grafo.getOrDefault(ponto, new ArrayList<>());
    }
}
