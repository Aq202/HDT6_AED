package HDT6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileController {
	public static final String PATH = System.getProperty("user.dir") + "\\ListadoProducto.txt";

	/**
	 * Metodo que se encarga de obtener todas las filas del archivo datos.txt
	 * @return String[]. Array con cada una de las filas de texto por casilla.
	 * @throws IOException
	 */
	public static String[] readFile() throws IOException {
		
		File doc = new File(PATH);

		  BufferedReader obj = new BufferedReader(new FileReader(doc));
		  ArrayList<String> linesList = new ArrayList<String>();

		  //leer y almacenar las filas del archivo de texto
		  String line;
		  while ((line = obj.readLine()) != null) {
		    linesList.add(line);
		  }
		  
		  obj.close();
		  
		  return linesList.toArray(new String[linesList.size()]); //convertir lista a array
	}
	
	/**
	 * Permite crear(si no existe) el archivo de almacenamiento y sobreescribir su contenido.
	 * @param text. Contenido del archivo
	 * @throws IOException
	 */
	public static void writeFile(String text) throws IOException {
       
		File file = new File(PATH);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        //añadir nuevo registro
        pw.println(text);

        pw.close();   

    }
}
