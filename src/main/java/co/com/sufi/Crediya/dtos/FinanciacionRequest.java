package co.com.sufi.Crediya.dtos;

import java.time.LocalDate;

public record FinanciacionRequest(

        int numeroCredito,
        double valorFinanciar,
        int numeroCuotas,
        double tasaMensual,
        double valorCuota,
        LocalDate fechaPrimeraCuota

) {}
