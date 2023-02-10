package hlc.ud04.appsec.core;

/**
 * Excepción del gestor de persistencia
 * @author mmontoro
 *
 */
public class GestorPersistenciaException extends RuntimeException {

  public GestorPersistenciaException(String message) {
    super(message);
  }

  public GestorPersistenciaException(Throwable cause) {
    super(cause);
  }

  public GestorPersistenciaException(String message, Throwable cause) {
    super(message, cause);
  }

  public GestorPersistenciaException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
