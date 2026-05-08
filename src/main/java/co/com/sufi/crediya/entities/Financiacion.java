package co.com.sufi.crediya.entities;

import co.com.sufi.crediya.enums.EstadoFinanciacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Financiacion implements Serializable {

    private int numeroCredito;
    private String titular;
    private double valorFinanciar;
    private int numeroCuotas;
    private double tasaMensual;
    private double valorCuota;
    private LocalDate fechaPrimeraCuota;
    private LocalDateTime fechaRegistro;
    private EstadoFinanciacion estado;

    public Financiacion(int numeroCredito,String titular, double valorFinanciar, int numeroCuotas, double tasaMensual, double valorCuota, LocalDate fechaPrimeraCuota) {
        this.numeroCredito = numeroCredito;
        this.titular = titular;
        this.valorFinanciar = valorFinanciar;
        this.numeroCuotas = numeroCuotas;
        this.tasaMensual = tasaMensual;
        this.valorCuota = valorCuota;
        this.fechaPrimeraCuota = fechaPrimeraCuota;
        this.fechaRegistro = LocalDateTime.now();
        this.estado = EstadoFinanciacion.PENDIENTE;

    }

    public int getNumeroCredito() {
        return numeroCredito;
    }
    public double getValorFinanciar() {
        return valorFinanciar;
    }
    public int getNumeroCuotas() {
        return numeroCuotas;
    }
    public double getTasaMensual() {
        return tasaMensual;
    }
    public double getValorCuota() {
        return valorCuota;
    }
    public LocalDate getFechaPrimeraCuota() {
        return fechaPrimeraCuota;
    }
    public String getTitular() {
        return titular;
    }

    public EstadoFinanciacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoFinanciacion estado) {
        this.estado = estado;
    }
}




