package br.com.nlp.models;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.com.nlp.utils.Utils;

public class Corpus {

	public static Set<Termo> termosUnicosDaBase = new LinkedHashSet<Termo>();

	private Set<Documento> documentos = new HashSet<>();

	public double idf(Set<Documento> documentos, Termo term) {
		double n = 0;
		for (Documento doc : documentos) {
			for (Termo word : doc.getTermos()) {
				if (term.getTermo().equalsIgnoreCase(word.getTermo())) {
					n++;
					break;
				}
			}
		}

		return Math.log(documentos.size() / n);
	}

	public Map<Termo, Double> getFrequenciaComMaiorSimilaridade(String chave) {

		String tmp = "";
		for (Termo termo : termosUnicosDaBase) {
			tmp += termo.getTermo() + ",";
		}

		Client c = Client.create();
		WebResource wr = c.resource(
				Utils.URL_SERVICO_VARIOS + "?chave=" + chave.trim() + "&valor=" + tmp);

		String json = wr.get(String.class);

		Gson gson = new Gson();

		Type listType = new TypeToken<ArrayList<Similaridade>>() {}.getType();
		List<Similaridade> similaridades = new Gson().fromJson(json, listType);
		
		return Utils.tratarValorDeRetornoDoServiço(similaridades, new ArrayList(termosUnicosDaBase));
		
	}

	public Double getDensidadeMediaDaPalavraNoCorpus(String palavra) {
		
		double valorTemp = 0.0;
		for (Documento documento : getDocumentos()) {
			valorTemp += documento.getDensidadeMediaDaPalavraNoText(palavra);
		}
		
		return (valorTemp / getDocumentos().size());
		
	}
	
	public long getQuantidadeMediaDaPalavraNoCorpus() {
		
		long valorTemp = 0;
		for (Documento documento : getDocumentos()) {
			valorTemp += documento.getTermos().size();
		}
		
		return (valorTemp / getDocumentos().size());
		
	}
	
	public Double getDistanciaMediaDaPalavraNoCorpus(String palavra) {
		
		double valorTemp = 0.0;
		for (Documento documento : getDocumentos()) {
			valorTemp += documento.getDistanciaMediaDaPalavraNoText(palavra);
		}
		
		return (valorTemp / getDocumentos().size());
		
	}
	
	public Corpus adicionarDocumento(Documento documento, boolean atualizaTF_IDF) {
		getDocumentos().add(documento);

		if (atualizaTF_IDF) {
			atualizarTF_IDF();
		}
		
		return this;

	}

	public Corpus adicionarDocumento(Documento documento) {
		getDocumentos().add(documento);
		return this;
	}

	public Corpus atualizarTF_IDF() {
		for (Termo termoUnico : termosUnicosDaBase) {

			double idf = idf(getDocumentos(), termoUnico);

			termoUnico.setIDF(idf);
			termoUnico.setTF_IDF(termoUnico.getFrequenciaDoTermoTF() * idf);

			for (Documento documento : getDocumentos()) {

				for (Termo termoDoc : documento.getTermos()) {

					if (termoUnico.equals(termoDoc)) {
						termoDoc.setIDF(termoUnico.getIDF());
						termoDoc.setTF_IDF(termoUnico.getTF_IDF());
					}

				}

			}
		}
		
		return this;

//		for (Termo termo : termosUnicosDaBase) {
//			System.out.println("IDF : " + termo.getIDF() + " TF-IDF : " + termo.getTF_IDF() + " termo : " + termo.getTermo());
//		}
	}

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

}
