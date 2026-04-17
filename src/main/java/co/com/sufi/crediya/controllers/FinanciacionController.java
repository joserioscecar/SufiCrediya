package co.com.sufi.crediya.controllers;

import co.com.sufi.crediya.exception.LogicaNegocioExcepcion;
import co.com.sufi.crediya.dtos.FinanciacionRequest;
import co.com.sufi.crediya.dtos.FinanciacionResponse;
import co.com.sufi.crediya.entities.Financiacion;
import co.com.sufi.crediya.services.FinanciacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/financiaciones")
public class FinanciacionController {

    @Autowired
    private FinanciacionService financiacionService;

    private List<Financiacion> creditosEnMemoria = new ArrayList<>();

    @PostMapping
    public ResponseEntity<FinanciacionResponse> calcularFinanciacion(@RequestBody FinanciacionRequest request) {

        int numeroCuotas = request.numeroCuotas();
        double valorFinanciar = request.valorFinanciar();

        try {

            double tasaMensual = financiacionService.calcularTasa(request.numeroCuotas());
            double valorCuota = financiacionService.calcularCuota(tasaMensual, numeroCuotas, valorFinanciar);

            int numeroCredito = financiacionService.generarNumeroCredito();
            LocalDate fechaPrimeraCuota = financiacionService.calcularFechaPrimeraCuota();
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

            return ResponseEntity.created(URI.create("/api/financiaciones/"+numeroCredito)).body(respuesta);

        } catch (LogicaNegocioExcepcion e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}


