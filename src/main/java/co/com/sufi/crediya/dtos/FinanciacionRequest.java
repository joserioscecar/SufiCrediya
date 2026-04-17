package co.com.sufi.crediya.dtos;

import java.time.LocalDate;

public record FinanciacionRequest(
        double valorFinanciar,
        int numeroCuotas

) {}
