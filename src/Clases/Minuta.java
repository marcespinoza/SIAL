/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;

/**
 *
 * @author Marceloi7
 */
public class Minuta {
    
    Date fechaMinuta;
    String Apellidos;
    String Nombres;
    int manzana;
    int parcela;
    float cobrado;
    float gastos;
    float rendido;
    int nroCuota;
    String observaciones;
    String categoria;
    int baja;

    public Minuta() {
    }

    public Minuta(Date fechaMinuta, String Apellidos, String Nombres, int manzana, int parcela, float cobrado, float gastos, float rendido, int nroCuota, String observaciones, String categoria, int baja) {
        this.fechaMinuta = fechaMinuta;
        this.Apellidos = Apellidos;
        this.Nombres = Nombres;
        this.manzana = manzana;
        this.parcela = parcela;
        this.cobrado = cobrado;
        this.gastos = gastos;
        this.rendido = rendido;
        this.nroCuota = nroCuota;
        this.observaciones = observaciones;
        this.categoria = categoria;
        this.baja = baja;
    }

    public Date getFechaMinuta() {
        return fechaMinuta;
    }

    public void setFechaMinuta(Date fechaMinuta) {
        this.fechaMinuta = fechaMinuta;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
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

    public float getCobrado() {
        return cobrado;
    }

    public void setCobrado(float cobrado) {
        this.cobrado = cobrado;
    }

    public float getGastos() {
        return gastos;
    }

    public void setGastos(float gastos) {
        this.gastos = gastos;
    }

    public float getRendido() {
        return rendido;
    }

    public void setRendido(float rendido) {
        this.rendido = rendido;
    }

    public int getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(int nroCuota) {
        this.nroCuota = nroCuota;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getBaja() {
        return baja;
    }

    public void setBaja(int baja) {
        this.baja = baja;
    }
    
    
}
