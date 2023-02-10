package hlc.ud04.appsec.core;

import java.util.List;

/**
 * Interfaz que deben implementar los gestores de persistencia
 * @author mmontoro
 *
 */
public interface GestorPersistencia {
  
  /**
   * Almacena un cliente en el almacenamiento. Si ya está, lo sobreescribe
   * @param cliente Cliente a persistir
   */
  void guardar(Cliente cliente);
  
  /**
   * Elimina un cliente en el almacenamiento. Si no está no hace nada
   * @param cliente Cliente a eliminar
   */
  void eliminar(Cliente cliente);
  
  /**
   * Obtiene un cliente determinado a partir de su búmero de cuenta
   * @param numeroCuenta Número de cuenta del cliente que se busca
   * @return Cliente con ese número de cuenta o null si no se localiza
   */
  Cliente obtenerPorNumero(String numeroCuenta);
  
  /**
   * Obtiene una lista con todos los clientes almacenados
   * @return Lista con todos los clientes almacenados (puede estar vacía)
   */
  List<Cliente> obtenerTodos();
}
