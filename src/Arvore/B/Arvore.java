/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arvore.B;

import javafx.scene.Node;

/**
 *
 * @author marcos
 */
public class Arvore {

    private No raiz;
    private int ordem;
    private int nElementos;

    public Arvore(int n) {
        this.raiz = new No(n);
        this.ordem = n;
        nElementos = 0;
    }

    public int getnElementos() {
        return nElementos;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }

    public No getRaiz() {
        return raiz;
    }

    public void insere(int valor) {

        if (BuscaChave(raiz, valor) == null) {

            if (raiz.getQtdChaves() == 0) {
                raiz.getChave().set(0, valor);
                raiz.setN(raiz.getQtdChaves() + 1);
            } else {
                No r = raiz;

                if (r.getQtdChaves() == ordem - 1) {//há necessidade de dividir a raiz
                    No s = new No(ordem);
                    raiz = s;
                    s.setFolha(false);
                    s.setN(0);
                    s.getFilho().set(0, r);
                    divideNo(s, 0, r);
                    insereNoNaoCheio(s, valor);
                } else {
                    insereNoNaoCheio(r, valor);
                }
            }
            nElementos++;
        }
    }

    public void divideNo(No pai, int i, No filho) {
        int t = (int) Math.floor((ordem - 1) / 2);
        //cria nó z
        No z = new No(ordem);
        z.setFolha(filho.isFolha());
        z.setN(t);

        for (int j = 0; j < t; j++) {
            if ((ordem - 1) % 2 == 0) {
                z.getChave().set(j, filho.getChave().get(j + t));
            } else {
                z.getChave().set(j, filho.getChave().get(j + t + 1));
            }
            filho.setN(filho.getQtdChaves() - 1);
        }

        if (!filho.isFolha()) {
            for (int j = 0; j < t + 1; j++) {
                if ((ordem - 1) % 2 == 0) {
                    z.getFilho().set(j, filho.getFilho().get(j + t));
                } else {
                    z.getFilho().set(j, filho.getFilho().get(j + t + 1));
                }

            }
        }

        filho.setN(t);

        for (int j = pai.getQtdChaves(); j > i; j--) {
            pai.getFilho().set(j + 1, pai.getFilho().get(j));
        }

        pai.getFilho().set(i + 1, z);

        for (int j = pai.getQtdChaves(); j > i; j--) {
            pai.getChave().set(j, pai.getChave().get(j - 1));
        }

        if ((ordem - 1) % 2 == 0) {
            pai.getChave().set(i, filho.getChave().get(t - 1));
            filho.setN(filho.getQtdChaves() - 1);

        } else {
            pai.getChave().set(i, filho.getChave().get(t));
        }

        pai.setN(pai.getQtdChaves() + 1);

    }

    public void insereNoNaoCheio(No x, int k) {
        int i = x.getQtdChaves() - 1;

        if (x.isFolha()) {

            while (i >= 0 && k < x.getChave().get(i)) {
                x.getChave().set(i + 1, x.getChave().get(i));
                i--;
            }
            i++;
            x.getChave().set(i, k);
            x.setN(x.getQtdChaves() + 1);

        } else {

            while ((i >= 0 && k < x.getChave().get(i))) {
                i--;
            }
            i++;

            if ((x.getFilho().get(i)).getQtdChaves() == ordem - 1) {
                divideNo(x, i, x.getFilho().get(i));
                if (k > x.getChave().get(i)) {
                    i++;
                }
            }
            insereNoNaoCheio(x.getFilho().get(i), k);
        }

    }

    public No BuscaChave(No X, int valor) {
        int i = 1;

        while ((i <= X.getQtdChaves()) && (valor > X.getChave().get(i - 1))) {
            i++;
            System.out.println("valores: " + X.getChave().get(i));
        }

        if ((i <= X.getQtdChaves()) && (valor == X.getChave().get(i - 1))) {
            return X;

        }

        if (X.isFolha()) { //se o no X for folha
            return null;
        } else {
            return (BuscaChave(X.getFilho().get(i - 1), valor));

        }
    }

    public void mostrar2(No X) {
        int i = 1;

        while ((i <= X.getQtdChaves())) {
            i++;
            System.out.println("valores: " + X.getChave().get(i));
        }
    }

    public void mostrar(int numero) {

        No busca = BuscaChave(raiz, numero);
        if (busca != null) {
            int posicao = busca.getChave().indexOf(numero);
            System.out.println("conteudo: " + busca.getChave().get(posicao));
        } else {
            System.out.println("Nao encontrado");
        }
    }

    public void Remove(int k) {

        if (BuscaChave(this.raiz, k) != null) {
            //N é o nó onde se encontra k
            No N = BuscaChave(this.raiz, k);
            int i = 1;

            while (N.getChave().get(i - 1) < k) {
                i++;
            }

            if (N.isFolha()) {
                for (int j = i + 1; j <= N.getQtdChaves(); j++) {
                    N.getChave().set(j - 2, N.getChave().get(j - 1));
                }
                N.setN(N.getQtdChaves() - 1);
                if (N != this.raiz) {
                    Balanceia_Folha(N);
                }
            } else {
                No S = Antecessor(this.raiz, k);
                int y = S.getChave().get(S.getQtdChaves() - 1);
                S.setN(S.getQtdChaves() - 1);
                N.getChave().set(i - 1, y);
                Balanceia_Folha(S);
            }
            nElementos--;
        }
    }

