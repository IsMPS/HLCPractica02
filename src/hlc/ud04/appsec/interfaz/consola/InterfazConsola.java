package hlc.ud04.appsec.interfaz.consola;

import java.util.Scanner;
import hlc.ud04.appsec.core.Cliente;
import hlc.ud04.appsec.core.Clientes;
import hlc.ud04.appsec.core.IteradorClientes;
import hlc.ud04.appsec.interfaz.Interfaz;
import hlc.ud04.appsec.seguridad.autenticacion.Usuario;
import hlc.ud04.appsec.seguridad.controlacceso.Operacion;
import hlc.ud04.appsec.seguridad.core.SistemaSeguridad;

/**
 * Interfaz de usuario de la aplicación basado en consola
 * @author mmontoro
 *
 */
public class InterfazConsola implements Interfaz {

  // Opciones del menu
  private static final int OPCION_SALIR = 0;
  private static final int OPCION_NUEVO = 1;
  private static final int OPCION_MODIFICAR = 2;
  private static final int OPCION_ELIMINAR = 3;
  private static final int OPCION_LISTAR = 4;
  
  // Clientes
  Clientes clientes;
  // Scanner a usar para leer desde consola
  Scanner scanner;
  // Usuario que nos está usando
  Usuario usuario;
  // Sistema de seguridad
  SistemaSeguridad sistemaSeguridad;

  /**
   * Constructor
   * @param clientes Clientes que vamos a gestionar
   * @param sistemaSeguridad Sistema de seguridad que vamos a usar
   */
  public InterfazConsola(Clientes clientes, SistemaSeguridad sistemaSeguridad) {
    // Almacenamos los clientes
    this.clientes = clientes;
    // Creamos el scanner para que lo usen los métodos internos
    scanner = new Scanner(System.in);
    // Usuario inicialmente a null
    usuario = null;
    // Almacenamos el sistema de seguridad
    this.sistemaSeguridad = sistemaSeguridad;
  }

  @Override
  public void run() {
    
    // Autentifica antes de continuar
    usuario = sistemaSeguridad.autentica();
    
    // Si estamos autenticados
    if (usuario != null) {
      int opcion;
      boolean salir = false;
      // Mientras que no se solicite salir
      do {
        // Muestra el menú y obtiene la opción elegida por el usuario (número)
        opcion = mostrarMenu();
        // Según la opción elegida
        // Comprueba que se tienen los permisos adecuados para realizar la operación
        // Y se lanza
        switch (opcion) {
          case OPCION_NUEVO:
            if (controlaAcceso(Operacion.ESCRITURA)) {
              nuevoCliente();
            }
            break;
          case OPCION_MODIFICAR:
            Operacion[] operaciones = {Operacion.LECTURA, Operacion.ESCRITURA};
            if (controlaAcceso(operaciones)) {
              modificarCliente();
            }
            break;
          case OPCION_ELIMINAR:
            if (controlaAcceso(Operacion.ESCRITURA)) {
              eliminarCliente();
            }
            break;
          case OPCION_LISTAR:
            if (controlaAcceso(Operacion.LECTURA)) {
              listarClientes();
            }
            break;
          case OPCION_SALIR:
            // En el caso de salida, cambiamos la bandera
            salir = true;
            break;
          default:
            // Opción incorrecta. Mostramos error pero seguimos
            System.err.println("Opción incorrecta.");
        }
      } while (!salir);
    } else {
      // No estamos autenticados
      System.err.println("Credenciales no válidas. Terminando.");
    }
    
    // Para que se vea claro cuando acaba el programa ya que sale mucho texto
    System.out.println("FIN DEL PROGRAMA");
  }

  /**
   * Controla el acceso a una operacíón y muestra un mensaje si no se puede
   * @param operacion Operacion a realizar
   * @return true si hay permisos para realizar la operación o false si no
   */
  private boolean controlaAcceso(Operacion operacion) {
    // Si el usuario tiene permisos
    if (sistemaSeguridad.estaPermitido(usuario, operacion, null)) {
      // Devuelve true
      return true;
    } else {
      // Si no tiene permisos, muestra error y devuelve false
      System.err.println("El usuario no tiene permisos suficientes para realizar la operacion");
      return false;
    }
  }

  /**
   * Controla el acceso a varias operaciones y muestra un mensaje si no se puede
   * @param operacion Operacion a realizar
   * @return true si hay permisos para realizar las operaciónes o false si no
   */
  private boolean controlaAcceso(Operacion[] operaciones) {
    // Para cada permiso 
    for (Operacion operacion: operaciones) {
      if (!sistemaSeguridad.estaPermitido(usuario, operacion, null)) {
        System.err.println("El usuario no tiene permisos suficientes para realizar las operaciones");
        return false;
      }
    }
    return true;
  }

  /**
   * Operación Listar Clientes
   */
  private void listarClientes() {
    // Obtenemos un iterador a los clientes
    IteradorClientes it = clientes.iterador();
    
    // Cabecera
    System.out.println("LISTAR CLIENTES");
    System.out.println("---------------");
    System.out.println();
    
    // Cabecera del listado (Mejorable)
    System.out.println("NUMERO\t\t\tNOMBRE\t\t\tSALDO");
    // Mientras haya clientes
    while (it.hasNext()) {
      // Se obtiene y se imprime (mejorable también)
      Cliente cliente = it.next();
      System.out.println(cliente.getNumeroCuenta() + "\t\t" + cliente.getNombre() + "\t\t" + cliente.getSaldo());
    }
    // Linea en blanco para más claridad
    System.out.println();
  }

