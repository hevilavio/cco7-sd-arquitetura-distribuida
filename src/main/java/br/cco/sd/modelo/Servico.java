package br.cco.sd.modelo;

public class Servico {

	private String nome;
	private String ip;
	private int porta;
	private int quantidadeUso;// para o Round-Robin

	public Servico(String nome, String ip, int porta) {
		this.nome = nome;
		this.ip = ip;
		this.porta = porta;
	}

	public String getNome() {
		return nome;
	}

	public String getIp() {
		return ip;
	}

	public int getPorta() {
		return porta;
	}

	public String getPortaString() {
		return String.valueOf(porta);
	}

	public int getQuantidadeUso() {
		return quantidadeUso;
	}

	public void incrementarUso() {
		// TODO: zerar esta variável caso ela esteja muito alta (erro de
		// overflow)!
		quantidadeUso++;
	}

	@Override
	public boolean equals(Object obj) {
		Servico s = (Servico) obj;

		if (this.nome.equals(s.getNome()) && this.ip.equals(s.getIp())
				&& this.porta == s.getPorta()
				&& this.quantidadeUso == s.getQuantidadeUso()) {

			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[")
		.append("nome: ").append(nome).append(", ")
		.append("ip: ").append(ip).append(", ")
		.append("porta: ").append(porta).append(", ")
		.append("quantidadeUso: ").append(quantidadeUso).append("]");
		
		return sb.toString();
	}
	
}
