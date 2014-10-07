package br.cco.sd.servicos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OperadorServidorNomes {

	public static String cadastrarServico(Socket s, String nome) {
		DataOutputStream output;
		String resposta = null;
		try {
			output = new DataOutputStream(s.getOutputStream());
			DataInputStream input = new DataInputStream(s.getInputStream());

			output.writeUTF("0_junit_127.0.0.1_9999");
			resposta = input.readUTF();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resposta;
	}
}
