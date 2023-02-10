package hlc.ud04.appsec.seguridad.controlacceso;

import hlc.ud04.appsec.seguridad.autenticacion.Usuario;

/**
 * Sistema de control de acceso
 * @author mmontoro
 *
 */
public interface ControlAcceso {
  
  /**
   * Comprueba si el usuario tiene permiso para realizar la operacion o no
   * @param usuario Usuario que va a realizar la operación
   * @param operacion que se quiere realizar
   * @param recurso Recurso sobre el que se quiere operar. null si es una operacion global
   * @return true si el usuario tiene permisos suficientes. false si no.
   */
  boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso);
}
