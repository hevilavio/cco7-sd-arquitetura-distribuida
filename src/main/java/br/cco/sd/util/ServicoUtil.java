package br.cco.sd.util;

import java.util.ArrayList;
import java.util.List;

import br.cco.sd.modelo.Servico;

public class ServicoUtil {
	
	public List<Servico> agruparPorNome(List<Servico> servicos, String nome){
		List<Servico> agrupamento = new ArrayList<>();
		for (Servico servico : servicos) {
			if(servico.getNome().equals(nome)){
				agrupamento.add(servico);
			}
		}
		return agrupamento;
	}
	
	/**
	 * Para a implementação de Round-Robin, precisamos <br/>
	 * escolher um serviço que foi menos usado;
	 * */
	public Servico getServicoComMenorUso(List<Servico> servicos){
		int menor = servicos.get(0).getQuantidadeUso();
		Servico s = null;
		for (Servico servico : servicos) {
			if(servico.getQuantidadeUso() < menor){
				menor = servico.getQuantidadeUso();
				s = servico;
			}
		}
		return s;
	}
}
