package hlc.ud04.practica01.parte01.seguridad;

import java.util.Scanner;
import hlc.ud04.appsec.seguridad.autenticacion.Autenticador;
import hlc.ud04.appsec.seguridad.autenticacion.Desafio;
import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.ControlAcceso;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.controlacceso.Recurso;
import hlc.ud04.appsec.seguridad.core.SistemaSeguridad;
import hlc.ud04.practica01.parte01.autenticador.RespuestaDesafioPassword;

public class SistemaSeguridadPasswordSqlite implements SistemaSeguridad {

  private Autenticador autenticador;
  private ControlAcceso controlAcceso;
  
  public SistemaSeguridadPasswordSqlite(Autenticador autenticador, ControlAcceso controlAcceso) {
    this.autenticador = autenticador;
    this.controlAcceso = controlAcceso;
  }
  
  @Override
  public Usuario autentica() {
    // Solicita el identificador (login) del usuario
    Scanner sc = new Scanner(System.in);
    System.out.print("Introduce el identificador del usuario: ");
    String identificador = sc.nextLine();
    Desafio desafio = autenticador.iniciaAutenticacion(identificador);
    
    // Ahora la password
    System.out.print("Introduce ahora la password del usuario: ");
    String password = sc.nextLine();
    // Autentifica y devuelve el resultado
    return autenticador.finalizaAutenticacion(desafio, new RespuestaDesafioPassword(password));
  }

  @Override
  public boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso) {
    return controlAcceso.estaPermitido(usuario, operacion, recurso);
  }

}
