package hlc.ud04.appsec.seguridad.core;

import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.controlacceso.Recurso;

public interface SistemaSeguridad {
  
  Usuario autentica();
  
  boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso);

}
