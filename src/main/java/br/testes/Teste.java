package br.testes;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.com.nlp.models.Documento;
import br.com.nlp.models.Termo;
import br.com.nlp.utils.Utils;

public class Teste {

	public static void main(String[] args) throws IOException {
		
		String chave = "ciclo";
		
		Document doc = Jsoup.connect("https://www.agendor.com.br/blog/o-que-e-bcg/").get();
		
//		Documento documento = new Documento(doc.getElementsByTag("body").text());
		
		Elements body = doc.getElementsByTag("body");
		for (Element link : body) {
			Elements e = link.getElementsByTag("h1");
			System.out.println(e.size());
			
			Elements h2s = link.getElementsByTag("h2");
			for (Element h2 : h2s) {
				System.out.println(h2.text());
			}
			
			Elements h3s = link.getElementsByTag("h3");
			for (Element h2 : h3s) {
				System.out.println(h2.text());
			}
			
		}
	    
		
	}
	
}
