/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package leitura.gravacao_arquivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class leituraArquivo {
    private FileReader arq;
    private BufferedReader lerArq;

    leituraArquivo(File nomeArq) throws IOException {
        try {
            arq = new FileReader(nomeArq);
            lerArq = new BufferedReader(arq);
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo.\n",
                    e.getMessage());
        }
    }

    String getLinha() throws IOException {
        return (lerArq.readLine());
    }

    void fechar() throws IOException {
        arq.close();
    }
}
