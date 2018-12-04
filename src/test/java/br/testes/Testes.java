package br.testes;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import br.com.nlp.models.Documento;
import br.com.nlp.models.Termo;

public class Testes {

	@Test
	public void test() {
		
		String str = "jose antonio a a a thiago maria  silva thiago. Queria ser um garoto legal como o Thiago";

		Documento documento = new Documento(str);
		
		Assert.assertEquals("Erro de similaridade",1.0, documento.getTermo("thiago").calularSimilaridade("thiago").getSimilaridade(), 0.00001);
		Assert.assertEquals("Erro no total de palavras", 17, documento.getTotalPalavras(), 0.00001);
		Assert.assertEquals("Erro na quantidade do termo", 3, documento.getTermo("thiago").getQtdTermo(), 0.00001);
		Assert.assertEquals("Erro na quantidade do termo", 1, documento.getTermo("queria").getQtdTermo(), 0.00001);
		Assert.assertEquals("Erro na distancia média", 5, documento.getDistanciaMediaDaPalavraNoText("thiago maria"), 0.00001);
//		Assert.assertEquals("Erro na frequencia do termo", 0.14285714285714285, documento.getTermo("thiago").getFrequenciaDoTermoTF(), 0.00000000000000000001);
		
		System.out.println("xxx" + documento.getTermo("thiago").getFrequenciaDoTermoTF());
		
		System.out.println("FREQUENCIA DO TERMO " + documento.getTermosRemovendoStopWords().size());
//		System.out.println("densidade " + documento.getDensidadeMediaDaPalavraNoText("antonio"));
//		System.out.println("densidade " + documento.getTermo("antonio").getFrequenciaDoTermoTF());
		
//		for (Termo termo : documento.getTermosRemovendoStopWords()) {
//			System.out.println(termo.getTermo() + " - " + str.toLowerCase().contains(termo.getTermo()));
//		}
//		
		
	}

}
