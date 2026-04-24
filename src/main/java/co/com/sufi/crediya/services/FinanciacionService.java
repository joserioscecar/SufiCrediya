package co.com.sufi.crediya.services;

import co.com.sufi.crediya.dtos.FinanciacionRequest;
import co.com.sufi.crediya.dtos.FinanciacionResponse;
import co.com.sufi.crediya.entities.Financiacion;
import co.com.sufi.crediya.exception.LogicaNegocioExcepcion;
import co.com.sufi.crediya.repositories.ObjectRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class FinanciacionService {

    private static final double IVA = 0.19;

    private ObjectRepository<Financiacion> financiacionRepository = new ObjectRepository<Financiacion>("data/datos.data");

    public FinanciacionResponse registrar(FinanciacionRequest request){

        try {

            int numeroCuotas = request.numeroCuotas();
            double valorFinanciar = request.valorFinanciar();

            double tasaMensual = calcularTasa(request.numeroCuotas());
            double valorCuota = calcularCuota(tasaMensual, numeroCuotas, valorFinanciar);

            int numeroCredito = generarNumeroCredito();
            LocalDate fechaPrimeraCuota = calcularFechaPrimeraCuota();
            Financiacion nuevaFinanciacion = new Financiacion(numeroCredito, valorFinanciar, numeroCuotas, tasaMensual, valorCuota, fechaPrimeraCuota);

            financiacionRepository.add(nuevaFinanciacion);

            return new FinanciacionResponse(
                    numeroCredito,
                    valorFinanciar,
                    numeroCuotas,
                    tasaMensual,
                    valorCuota,
                    fechaPrimeraCuota
            );

        }catch (Exception e){

            throw new RuntimeException("Ha ocuerido un error en el sistema");

        }

    }

    public List<Financiacion> listar(){

        try {

          return financiacionRepository.getAll();

        }catch (Exception e){

        }
        return null;
    }


    private double calcularTasa(int numeroCuotas){

        double tasaBase = 0.0;
        if (numeroCuotas >= 2 && numeroCuotas <= 4) {
            tasaBase = 0.01;
        } else if (numeroCuotas >= 5 && numeroCuotas <= 10) {
            tasaBase = 0.018;
        }else {
            throw new LogicaNegocioExcepcion("El número de cuotas no se encuentra en el rango permitido");
        }
        double tasaMensual = tasaBase * (1 + IVA);

        return tasaMensual;
    }

    private double calcularCuota(double tasaMensual,int numeroCuotas, double valorFinanciar){

        if(valorFinanciar<=0){

            throw  new LogicaNegocioExcepcion("El valor a finanaciar debe ser mayor a cero");
        }

        double factor = Math.pow(1 + tasaMensual, numeroCuotas);
        double valorCuota = valorFinanciar * ((tasaMensual * factor) / (factor - 1));

        return  valorCuota;
    }


    private LocalDate calcularFechaPrimeraCuota(){

        LocalDate fechaPrimeraCuota = LocalDate.now().plusMonths(1);

        if (fechaPrimeraCuota.getDayOfWeek() == DayOfWeek.SATURDAY) {
            fechaPrimeraCuota = fechaPrimeraCuota.plusDays(2);
        } else if (fechaPrimeraCuota.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fechaPrimeraCuota = fechaPrimeraCuota.plusDays(1);
        }

        return fechaPrimeraCuota;
    }

    private int generarNumeroCredito(){

       return  1000000 + new Random().nextInt(9000000);
    }


}
