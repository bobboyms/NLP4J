package br.com.nlp.models;

public class Similaridade {
	
	private String chave;
	private String valor;
	private double similaridade;
	
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public double getSimilaridade() {
		return similaridade;
	}
	public void setSimilaridade(double similaridade) {
		this.similaridade = similaridade;
	}
}
