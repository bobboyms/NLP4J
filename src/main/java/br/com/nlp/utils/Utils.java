package br.com.nlp.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.nlp.models.Similaridade;
import br.com.nlp.models.Termo;

public class Utils {

	public static String URL_SERVICO_VARIOS = "http://127.0.0.1:5000/calcular_similaridade/varios/";
	public static String URL_SERVICO = "http://127.0.0.1:5000/calcular_similaridade/";

	public static Map<Termo, Double> tratarValorDeRetornoDoServiço(List<Similaridade> similaridades,
			List<Termo> termos) {

		Map<Termo, Double> mapa = new HashMap<>();
		for (Similaridade similaridade : similaridades) {

			for (Termo termo : termos) {

				if (similaridade.getValor().equals(termo.getTermo())) {
					mapa.put(termo, similaridade.getSimilaridade());
				}

			}
		}

		final Map<Termo, Double> items = Utils.sortByValueTermoDouble(mapa);

		return items;

	}

	public static Map<String, Double> sortByValueDouble(final Map<String, Double> wordCounts) {
		return wordCounts.entrySet().stream().sorted((Map.Entry.<String, Double>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	public static Map<Termo, Double> sortByValueTermoDouble(final Map<Termo, Double> wordCounts) {
		return wordCounts.entrySet().stream().sorted((Map.Entry.<Termo, Double>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	public static Map<String, Long> sortByValueLong(final Map<String, Long> wordCounts) {
		return wordCounts.entrySet().stream().sorted((Map.Entry.<String, Long>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	public static double tf(List<Termo> doc, Termo term) {
		double result = 0;
		for (Termo word : doc) {
			if (term.getTermo().equalsIgnoreCase(word.getTermo()))
				result++;
		}
		return result / doc.size();
	}

	public static void setTF(List<Termo> doc, Termo term) {
		double result = 0;
		for (Termo word : doc) {
			if (term.getTermo().equalsIgnoreCase(word.getTermo()))
				result++;
		}

		final double TF = (double) (result / doc.size());

		term.setQtdTermo((long) result);
		term.setFrequenciaDoTermoTF(TF);

	}

	public static double truncar(double d, int casas_decimais) {
		int var1 = (int) d; // Remove a parte decimal do número... 2.3777 fica 2
		double var2 = var1 * Math.pow(10, casas_decimais); // adiciona zeros..2.0 fica 200.0
		double var3 = (d - var1) * Math.pow(10,
				casas_decimais); /**
									 * Primeiro retira a parte decimal fazendo 2.3777 - 2 ..fica 0.3777, depois
									 * multiplica por 10^(casas decimais) por exemplo se o número de casas decimais
									 * que queres considerar for 2, então fica 0.3777*10^2 = 37.77
									 **/
		int var4 = (int) var3; // Remove a parte decimal da var3, ficando 37
		int var5 = (int) var2; // Só para não haver erros de precisão: 200.0 passa a 200
		int resultado = var5 + var4; // O resultado será 200+37 = 237
		double resultado_final = resultado / Math.pow(10, casas_decimais); // Finalmente divide-se o resultado pelo
																			// número de casas decimais, 237/100 = 2.37
		return resultado_final; // Retorna o resultado_final :P
	}

}
