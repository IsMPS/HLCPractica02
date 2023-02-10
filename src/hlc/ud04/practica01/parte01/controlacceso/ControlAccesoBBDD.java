package hlc.ud04.practica01.parte01.controlacceso;

import hlc.ud04.appsec.core.GestorPersistencia;
import hlc.ud04.appsec.persistencia.GestorPersistenciaSqlite;
import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.ControlAcceso;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.controlacceso.Recurso;

public class ControlAccesoBBDD implements ControlAcceso {
	GestorPersistencia gp = new GestorPersistenciaSqlite("db/base.db");
	
	@Override
	  public boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso) {
	    // El usuario 1 tiene acceso a todo
	    if (gp.obtenerPermisosPorId(usuario.getUid()).contains("r") && gp.obtenerPermisosPorId(usuario.getUid()).contains("w")) {
	      return true;
	    } else  if (gp.obtenerPermisosPorId(usuario.getUid()).contains("r")) {
	      // El r tiene acceso sólo lectura
	      return operacion == Operacion.LECTURA;
	    } else if (gp.obtenerPermisosPorId(usuario.getUid()).contains("w")) {
	      // El w sólo escritura
	      return operacion == Operacion.ESCRITURA;
	    } else {
	      // Cualquier otro no tiene acceso
	      return false;
	    }
	   }
}
