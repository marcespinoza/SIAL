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
public class ClientesPorLotes {
    
    int dni;
    String apellidos;
    String nombres;
    Date fecha_nacimiento;
    String barrio_cliente;
    String calle_cliente;
    int nro_cliente;
    String telefono1;
    String telefono2;
    String trabajo;
    int baja;
    int idControl;
    int cantidad_cuotas;
    BigDecimal gastos;
    BigDecimal bolsa_cemento;
    Date fecha_actualizacion;
    int manzana;
    String barrio;
    int parcela;
    String observacion;
    BigDecimal cuota_pura;

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getBarrio_cliente() {
        return barrio_cliente;
    }

    public void setBarrio_cliente(String barrio_cliente) {
        this.barrio_cliente = barrio_cliente;
    }

    public String getCalle_cliente() {
        return calle_cliente;
    }

    public void setCalle_cliente(String calle_cliente) {
        this.calle_cliente = calle_cliente;
    }

    public int getNro_cliente() {
        return nro_cliente;
    }

    public void setNro_cliente(int nro_cliente) {
        this.nro_cliente = nro_cliente;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public int getBaja() {
        return baja;
    }

    public void setBaja(int baja) {
        this.baja = baja;
    }

    public int getCantidad_cuotas() {
        return cantidad_cuotas;
    }

    public void setCantidad_cuotas(int cantidad_cuotas) {
        this.cantidad_cuotas = cantidad_cuotas;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public BigDecimal getBolsa_cemento() {
        return bolsa_cemento;
    }

    public void setBolsa_cemento(BigDecimal bolsa_cemento) {
        this.bolsa_cemento = bolsa_cemento;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public int getManzana() {
        return manzana;
    }

    public void setManzana(int manzana) {
        this.manzana = manzana;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getCuota_pura() {
        return cuota_pura;
    }

    public void setCuota_pura(BigDecimal cuota_pura) {
        this.cuota_pura = cuota_pura;
    }
    
    
    public int getIdControl() {
        return idControl;
    }

    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public ClientesPorLotes() {
    }
    
    
    
}
