package co.com.sufi.Crediya.controllers;

import co.com.sufi.Crediya.dtos.FinanciacionRequest;
import co.com.sufi.Crediya.dtos.FinanciacionResponse;
import co.com.sufi.Crediya.entities.Financiacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/api/financiaciones")
    public class FinanciacionController {

        private static final double IVA = 0.19;

        private List<Financiacion> creditosEnMemoria = new ArrayList<>();

        @PostMapping
        public ResponseEntity<FinanciacionResponse> calcularFinanciacion(@RequestBody FinanciacionRequest request) {

            double valorFinanciar = request.valorFinanciar();
            int numeroCuotas = request.numeroCuotas();
            double tasaBase = 0.0;
            if (numeroCuotas >= 2 && numeroCuotas <= 4) {
                tasaBase = 0.01;
            } else if (numeroCuotas >= 5 && numeroCuotas <= 10) {
                tasaBase = 0.018;
            }
            double tasaMensual = tasaBase * (1 + IVA);

            double factor = Math.pow(1 + tasaMensual, numeroCuotas);
            double valorCuota = valorFinanciar * ((tasaMensual * factor) / (factor - 1));

            valorCuota = Math.round(valorCuota * 100.0) / 100.0;
            tasaMensual = Math.round(tasaMensual * 100000.0) / 100000.0;

            int numeroCredito = 1000000 + new Random().nextInt(9000000);

            LocalDate fechaPrimeraCuota = LocalDate.now().plusMonths(1);

            if (fechaPrimeraCuota.getDayOfWeek() == DayOfWeek.SATURDAY) {
                fechaPrimeraCuota = fechaPrimeraCuota.plusDays(2);
            } else if (fechaPrimeraCuota.getDayOfWeek() == DayOfWeek.SUNDAY) {
                fechaPrimeraCuota = fechaPrimeraCuota.plusDays(1);
            }

            Financiacion nuevaFinanciacion = new Financiacion(numeroCredito, valorFinanciar, numeroCuotas, tasaMensual, valorCuota, fechaPrimeraCuota);
            creditosEnMemoria.add(nuevaFinanciacion);


            FinanciacionResponse respuesta = new FinanciacionResponse(
                    numeroCredito,
                    valorFinanciar,
                    numeroCuotas,
                    tasaMensual,
                    valorCuota,
                    fechaPrimeraCuota
            );

            return ResponseEntity.ok(respuesta);
        }
    }


