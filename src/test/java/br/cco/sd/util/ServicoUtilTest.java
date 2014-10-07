package br.cco.sd.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.cco.sd.modelo.Servico;

public class ServicoUtilTest {

	@Test
	public void possoAgruparListaPorNomeDeServico() {
		List<Servico> servicos = GeradorServico.gerarServicosPorNome("SOMA", "SOMA", "SOMA", "MULT", "MULT");
		ServicoUtil util = new ServicoUtil();
		
		List<Servico> servicosSoma = util.agruparPorNome(servicos, "SOMA");
		List<Servico> servicosMult = util.agruparPorNome(servicos, "MULT");
		List<Servico> servicosX = util.agruparPorNome(servicos, "X");
		
		assertEquals(3, servicosSoma.size());
		assertEquals(2, servicosMult.size());
		assertEquals(0, servicosX.size());
	}

	@Test
	public void possoEscolherRoundRobinCorretamenteListaCrescente(){
		List<Servico> servicos = GeradorServico.gerarServicosPorNome("SOMA", "SOMA", "SOMA");
		servicos.get(0).incrementarUso();
		servicos.get(1).incrementarUso();
		
		// servico ñ incrementado
		Servico s2 = servicos.get(2);
		Servico sEscolhido = new ServicoUtil().getServicoComMenorUso(servicos);
		
		assertEquals(s2, sEscolhido);
	}
	@Test
	public void possoEscolherRoundRobinCorretamenteListaDecrescente(){
		List<Servico> servicos = GeradorServico.gerarServicosPorNome("SOMA", "SOMA", "SOMA");
		servicos.get(1).incrementarUso();
		servicos.get(2).incrementarUso();
		
		// servico ñ incrementado
		Servico s0 = servicos.get(0);
		Servico sEscolhido = new ServicoUtil().getServicoComMenorUso(servicos);
		
		assertEquals(s0, sEscolhido);
	}
}


class GeradorServico{
	
	public static List<Servico> gerarServicosPorNome(String... nomes){
		List<Servico> servicos = new ArrayList<>();
		
		int counter = 0;
		for(String nome : nomes){
			servicos.add(new Servico(nome, "ip" + counter, counter));

			counter++;
		}
		return servicos;
	}
}