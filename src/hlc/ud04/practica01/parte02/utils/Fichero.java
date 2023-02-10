package hlc.ud04.practica01.parte02.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Fichero {

	private String ruta;

	
	
	public Fichero(String ruta) {
		super();
		this.ruta = ruta;
	}



	public String leerArchivo() {
		String texto = "";
		try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
			while (lector.ready()) {
				texto += lector.readLine() + ";";
			}
			return texto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texto;
	}
}
