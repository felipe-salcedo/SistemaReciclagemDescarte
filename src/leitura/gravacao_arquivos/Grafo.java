package leitura.gravacao_arquivos;

import java.util.*;

import javax.swing.JOptionPane;

public class Grafo {
    private Map<String, List<Aresta>> grafo = new HashMap<>();

    public static class Aresta {
        String destino;
        int peso;
        String nomeRota;

        public Aresta(String destino, int peso, String nomeRota) {
            this.destino = destino;
            this.peso = peso;
            this.nomeRota = nomeRota;
        }
    }

    // Adicionar uma rota (aresta)
    public void adicionarRota(String nomeRota, String origem, String destino, int peso) {
        if (rotaExiste(nomeRota)) {
            throw new IllegalArgumentException("A rota com o nome '" + nomeRota + "' já existe.");
        }

        grafo.putIfAbsent(origem, new ArrayList<>());
        grafo.putIfAbsent(destino, new ArrayList<>());
        grafo.get(origem).add(new Aresta(destino, peso, nomeRota));
        grafo.get(destino).add(new Aresta(origem, peso, nomeRota));

    }

    // Remover uma rota (aresta)
    public void removerRota(String nomeRota, String origem, String destino) {
        boolean removido = grafo.getOrDefault(origem, new ArrayList<>())
                .removeIf(aresta -> aresta.destino.equals(destino) && aresta.nomeRota.equals(nomeRota));

        removido |= grafo.getOrDefault(destino, new ArrayList<>())
                .removeIf(aresta -> aresta.destino.equals(origem) && aresta.nomeRota.equals(nomeRota));

        if (!removido) {
            throw new IllegalArgumentException("A rota com o nome '" + nomeRota + "' não existe.");
        }
    }

    // Exibir estrutura do grafo
    public void exibirGrafo() {
        StringBuilder estrutura = new StringBuilder("Estrutura do Grafo:\n");
        for (String ponto : grafo.keySet()) {
            estrutura.append("Ponto: ").append(ponto).append("\n");
            for (Aresta aresta : grafo.get(ponto)) {
                estrutura.append("  -> ").append(aresta.destino)
                        .append(" (Distância: ").append(aresta.peso)
                        .append(", Rota: ").append(aresta.nomeRota).append(")\n");
            }
        }
        JOptionPane.showMessageDialog(null, estrutura.toString(), "Estrutura do Grafo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean rotaExiste(String nomeRota) {
        for (List<Aresta> arestas : grafo.values()) {
            for (Aresta aresta : arestas) {
                if (aresta.nomeRota.equals(nomeRota)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Algoritmo de Dijkstra para encontrar o menor caminho
    public Map<String, Object> dijkstra(String origem, String destino) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecessores = new HashMap<>();
        PriorityQueue<String> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        Set<String> visitados = new HashSet<>();
        Map<String, Object> resultado = new HashMap<>();
        String nomeRotaMaisProxima = null;

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
            String anterior = predecessores.get(passo);
            if (anterior != null) {
                // Encontrar a aresta correspondente entre `anterior` e `passo`
                for (Aresta aresta : grafo.get(anterior)) {
                    if (aresta.destino.equals(passo)) {
                        nomeRotaMaisProxima = aresta.nomeRota; // Armazenar o nome da rota
                        break;
                    }
                }
            }
            caminho.add(0, passo);
            passo = anterior;
        }

        if (caminho.isEmpty() || !caminho.get(0).equals(origem)) {
            return null; // Caso não haja caminho
        }


        // Adicionando informações no mapa
        resultado.put("caminho", caminho);
        resultado.put("distanciaTotal", distancias.get(destino));
        resultado.put("nomeRota", nomeRotaMaisProxima); // Associando o nome da rota

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
}
