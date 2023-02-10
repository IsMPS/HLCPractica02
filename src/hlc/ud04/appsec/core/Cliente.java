package hlc.ud04.appsec.core;

/**
 * Informacion de un cliente
 * @author mmontoro
 *
 */
public class Cliente {

  // Numero de cuenta
  private String numeroCuenta;
  // Nombre del cliente
  private String nombre;
  // Saldo de la cuenta
  private double saldo;
  
  /**
   * Constructor
   * @param numeroCuenta Numero de cuenta
   * @param nombre Nombre
   * @param saldo Saldo inicial
   */
  public Cliente(String numeroCuenta, String nombre, double saldo) {
    this.numeroCuenta = numeroCuenta;
    this.nombre = nombre;
    this.saldo = saldo;
  }

  /**
   * Devuelve el nombre del cliente
   * @return Nombre del cliente
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Modifica el nombre de cliente
   * @param nombre Nuevo nombre del cliente
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el número de cuenta
   * @return Número de cuenta
   */
  public String getNumeroCuenta() {
    return numeroCuenta;
  }

  /**
   * Obtiene el saldo de la cuenta
   * @return Saldo de la cuenta
   */
  public double getSaldo() {
    return saldo;
  }

  /**
   * Modifica el saldo de la cuenta
   * @param saldo Nuevo saldo de la cuenta
   */
  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
