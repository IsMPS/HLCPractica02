package hlc.ud04.practica01.parte01.controlacceso;

import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.ControlAcceso;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.controlacceso.Recurso;

public class ControlAccesoFichero implements ControlAcceso {

	  @Override
	  public boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso) {
	    // El usuario 1 tiene acceso a todo
	    if (usuario.getUid() == 1) {
	      return true;
	    } else  if (usuario.getUid() == 2) {
	      // El 2 tiene acceso sólo lectura
	      return operacion == Operacion.LECTURA;
	    } else if (usuario.getUid() == 3) {
	      // El 3 sólo escritura
	      return operacion == Operacion.ESCRITURA;
	    } else {
	      // Cualquier otro no tiene acceso
	      return false;
	    }
	   }

	  
	  
	}
