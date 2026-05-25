package co.com.sufi.crediya.repositories;

import co.com.sufi.crediya.entities.Financiacion;
import co.com.sufi.crediya.enums.EstadoFinanciacion;

import java.util.List;
import java.util.Optional;

public interface FinanciacionRepository {

    List<Financiacion> listar();

    Optional<Financiacion> consultar(int numeroCredito);

    boolean actualizar(int numeroCredito, EstadoFinanciacion estado);

    boolean eliminar(int numeroCredito);

    void registrar(Financiacion financiacion);

}
