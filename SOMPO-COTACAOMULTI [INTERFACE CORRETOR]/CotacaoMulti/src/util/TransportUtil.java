package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class TransportUtil implements Serializable {

	private static final long serialVersionUID = -1075998559141555715L;

	static int BUFFER_SIZE = 4096;
	public static final String DIR_DESTINO = "/u/dadosapp/cotacao";
	public static final String DIR_TEMPORARIO = "/u/dadosapp/cotacao/tmp";
	//public static String USE_MARITIMA = "\\\\10.1.1.61\\data1\\ftp\\siscota";
	public static String USE_MARITIMA = "\\\\Wdc10900fslu122\\dados"; 

	public static int copy(File in, File out) throws IOException {		
		//System.out.println("\n\nin: "+in+"\n\n");
		//System.out.println("out: "+out+"\n\n");		
		return copy(new BufferedInputStream(new FileInputStream(in)),
				new BufferedOutputStream(new FileOutputStream(out)));				
	}
	
	private static int copy(InputStream in, OutputStream out) throws IOException {		
		try { 
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		} finally {
			in.close();
			out.close();
		}
	}

}
