package leitura.gravacao_arquivos;

import java.io.*;
import java.util.*;

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
