package leitura.gravacao_arquivos;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class RelatoriosMenu {

    public static void iniciarMenuRelatorios(File arquivoMateriais, Grafo grafo) {
        int escolhaRelatorio;
        do {
            escolhaRelatorio = Integer.parseInt(JOptionPane.showInputDialog(
                "Geração de Relatórios\n" +
                "1 - Gerar relatório de Materiais por Ponto de Coleta\n" +
                "2 - Gerar relatório de Rotas Mais Curtas\n" +
                "3 - Voltar\n" +
                "Escolha uma opção:"));

            switch (escolhaRelatorio) {
                case 1:
                    String caminhoMateriais = JOptionPane.showInputDialog("Digite o nome do arquivo para salvar o relatório de materiais (ex: materiais.txt):");
                    gerarRelatorioMateriaisPorPonto(arquivoMateriais, caminhoMateriais);
                    JOptionPane.showMessageDialog(null, "Relatório de Materiais por Ponto de Coleta gerado com sucesso!");
                    break;

                case 2:
                    String caminhoRotas = JOptionPane.showInputDialog("Digite o nome do arquivo para salvar o relatório de rotas (ex: rotas.txt):");
                    gerarRelatorioRotasMaisCurtas(grafo, caminhoRotas);
                    JOptionPane.showMessageDialog(null, "Relatório de Rotas Mais Curtas gerado com sucesso!");
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (escolhaRelatorio != 3);
    }

    private static void gerarRelatorioMateriaisPorPonto(File arquivoMateriais, String caminhoRelatorio) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoMateriais));
             BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoRelatorio))) {
            
            Map<String, List<String>> materiaisPorPonto = new TreeMap<>();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    String nome = partes[0].trim();
                    String pontoDeColeta = partes[2].trim();
                    materiaisPorPonto.putIfAbsent(pontoDeColeta, new ArrayList<>());
                    materiaisPorPonto.get(pontoDeColeta).add(nome);
                }
            }

            // Escrever no relatório
            bw.write("Relatório de Materiais por Ponto de Coleta:\n\n");
            for (Map.Entry<String, List<String>> entrada : materiaisPorPonto.entrySet()) {
                bw.write("Ponto de Coleta: " + entrada.getKey() + "\n");
                for (String material : entrada.getValue()) {
                    bw.write("  - " + material + "\n");
                }
                bw.write("\n");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private static void gerarRelatorioRotasMaisCurtas(Grafo grafo, String caminhoRelatorio) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoRelatorio))) {
            bw.write("Relatório de Rotas Mais Curtas:\n\n");

            // Para cada ponto no grafo
            for (String pontoOrigem : grafo.getPontos()) {
                bw.write("Ponto de Origem: " + pontoOrigem + "\n");
                int menorDistancia = Integer.MAX_VALUE;
                String pontoMaisProximo = null;

                // Calcular menor caminho para todos os outros pontos
                for (String pontoDestino : grafo.getPontos()) {
                    if (!pontoOrigem.equals(pontoDestino)) {
                        List<String> caminho = grafo.dijkstra(pontoOrigem, pontoDestino);
                        if (caminho != null) {
                            int distancia = calcularDistanciaDoCaminho(grafo, caminho);
                            if (distancia < menorDistancia) {
                                menorDistancia = distancia;
                                pontoMaisProximo = pontoDestino;
                            }
                        }
                    }
                }

                // Escrever menor rota
                if (pontoMaisProximo != null) {
                    List<String> menorCaminho = grafo.dijkstra(pontoOrigem, pontoMaisProximo);
                    bw.write("  - Ponto mais próximo: " + pontoMaisProximo + "\n");
                    bw.write("  - Menor caminho: " + String.join(" -> ", menorCaminho) + "\n");
                    bw.write("  - Distância: " + menorDistancia + "\n\n");
                } else {
                    bw.write("  - Nenhuma rota disponível.\n\n");
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private static int calcularDistanciaDoCaminho(Grafo grafo, List<String> caminho) {
        int distanciaTotal = 0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            String origem = caminho.get(i);
            String destino = caminho.get(i + 1);

            for (Grafo.Aresta aresta : grafo.getArestas(origem)) {
                if (aresta.destino.equals(destino)) {
                    distanciaTotal += aresta.peso;
                    break;
                }
            }
        }
        return distanciaTotal;
    }
}

