package hlc.ud04.appsec.core;

/**
 * Excepcion del paquete
 * @author mmontoro
 *
 */
public class ClienteException extends RuntimeException {

  public ClienteException(String message) {
    super(message);
  }

  public ClienteException(Throwable cause) {
    super(cause);
  }

  public ClienteException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClienteException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
