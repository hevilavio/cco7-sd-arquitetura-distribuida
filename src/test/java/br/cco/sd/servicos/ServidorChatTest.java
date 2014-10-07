package br.cco.sd.servicos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import br.cco.sd.chat.ServidorChat;
import br.cco.sd.modelo.Servico;

public class ServidorChatTest {

	@Test
	public void possoCadastrarServicoEmSn() throws UnknownHostException {
		String nome = "CHAT";
		String ip = InetAddress.getLocalHost().getHostAddress();
		int porta = 8081;		
		
		Servico servico = new Servico(nome, ip, porta);
		
		ServidorNomesConnector mockSnConnector = mock(ServidorNomesConnector.class);
		when(mockSnConnector.cadastrarServico(servico)).thenReturn("OK");
		
		new ServidorChat(mockSnConnector, 8081);

		verify(mockSnConnector).cadastrarServico(servico);
	}
}
