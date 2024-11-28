package leitura.gravacao_arquivos;

import java.util.*;

import javax.swing.JOptionPane;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


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
        boolean removido = grafo.getOrDefault(origem, new ArrayList<>())
                .removeIf(aresta -> aresta.destino.equals(destino));

        removido |= grafo.getOrDefault(destino, new ArrayList<>())
                .removeIf(aresta -> aresta.destino.equals(origem));

        if (!removido) {
            throw new IllegalArgumentException("A rota entre " + origem + " e " + destino + " não existe.");
        }
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
    public Map<String, Object> dijkstra(String origem, String destino) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecessores = new HashMap<>();
        PriorityQueue<String> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        Set<String> visitados = new HashSet<>();
        Map<String, Object> resultado = new HashMap<>();

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

        if (caminho.isEmpty() || !caminho.get(0).equals(origem)) {
            return null; // Caso não haja caminho
        }

        // Adicionando informações no mapa
        resultado.put("caminho", caminho);
        resultado.put("distanciaTotal", distancias.get(destino));
        return resultado;
    }

    // Retorna os pontos (nós) do grafo
    public Set<String> getPontos() {
        return grafo.keySet();
    }

    // Retorna as arestas conectadas a um ponto
    public List<Aresta> getArestas(String ponto) {
        return grafo.getOrDefault(ponto, new ArrayList<>());
    }

    public void exibirMatrizAdjacencia() {
        Set<String> pontos = grafo.keySet();
        List<String> listaPontos = new ArrayList<>(pontos);
        int tamanho = listaPontos.size();

        int[][] matriz = new int[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                String origem = listaPontos.get(i);
                String destino = listaPontos.get(j);

                List<Aresta> arestas = grafo.getOrDefault(origem, new ArrayList<>());
                matriz[i][j] = arestas.stream()
                        .filter(aresta -> aresta.destino.equals(destino))
                        .mapToInt(aresta -> aresta.peso)
                        .findFirst()
                        .orElse(0); // 0 indica ausência de conexão
            }
        }

        StringBuilder matrizTexto = new StringBuilder("Matriz de Adjacência:\n   ");
        for (String ponto : listaPontos) {
            matrizTexto.append(ponto).append("\t");
        }
        matrizTexto.append("\n");

        for (int i = 0; i < tamanho; i++) {
            matrizTexto.append(listaPontos.get(i)).append("  ");
            for (int j = 0; j < tamanho; j++) {
                matrizTexto.append(matriz[i][j]).append("\t");
            }
            matrizTexto.append("\n");
        }

        JOptionPane.showMessageDialog(null, matrizTexto.toString(), "Matriz de Adjacência",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirGrafoGrafico() {
        // Criar um grafo usando GraphStream
        Graph graph = new SingleGraph("Grafo");

        // Configurar estilo visual do grafo
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: blue; size: 70px; text-size: 15px; text-color: white; } " +
                        "edge { fill-color: gray; text-size: 15px; }");

        // Adicionar nós ao grafo
        for (String ponto : grafo.keySet()) {
            Node node = graph.addNode(ponto);
            node.setAttribute("ui.label", ponto);
        }

        // Adicionar arestas ao grafo
        for (String origem : grafo.keySet()) {
            for (Aresta aresta : grafo.get(origem)) {
                String destino = aresta.destino;
                String edgeId = origem + "-" + destino;

                // Verifica se a aresta já foi adicionada (grafo não-direcionado)
                if (graph.getEdge(edgeId) == null && graph.getEdge(destino + "-" + origem) == null) {
                    Edge edge = graph.addEdge(edgeId, origem, destino);
                    edge.setAttribute("ui.label", String.valueOf(aresta.peso));
                }
            }
        }
        // Exibir o grafo em uma janela
        graph.display();

        
    }
}
