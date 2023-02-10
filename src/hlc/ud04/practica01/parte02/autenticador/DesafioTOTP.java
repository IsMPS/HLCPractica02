package hlc.ud04.practica01.parte02.autenticador;

import hlc.ud04.appsec.seguridad.autenticacion.Desafio;

public class DesafioTOTP implements Desafio {
  
  private String identificador;
  
  public DesafioTOTP(String identificador) {
    this.identificador = identificador;
  }

  public String getIdentificador() {
    return identificador;
  }
}
