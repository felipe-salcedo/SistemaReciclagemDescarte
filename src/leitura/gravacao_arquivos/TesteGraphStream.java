package leitura.gravacao_arquivos;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class TesteGraphStream {
    public static void main(String[] args) {
        // Configurar o pacote de UI para usar Swing
        System.setProperty("org.graphstream.ui", "swing");

        // Criar um grafo usando GraphStream
        Graph graph = new SingleGraph("Grafo");

        // Configurar o estilo visual do grafo
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: blue; size: 70px; text-size: 15px; text-color: white; } " +
                        "edge { fill-color: gray; text-size: 15px; }");

        // Adicionar nós ao grafo
        graph.addNode("A").setAttribute("ui.label", "Ponto A");
        graph.addNode("B").setAttribute("ui.label", "Ponto B");
        graph.addEdge("AB", "A", "B").setAttribute("ui.label", "1");

        // Exibir o grafo
        graph.display();
    }
}
