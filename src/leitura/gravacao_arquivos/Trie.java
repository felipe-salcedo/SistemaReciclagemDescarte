package leitura.gravacao_arquivos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bruno
 */
public class Trie {
     /**
     * Representa um nó da Trie
     */
    public static class NoTrie {
        public boolean eFimDePalavra;
        public NoTrie[] filhos;
        public String tipo; 
        public String pontoDeColeta; 

        public NoTrie() {
            eFimDePalavra = false;
            filhos = new NoTrie[26];
            for (int i = 0; i < 26; ++i) {
                filhos[i] = null;
            }
            tipo = null;
            pontoDeColeta = null;
        }
    }

    private NoTrie raiz;

    public Trie() {
        raiz = new NoTrie();
    }

    public void insereComInformacoes(String palavra, String tipo, String pontoDeColeta) {
        palavra = palavra.toLowerCase();
        NoTrie noAuxiliar = raiz;
    
        for (int i = 0; i < palavra.length(); ++i) {
            char c = palavra.charAt(i);
            int index = c - 'a';
    
            // Verifica se o caractere é uma letra válida
            if (index < 0 || index >= 26) {
                // Se o caractere não for uma letra minúscula, ignore ou trate o erro
                continue; // ou lance um erro: throw new IllegalArgumentException("Caractere inválido: " + c);
            }
    
            // Se o nó filho para o índice não existe, cria um novo nó
            if (noAuxiliar.filhos[index] == null) {
                noAuxiliar.filhos[index] = new NoTrie();
            }
            noAuxiliar = noAuxiliar.filhos[index];
        }
    
        // Marca o final da palavra e armazena as informações associadas
        noAuxiliar.eFimDePalavra = true;
        noAuxiliar.tipo = tipo;
        noAuxiliar.pontoDeColeta = pontoDeColeta;
    }
    

    public MaterialInfo buscaComInformacoes(String palavra) {
        palavra = palavra.toLowerCase();
        NoTrie noAuxiliar = raiz;

        for (int i = 0; i < palavra.length(); ++i) {
            int index = palavra.charAt(i) - 'a';
            if (noAuxiliar.filhos[index] == null) {
                return null;
            }
            noAuxiliar = noAuxiliar.filhos[index];
        }

        if (noAuxiliar != null && noAuxiliar.eFimDePalavra) {
            return new MaterialInfo(noAuxiliar.tipo, noAuxiliar.pontoDeColeta);
        }
        return null;
    }

    private void coletarPalavras(NoTrie no, String palavra, List<String> sugestoes) {
        if (no.eFimDePalavra) {
            sugestoes.add(palavra);
        }

        for (int i = 0; i < 26; ++i) {
            if (no.filhos[i] != null) {
                char c = (char) (i + 'a');
                coletarPalavras(no.filhos[i], palavra + c, sugestoes);
            }
        }
    }


    public List<String> autocompletar(String prefixo) {
        List<String> sugestoes = new ArrayList<>();
        NoTrie noAuxiliar = raiz;

        // Navega até o final do prefixo
        for (int i = 0; i < prefixo.length(); ++i) {
            char c = prefixo.charAt(i);
            if (noAuxiliar.filhos[c - 'a'] == null) {
                return sugestoes; // Prefixo não encontrado
            }
            noAuxiliar = noAuxiliar.filhos[c - 'a'];
        }

        // Coleta palavras a partir do prefixo
        coletarPalavras(noAuxiliar, prefixo, sugestoes);
        return sugestoes;
    }

    public static class MaterialInfo {
        public String tipo;
        public String pontoDeColeta;

        public MaterialInfo(String tipo, String pontoDeColeta) {
            this.tipo = tipo;
            this.pontoDeColeta = pontoDeColeta;
        }
    }

    public boolean busca( String palavra ){
        
        palavra = palavra.toLowerCase(); // -> Tornando a palavra minúscula
        
        NoTrie noAuxiliar = this.raiz;

        for(int i = 0; i < palavra.length(); ++i){

            if( noAuxiliar.filhos[ palavra.charAt(i) - 'a' ] == null ){  // -> Não há nó correspondente
                return false;
            }

            noAuxiliar = noAuxiliar.filhos[palavra.charAt(i) - 'a' ];

        }

        if( ( noAuxiliar != null ) && ( noAuxiliar.eFimDePalavra == true ) ){  // -> Se o nó existe e ele for um nó com o atributo fim de palavra ativado, foi encontrado o nó  
            return true;
        }

        return false;  // -> Ou noAuxiliar ficou nulo ou nao eh um no com o atributo fim de palavra ativado

    }


    public boolean remove(String palavra) {
        palavra = palavra.toLowerCase(); // Tornando a palavra minúscula
    
        if (!busca(palavra)) { // Se a palavra não existe na Trie
            return false;
        }
    
        // Chama um método recursivo para tentar remover a palavra
        removeRecursivo(palavra, raiz, 0);
        return true;
    }
    
    private boolean removeRecursivo(String palavra, NoTrie noAtual, int indice) {
        // Caso base: se chegamos ao final da palavra
        if (indice == palavra.length()) {
            if (noAtual.eFimDePalavra) {
                noAtual.eFimDePalavra = false; // Marca o final da palavra como falso
                return !temFilhos(noAtual); // Retorna verdadeiro se o nó não tiver filhos
            }
            return false;
        }
    
        // Recursão: continuar o percurso pela Trie
        int index = palavra.charAt(indice) - 'a';
        NoTrie proximoNo = noAtual.filhos[index];
    
        if (proximoNo == null) {
            return false; // Se o próximo nó não existe, a palavra não pode ser removida
        }
    
        boolean podeExcluir = removeRecursivo(palavra, proximoNo, indice + 1);
    
        // Após a remoção recursiva, verificamos se podemos remover o nó atual
        if (podeExcluir) {
            noAtual.filhos[index] = null; // Exclui o nó
            return !noAtual.eFimDePalavra && !temFilhos(noAtual); // Retorna verdadeiro se o nó pode ser excluído
        }
    
        return false;
    }
    
    private boolean temFilhos(NoTrie no) {
        for (NoTrie filho : no.filhos) {
            if (filho != null) {
                return true; // Se encontrar algum filho não nulo, retorna verdadeiro
            }
        }
        return false; // Se não encontrar filhos, retorna falso
    }
    

}
