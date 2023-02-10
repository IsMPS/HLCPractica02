package hlc.ud04.practica01.parte01.autenticador;

import hlc.ud04.appsec.seguridad.autenticacion.RespuestaDesafio;

public class RespuestaDesafioPassword implements RespuestaDesafio {
  
  private String password;
  
  public RespuestaDesafioPassword(String password) {
    this.password = password;
  }
  
  public String getPassword() {
    return password;
  }
}
