package leitura.gravacao_arquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
     public static class LeituraArquivo {
        private FileReader arq;
        private BufferedReader lerArq;

        public LeituraArquivo(File nomeArq) throws IOException {
            arq = new FileReader(nomeArq);
            lerArq = new BufferedReader(arq);
        }

        public String getLinha() throws IOException {
            return lerArq.readLine();
        }

        public void fechar() throws IOException {
            arq.close();
        }
    }

    public static class Gravacao {
        private FileWriter arq;
        private BufferedWriter writer;

        public Gravacao(File nomeArq) throws IOException {
            arq = new FileWriter(nomeArq);
            writer = new BufferedWriter(arq);
        }

        public void escreveLinha(String texto) throws IOException {
            writer.write(texto);
            writer.flush();
        }

        public void fechar() throws IOException {
            writer.close();
        }
    }
}
