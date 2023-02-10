package hlc.ud04.practica01.parte02.utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Fichero {
	
	
	public String leerArchivo() {
		String ruta = "archivo/permisos.txt";
		String texto="";
		try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
			while (lector.ready()) {
				texto = lector.readLine() + "\n";	
			}
			return texto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texto;
	}
}
