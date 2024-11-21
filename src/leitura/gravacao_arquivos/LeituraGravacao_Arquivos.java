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
        String arquivo = "", linha = "", texto = "";
        String diretorio;
        File arquivoSelecionado = null;
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(
                    "Manipulação de Arquivos de Texto\n" +
                            "1 - Abrir arquivo texto\n" +
                            "2 - Fazer leitura de Dados\n" +
                            "3 - Escrever dados\n" +
                            "4 - Sair"));
            switch (op) {
                case 1:
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Selecione um arquivo .txt para abrir");

                    int userSelection = fileChooser.showOpenDialog(null);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        arquivoSelecionado = fileChooser.getSelectedFile();
                    } else {
                        System.out.println("Nenhum arquivo foi selecionado.");
                    }
                    break;
                case 2:
                    if (arquivoSelecionado != null) {
                        leituraArquivo lerArq = new leituraArquivo(arquivoSelecionado);
                        texto = "";
                        linha = lerArq.getLinha();
                        while (linha != null) {
                            texto += linha + "\n";

                            linha = lerArq.getLinha(); // lê da segunda até a última linha
                        }
                        JOptionPane.showMessageDialog(null, texto);
                        lerArq.fechar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Abra o arquivo primeiro!");
                    }
                    break;
                case 3:
                    if (arquivoSelecionado != null) {
                        gravacao gravaArq = new gravacao(arquivoSelecionado);
                        texto = JOptionPane.showInputDialog("Informe o texto a ser inserido:");
                        gravaArq.escreveLinha(texto);

                        gravaArq.fechar();
                    } else {

                        JOptionPane.showMessageDialog(null, "Abra o arquivo primeiro!");
                    }
                    break;
                case 4:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (op != 4);
    }

}
