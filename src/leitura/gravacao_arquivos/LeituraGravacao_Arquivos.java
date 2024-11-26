/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package leitura.gravacao_arquivos;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author bruno
 */
public class LeituraGravacao_Arquivos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int op = 0;
        File arquivoMateriais = null;
        File arquivoRotas = null;
        DataValidator dataValidator = new DataValidator();

        do {
            try {
                op = Integer.parseInt(JOptionPane.showInputDialog(
                    "Sistema de Cooperativa de Reciclagem\n" +
                            "1 - Selecionar arquivo Materiais.txt\n" +
                            "2 - Selecionar arquivo Rotas.txt\n" +
                            "3 - Validar e processar arquivos\n" +
                            "4 - Sair"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Erro: Entrada inválida. Por favor, digite apenas números inteiros.");
            }
           
            switch (op) {
                case 1:
                    arquivoMateriais = selecionarArquivo("Selecione o arquivo Materiais.txt");

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
                    JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (op != 4);
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
