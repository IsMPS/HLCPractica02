package hlc.ud04.appsec.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Conjunto de los clientes
 * @author mmontoro
 *
 */
public class Clientes {

  // Datos de los clientes, indexados por número de cuenta
  // Es protegido de paquete porque debe poder accederse por IteradorClientes
  Map<String, Cliente> clientes;
  // Gestor de persistencia. Controla el almacenamiento de los datos de clientes
  private GestorPersistencia gestorPersistencia;
  
  /**
   * Constructor
   * @param gestorPersistencia Gestor de persistencia que gestiona el
   * almacenamiento permanente de los datos de cliente
   */
  public Clientes(GestorPersistencia gestorPersistencia) {
    // Almacenamos el gestor de persistencia
    this.gestorPersistencia = gestorPersistencia;
    // Se inicia el mapa
    this.clientes = new HashMap<>();
    // Se leen todos los clientes desde almacenamiento
    leeClientes();
  }
  
  /**
   * Añade un cliente al sistema
   * @param cliente Nuevo cliente a añadir
   * @throws ClienteException Si ya existe un cliente con el mismo número de cuenta.
   */
  public void add(Cliente cliente) {
    // Si ya hay un cliente con ese número
    if (clientes.containsKey(cliente.getNumeroCuenta())) {
      // Se lanza una excepción
      throw new ClienteException("Ya existe un cliente con ese número de cuenta");
    } else {
      // Añade el cliente al mapa
      clientes.put(cliente.getNumeroCuenta(), cliente);
      // Y lo almacena de forma permanente
      gestorPersistencia.guardar(cliente);
    }
  }
  
  /**
   * Modifica un cliente ya existente (sólo se puede modificar el nombre y el saldo)
   * @param cliente Cliente a modificar (sólo se usa el nombre y el saldo)
   * @throws ClienteException Si no hay cliente con el número de cuenta en el sistema
   */
  public void modify(Cliente cliente) {
    // Si el cliente no está ya en el sistema
    if (!clientes.containsKey(cliente.getNumeroCuenta())) {
      // Lanza la excepción
      throw new ClienteException("No existe cliente con ese número de cuenta");
    } else {
      // Añade (reemplaza) al cliente en el mapa
      clientes.put(cliente.getNumeroCuenta(), cliente);
      // Lo persiste
      gestorPersistencia.guardar(cliente);
    }
  }
  
  /**
   * Elimina un cliente del sistema
   * @param cliente Cliente a eliminar
   * @throws ClienteException Si no existe ese cliente en el sistema
   */
  public void remove(Cliente cliente) {
    // Si el cliente no está en el sistema
    if (!clientes.containsKey(cliente.getNumeroCuenta())) {
      // Lanza la excepción
      throw new ClienteException("No existe cliente con ese número de cuenta");
    } else {
      // Elimina al cliente del mapa
      clientes.remove(cliente.getNumeroCuenta());
      // Persiste la eliminación
      gestorPersistencia.eliminar(cliente);
    }
  }
  
  /**
   * Localiza a un cliente determinado por su número de cuenta
   * @param numeroCuenta Número de cuenta del cliente a buscar
   * @return Cliente con el número de cuenta dado o null si no
   * se encuentra cliente con ese número de cuenta
   */
  public Cliente buscarPorNumeroCuenta(String numeroCuenta) {
    return clientes.get(numeroCuenta);
  }
  
  /**
   * Devuelve un iterador para recorrer los clientes
   * @return Iterador para recorrer los clientes
   */
  public IteradorClientes iterador() {
    return new IteradorClientes(this);
  }
  
  /**
   * Lee los clientes desde almacenamiento
   */
  private void leeClientes() {
    // Limpia el mapa y lo deja vacío
    clientes.clear();
    // Obtiene una lista con todos los clientes
    List<Cliente> listaClientes = gestorPersistencia.obtenerTodos();
    // Recorre la lista e inserta todos los clientes en el mapa
    for(Cliente cliente: listaClientes) {
      clientes.put(cliente.getNumeroCuenta(), cliente);
    }
  }
}
