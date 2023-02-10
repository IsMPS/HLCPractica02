package hlc.ud04.appsec.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import hlc.ud04.appsec.core.Cliente;
import hlc.ud04.appsec.core.GestorPersistencia;
import hlc.ud04.appsec.core.GestorPersistenciaException;

/**
 * Gestor de persistencia que emplea una base de datos SQLite
 * @author mmontoro
 *
 */
public class GestorPersistenciaSqlite implements GestorPersistencia {
  
  // Prefijo para la conexión a la base de datos (falta al final el nombre del archivo)
  private static final String JDBC_PREFIX = "jdbc:sqlite:";
  
  // Nombre del archivo de base de datos
  private String database;
  
  /**
   * Constructor
   * @param database Ruta al archivo de base de datos
   */
  public GestorPersistenciaSqlite(String database) {
    this.database = database;
  }

  @Override
  public void guardar(Cliente cliente) {
    // Conexión (hay que crearla aqui para poderla usar en finaly)
    Connection conn = null;
    try {
      // Eliminamos al cliente (si es que está almacenado)
      eliminar(cliente);
      // Obtenemos conexión
      conn = getConnection();
      // Y de la conexión un gestor de sentencias
      Statement statement = conn.createStatement();
      // Y con el gestor insertamos el cliente en la base de datos
      statement.executeUpdate("INSERT INTO cliente (numero, nombre, saldo) VALUES ("
          + statement.enquoteLiteral(cliente.getNumeroCuenta()) + ", "
          + statement.enquoteLiteral(cliente.getNombre()) + ", "
          + cliente.getSaldo() + ");");
    } catch (SQLException e) {
      // Si hay excepción, la cambia por una nuestra
      throw new GestorPersistenciaException(e);
    } finally {
      // En cualquier caso hay que intentar cerrar la conexión
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {}
    }
  }

  @Override
  public void eliminar(Cliente cliente) {
    Connection conn = null;
    try {
      conn = getConnection();
      Statement statement = conn.createStatement();
      statement.executeUpdate("DELETE FROM cliente WHERE numero = " + statement.enquoteLiteral(cliente.getNumeroCuenta()));
    } catch (SQLException e) {
      throw new GestorPersistenciaException(e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {}
      }
    }
  }

  @Override
  public Cliente obtenerPorNumero(String numeroCuenta) {
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = getConnection();
      Statement statement = conn.createStatement();
      rs = statement.executeQuery("SELECT numero, nombre, saldo FROM cliente WHERE numero = "
          + statement.enquoteLiteral(numeroCuenta) + ";");
      Cliente cliente = null;
      if (rs.next()) {
        cliente = new Cliente(rs.getString("numero"), rs.getString("nombre"), rs.getDouble("saldo"));
      }
      return cliente;
    } catch (SQLException e) {
      throw new GestorPersistenciaException(e);
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {}
      }
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {}
      }
    }
  }

  @Override
  public List<Cliente> obtenerTodos() {
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = getConnection();
      Statement statement = conn.createStatement();
      rs = statement.executeQuery("SELECT numero, nombre, saldo FROM cliente;");
      List<Cliente> clientes = new ArrayList<>();
      while (rs.next()) {
        clientes.add(new Cliente(rs.getString("numero"), rs.getString("nombre"), rs.getDouble("saldo")));
      }
      return clientes;
    } catch (SQLException e) {
      throw new GestorPersistenciaException(e);
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {}
      }
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {}
      }
    }
  }
  
  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(JDBC_PREFIX + database);
  }

}
