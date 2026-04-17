package co.com.sufi.crediya.services;

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


    public void registrar(Financiacion financiacion){

        try {

            financiacionRepository.add(financiacion);

        }catch (Exception e){

        }

    }

    public List<Financiacion> listar(){

        try {

          return   financiacionRepository.getAll();

        }catch (Exception e){

        }
        return null;
    }


    public double calcularTasa(int numeroCuotas){

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

    public double calcularCuota(double tasaMensual,int numeroCuotas, double valorFinanciar){

        if(valorFinanciar<=0){

            throw  new LogicaNegocioExcepcion("El valor a finanaciar debe ser mayor a cero");
        }

        double factor = Math.pow(1 + tasaMensual, numeroCuotas);
        double valorCuota = valorFinanciar * ((tasaMensual * factor) / (factor - 1));

        return  valorCuota;
    }


    public LocalDate calcularFechaPrimeraCuota(){

        LocalDate fechaPrimeraCuota = LocalDate.now().plusMonths(1);

        if (fechaPrimeraCuota.getDayOfWeek() == DayOfWeek.SATURDAY) {
            fechaPrimeraCuota = fechaPrimeraCuota.plusDays(2);
        } else if (fechaPrimeraCuota.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fechaPrimeraCuota = fechaPrimeraCuota.plusDays(1);
        }

        return fechaPrimeraCuota;
    }

    public int generarNumeroCredito(){

       return  1000000 + new Random().nextInt(9000000);
    }


}
