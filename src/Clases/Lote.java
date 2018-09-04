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
int vendido;
String apellidoPropietario;
String nombrePropietario;
int nroRecibo;
String observaciones;
String propietario_cuit;  

    public Lote(String barrio, int manzana, int parcela) {
        this.barrio = barrio;
        this.manzana = manzana;
        this.parcela = parcela;
    }

      public int getVendido() {
        return vendido;
    }

    public void setVendido(int vendido) {
        this.vendido = vendido;
    }

    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPropietario_cuit() {
        return propietario_cuit;
    }

    public void setPropietario_cuit(String propietario_cuit) {
        this.propietario_cuit = propietario_cuit;
    }

    public Lote() {
    }
    
    
    public String getApellidoPropietario() {
        return apellidoPropietario;
    }

    public void setApellidoPropietario(String apellidoPropietario) {
        this.apellidoPropietario = apellidoPropietario;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public int getNroRecibo() {
        return nroRecibo;
    }

    public void setNroRecibo(int nroRecibo) {
        this.nroRecibo = nroRecibo;
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
