package br.com.nlp.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class StopWords {

	private static String STOPWORDS = "da, com, de, num, do, ja, ao, as, dos, se, por, sem, mas"
			+ "a, agora, ainda, alguém, algum, alguma, algumas, alguns, ampla, amplas, amplo, amplos, ante, antes, ao, aos,"
			+ " após, aquela, aquelas, aquele, aqueles, aquilo, as, até, através, cada, coisa, coisas,"
			+ " com, como, contra, contudo, da, daquele, daqueles, das, de, dela, delas, dele, deles, "
			+ "depois, dessa, dessas, desse, desses, desta, destas, deste, deste, destes, deve, devem, "
			+ "devendo, dever, deverá, deverão, deveria, deveriam, devia, deviam, disse, disso, disto, "
			+ "dito, diz, dizem, do, dos, e, é, ela, elas, ele, eles, em, enquanto, entre, era, essa, "
			+ "essas, esse, esses, esta, está, estamos, estão, estas, estava, estavam, estávamos, este, "
			+ "estes, estou, eu, fazendo, fazer, feita, feitas, feito, feitos, foi, for, foram, fosse, "
			+ "fossem, grande, grandes, há, isso, isto, já, la, lá, lhe, lhes, lo, mas, me, mesma, mesmas,"
			+ " mesmo, mesmos, meu, meus, minha, minhas, muita, muitas, muito, muitos, na, não, nas, nem, "
			+ "nenhum, nessa, nessas, nesta, nestas, ninguém, no, nos, nós, nossa, nossas, nosso, nossos, "
			+ "num, numa, nunca, o, os, ou, outra, outras, outro, outros, para, pela, pelas, pelo, pelos,"
			+ " pequena, pequenas, pequeno, pequenos, per, perante, pode, pude, podendo, poder, poderia, poderiam,"
			+ " podia, podiam, pois, por, porém, porque, posso, pouca, poucas, pouco, poucos, primeiro, primeiros,"
			+ " própria, próprias, próprio, próprios, quais, qual, quando, quanto, quantos, que, quem, são, se, "
			+ "seja, sejam, sem, sempre, sendo, será, serão, seu, seus, si, sido, só, sob, sobre, sua, suas, talvez,"
			+ " também, tampouco, te, tem, tendo, tenha, ter, teu, teus, ti, tido, tinha, tinham, toda, todas, todavia,"
			+ " todo, todos, tu, tua, tuas, tudo, última, últimas, último, últimos, um, uma, umas, uns, vendo, ver, "
			+ "vez, vindo, vir, vos, vós, r$, R$, (), mais, mas";
	
	private StopWords() {}

	//implementa o padrÃ£o de projeto singleton
	public static StopWords getInstance() {
		
		if (stopWords == null) {
			stopWords = new StopWords();
		}
		
		return stopWords;
	}
	
	private static StopWords stopWords;
	
	public boolean isStopWord(String palavra) {
		
		StringBuilder builder = new StringBuilder(STOPWORDS);
		
		List<String> stops = getInstance().tokenizador(builder);
		
		for (String stop : stops) {
			if (stop.equals(palavra)) {
				return true;
			}
		}
		
		return false;
		
	}

	public String textNormalizer(String texto) {
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		return texto.replaceAll("[^\\p{ASCII}]", "");
	}

	public List<String> tokenizador(StringBuilder stringBuilder) {
		String[] texto = stringBuilder.toString().split(" "); // ".split(" ");

		List<String> lisTokens = new ArrayList<String>();

		for (String palavra : texto) {

			String[] letras = palavra.trim().split("");

			String token = "";

			for (String letra : letras) {

				// transforma a letra em minusculo
				letra = letra.toLowerCase();

				if (letra.equals(",") || letra.equals(".") || letra.equals("/") || letra.equals("\\")
						|| letra.equals("-") || letra.equals("*")) {
					continue;
				}

				token += letra;
			}

			lisTokens.add(textNormalizer(token));

		}

		return lisTokens;
	}


}
