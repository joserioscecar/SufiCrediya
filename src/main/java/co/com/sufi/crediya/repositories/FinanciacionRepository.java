package co.com.sufi.crediya.repositories;


import co.com.sufi.crediya.entities.Financiacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class FinanciacionRepository {


  //  @Autowired
    private DataSource dataSource;
    private String sql;


        public FinanciacionRepository(DataSource dataSource){

        this.dataSource = dataSource;
    }




    public void registrar(Financiacion financiacion) {

        sql = "INSERT INTO financiaciones VALUES(?,?,?,?,?,?,?)";


        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setInt(1,financiacion.getNumeroCredito());
            preparedStatement.setString(2,"1108");
            preparedStatement.setDouble(3,financiacion.getValorFinanciar());
            preparedStatement.setInt(4, financiacion.getNumeroCuotas());
            preparedStatement.setDouble(5,financiacion.getTasaMensual());
            preparedStatement.setDouble(6,financiacion.getValorCuota());
            preparedStatement.setDate(7, Date.valueOf(financiacion.getFechaPrimeraCuota()));

            preparedStatement.execute();

        } catch (SQLException sqle) {

            sqle.printStackTrace();
        }


    }


}
