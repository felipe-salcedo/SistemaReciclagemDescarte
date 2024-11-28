package leitura.gravacao_arquivos;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class DataValidator {
     // Método para carregar e validar dados do arquivo Materiais.txt
     public List<String> carregarMateriais(File arquivo) throws IOException {
        List<String> materiais = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                materiais.add(linha.trim());
            }
        }
        return materiais;
    }

     // Método para carregar e validar dados do arquivo Rotas.txt

     public List<String> carregarRotas(File arquivo) throws IOException {
            List<String> rotas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    rotas.add(linha.trim());
                }
            }
            return rotas;
        }

     public void carregarRotasNoGrafo(Grafo grafo, File arquivoRotas) {
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
    //  public List<String> carregarRotas(File arquivo) throws IOException {
    //     List<String> rotas = new ArrayList<>();
    //     try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
    //         String linha;
    //         while ((linha = br.readLine()) != null) {
    //             rotas.add(linha.trim());
    //         }
    //     }
    //     return rotas;
    // }

    public void validarMateriais(List<String> materiais) {
        Set<String> duplicados = new HashSet<>();
        for (String linha : materiais) {
            if (!linha.matches("^([a-zA-Z\\s]+),\\s*(Reutilizavel|Reciclavel|Descartavel),\\s*Ponto\\s[A-Z]$")) {
                throw new IllegalArgumentException("Formato inválido em Materiais.txt: " + linha);
            }
            if (!duplicados.add(linha)) {
                throw new IllegalArgumentException("Duplicação encontrada em Materiais.txt: " + linha);
            }
        }
    }

    public void validarRotas(List<String> rotas) {
        Set<String> duplicados = new HashSet<>();
        for (String linha : rotas) {
            if (!linha.matches("^Ponto\\s[A-Z],\\s*Ponto\\s[A-Z],\\s*\\d+$")) {
                throw new IllegalArgumentException("Formato inválido em Rotas.txt: " + linha);
            }
            if (!duplicados.add(linha)) {
                throw new IllegalArgumentException("Duplicação encontrada em Rotas.txt: " + linha);
            }
        }
    }
}
