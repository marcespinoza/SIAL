/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Marceloi7
 */
public class Actualizacion {
    
    int id_control;
    Date fecha;
    byte porcentaje;
    BigDecimal saldo_anterior;
    BigDecimal saldo_nuevo;

    public Actualizacion() {
    }

    public byte getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(byte actualizacion) {
        this.porcentaje = actualizacion;
    }

    public int getId_control() {
        return id_control;
    }

    public void setId_control(int id_control) {
        this.id_control = id_control;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(BigDecimal saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public BigDecimal getSaldo_nuevo() {
        return saldo_nuevo;
    }

    public void setSaldo_nuevo(BigDecimal saldo_nuevo) {
        this.saldo_nuevo = saldo_nuevo;
    }
    
    
    
}
