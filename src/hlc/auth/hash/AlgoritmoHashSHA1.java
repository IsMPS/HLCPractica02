package hlc.auth.hash;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Implementación de AlgoritmoHash con el algoritmo HMAC-SHA1
 * @author mmontoro
 *
 */
public class AlgoritmoHashSHA1 implements AlgoritmoHash {

  // Nombre del algoritmo en la libreria
  private static final String ALGORITMO = "HmacSHA1";
  
  @Override
  public byte[] resume(byte[] clave, byte[] datos) {
    try {
      // Obtenemos el objeto SecretKey a partir de la clave
      SecretKeySpec claveAlg = new SecretKeySpec(clave, ALGORITMO);
      // Obtenemos la instancia del algoritom
      Mac mac = Mac.getInstance(ALGORITMO);
      // Se inicializa con la clave
      mac.init(claveAlg);

      // Crea y devuelve el resumen con los datos proporcionados
      return mac.doFinal(datos);
      
    } catch (Exception e) {
      throw new AlgoritmoHashException("Error generando hash. Error inicial: ", e);
    }
  }

}
