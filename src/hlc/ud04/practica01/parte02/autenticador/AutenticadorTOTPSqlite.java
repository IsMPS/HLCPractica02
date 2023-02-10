package hlc.ud04.practica01.parte02.autenticador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import hlc.auth.otp.GeneradorHOTP;
import hlc.ud04.appsec.seguridad.autenticacion.Autenticador;
import hlc.ud04.appsec.seguridad.autenticacion.Desafio;
import hlc.ud04.appsec.seguridad.autenticacion.RespuestaDesafio;
import hlc.ud04.appsec.seguridad.autenticacion.Usuario;

public class AutenticadorTOTPSqlite implements Autenticador {

  private static final String SQLITE_JDBC = "jdbc:sqlite:";
  public static final long FACTOR = 30000;
  
  private String database;
  
  public AutenticadorTOTPSqlite(String database) {
    this.database = database;
  }
  
  @Override
  public Desafio iniciaAutenticacion(String identificador) {
    return new DesafioTOTP(identificador);
  }

  @Override
  public Usuario finalizaAutenticacion(Desafio desafio, RespuestaDesafio respuesta) {
    
    DesafioTOTP dp = (DesafioTOTP) desafio;
    RespuestaDesafioTOTP rp = (RespuestaDesafioTOTP)respuesta;

    DatosUsuario datosUsuario = getSecreto(dp.getIdentificador());
    
    if (datosUsuario != null) {
      // Creamos el generator OTP
      GeneradorHOTP generador = new GeneradorHOTP();
      String pin = generador.genera(datosUsuario.secreto, System.currentTimeMillis() / FACTOR);
      if (pin.equals(rp.getPin())) {
        return new Usuario(datosUsuario.uid);
      }
    } 
    return null;
  }

  private DatosUsuario getSecreto(String identificador) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = DriverManager.getConnection(SQLITE_JDBC + database);
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT secreto, uid FROM sec_usuario_totp WHERE usuario = " 
        + stmt.enquoteLiteral(identificador));
      if (rs.next()) {
        String secreto = rs.getString("secreto");
        long uid = rs.getLong("uid");
        return new DatosUsuario(secreto, uid);
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

class DatosUsuario {
  String secreto;
  long uid;
  
  DatosUsuario(String secreto, long uid) {
    this.secreto = secreto;
    this.uid = uid;
  }
}
