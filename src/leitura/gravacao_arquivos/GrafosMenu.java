package leitura.gravacao_arquivos;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class GrafosMenu {
    public static void iniciarInteracaoComGrafo(File arquivoRotas, Grafo grafo) {
        int escolhaGrafo = 0;
        do {

            try {
                escolhaGrafo = Integer.parseInt(JOptionPane.showInputDialog(
                        "Modelagem e Manipulação de Grafos\n" +
                                "1 - Exibir estrutura do grafo\n" +
                                "2 - Adicionar rota\n" +
                                "3 - Remover rota\n" +
                                "4 - Encontrar menor caminho (Dijkstra)\n" +
                                "5 - Voltar\n" +
                                "Escolha uma opção:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Erro: Entrada inválida. Por favor, digite apenas números inteiros.");
            }

            switch (escolhaGrafo) {
                case 1:
                    int escolhaVisualizacao = JOptionPane.showOptionDialog(
                            null,
                            "Escolha como deseja visualizar o grafo:",
                            "Visualização do Grafo",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[] { "Por texto", "Visualização Gráfica" },
                            "Por texto");

                    if (escolhaVisualizacao == 0) {
                        grafo.exibirGrafo();
                    } else if (escolhaVisualizacao == 1) {
                        grafo.exibirGrafoGrafico();
                    }
                    break;

                case 2:
                    String origem = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    String destino = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    int peso = Integer.parseInt(JOptionPane.showInputDialog("Digite a distância entre os pontos:"));
                    grafo.adicionarRota(origem, destino, peso);
                    JOptionPane.showMessageDialog(null, "Rota adicionada com sucesso!");
                    break;

                case 3:
                    String origemRemover = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    String destinoRemover = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    grafo.removerRota(origemRemover, destinoRemover);
                    JOptionPane.showMessageDialog(null, "Rota removida com sucesso!");
                    break;

                case 4:
                    origem = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    destino = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    Map<String, Object> resultado = grafo.dijkstra(origem, destino);
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Não há caminho disponível entre os pontos.");
                    } else {
                        int distanciaTotal = (int) resultado.get("distanciaTotal");
                        List<String> caminho = (List<String>) resultado.get("caminho");

                        // Exibir o caminho e a distância total
                        JOptionPane.showMessageDialog(null, "A menor rota entre "
                                + origem + " e " + destino + " é:\n" +
                                String.join(" -> ", caminho) +
                                "\nDistância total: " + distanciaTotal + " unidades.");
                    }
                    break;

                case 5:
                    JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (escolhaGrafo != 5);
    }
}
