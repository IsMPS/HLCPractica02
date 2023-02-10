package hlc.ud04.appsec.seguridad.core;

import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.controlacceso.Recurso;

/**
 * Sistema de seguridad nulo. No hace nada
 * USAR SOLO PARA PRUEBAS
 * @author mmontoro
 *
 */
public class SistemaSeguridadNulo implements SistemaSeguridad {

  public SistemaSeguridadNulo() {
  }

  @Override
  public Usuario autentica() {
    // Devuelve un usuario de pega con el id 0
    return new Usuario(0);
  }

  @Override
  public boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso) {
    // Todo está permitido
    return true;
  }

}
