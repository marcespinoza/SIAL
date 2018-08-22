/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.math.BigDecimal;

/**
 *
 * @author Marceloi7
 */
public class FichaDeControl {
    
    BigDecimal precio;
    BigDecimal gastos;
    BigDecimal bolsaCemento;
    String dimension;
    int cantidadCuotas;
    BigDecimal cuotaPura;
    String barrio; 
    int manzana;
    int parcela;
    
    public FichaDeControl() {
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public BigDecimal getCuotaPura() {
        return cuotaPura;
    }

    public void setCuotaPura(BigDecimal cuotaPura) {
        this.cuotaPura = cuotaPura;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public BigDecimal getBolsaCemento() {
        return bolsaCemento;
    }

    public void setBolsaCemento(BigDecimal bolsaCemento) {
        this.bolsaCemento = bolsaCemento;
    }
    
}
