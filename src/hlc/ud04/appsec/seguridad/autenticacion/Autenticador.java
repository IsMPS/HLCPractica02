package hlc.ud04.appsec.seguridad.autenticacion;

/**
 * Un Autenticador representa un algoritmo de autenticación de uno o dos pasos
 * @author mmontoro
 *
 */
public interface Autenticador {
  
  /**
   * Inicia la autenticación proporcionando el ID del usuario
   * @param identificador Identificador del usaurio
   * @return desafio que presenta el algoritmo. Este desafio debe contener al
   * menos la información suficiente para identificar al usuario
   */
  Desafio iniciaAutenticacion(String identificador);
  
  /**
   * Finaliza la identificación y devuelve al usuario
   * @param desafio Desafío devuelto por iniciaAutenticacion
   * @param respuesta Respuesta al desafío
   * @return usuario autenticado, si ésta tuvo éxito. null en caso de que no
   * se pueda autenticar al usuario por la razón que sea
   */
  Usuario finalizaAutenticacion(Desafio desafio, RespuestaDesafio respuesta);

}
