package co.com.sufi.crediya.dtos;

import java.time.LocalDate;

public record FinanciacionRequest(
        String titular,
        double valorFinanciar,
        int numeroCuotas

) {}
