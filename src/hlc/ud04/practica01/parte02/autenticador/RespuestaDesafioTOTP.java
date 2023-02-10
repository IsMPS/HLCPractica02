package hlc.ud04.practica01.parte02.autenticador;

import hlc.ud04.appsec.seguridad.autenticacion.RespuestaDesafio;

public class RespuestaDesafioTOTP implements RespuestaDesafio {
  
  private String pin;
  
  public RespuestaDesafioTOTP(String pin) {
    this.pin = pin;
  }

  public String getPin() {
    return pin;
  }
}
