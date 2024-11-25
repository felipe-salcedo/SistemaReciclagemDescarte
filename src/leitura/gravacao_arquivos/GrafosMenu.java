package leitura.gravacao_arquivos;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class GrafosMenu {
    public static void iniciarInteracaoComGrafo(File arquivoRotas) {
        Grafo grafo = new Grafo();
        carregarRotasNoGrafo(grafo, arquivoRotas);

        int escolhaGrafo;
        do {
            escolhaGrafo = Integer.parseInt(JOptionPane.showInputDialog(
                "Modelagem e Manipulação de Grafos\n" +
                "1 - Exibir estrutura do grafo\n" +
                "2 - Adicionar rota\n" +
                "3 - Remover rota\n" +
                "4 - Encontrar menor caminho (Dijkstra)\n" +
                "5 - Voltar\n" +
                "Escolha uma opção:"));

            switch (escolhaGrafo) {
                case 1:
                    System.out.println("Estrutura do Grafo:");
                    grafo.exibirGrafo();
                    break;

                case 2:
                    String origem = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    String destino = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    int peso = Integer.parseInt(JOptionPane.showInputDialog("Digite a distância entre os pontos:"));
                    grafo.adicionarRota(origem, destino, peso);
                    JOptionPane.showMessageDialog(null, "Rota adicionada com sucesso!");
                    break;

                case 3:
                    origem = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    destino = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    grafo.removerRota(origem, destino);
                    JOptionPane.showMessageDialog(null, "Rota removida com sucesso!");
                    break;

                case 4:
                    origem = JOptionPane.showInputDialog("Digite o ponto de origem:");
                    destino = JOptionPane.showInputDialog("Digite o ponto de destino:");
                    List<String> caminho = grafo.dijkstra(origem, destino);
                    if (caminho == null) {
                        JOptionPane.showMessageDialog(null, "Não há caminho disponível entre os pontos.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Menor caminho: " + String.join(" -> ", caminho));
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

    private static void carregarRotasNoGrafo(Grafo grafo, File arquivoRotas) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoRotas))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    String origem = partes[0].trim();
                    String destino = partes[1].trim();
                    int peso = Integer.parseInt(partes[2].trim());
                    grafo.adicionarRota(origem, destino, peso);
                }
            }
            JOptionPane.showMessageDialog(null, "Rotas carregadas no grafo com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar rotas: " + e.getMessage());
        }
    }
}

