package hlc.ud04.appsec.seguridad.autenticacion;

/**
 * Información de un usuario. En nuestro caso es un mínimo y contiene sólo
 * un ID numérico.
 * @author mmontoro
 *
 */
public class Usuario {
  
  /**
   * ID del usuario
   */
  private long uid;
  
  /**
   * Constructor
   * @param uid ID del usuario
   */
  public Usuario(long uid) {
    this.uid = uid;
  }
  
  /**
   * Obtiene el ID numérico del usuario
   * @return
   */
  public long getUid() {
    return uid;
  }

}
