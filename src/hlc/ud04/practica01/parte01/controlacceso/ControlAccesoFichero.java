package hlc.ud04.practica01.parte01.controlacceso;

import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.ControlAcceso;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.controlacceso.Recurso;
import hlc.ud04.practica01.parte02.utils.Fichero;

public class ControlAccesoFichero implements ControlAcceso {

	  @Override
	  public boolean estaPermitido(Usuario usuario, Operacion operacion, Recurso recurso) {
		  
		  Fichero f = new Fichero("archivo/permisos.txt");
		  String texto = f.leerArchivo();
		  String[] permisos = texto.split(";");
		  String[] permiso = new String[2];
		  
		  for (int i = 0; i < permisos.length; i++) {
			if(permisos[i].substring(0,1).equals(String.valueOf(usuario.getUid()).substring(0))) {
				permiso = permisos[i].split(":");
			}
		}
		  
	    // El usuario 1 tiene acceso a todo
	    if (permiso[1].contains("r") && permiso[1].contains("w")) {
	      return true;
	    } else  if (permiso[1].contains("r")) {
	      // El 2 tiene acceso sólo lectura
	      return operacion == Operacion.LECTURA;
	    } else if (permiso[1].contains("w")) {
	      // El 3 sólo escritura
	      return operacion == Operacion.ESCRITURA;
	    } else {
	      // Cualquier otro no tiene acceso
	      return false;
	    }
	   }

	  
	  
	}
