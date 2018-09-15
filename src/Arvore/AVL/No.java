/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arvore.AVL;

/**
 *
 * @author marcos
 */
class No {

    public long valor;
    public No dir;
    public No esq;
    public int fatBal;
    
    public No(long valor) {
        this.esq = null;
        this.dir = null;
        this.valor = valor;
        this.fatBal = fatBal;
    }
    
}
