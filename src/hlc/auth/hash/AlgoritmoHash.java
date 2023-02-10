package hlc.auth.hash;

/**
 * Algoritmo de hashing HMAC
 * @author mmontoro
 *
 */
public interface AlgoritmoHash {

  /**
   * Obtiene un resumen o hash HMAC
   * @param clave Clave del algoritmo (8 bytes)
   * @param datos Datos a resumir (cualquier longitud)
   * @return Hash con el resumen
   */
  byte[] resume(byte[] clave, byte[] datos);
}
