package leitura.gravacao_arquivos;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        int op = 0;
        File arquivoMateriais = null;
        File arquivoRotas = null;
        DataValidator dataValidator = new DataValidator();

        // Inicializar o grafo sem carregar rotas inicialmente
        Grafo grafo = new Grafo();

        do {
            try {
                op = Integer.parseInt(JOptionPane.showInputDialog(
                        "Sistema de Cooperativa de Reciclagem\n" +
                                "1 - Selecionar arquivo Materiais.txt\n" +
                                "2 - Selecionar arquivo Rotas.txt\n" +
                                "3 - Validar e processar arquivos\n" +
                                "4 - Carregar materiais na Trie e interagir\n" +
                                "5 - Carregar rotas no Grafo e interagir\n" +
                                "6 - Gerar relatorios\n" +
                                "7 - Sair"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Erro: Entrada inválida. Por favor, digite apenas números inteiros.");
            }

            switch (op) {
                case 1:
                    arquivoMateriais = selecionarArquivo("Selecione o arquivo Materiais.txt");
                    break;
                case 2:
                    arquivoRotas = selecionarArquivo("Selecione o arquivo Rotas.txt");
                    break;
                case 3:
                    if (arquivoMateriais != null && arquivoRotas != null) {
                        try {
                            List<String> materiais = dataValidator.carregarMateriais(arquivoMateriais);
                            List<String> rotas = dataValidator.carregarRotas(arquivoRotas);
                            dataValidator.validarMateriais(materiais);
                            dataValidator.validarRotas(rotas);

                            JOptionPane.showMessageDialog(null, "Arquivos validados e processados com sucesso!");
                        } catch (IllegalArgumentException e) {
                            JOptionPane.showMessageDialog(null, "Erro de validação: " + e.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Certifique-se de selecionar ambos os arquivos!");
                    }
                    break;
                case 4:
                    if (arquivoMateriais != null) {
                        ArvoresTries.iniciarInteracaoComTrie(arquivoMateriais);
                    } else {
                        JOptionPane.showMessageDialog(null, "Arquivo Materiais.txt não selecionado!");
                    }
                    break;
                case 5:
                    if (arquivoRotas != null) {
                        carregarRotasNoGrafo(grafo, arquivoRotas);
                        GrafosMenu.iniciarInteracaoComGrafo(arquivoRotas);
                    } else {
                        JOptionPane.showMessageDialog(null, "Arquivo Rotas.txt não selecionado!");
                    }
                    break;
                case 6:
                    if (arquivoMateriais != null && arquivoRotas != null) {
                        RelatoriosMenu.iniciarMenuRelatorios(arquivoMateriais, grafo);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Certifique-se de selecionar os arquivos Materiais.txt e Rotas.txt!");
                    }
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (op != 7);
    }

    private static File selecionarArquivo(String titulo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado.");
            return null;
        }
    }

    private static void carregarRotasNoGrafo(Grafo grafo, File arquivoRotas) {
        if (arquivoRotas == null) {
            JOptionPane.showMessageDialog(null, "Arquivo Rotas.txt não selecionado!");
            return;
        }

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
