package hlc.ud04.practica01.autenticadortotp;

import hlc.auth.otp.GeneradorHOTP;

public class AutenticadorTOTPApp {

  public static void main(String[] args) {
    if (args.length > 0) {
      String secreto = args[0];
      GeneradorHOTP generador = new GeneradorHOTP();
      long contador = System.currentTimeMillis() / 1000;
      long validez = 30 - (contador % 30);
      System.out.println("El PIN actual es: " + generador.genera(secreto, contador / 30) + ". Validez: " + validez + " segundos");
    } else {
      System.err.println("Error. Debe proporcionar el secreto como un número hexadecimal de 16 dígitos. Saliendo.");
    }
  }

}
