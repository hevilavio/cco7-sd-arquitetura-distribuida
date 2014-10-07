package br.cco.sd.servicos;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.cco.sd.modelo.Servico;

public class ServidorNomesConnectorTest {

	private ServidorNomes sNomes;
	private Thread sNomesThread;
	private Servico snPrimario, snSecundario;
	
	
	@Before
	public void init(){
		sNomes = new ServidorNomes();
		sNomesThread = new Thread(sNomes);
		sNomesThread.start();
		
		snPrimario = new Servico("snPrimario", "127.0.0.1", 8888);
		snSecundario = new Servico("snPrimario", "127.0.0.1", 8888);
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
	public void podeCadastrarServico() {
		ServidorNomesConnector connector = new ServidorNomesConnector(snPrimario, snSecundario);
		String resposta = connector.cadastrarServico(new Servico("junit", "192.168.1.10", 8081));
		
		assertEquals("OK", resposta);
	}
	
	@Test
	public void podeRecuperarServico() throws IOException {
		// precisamos cadastrar para recuperar um servico
		podeCadastrarServico();
		
		ServidorNomesConnector connector = new ServidorNomesConnector(snPrimario, snSecundario);
		Servico servico = connector.requisitarServico("junit");

		assertEquals("192.168.1.10", servico.getIp());
		assertEquals(8081, servico.getPorta());
	}

}
