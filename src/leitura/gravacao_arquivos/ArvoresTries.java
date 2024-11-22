package leitura.gravacao_arquivos;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ArvoresTries {
    public static void carregarMateriaisNaTrie(Trie trie, File arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    trie.insereComInformacoes(partes[0].trim().toLowerCase(), partes[1].trim(), partes[2].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void iniciarInteracaoComTrie(File arquivoMateriais) {
        Trie trie = new Trie();
        carregarMateriaisNaTrie(trie, arquivoMateriais);
        JOptionPane.showMessageDialog(null, "Materiais carregados na Trie com sucesso!");

        int op;
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(
                    "1 - Buscar material\n" +
                            "2 - Remover material\n" +
                            "3 - Sair\n" +
                            "Escolha opção desejada:"));
            switch (op) {
                case 1:
                    String prefixo = JOptionPane.showInputDialog("Digite as primeiras letras do material ou deixe vazio para ver todos:")
                            .toLowerCase();
                    List<String> sugestoes = trie.autocompletar(prefixo);
                    if (sugestoes.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum material encontrado com o prefixo fornecido!");
                    } else {
                        String escolha = (String) JOptionPane.showInputDialog(
                                null,
                                "Selecione um material:",
                                "Sugestões",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                sugestoes.toArray(),
                                sugestoes.get(0));
                        if (escolha != null) {
                            Trie.MaterialInfo info = trie.buscaComInformacoes(escolha.toLowerCase());
                            if (info != null) {
                                JOptionPane.showMessageDialog(null,
                                        "Material encontrado!\n" +
                                                "Material: " + escolha + "\n" +
                                                "Tipo: " + info.tipo + "\n" +
                                                "Ponto de Coleta: " + info.pontoDeColeta);
                            }
                        }
                    }
                    break;
                case 2: // Opção para remover material
                    String prefixoRemocao = JOptionPane
                            .showInputDialog("Digite as primeiras letras do material para remover ou deixe vazio para ver todos:").toLowerCase();
                    List<String> sugestoesRemocao = trie.autocompletar(prefixoRemocao);
                    if (sugestoesRemocao.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum material encontrado com o prefixo fornecido!");
                    } else {
                        String materialParaRemover = (String) JOptionPane.showInputDialog(
                                null,
                                "Selecione o material para remover:",
                                "Sugestões de Materiais",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                sugestoesRemocao.toArray(),
                                sugestoesRemocao.get(0));
                        if (materialParaRemover != null && !materialParaRemover.isEmpty()) {
                            if (trie.remove(materialParaRemover.toLowerCase())) {
                                JOptionPane.showMessageDialog(null, "Material removido com sucesso!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao tentar remover o material.");
                            }
                        }
                    }
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saindo...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (op != 3);
    }
}
