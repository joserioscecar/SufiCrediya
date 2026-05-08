package co.com.sufi.crediya.repositories;

import co.com.sufi.crediya.entities.Financiacion;
import co.com.sufi.crediya.enums.EstadoFinanciacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FinanciacionRepository {


    private String sql;
    @Autowired
    private DataSource dataSource;



    public List<Financiacion> listar(){

        sql ="SELECT * FROM financiaciones";

        List<Financiacion> financiaciones = new ArrayList<>();

        try(
                Connection connection = dataSource.getConnection()
        ) {

            Statement sentencia = connection.createStatement();
            ResultSet resultadoSQL = sentencia.executeQuery(sql);

            while (resultadoSQL.next()){

                int codigo = resultadoSQL.getInt(1);
                String titular   = resultadoSQL.getString(2);

                Financiacion financiacion = new Financiacion(codigo,titular,0,1,0,0, LocalDate.now());

                financiaciones.add(financiacion);

            }


        }catch (SQLException sqle){

            sqle.printStackTrace();

            throw  new RuntimeException("");
        }

        return  financiaciones;

    }

    public  boolean actualizar(int numeroCredito, EstadoFinanciacion estado){

        sql ="UPDATE  financiaciones  SET estado = ? WHERE numero_credito = ?";

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement sentencia = connection.prepareStatement(sql);
        ) {


            sentencia.setString(1,estado.toString());
            sentencia.setInt(2,numeroCredito);

            return sentencia.executeUpdate()>0;

        }catch (SQLException sqle){

            sqle.printStackTrace();

            throw  new RuntimeException("");
        }


    }

    public  boolean eliminar(int numeroCredito){

        sql ="DELETE FROM financiaciones WHERE numero_credito = ?";

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement sentencia = connection.prepareStatement(sql);
        ) {



            sentencia.setInt(1,numeroCredito);

            return sentencia.executeUpdate()>0;

        }catch (SQLException sqle){

            sqle.printStackTrace();

            throw  new RuntimeException("");
        }


    }


    public  void registrar(Financiacion financiacion){

        sql ="insert into  financiaciones(numero_credito,titular,valor_financiar,numero_cuotas,tasa_mensual,valor_cuota,fecha_primera_cuota,fecha_registro,estado) values (?,?,?,?,?,?,?,?,?)";

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement sentencia = connection.prepareStatement(sql);
     ) {

            sentencia.setInt(1,financiacion.getNumeroCredito());
            sentencia.setString(2,financiacion.getTitular());
            sentencia.setDouble(3,financiacion.getValorFinanciar());
            sentencia.setInt(4,financiacion.getNumeroCuotas());
            sentencia.setDouble(5,financiacion.getTasaMensual());
            sentencia.setDouble(6,financiacion.getValorCuota());
            sentencia.setDate(7, Date.valueOf(financiacion.getFechaPrimeraCuota()));
            sentencia.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            sentencia.setString(9, financiacion.getEstado().toString());

            sentencia.executeUpdate();

        }catch (SQLException sqle){

            sqle.printStackTrace();

            throw  new RuntimeException("");
        }


    }



}
