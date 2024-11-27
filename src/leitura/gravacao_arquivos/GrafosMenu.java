package leitura.gravacao_arquivos;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class GrafosMenu {
    public static void iniciarInteracaoComGrafo(File arquivoRotas, Grafo grafo) {
        // Grafo grafo = new Grafo();
        // carregarRotasNoGrafo(grafo, arquivoRotas);

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
                    grafo.exibirGrafo();
                    break;

                case 2:
                    String nomeRota = JOptionPane.showInputDialog("Digite o nome da rota:");
                    String origem = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    String destino = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    int peso = Integer.parseInt(JOptionPane.showInputDialog("Digite a distância entre os pontos:"));
                    grafo.adicionarRota(nomeRota, origem, destino, peso);
                    JOptionPane.showMessageDialog(null, "Rota adicionada com sucesso!");
                    break;

                case 3:
                    nomeRota = JOptionPane.showInputDialog("Digite o nome da rota:");
                    origem = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    destino = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    grafo.removerRota(nomeRota, origem, destino);
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
                        nomeRota = (String) resultado.get("nomeRota");

                        // Exibir o caminho, distância e nome da rota
                        JOptionPane.showMessageDialog(null, "A menor rota entre "
                                + origem + " e " + destino + " e a:" +
                                nomeRota + "\nDistância total: " + distanciaTotal);
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
