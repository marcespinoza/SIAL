/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Marceloi7
 */
public class Lote {
    
String barrio;
int manzana;
int parcela;

    public Lote(String barrio, int manzana, int parcela) {
        this.barrio = barrio;
        this.manzana = manzana;
        this.parcela = parcela;
    }

    public Lote() {
    }
    
    

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getManzana() {
        return manzana;
    }

    public void setManzana(int manzana) {
        this.manzana = manzana;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }


    
}
