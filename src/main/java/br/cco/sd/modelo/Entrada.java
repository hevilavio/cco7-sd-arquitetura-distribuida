package br.cco.sd.modelo;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Apenas um Encapsulamento de DataInputStream para tornar o c�digo mais test�vel.
 * */
public class Entrada {

	private DataInputStream dis;
	
	public Entrada(DataInputStream dis) {
		this.dis = dis;
	}

	public String readUTF() throws IOException{
		return dis.readUTF();
	}

}
