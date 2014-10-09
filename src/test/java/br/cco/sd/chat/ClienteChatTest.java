package br.cco.sd.chat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.cco.sd.modelo.Servico;

public class ClienteChatTest {
	
	private SimuladorServidorDeNomes simuladorSn;
	@SuppressWarnings("unused")
	private Servico servicoTesteLocal;
	
	
	@Before
	public void init(){
		simuladorSn = new SimuladorServidorDeNomes();
		simuladorSn.iniciar(8889);
		
		servicoTesteLocal = new Servico("CHAT", "127.0.0.1", 8889);
	}
	
	@After
	public void goAway(){
		simuladorSn.finalizar();
	}
	
	@Test
	public void possoEnviarMensagensParaServidorChat() throws IOException {
		//TODO: Pensar em uma maneira elegante de fazer teste teste.
		
//		ServidorNomesConnector connector = mock(ServidorNomesConnector.class);
//		when(connector.requisitarServico("CHAT")).thenReturn(servicoTesteLocal);
//		
//		ClienteChat cliente = new ClienteChat(connector);
//		
		
	}

}
