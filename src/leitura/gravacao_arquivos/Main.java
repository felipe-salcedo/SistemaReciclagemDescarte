package leitura.gravacao_arquivos;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        int op = 0;
        File arquivoMateriais = null;
        File arquivoRotas = null;
        DataValidator dataValidator = new DataValidator();

        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(
                    "Sistema de Cooperativa de Reciclagem\n" +
                            "1 - Selecionar arquivo Materiais.txt\n" +
                            "2 - Selecionar arquivo Rotas.txt\n" +
                            "3 - Validar e processar arquivos\n" +
                            "4 - Carregar materiais na Trie e interagir\n" +
                /*A fazer */"5 - Carregar rotas no Grafo e interagir\n" +
                /*A fazer */"6 - Gerar relatorios\n" +
                            "7 - Sair"));
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
                    ArvoresTries.iniciarInteracaoComTrie(arquivoMateriais);
                    break;

                case 5:
                ArvoresTries.iniciarInteracaoComTrie(arquivoMateriais);
                break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (op != 5);
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
}
