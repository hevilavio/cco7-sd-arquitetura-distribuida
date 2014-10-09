package br.cco.sd.chat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;

import br.cco.sd.modelo.Entrada;

public class DistribuidorRunnableTest {

	@Test
	public void possoResponderAEventoQuandoChegarMensagem() throws IOException {
		
		Entrada entrada = mock(Entrada.class);
		Evento evento = mock(Evento.class);

		when(entrada.readUTF()).thenReturn("oi", "bom", "dia", "/quit");
		
		DistribuidorRunnable distribuidor = new DistribuidorRunnable(entrada, evento);
		distribuidor.run();// run() mesmo!
		
		verify(evento).quandoChegarMensagem("oi");
		verify(evento).quandoChegarMensagem("bom");
		verify(evento).quandoChegarMensagem("dia");
		
	}

}
