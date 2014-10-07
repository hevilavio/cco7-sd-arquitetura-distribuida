package br.cco.sd.servicos;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.cco.sd.modelo.Servico;

public class ServidorNomesTest {
	private Thread sNomesThread;
	private ServidorNomes sNomes;
	private final String servicoTeste = "0_junit_127.0.0.1_9999";
	
	@Before
	public void init(){
		sNomes = new ServidorNomes();
		sNomesThread = new Thread(sNomes);
		sNomesThread.start();
	}
	
	@After
	public void goAway(){
		try {
			sNomes.parar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
	
	@Test
	public void servidorDeNomesPodeCadastrarServico() {
		try {
			Socket socket = new Socket("127.0.0.1", 8888);
			String resposta = OperadorServidorNomes.cadastrarServico(socket, servicoTeste);
			
			assertEquals("OK", resposta);
			assertEquals(1, sNomes.getServicos().size());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void servidorDeNomesPodeRecuperarServico(){
		try {
			// precisamos cadastrar um servico para tentar recuperá-lo
			servidorDeNomesPodeCadastrarServico();
			
			Socket s = new Socket("127.0.0.1", 8888);
			String nome = "junit";
			Servico servico = OperadorServidorNomes.recuperarServico(s, nome);
			s.close();
			
			assertEquals(true, servico != null);
			assertEquals("127.0.0.1", servico.getIp());
			assertEquals(9999, servico.getPorta());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class OperadorServidorNomes {
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
	
	public static Servico recuperarServico(Socket s, String nome) {
		DataOutputStream output;
		String resposta = null;
		Servico servico = null;
		try {
			output = new DataOutputStream(s.getOutputStream());
			DataInputStream input = new DataInputStream(s.getInputStream());

			output.writeUTF("1_" + nome);
			resposta = input.readUTF();
			String[] ipEPorta = resposta.split("_");
			servico = new Servico(nome, ipEPorta[0], Integer.parseInt(ipEPorta[1]));
			
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return servico;
	}
}

