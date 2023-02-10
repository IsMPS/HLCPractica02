package hlc.ud04.practica01.parte02.seguridad;

import java.util.Scanner;
import hlc.ud04.appsec.seguridad.autenticacion.Autenticador;
import hlc.ud04.appsec.seguridad.autenticacion.Desafio;
import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.ControlAcceso;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.controlacceso.Recurso;
import hlc.ud04.appsec.seguridad.core.SistemaSeguridad;
import hlc.ud04.practica01.parte02.autenticador.RespuestaDesafioTOTP;

public class SistemaSeguridadTOTP implements SistemaSeguridad {

  private Autenticador autenticador;
  private ControlAcceso controlAcceso;
  
  public SistemaSeguridadTOTP(Autenticador autenticador, ControlAcceso controlAcceso) {
    this.autenticador = autenticador;
    this.controlAcceso = controlAcceso;
  }
  
  @Override
  public Usuario autentica() {
    
    Scanner sc = new Scanner(System.in);
    
    // Solicita el usuario
    System.out.print("Introduzca el identificador del usuario: ");
    String identificador = sc.nextLine();
    
    Desafio desafio = autenticador.iniciaAutenticacion(identificador);
    
    // Y ahora el pin
    System.out.print("Introduzca ahora el PIN actual del usuario (6 cifras): ");
    String pin = sc.nextLine();
    
    // Autentica
    return autenticador.finalizaAutenticacion(desafio, new RespuestaDesafioTOTP(pin));
  }

  @Override
  public boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso) {
    return controlAcceso.estaPermitido(usuario, operacion, recurso);
  }

}
