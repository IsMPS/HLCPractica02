package hlc.ud04.appsec.core;

import java.util.Iterator;

/**
 * Permite iterar (recorrer) todos los clientes 
 * @author mmontoro
 *
 */
public class IteradorClientes {
  
  // Clientes
  Clientes clientes;
  // Iterador de sistema sobre las claves
  Iterator<String> iterador;
  
  /**
   * Constructor
   * @param clientes Clientes sobre los que se va a iterar
   */
  public IteradorClientes(Clientes clientes) {
    // Almacena el sistema de clientes
    this.clientes = clientes;
    // Crea el iterador sobre las claves del mapa
    this.iterador = clientes.clientes.keySet().iterator();
  }
  
  /**
   * Comprueba si hay más elementos a iterar
   * @return true si hay más elementos o false en caso contrario
   */
  public boolean hasNext() {
    return iterador.hasNext();
  }
  
  /**
   * Devuelve el siguiente cliente en el iterador y avanza una posición
   * @return Siguiente cliente
   */
  public Cliente next() {
    // Obtenemos la siguiente clave
    String clave = iterador.next();
    // Y a partir de ella, el cliente (accedemos directamente al atributo)
    return clientes.clientes.get(clave);
  }

}
