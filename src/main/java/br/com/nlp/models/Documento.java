package br.com.nlp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.com.nlp.Tokenizer;
import br.com.nlp.utils.StopWords;
import br.com.nlp.utils.Utils;

public class Documento {

	public static void main(String[] args) {
//		Termo termo = new Termo("1");		
//		Termo termo1 = new Termo("5");	
//		Termo termo2 = new Termo("3");
//		Termo termo3 = new Termo("4");
//		
//		Set<Termo> termos = new LinkedHashSet()<>;
//		termos.add(termo);
//		termos.add(termo1);
//		termos.add(termo2);
//		termos.add(termo3);
//		
//		termos.forEach((k)->System.out.println("Item : " + k.getTermo()));
//		
//		
//		System.out.println(termos.size());

	}

	private static Tokenizer tokenizador = new Tokenizer();
	private List<Termo> termos;
	private String texto;
	private Set<String> sentencas;
	
	private String url;
	private String tituloH1;
	private Set<String> subtitulos;
	
	public Documento(String texto, String url, Document doc) {

		this.texto = texto;
		this.url = url;

		if (termos == null) {
			termos = new ArrayList<Termo>();
			tokeNizacao(texto);
			gerarSentencas(texto);
			analisarDadosDOM(doc);
		}

	}
	
	private void analisarDadosDOM(Document doc) {
		
		if (getSubtitulos() != null) {
			return;
		}
		
		setSubtitulos(new HashSet<>());
		
		Elements body = doc.getElementsByTag("body");
		for (Element link : body) {
			Elements h1 = link.getElementsByTag("h1");
			
			this.tituloH1 = h1.text();
			
			Elements h2s = link.getElementsByTag("h2");
			for (Element h2 : h2s) {
				getSubtitulos().add(h2.text());
			}
			
			Elements h3s = link.getElementsByTag("h3");
			for (Element h3 : h3s) {
				getSubtitulos().add(h3.text());
			}
		}
	}
	
	public Documento(String texto) {

		this.texto = texto;

		if (termos == null) {
			termos = new ArrayList<Termo>();
			tokeNizacao(texto);
			gerarSentencas(texto);
		}

	}

	private void gerarSentencas(String texto) {

		setSentencas(new HashSet<String>());
		String[] sentencas = texto.split("\\.");

		for (String sentenca : sentencas) {

			List<Termo> termos = tokenizador.tokenizadorTermo(new StringBuilder(sentenca));

			String tmp = "";

			for (Termo termo : termos) {
				tmp += termo.getTermo() + " ";
			}

			getSentencas().add(tmp.trim());
		}

	}
	
	public Map<Termo, Double> getFrequenciaComMaiorSimilaridade(String chave) throws UnsupportedEncodingException {
		
		Set<Termo> palavraUnica = new HashSet<Termo>();
		
		for (Termo termo : getTermos()) {
			palavraUnica.add(termo);
		}
		
		String tmp = "";
		for (Termo termo : palavraUnica) {
			if (termo.getTermo().length() >= 3 && termo.getQtdTermo() >= 3) {
				tmp += termo.getTermo() + ",";
			}
		}
		
		String url = Utils.URL_SERVICO_VARIOS + "?chave="
	    		+ URLEncoder.encode(chave.trim(), "UTF-8") + "&valor=" + tmp;
		
		Client c = Client.create();
	    WebResource wr = c.resource(url);
		
	    System.out.println(url);
	    
	    String json = wr.get(String.class);
	    
	    Gson gson = new Gson();
	    
	    Type listType = new TypeToken<ArrayList<Similaridade>>(){}.getType();
	    List<Similaridade> similaridades = new Gson().fromJson(json, listType);
	    
	    return Utils.tratarValorDeRetornoDoServiço(similaridades, getTermos());
		
	}

