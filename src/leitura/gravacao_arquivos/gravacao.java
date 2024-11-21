/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package leitura.gravacao_arquivos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;

public class gravacao {
    private FileWriter arq;
    private BufferedWriter writer;
    // private PrintWriter gravarArq;

    gravacao(File nomeArq) throws IOException {
        try {
            arq = new FileWriter(nomeArq);
            writer = new BufferedWriter(arq);
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo.\n",
                    e.getMessage());
        }
    }

    void escreveLinha(String texto) throws IOException {
        writer.write(texto);
        writer.flush();
    }

    void fechar() throws IOException {
        arq.close();
    }

}
