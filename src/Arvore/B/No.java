/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arvore.B;

/**
 *
 * @author marcos
 */
import java.util.Vector;

public class No {
    
    private int qtdChaves; 
    private Vector<Integer> chave; 
    private Vector<No> filho;
    private boolean folha;
    private int X;
    private int Y;
    private int larguraFilho;            
    final int DIFERENCA_ALTURA = 30;
    final int DIFERENCA_IRMAOS = 5;
    
    

    public No(int n) {
        this.chave = new Vector<Integer>(n - 1);
        for (int i = 0; i < n - 1; i++) {
            this.chave.add(null);
        }
        this.filho = new Vector<No>(n);
        for (int i = 0; i < n; i++) {
            this.filho.add(null);
        }
        this.folha = true;
        this.qtdChaves = 0;
    }

    public Vector<Integer> getChave() {
        return chave;
    }

    public void setChave(Vector<Integer> chave) {
        this.chave = chave;
    }

    public Vector<No> getFilho() {
        return filho;
    }

    public void setFilho(Vector<No> filho) {
        this.filho = filho;
    }

    public boolean isFolha() {
        return folha;
    }

    public void setFolha(boolean folha) {
        this.folha = folha;
    }

    public int getQtdChaves() {
        return qtdChaves;
    }

    public void setN(int n) {
        this.qtdChaves = n;
    }

     public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int computeSize() {
        return qtdChaves * 28 + 12;
    }


    public void updateCoordenates(No parent, int x) {
        if (parent == null) {
            if (x == 0) {
                UpdateLFilho();
            }
            Y = 0;
        } else {
            Y = parent.getY() + DIFERENCA_ALTURA;
        }
        if (!folha) {
            X = (larguraFilho / 2) + x;
            //X = x - (larguraFilho / 2);
            int xAcumuladoLocal = x;
            for (int i = 0; i < qtdChaves + 1; i++) {
                filho.get(i).updateCoordenates(this, xAcumuladoLocal);
                xAcumuladoLocal += filho.get(i).larguraFilho + DIFERENCA_IRMAOS;
            }
        } else {
            X = x;
        }
    }

     public int UpdateLFilho() {
        larguraFilho = 0;
        if (!folha) {
            for (int i = 0; i < qtdChaves + 1; i++) {
                larguraFilho += filho.get(i).UpdateLFilho();
            }
        } else {
            larguraFilho = computeSize() + DIFERENCA_IRMAOS;
        }
        return larguraFilho;
    }
     
    public int BuscaBinariaIterativa(int[] vetor, int valorBuscado) {
        int inicio = 0;
        int fim = vetor.length - 1;
        int meio = -1;

        while (inicio <= fim) {

            meio = (inicio + fim) / 2;
            if (valorBuscado == vetor[meio]) {
                return meio;
            } else if (valorBuscado < vetor[meio]) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }

        }
        return -1;
    }

  
}
