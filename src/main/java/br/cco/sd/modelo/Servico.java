package br.cco.sd.modelo;

public class Servico {
	
	private String nome;
	private String ip;
	private int porta;
	private int quantidadeUso;// para o Round-Robin
	

	public Servico(String nome, String ip, int porta){
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
	public void incrementarUso(){
		//TODO: zerar esta variável caso ela esteja muito alta (erro de overflow)!
		quantidadeUso++;
	}

	
	
}
