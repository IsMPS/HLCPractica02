package hlc.ud04.practica01.parte02;

import hlc.ud04.appsec.core.Clientes;
import hlc.ud04.appsec.core.GestorPersistencia;
import hlc.ud04.appsec.interfaz.Interfaz;
import hlc.ud04.appsec.interfaz.consola.InterfazConsola;
import hlc.ud04.appsec.persistencia.GestorPersistenciaSqlite;
import hlc.ud04.appsec.seguridad.core.SistemaSeguridad;
import hlc.ud04.practica01.parte01.autenticador.AutenticadorPasswordSqlite;
import hlc.ud04.practica01.parte01.controlacceso.ControlAccesoFichero;
import hlc.ud04.practica01.parte01.controlacceso.ControlAccesoSimple;
import hlc.ud04.practica01.parte01.seguridad.SistemaSeguridadPasswordSqlite;
import hlc.ud04.practica01.parte02.autenticador.AutenticadorTOTPSqlite;
import hlc.ud04.practica01.parte02.seguridad.SistemaSeguridadTOTP;

public class MainApp {

  // Ruta a la base de datos en disco
  private static final String DATABASE_PATH = "db/base.db";
  
  public static void main(String[] args) {
    // Creamos el gestor de persistencia SQLite
    GestorPersistencia gestor = new GestorPersistenciaSqlite(DATABASE_PATH);
    // Y lo inyectamos en el core
    Clientes clientes = new Clientes(gestor);
    
    // Usamos nuestro sistema de seguridad
    SistemaSeguridad sistemaSeguridad = new SistemaSeguridadTOTP(new AutenticadorTOTPSqlite(DATABASE_PATH), new ControlAccesoFichero());
    
    // Creamos interfaz de usuario de tipo consola
    Interfaz interfaz = new InterfazConsola(clientes, sistemaSeguridad);
    // Lanzamos la interfaz de usuario
    interfaz.run();
  }

}
