package br.cco.sd.servicos;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServidorNomesTest {
	private Thread sNomesThread;
	private ServidorNomes sNomes;
	
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
			Socket s = new Socket("127.0.0.1", 8888);
			String resposta = OperadorServidorNomes.cadastrarServico(s, "0_junit_127.0.0.1_9999");
			
			assertEquals("OK", resposta);
			assertEquals(1, sNomes.getServicos().size());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void servidorDeNomesPodeRecuperarServico(){
		try {
			Socket s = new Socket("127.0.0.1", 8888);
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			DataInputStream input= new DataInputStream(s.getInputStream());
			
			output.writeUTF("0_junit_127.0.0.1_9999");
			String resposta = input.readUTF();
			s.close();
			
			assertEquals("OK", resposta);
			assertEquals(1, sNomes.getServicos().size());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void escolhaDoServicoObedeceRoundRobin(){
		
	}
}
