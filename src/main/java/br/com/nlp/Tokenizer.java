package br.com.nlp;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.nlp.models.Termo;
import br.com.nlp.utils.Utils;
import br.com.nlp.utils.unicoTermoArrayListIR;

public class Tokenizer {

	// LISTA DE STOPWORDS
	public static void main(String[] args) {

	}

	/**
	 * 
	 * Tem a função de retirar acentos e caracter especiais dos textos.
	 * 
	 * 
	 * @param texto
	 * @return
	 */
	public static String textNormalizer(String texto) {
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		return texto.replaceAll("[^\\p{ASCII}]", "");
	}

	private boolean isNumero(String ch) {

		try {
			new Long(ch);
		} catch (Exception ex) {
			return false;
		}

		return true;

	}

	/**
	 * 
	 * Tem a função de transformar as palavras em letras minusculas remover
	 * pontos, vergulas e acentos;
	 * 
	 * @param stringBuilder
	 * @return
	 */
	public List<Termo> tokenizadorTermo(StringBuilder stringBuilder) {

		String[] texto = stringBuilder.toString().split(" "); // ".split(" ");

		List<Termo> lisTokens = new ArrayList<>();

		for (String palavra : texto) {

			if (palavra.trim().length() == 0) {
				continue;
			}

			String[] letras = palavra.trim().split("");

			String token = "";

			for (String letra : letras) {

				// transforma a letra em minusculo
				letra = letra.toLowerCase().trim();

				if (letra.equals(",") || letra.equals(".") || letra.equals("/") || letra.equals("\\")
						|| letra.equals("-") || letra.equals("*") || letra.equals(":") || letra.equals("]")
						|| letra.equals("[") || letra.equals("{") || letra.equals("}") || letra.equals("=")
						|| letra.equals(")") || letra.equals("(") || letra.equals("%") || letra.equals("#")
						|| letra.equals("|") || letra.equals("!") || letra.equals("?") || letra.equals("'")
						|| letra.equals("\"") || letra.equals(";") || isNumero(letra)) {
					continue;
				}

				token += letra;
			}

			//
			// VERIFICA SE NÃO É UMA STOPWORDS
			//
			token = textNormalizer(token);

//			System.out.println("PALAVRA : " + token);

			lisTokens.add(new Termo(token));

		}

		/**
		 * 
		 * Calcula a frequencia do termo no documento TF
		 * 
		 */

		for (Termo termo : lisTokens) {

			if (termo.getFrequenciaDoTermoTF() == 0) {
				Utils.setTF(lisTokens, termo);
//				termo.setFrequenciaDoTermoTF(TF);
			}
		}

		/**
		 * Adiciona a distancia media da palavra no texto
		 */
		for (Termo termo : lisTokens) {

			termo.setDistanciaMediaDaPalavraNoText(getDistanciaMediaDaPalavraNoText(lisTokens, termo));

		}

		return lisTokens;

	}

	private Long getDistanciaMediaDaPalavraNoText(List<Termo> lisTokens, Termo palavra) {

		List<Long> distancias = new ArrayList<Long>();
		List<Termo> chaves = new ArrayList<Termo>();
		chaves.add(palavra);

		long contador = 0l;
		long i = 0l;

		while (lisTokens.size() != i) {

			long j = 0l;
			boolean achou = false;

			while (chaves.size() != j) {
				if (lisTokens.get((int) (i + j)).equals(chaves.get((int) j))) {
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

		if (distancias.size() > 0) {

			long soma = 0l;
			for (Long distancia : distancias) {
				soma = soma + distancia;
			}

			return (long) Math.round(soma / distancias.size());
		}

		return 0l;

	}

}
