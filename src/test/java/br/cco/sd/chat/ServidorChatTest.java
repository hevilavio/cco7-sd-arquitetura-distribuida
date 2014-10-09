package br.cco.sd.chat;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.cco.sd.modelo.Servico;
import br.cco.sd.servicos.ServidorNomesConnector;

public class ServidorChatTest {
	private SimuladorServidorDeNomes simuladorSn;
	private Servico servicoTesteLocal;

	@Before
	public void init() {
		simuladorSn = new SimuladorServidorDeNomes();
		simuladorSn.iniciar(8890);

		servicoTesteLocal = new Servico("CHAT", "127.0.0.1", 8890);
	}

	@After
	public void goAway() {
		simuladorSn.finalizar();
	}

	@Test
	public void possoMeCadastrarQuandoSouInstanciado() {

		ServidorNomesConnector connector = new ServidorNomesConnector(servicoTesteLocal, null);
		new ServidorChat(connector, 8891);

		assertEquals(1, simuladorSn.getServicos().size());
	}
	
	// teste: iniciar um ServidorChat, conectar um cliente, verificar 
	// comprimento da lista de Clientes

	
	
}
