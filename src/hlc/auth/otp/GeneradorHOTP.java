package hlc.auth.otp;

import java.security.InvalidParameterException;
import hlc.auth.hash.AlgoritmoHash;
import hlc.auth.hash.AlgoritmoHashSHA1;

/**
 * Generador de clases OTP basada en RFC 4226
 * @author mmontoro
 *
 */
/**
 * @author mmontoro
 *
 */
public class GeneradorHOTP {

  // Algoritmo Hash a usar
  private AlgoritmoHash algoritmo;
  // dígitos que se van a generar
  private int digitos;
  
  // Varios constructores con distintas combinaciones de parámetros
  
  public GeneradorHOTP() {
    this(new AlgoritmoHashSHA1(), 6);
  }
  
  public GeneradorHOTP(int digitos) {
    this(new AlgoritmoHashSHA1(), digitos);
  }
  
  public GeneradorHOTP(AlgoritmoHash algoritmo) {
    this(algoritmo, 6);
  }
  
  public GeneradorHOTP(AlgoritmoHash algoritmo, int digitos) {
    if (algoritmo == null) {
      throw new InvalidParameterException("El algoritmo hash no puede ser nulo");
    }
    if (digitos < 1) {
      throw new InvalidParameterException("digitos debe ser mayor que cero");
    }
    
    this.algoritmo = algoritmo;
    this.digitos = digitos;
  }

  /**
   * Genera una clave OTP partiendo de un secreto y un contador
   * @param secreto. Secreto. Número hexadecimal de 16 cifras (64 bits)
   * @param contador. Contador
   * @return Clave OTP con el número de dígitos configurado en la creación
   */
  public String genera(String secreto, long contador) {
    try {
      /**
       * Genera el hash a partir del secreto y el contador usando el algoritmo
       * indicado en la creación
       */
      byte[] hash = generaHash(secreto, contador);
      // Extrae los dígitos necesarios desde el hash
      return getDigitosDesdeHash(hash);
    } catch (Exception e) {
      // Si hay excepción la sustituye por la nuestra
      throw new GeneradorException("Error generando el dódigo. Ver excepción", e);
    }
  }

  /**
   * Genera el hash
   * @param secreto Valor hexadecimal de 64 bits (16 dígitos)
   * @param contador Contador
   * @return array de 8 bytes con el hash
   */
  private byte[] generaHash(String secreto, long contador) {
    
    // Dividimos tanto el secreto como el contador en bytes
    // porque son necesarios para el algoritmo de hash el tenerlos en ese formato
    byte[] datos = longToByteArray(contador);
    byte[] clave = longToByteArray(Long.parseUnsignedLong(secreto, 16));
    
    // Invoca al algoritmo y devuelve el resumen
    return algoritmo.resume(clave, datos);
  }

  /**
   * Extrae los dígitos de la clave a partir del hash
   * @param hash Hash a usar para extraer los dígitos
   * @return Digitos de la clave
   */
  private String getDigitosDesdeHash(byte[] hash) {
    // Obtenemos el byte desde el que comenzar a partir de los 4 bits menos significativos
    int desplazamiento = hash[hash.length - 1] & 0xF;

    // Aqui vamos a almacenar la parte a extraer desde el hash (hash truncado)
    long truncado = 0;

    // Para cada byte a extraer
    for (int i = 0; i < 4; ++i) {
        // Desplazamos el contenido actual 1 byte a la izquierda
        truncado <<= 8;
        // Y añadimos por el final el byte que corresponde
        truncado |= (hash[desplazamiento + i] & 0xFF);
    }

    // Ponemos el bit más significativo a cero (para quitar problemas de signo)
    truncado &= 0x7FFFFFFF;
    
    // Nos quedamos con los n digitos (decimales) menos significativos del valor truncado
    truncado %= Math.pow(10, digitos);

    // Se rellena con ceros en caso de que el número obtenido no tenga los dígitos deseados
    return String.format("%0" + digitos + "d", truncado);
  }

  /**
   * Conviete un long en un array con los bytes que lo forman
   * @param valor Valor long a convertir
   * @return Array de 8 bytes con las partes del valor long original
   */
  private byte[] longToByteArray(long valor) {

    // Inicializamos el resultado
    byte[] resultado = new byte[8];
    
    // Comenzamos por byte menos significativo
    // en cada vuelta valor se desplaza 8 bits para la derecha y se re
    for (int i = 8; i-- > 0; valor >>>= 8) {
      // Se almacena el byte menos significativo en el elemento del array que le corresponde
      resultado[i] = (byte) valor;
    }
    
    // Devolvemos el resultado
    return resultado;
 }
}
