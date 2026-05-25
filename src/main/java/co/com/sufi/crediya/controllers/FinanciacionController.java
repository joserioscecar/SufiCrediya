package co.com.sufi.crediya.controllers;

import co.com.sufi.crediya.dtos.ActualizarFInanciacionRequest;
import co.com.sufi.crediya.exception.LogicaNegocioExcepcion;
import co.com.sufi.crediya.dtos.FinanciacionRequest;
import co.com.sufi.crediya.dtos.FinanciacionResponse;
import co.com.sufi.crediya.entities.Financiacion;
import co.com.sufi.crediya.repositories.FinanciacionRepository;
import co.com.sufi.crediya.repositories.FinanciacionRepositoryJdbc;
import co.com.sufi.crediya.services.FinanciacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/financiaciones")
public class FinanciacionController {

    @Autowired
    private FinanciacionService financiacionService;

    @Autowired
    private FinanciacionRepository financiacionRepository;

    @GetMapping
    public ResponseEntity<List<Financiacion>> listar(){
        return  ResponseEntity.ok(financiacionService.listar());
    }

    @DeleteMapping("{numeroCredito}")
    public ResponseEntity borrar(@PathVariable int numeroCredito){


        /*
         * Nota: Utilizo el repositorio directamente porque no tengo lógica de negocio
         * */

        boolean borrado = financiacionRepository.eliminar(numeroCredito);

        if (borrado){

            return  ResponseEntity.noContent().build();

        }

        return  ResponseEntity.notFound().build();
    }


    @GetMapping("{numeroCredito}")
    public  ResponseEntity consultar(@PathVariable int numeroCredito){

        /*
         * Nota: Utilizo el repositorio directamente porque no tengo lógica de negocio
         * */

        Optional<Financiacion> encontrado = financiacionRepository.consultar(numeroCredito);

        if (encontrado.isPresent()){

            return  ResponseEntity.ok(encontrado.get());

        }

        return  ResponseEntity.notFound().build();

    }

    @PutMapping("{numeroCredito}")
    public  ResponseEntity actualizar(@PathVariable int numeroCredito, @RequestBody ActualizarFInanciacionRequest request){

        /*
        * Nota: Utilizo el repositorio directamente porque no tengo lógica de negocio
        * */

        boolean actualizado = financiacionRepository.actualizar(numeroCredito,request.estado());

        if (actualizado){

            return  ResponseEntity.noContent().build();

        }

        return  ResponseEntity.notFound().build();

    }


    @PostMapping
    public ResponseEntity<FinanciacionResponse> calcularFinanciacion(@RequestBody FinanciacionRequest request) {

        try {

            FinanciacionResponse respuesta = financiacionService.registrar(request);

            return ResponseEntity.created(URI.create("/api/financiaciones/"+respuesta.numeroCredito())).body(respuesta);

        } catch (LogicaNegocioExcepcion e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}