	public Long getDistanciaMediaDaPalavraNoText(String palavra) {

		List<Long> distancias = new ArrayList<Long>();
		List<Termo> chaves = tokenizador.tokenizadorTermo(new StringBuilder(palavra));

		getTermos().add(new Termo(""));

		long contador = 0l;
		long i = 0l;

		while (getTermos().size() != i) {

			long j = 0l;
			boolean achou = false;

			while (chaves.size() != j) {
				if (getTermos().get((int) (i + j)).equals(chaves.get((int) j))) {
					j = j + 1;
					achou = true;
				} else {
					achou = false;
					break;
				}
			}

			if (achou) {
				distancias.add(contador);
				contador = 0;
			} else {
				contador = contador + 1;
			}

			i = i + 1;
		}

		getTermos().remove(new Termo(""));

		if (distancias.size() > 0) {

			long soma = 0l;
			for (Long distancia : distancias) {
				soma = soma + distancia;
			}

//			return (long) Math.round(((getTermos().size() - distancias.size()) / distancias.size()));
			
			return (long) Math.round(soma / distancias.size());
		}
		
	
		return 0l;
	}

	public float getDensidadeMediaDaPalavraNoText(String palavra) {

		List<Long> distancias = new ArrayList<Long>();
		List<Termo> chaves = tokenizador.tokenizadorTermo(new StringBuilder(palavra));

		getTermos().add(new Termo(""));

		long contador = 0l;
		long i = 0l;

		while (getTermos().size() != i) {

			long j = 0l;
			boolean achou = false;

			while (chaves.size() != j) {
				if (getTermos().get((int) (i + j)).equals(chaves.get((int) j))) {
					j = j + 1;
					achou = true;
				} else {
					achou = false;
					break;
				}
			}

			if (achou) {
				distancias.add(contador);
				contador = 0;
			} else {
				contador = contador + 1;
			}

			i = i + 1;
		}

		getTermos().remove(new Termo(""));

		if (distancias.size() > 0) {
			
			float distanc = (float)distancias.size();
			float terms = (float)getTermos().size();
			
			return (distanc / terms);
		}

		return 0f;
	}

	public Termo getTermo(String termo) {

		Termo key = new Termo(Tokenizer.textNormalizer(termo.toLowerCase().trim()));

		for (Termo token : termos) {
			if (token.equals(key)) {
				return token;
			}
		}
		
		throw new Error("Termo " + termo + " não existe na base");

	}

	public long getTotalPalavras() {
		return getTermos().size();
	}

	private void tokeNizacao(String texto) {

		setTermos(tokenizador.tokenizadorTermo(new StringBuilder(texto)));

		for (Termo termo : getTermos()) {
			
			for (Termo termoUnico : Corpus.termosUnicosDaBase) {
				
				if (termo.equals(termoUnico)) {
					termoUnico.setQtdGeralDoTermo(termoUnico.getQtdGeralDoTermo() + 1);
				}
				
			}
			
			Corpus.termosUnicosDaBase.add(termo);
		}
	}

	@Override
	public String toString() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public boolean equals(Object obj) {

		if (((Documento) obj).toString().equals(texto)) {
			return true;
		}

		return false;

	}

	public List<Termo> getTermos() {
		return termos;
	}

	/**
	 * Retorna os termos(palavras) que contem no documento, removendo as palavras
	 * que são stopWords
	 * 
	 * @return
	 */
	public Set<Termo> getTermosRemovendoStopWords() {

		Set<Termo> tmp = new HashSet<Termo>();

		for (Termo termo : getTermos()) {
			if (!StopWords.getInstance().isStopWord(termo.getTermo()) && termo.getTermo().trim().length() > 1) {
				tmp.add(termo);
			}
		}

		return tmp;

	}

	public Long getTotalDeTermosRemovendoStopWords() {
		return (long) getTermosRemovendoStopWords().size();
	}

	public void setTermos(List<Termo> tokens) {
		this.termos = tokens;
	}

	public Set<String> getSentencas() {
		return sentencas;
	}

	public void setSentencas(Set<String> sentencas) {
		this.sentencas = sentencas;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTituloH1() {
		return tituloH1;
	}

	public void setTituloH1(String tituloH1) {
		this.tituloH1 = tituloH1;
	}

	public Set<String> getSubtitulos() {
		return subtitulos;
	}

	public void setSubtitulos(Set<String> subtitulos) {
		this.subtitulos = subtitulos;
	}

}
