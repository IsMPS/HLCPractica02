package hlc.ud04.appsec.seguridad.autenticacion;

/**
 * Interfaz de usuario para un sistema de autenticación
 * Solicita la información al usuario y se comunica con el sistema de autenticación
 * para determinar la información del usuario
 * @author mmontoro
 *
 */
public abstract class InterfazAutenticacion {
  
  /**
   * Algoritmo de autentificación a usar
   */
  protected Autenticador autenticador;
  
  /**
   * Constructor
   * @param autenticador Algoritmo de autenticación a usar por el interfaz
   */
  public InterfazAutenticacion(Autenticador autenticador) {
    this.autenticador = autenticador;
  }
  
  /**
   * Lanza el interfaz de usuario para la autenticación
   * @return Usuario si la autenticación tuvo éxito o null si no la tuvo
   */
  public abstract Usuario autentica();

}
