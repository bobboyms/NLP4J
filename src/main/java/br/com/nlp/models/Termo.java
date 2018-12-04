package br.com.nlp.models;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.com.nlp.utils.Utils;

public final class Termo implements Serializable {

	private static final long serialVersionUID = -1166133309989398578L;

	private long qtdGeralDoTermo = 1;
	private long qtdTermo = 1;
	private final String termo;
	private double frequenciaDoTermoTF = 0;
	private long distanciaMediaDaPalavraNoText = 0;
	private double IDF = 0;
	private double TF_IDF = 0;
	
	public Termo(String termo) {
		this.termo = termo;
	}
	
	@Override
	public int hashCode() {
		return this.termo.hashCode();
	}

	@Transient
	public Similaridade calularSimilaridade(String valor) {
		
		Client c = Client.create();
	    WebResource wr = c.resource(Utils.URL_SERVICO + "?chave="
	    		+ getTermo().trim() + "&valor=" + valor.trim().replace(" ", "%20"));
		
	    String json = wr.get(String.class);
	    Gson gson = new Gson();
	    Similaridade similaridade = gson.fromJson(json, Similaridade.class);
	    
	    return similaridade;
	}
	
	@Override
	public boolean equals(Object arg0) {

		String termo = ((Termo) arg0).getTermo();

		if (getTermo().equals(termo)) {
			return true;
		}

		return false;
	}

	public long getQtdTermo() {
		return qtdTermo;
	}

	public void inclementaQtd() {
		setQtdTermo(getQtdTermo() + 1);
	}

	public String getTermo() {
		return termo;
	}

	public double getFrequenciaDoTermoTF() {
		return frequenciaDoTermoTF;
	}

	public void setFrequenciaDoTermoTF(double frequenciaDoTermoTF) {
		this.frequenciaDoTermoTF = frequenciaDoTermoTF;
	}

	public double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        
        return result / doc.size();
    }

    /**
     * @param docs list of list of strings represents the dataset
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        
        System.out.println("size : " + docs.size() + " " + n);
        
        return Math.log(docs.size() / n);
    }

    /**
     * @param doc  a text document
     * @param docs all documents
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        
    	System.out.println("IDF : " + idf(docs, term));
    	
    	return tf(doc, term) * idf(docs, term);

    }

    public static void main(String[] args) {
    	

    }

	public long getDistanciaMediaDaPalavraNoText() {
		return distanciaMediaDaPalavraNoText;
	}

	public void setDistanciaMediaDaPalavraNoText(long distanciaMediaDaPalavraNoText) {
		this.distanciaMediaDaPalavraNoText = distanciaMediaDaPalavraNoText;
	}

	public void setQtdTermo(long qtdTermo) {
		this.qtdTermo = qtdTermo;
	}

	public double getIDF() {
		return IDF;
	}

	public void setIDF(double iDF) {
		IDF = iDF;
	}

	public double getTF_IDF() {
		return TF_IDF;
	}

	public void setTF_IDF(double tF_IDF) {
		TF_IDF = tF_IDF;
	}

	public long getQtdGeralDoTermo() {
		return qtdGeralDoTermo;
	}

	public void setQtdGeralDoTermo(long qtdGeralDoTermo) {
		this.qtdGeralDoTermo = qtdGeralDoTermo;
	}

}
