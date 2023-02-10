package hlc.ud04.practica01.parte01.autenticador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import hlc.ud04.appsec.seguridad.autenticacion.Autenticador;
import hlc.ud04.appsec.seguridad.autenticacion.Desafio;
import hlc.ud04.appsec.seguridad.autenticacion.RespuestaDesafio;
import hlc.ud04.appsec.seguridad.autenticacion.Usuario;

public class AutenticadorPasswordSqlite implements Autenticador {

  private static final String SQLITE_JDBC = "jdbc:sqlite:";
  
  private String database;
  
  public AutenticadorPasswordSqlite(String database) {
    this.database = database;
  }
  
  @Override
  public Desafio iniciaAutenticacion(String identificador) {
    return new DesafioPassword(identificador);
  }

  @Override
  public Usuario finalizaAutenticacion(Desafio desafio, RespuestaDesafio respuesta) {
    DesafioPassword dp = (DesafioPassword) desafio;
    RespuestaDesafioPassword rp = (RespuestaDesafioPassword)respuesta;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = DriverManager.getConnection(SQLITE_JDBC + database);
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT * FROM sec_usuario_password WHERE usuario = " 
        + stmt.enquoteLiteral(dp.getIdentificador())
        + "AND password = "
        + stmt.enquoteLiteral(rp.getPassword()));
      if (rs.next()) {
        long uid = rs.getLong("uid");
        return new Usuario(uid);
      } else {
        return null;
      }
    } catch (SQLException e) {
      return null;
    } finally {
      // Intenta liberar lo que se esté usando
      try {
        rs.close();
      } catch (Exception e) {}
      try {
        stmt.close();
      } catch (Exception e) {}
      try {
        conn.close();
      } catch (Exception e) {}
    }
  }

}
