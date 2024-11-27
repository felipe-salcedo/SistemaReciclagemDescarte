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
                        // A chamada ao método agora retorna um Map<String, Object>
                        Map<String, Object> resultado = grafo.dijkstra(pontoOrigem, pontoDestino);
                        if (resultado != null) {
                            // Obter o caminho da chave "caminho"
                            List<String> caminho = (List<String>) resultado.get("caminho");
                            int distancia = (int) resultado.get("distanciaTotal"); // A distância total
                            if (distancia < menorDistancia) {
                                menorDistancia = distancia;
                                pontoMaisProximo = pontoDestino;
                            }
                        }
                    }
                }
    
                // Escrever menor rota
                if (pontoMaisProximo != null) {
                    Map<String, Object> resultado = grafo.dijkstra(pontoOrigem, pontoMaisProximo);
                    List<String> menorCaminho = (List<String>) resultado.get("caminho"); // Extraímos o caminho
                    int distanciaFinal = (int) resultado.get("distanciaTotal"); // Distância final
                    String nomeRota = (String) resultado.get("nomeRota"); // Nome da rota
    
                    bw.write("  - Ponto mais próximo: " + pontoMaisProximo + "\n");
                    bw.write("  - Nome da rota: " + nomeRota + "\n");  // Exibindo o nome da rota
                    bw.write("  - Menor caminho: " + String.join(" -> ", menorCaminho) + "\n");
                    bw.write("  - Distância: " + distanciaFinal + "\n\n");
                } else {
                    bw.write("  - Nenhuma rota disponível.\n\n");
                }
            }
    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
        }
    }
    

}

