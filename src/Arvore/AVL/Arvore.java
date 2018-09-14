/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arvore.AVL;

public class Arvore {

    private No raiz; // raiz

    public Arvore() {
        raiz = null;
    } // inicializa 

    public void inserir(long v) {
        No novo = new No(); // cria um novo Nó
        novo.item = v; // atribui o valor recebido ao item de dados do Nó
        novo.dir = null;
        novo.esq = null;

        if (raiz == null) {
            raiz = novo;
        } else { // se nao for a raiz
            No atual = raiz;
            No anterior;
            while (true) {
                anterior = atual;
                if (v <= atual.item) { // ir para esquerda
                    atual = atual.esq;
                    if (atual == null) {
                        anterior.esq = novo;
                        return;
                    }
                } // fim da condição ir a esquerda
                else { // ir para direita
                    atual = atual.dir;
                    if (atual == null) {
                        anterior.dir = novo;
                        return;
                    }
                } // fim da condição ir a direita
            } // fim do laço while
        } // fim do else não raiz

    }

    private void inserir() {

    }

    /**
     * *
     * Mesmo método utilizado na busca binária
     *
     * @param chave
     * @return atual
     */
    public No buscar(long chave) {
        if (raiz == null) {
            return null;
        }
        No atual = raiz;
        while (atual.item != chave) {
            if (chave < atual.item) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
            if (atual == null) {
                return null;
            }
        }
        return atual;
    }

    public boolean remover(long v) {
        if (raiz == null) {
            return false; // se arvore vazia
        }
        No atual = raiz;
        No pai = raiz;
        boolean filho_esq = true;

        // ****** Buscando o valor **********
        while (atual.item != v) { // enquanto nao encontrou
            pai = atual;
            if (v < atual.item) { // caminha para esquerda
                atual = atual.esq;
                filho_esq = true; // é filho a esquerda? sim
            } else { // caminha para direita
                atual = atual.dir;
                filho_esq = false; // é filho a esquerda? NAO
            }
            if (atual == null) {
                return false; // encontrou uma folha -> sai
            }
        } // fim laço while de busca do valor

        // **************************************************************
        // se chegou aqui quer dizer que encontrou o valor (v)
        // "atual": contem a referencia ao No a ser eliminado
        // "pai": contem a referencia para o pai do No a ser eliminado
        // "filho_esq": é verdadeiro se atual é filho a esquerda do pai
        // **************************************************************
        // Se nao possui nenhum filho (é uma folha), elimine-o
        if (atual.esq == null && atual.dir == null) {
            if (atual == raiz) {
                raiz = null; // se raiz
            } else if (filho_esq) {
                pai.esq = null; // se for filho a esquerda do pai
            } else {
                pai.dir = null; // se for filho a direita do pai
            }
        } // Se é pai e nao possui um filho a direita, substitui pela subarvore a direita
        else if (atual.dir == null) {
            if (atual == raiz) {
                raiz = atual.esq; // se raiz
            } else if (filho_esq) {
                pai.esq = atual.esq; // se for filho a esquerda do pai
            } else {
                pai.dir = atual.esq; // se for filho a direita do pai
            }
        } // Se é pai e nao possui um filho a esquerda, substitui pela subarvore a esquerda
        else if (atual.esq == null) {
            if (atual == raiz) {
                raiz = atual.dir; // se raiz
            } else if (filho_esq) {
                pai.esq = atual.dir; // se for filho a esquerda do pai
            } else {
                pai.dir = atual.dir; // se for  filho a direita do pai
            }
        } // Se possui mais de um filho, se for um avô ou outro grau maior de parentesco
        else {
            No sucessor = no_sucessor(atual);
            // Usando sucessor que seria o Nó mais a esquerda da subarvore a direita do No que deseja-se remover
            if (atual == raiz) {
                raiz = sucessor; // se raiz
            } else if (filho_esq) {
                pai.esq = sucessor; // se for filho a esquerda do pai
            } else {
                pai.dir = sucessor; // se for filho a direita do pai
            }
            sucessor.esq = atual.esq; // acertando o ponteiro a esquerda do sucessor agora que ele assumiu 
            // a posição correta na arvore   
        }

        return true;
    }