  /**
   * Operación Eliminar Cliente
   */
  private void eliminarCliente() {
    // Cabecera
    System.out.println("ELIMINAR CLIENTE");
    System.out.println("----------------");
    System.out.println();
    
    // Solicitamos el número del cliente a eliminar (no hace falta más información)
    System.out.print("Introduzca el número del cliente a eliminar: ");
    String numero = scanner.nextLine();
    
    // Busca el cliente por su número
    Cliente cliente = clientes.buscarPorNumeroCuenta(numero);
    
    // Si se encontro
    if (cliente != null) {
      // Lo elimina
      clientes.remove(cliente);
      System.out.println("Cliente eliminado");
    } else {
      // Si no se encontró, error al canto
      System.err.println("No existe cliente con el número " + numero);
    }
    System.out.println();
  }

  /**
   * Operacion Modificar Cliente
   */
  private void modificarCliente() {
    // Cabecera
    System.out.println("MODIFICAR CLIENTE");
    System.out.println("-----------------");
    System.out.println();
    
    // Solicita el número del cliente a modificar
    System.out.print("Introduzca el número del cliente a modificar: ");
    String numero = scanner.nextLine();
    
    // Lo intenta localizar
    Cliente cliente = clientes.buscarPorNumeroCuenta(numero);
    // Si se encontro
    if (cliente != null) {
      // Lo muestra antes de modificarlo
      System.out.println("NUMERO\t\t\tNOMBRE\t\t\tSALDO");
      System.out.println(cliente.getNumeroCuenta() + "\t\t" + cliente.getNombre() + "\t\t" + cliente.getSaldo());
      
      // Ahora solicita los datos del cliente (excepto el número de cuenta)
      System.out.print("Introduzca el nuevo nombre del cliente (en blanco para no modificarlo): ");
      String valor = scanner.nextLine();
      // Si se especificó
      if (valor.length() > 0) {
        // Lo actualiza (sino se queda como está)
        cliente.setNombre(valor);
      }
      
      // Solicita el saldo
      System.out.print("Introduzca el nuevo saldo del cliente (en blanco para no modificarlo): ");
      valor = scanner.nextLine();
      // Si se especificó
      if (valor.length() > 0) {
        // Intenta procesarlo a un número
        try {
          // Si puede se guardará
          cliente.setSaldo(Double.parseDouble(valor));
        } catch (NumberFormatException e) {
          // Si no, no se guarda y se muestra un error
          System.err.println("El formato del saldo no es correcto. No se modificará");
        }
      }
      
      // Se guarda el cliente
      clientes.modify(cliente);
    } else {
      // Error porque el cliente no existe
      System.err.println("No existe cliente con el número " + numero);
    }
    System.out.println();
  }

  /**
   * Operación Nuevo Cliente
   */
  private void nuevoCliente() {
    // Cabecera
    System.out.println("NUEVO CLIENTE");
    System.out.println("-------------");
    System.out.println();
    
    // Leemos el número del nuevo cliente
    String numero;
    System.out.print("Introduzca el número del nuevo cliente (no puede ser vacío): ");
    numero = scanner.nextLine();
    
    // Lo intenta localizar
    Cliente cliente = clientes.buscarPorNumeroCuenta(numero);
    
    // Si NO se encontro (OK)
    if (cliente == null) {
      // Solicita el resto de datos del cliente (en caso de error se aborta)
      System.out.print("Introduzca el nombre del cliente (no puede ser vacío): ");
      String nombre = scanner.nextLine();
      if (nombre.length() == 0) {
        System.err.println("El nombre no puede dejarse en blanco. Abortando operación");
        return;
      }
      System.out.print("Introduzca el saldo del cliente (debe ser un número positivo ó 0): ");
      String saldoStr = scanner.nextLine();
      double saldo = 0;
      if (saldoStr.length() > 0) {
        try {
          saldo = Double.parseDouble(saldoStr);
          if (saldo < 0) {
            System.err.println("Saldo no válido. Abortando");
            return;
          }
        } catch (NumberFormatException e) {
          System.err.println("El formato del saldo no es correcto. Abortando");
          return;
        }
      }
      
      // Se crea y añade el cliente
      cliente = new Cliente(numero, nombre, saldo);
      clientes.add(cliente);

    } else {
      // El cliente ya existe. Se aborta
      System.err.println("Ya existe cliente con el número " + numero + ". Abortando");
    }
    
    System.out.println();
  }

  /**
   * Muestra el menú y lee la opción elegida por el usuario
   * @return Opción numérica elegida por el usuario
   */
  private int mostrarMenu() {
    // Muestra el menú
    System.out.println("MENU PRINCIPAL");
    System.out.println("--------------");
    System.out.println();
    System.out.println("1.- Nuevo cliente");
    System.out.println("2.- Modificar cliente");
    System.out.println("3.- Eliminar cliente");
    System.out.println("4.- Listar clientes");
    System.out.println("0.- Salir de la aplicación");
    
    // Ciclo que lee desde teclado hasta que se introduce un número entero
    int resultado = -1;
    do {
      System.out.print("Elija una opción (0-4): ");
      try {
        resultado = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.err.println("Error. No es un número");
        resultado = -1;
      }
    } while (resultado == -1);

    // Devuelve el número elegido
    return resultado;
  }

}
