package hlc.ud04.practica01.parte01.autenticador;

import hlc.ud04.appsec.seguridad.autenticacion.Desafio;

public class DesafioPassword implements Desafio {
  
  private String identificador;
  
  public DesafioPassword(String identificador) {
    this.identificador = identificador;
  }

  public String getIdentificador() {
    return identificador;
  }
  
}
