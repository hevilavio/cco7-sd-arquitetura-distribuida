package br.cco.sd.chat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.Test;

public class EnviarMensagemEventoTest {

	@Test
	public void possoEnviarAMensagemDoEvento() throws IOException {
		
		DataOutputStream output = mock(DataOutputStream.class);
		Socket socket = mock(Socket.class);
		
		when(socket.getOutputStream()).thenReturn(output);
		
		new EnviarMensagemEvento(socket).quandoChegarMensagem("ola");
		
		verify(output).writeUTF("ola");
		
	}

}
