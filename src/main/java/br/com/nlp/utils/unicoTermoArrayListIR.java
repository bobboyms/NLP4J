package br.com.nlp.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.nlp.models.Termo;


public class unicoTermoArrayListIR<E> extends ArrayList<E> {
	
	private long sizeGeral = 0;
	
	public long getSizeGeral() {
		return sizeGeral;
	}
	
	private void incSizeGeral() {
		this.sizeGeral++;
	}
	
	@Override
	public boolean add(E arg0) {
	
		incSizeGeral();
		
		@SuppressWarnings("unchecked")
		List<Termo> es = (List<Termo>)this;
		
		for (Termo e : es) {
			
			if (e.equals(arg0)) {
				
				e.inclementaQtd();
				
				continue;
			}
			
		}
		
		return super.add(arg0);
	}


	
	public static void main(String[] args) {
//		
//		StringBuilder builder = new StringBuilder("Fora do prato, parece outra pessoa pessoa. Quer dizer, outro alimento. A couve é a base desta receita de suco verde que, além de ajudar a detonar a gordura extra, refresca e faz bem à saúde");
//		
//		List<Termo> termos = new Tokenizacao().tokenizadorTermo(builder);
//		
//		System.out.println("\n****************************\n");
//		
//		for (Termo ls : termos) {
//			System.out.println("TERMO : " + ls.getTermo() + " " + ls.getQtdTermo());
//		}
		
		
//		
//		List<Termo> lista = new unicoTermoArrayListIR<>();
//		
//		lista.add(new Termo("casa"));
//		lista.add(new Termo("pinto"));
//		lista.add(new Termo("casa"));
//		lista.add(new Termo("veia"));
//		lista.add(new Termo("pinto"));
//		lista.add(new Termo("pinto"));
//		lista.add(new Termo("pinto"));
//		lista.add(new Termo("casa"));
//		
		
	}


	
}