    // O sucessor é o Nó mais a esquerda da subarvore a direita do No que foi passado como parametro do metodo
    public No no_sucessor(No apaga) { // O parametro é a referencia para o No que deseja-se apagar
        No paidosucessor = apaga;
        No sucessor = apaga;
        No atual = apaga.dir; // vai para a subarvore a direita

        while (atual != null) { // enquanto nao chegar no Nó mais a esquerda
            paidosucessor = sucessor;
            sucessor = atual;
            atual = atual.esq; // caminha para a esquerda
        }
        // *********************************************************************************
        // quando sair do while "sucessor" será o Nó mais a esquerda da subarvore a direita
        // "paidosucessor" será o o pai de sucessor e "apaga" o Nó que deverá ser eliminado
        // *********************************************************************************
        if (sucessor != apaga.dir) { // se sucessor nao é o filho a direita do Nó que deverá ser eliminado
            paidosucessor.esq = sucessor.dir; // pai herda os filhos do sucessor que sempre serão a direita
            // lembrando que o sucessor nunca poderá ter filhos a esquerda, pois, ele sempre será o
            // Nó mais a esquerda da subarvore a direita do Nó apaga.
            // lembrando também que sucessor sempre será o filho a esquerda do pai

            sucessor.dir = apaga.dir; // guardando a referencia a direita do sucessor para 
            // quando ele assumir a posição correta na arvore
        }
        return sucessor;
    }

    public void caminhar() {
        System.out.print("\n Exibindo em ordem: ");
        inOrder(raiz);
        System.out.print("\n Exibindo em pos-ordem: ");
        posOrder(raiz);
        System.out.print("\n Exibindo em pre-ordem: ");
        preOrder(raiz);
        System.out.print("\n Altura da arvore: " + altura(raiz));
        System.out.print("\n Quantidade de folhas: " + folhas(raiz));
        System.out.print("\n Quantidade de Nós: " + contarNos(raiz));
        if (raiz != null) {  // se arvore nao esta vazia
            System.out.print("\n Valor minimo: " + min().item);
            System.out.println("\n Valor maximo: " + max().item);
        }
    }

    public void inOrder(No atual) {
        if (atual != null) {
            inOrder(atual.esq);
            System.out.print(atual.item + " ");
            inOrder(atual.dir);
        }
    }

    public void preOrder(No atual) {
        if (atual != null) {
            System.out.print(atual.item + " ");
            preOrder(atual.esq);
            preOrder(atual.dir);
        }
    }

    public void posOrder(No atual) {
        if (atual != null) {
            posOrder(atual.esq);
            posOrder(atual.dir);
            System.out.print(atual.item + " ");
        }
    }

    public int altura(No atual) {
        if (atual == null || (atual.esq == null && atual.dir == null)) {
            return 0;
        } else {
            if (altura(atual.esq) > altura(atual.dir)) {
                return (1 + altura(atual.esq));
            } else {
                return (1 + altura(atual.dir));
            }
        }
    }

    public int folhas(No atual) {
        if (atual == null) {
            return 0;
        }
        if (atual.esq == null && atual.dir == null) {
            return 1;
        }
        return folhas(atual.esq) + folhas(atual.dir);
    }

    public int contarNos(No atual) {
        if (atual == null) {
            return 0;
        } else {
            return (1 + contarNos(atual.esq) + contarNos(atual.dir));
        }
    }

    public No min() {
        No atual = raiz;
        No anterior = null;
        while (atual != null) {
            anterior = atual;
            atual = atual.esq;
        }
        return anterior;
    }

    public No max() {
        No atual = raiz;
        No anterior = null;
        while (atual != null) {
            anterior = atual;
            atual = atual.dir;
        }
        return anterior;
    }

    private void calculaBalanecamento(No no) {
//        if (no == null || no) {
//            
//        }
    }

    public int alturaArvoreDireita(No dir) {
        if (dir.dir != null) {
            return alturaArvoreDireita(dir.dir) + 1;
        }
        return 0;
    }

    public int alturaArvoreEsquerda(No esq) {
        if (esq.esq != null) {
            return alturaArvoreEsquerda(esq.esq) + 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Arvore a = new Arvore();
        a.inserir(5);
        a.inserir(5);
        a.inserir(5);
        a.inserir(8);
        a.inserir(7);
        a.inserir(1);
        a.inserir(88);
        a.inserir(-8);
        System.out.println(a.alturaArvoreDireita(a.raiz));
        System.out.println(a.alturaArvoreEsquerda(a.raiz));
    }

}