    private void Balanceia_Folha(No F) {

        if (F.getQtdChaves() < Math.floor((ordem - 1) / 2)) {
            No P = getPai(raiz, F);//P é o pai de F
            int j = 1;

            while (P.getFilho().get(j - 1) != F) {
                j++;
            }

            if (j == 1 || (P.getFilho().get(j - 2)).getQtdChaves() == Math.floor((ordem - 1) / 2)) {

                if (j == P.getQtdChaves() + 1 || (P.getFilho().get(j).getQtdChaves() == Math.floor((ordem - 1) / 2))) {
                    Diminui_Altura(F);
                } else {
                    Balanceia_Dir_Esq(P, j - 1, P.getFilho().get(j), F);
                }
            } else {
                Balanceia_Esq_Dir(P, j - 2, P.getFilho().get(j - 2), F);
            }
        }
    }

    private void Diminui_Altura(No X) {
        int j;
        No P = new No(ordem);

        if (X == this.raiz) {

            if (X.getQtdChaves() == 0) {
                this.raiz = X.getFilho().get(0);
                X.getFilho().set(0, null);
            }
        } else {
            int t = (int) Math.floor((ordem - 1) / 2);

            if (X.getQtdChaves() < t) {
                P = getPai(raiz, X);
                j = 1;

                while (P.getFilho().get(j - 1) != X) {
                    j++;
                }

                if (j > 1) {
                    Juncao_No(getPai(raiz, X), j - 1);
                } else {
                    Juncao_No(getPai(raiz, X), j);
                }
                Diminui_Altura(getPai(raiz, X));
            }
        }
    }

    private void Balanceia_Esq_Dir(No P, int e, No Esq, No Dir) {

        for (int i = 0; i < Dir.getQtdChaves(); i++) {
            Dir.getChave().set(i + 1, Dir.getChave().get(i));
        }

        if (!Dir.isFolha()) {
            for (int i = 0; i > Dir.getQtdChaves(); i++) {
                Dir.getFilho().set(i + 1, Dir.getFilho().get(i));
            }
        }
        Dir.setN(Dir.getQtdChaves() + 1);
        Dir.getChave().set(0, P.getChave().get(e));
        P.getChave().set(e, Esq.getChave().get(Esq.getQtdChaves() - 1));
        Dir.getFilho().set(0, Esq.getFilho().get(Esq.getQtdChaves()));
        Esq.setN(Esq.getQtdChaves() - 1);

    }

    private void Balanceia_Dir_Esq(No P, int e, No Dir, No Esq) {

        Esq.setN(Esq.getQtdChaves() + 1);
        Esq.getChave().set(Esq.getQtdChaves() - 1, P.getChave().get(e));
        P.getChave().set(e, Dir.getChave().get(0));
        Esq.getFilho().set(Esq.getQtdChaves(), Dir.getFilho().get(0));

        for (int j = 1; j < Dir.getQtdChaves(); j++) {
            Dir.getChave().set(j - 1, Dir.getChave().get(j));
        }

        if (!Dir.isFolha()) {
            for (int i = 1; i < Dir.getQtdChaves() + 1; i++) {
                Dir.getFilho().set(i - 1, Dir.getFilho().get(i));
            }
        }

        Dir.setN(Dir.getQtdChaves() - 1);

    }

    private void Juncao_No(No X, int i) {
        No Y = X.getFilho().get(i - 1);
        No Z = X.getFilho().get(i);

        int k = Y.getQtdChaves();
        Y.getChave().set(k, X.getChave().get(i - 1));

        for (int j = 1; j <= Z.getQtdChaves(); j++) {
            Y.getChave().set(j + k, Z.getChave().get(j - 1));
        }

        if (!Z.isFolha()) {
            for (int j = 1; j <= Z.getQtdChaves(); j++) {
                Y.getFilho().set(j + k, Z.getFilho().get(j - 1));
            }
        }

        Y.setN(Y.getQtdChaves() + Z.getQtdChaves() + 1);

        X.getFilho().set(i, null);

        for (int j = i; j <= X.getQtdChaves() - 1; j++) {//ainda nao
            X.getChave().set(j - 1, X.getChave().get(j));
            X.getFilho().set(j, X.getFilho().get(j + 1));
        }

        X.setN(X.getQtdChaves() - 1);
    }

    private No Antecessor(No N, int k) {
        int i = 1;
        while (i <= N.getQtdChaves() && N.getChave().get(i - 1) < k) {
            i++;
        }
        if (N.isFolha()) {
            return N;
        } else {
            return Antecessor(N.getFilho().get(i - 1), k);
        }
    }

    private No getPai(No T, No N) {
        if (this.raiz == N) {
            return null;
        }
        for (int j = 0; j <= T.getQtdChaves(); j++) {
            if (T.getFilho().get(j) == N) {
                return T;
            }
            if (!T.getFilho().get(j).isFolha()) {
                No X = getPai(T.getFilho().get(j), N);
                if (X != null) {
                    return X;
                }
            }
        }
        return null;
    }
    private Node roote;

    public void LimparArvore(No N, int ordem) {

        for (int i = 0; i < N.getQtdChaves() + 1; i++) {
            if (!N.isFolha()) {
                LimparArvore(N.getFilho().get(i), ordem);
            }
            N.getFilho().set(i, null);
            N.setN(0);
        }

        if (N == this.raiz) {
            this.raiz = new No(ordem);
        }
        nElementos = 0;
    }

}
