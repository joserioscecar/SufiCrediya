package co.com.sufi.crediya.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Financiacion implements Serializable {

    private int numeroCredito;
    private double valorFinanciar;
    private int numeroCuotas;
    private double tasaMensual;
    private double valorCuota;
    private LocalDate fechaPrimeraCuota;

    public Financiacion(int numeroCredito, double valorFinanciar, int numeroCuotas, double tasaMensual, double valorCuota, LocalDate fechaPrimeraCuota) {
        this.numeroCredito = numeroCredito;
        this.valorFinanciar = valorFinanciar;
        this.numeroCuotas = numeroCuotas;
        this.tasaMensual = tasaMensual;
        this.valorCuota = valorCuota;
        this.fechaPrimeraCuota = fechaPrimeraCuota;
    }

    public int getNumeroCredito() { return numeroCredito; }
    public double getValorFinanciar() { return valorFinanciar; }
    public int getNumeroCuotas() { return numeroCuotas; }
    public double getTasaMensual() { return tasaMensual; }
    public double getValorCuota() { return valorCuota; }
    public LocalDate getFechaPrimeraCuota() { return fechaPrimeraCuota; }
}




