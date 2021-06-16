package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsanalyzer.ui.UserInterface;
import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.beans.Source;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "86dcf794e270499bb21c916e440fb2f6";  //TODO add your api key

	public void process(NewsApi newsApi) {
		System.out.println("Start process");

		//TODO implement Error handling

		//TODO load the news based on the parameters

		NewsResponse newsResponse = null;
		List<Article> articles = null;
		try{
			newsResponse = newsApi.getNews();
			articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		} catch (Exception e){

			System.out.println("Error: "+e.getMessage());
			return;

		}

		Article shortestAuthorNameArticle = null;

		//TODO implement methods for analysis
		System.out.println("\n\n\n*********Analysis*********");

		Map<String, Long> nameCountMap = articles.stream().collect(Collectors.groupingBy(a -> a.getSource().getName(), Collectors.counting())); //Map pairs provider name with their number of occurrences
		try{
			shortestAuthorNameArticle = articles.stream().filter(article -> article.getAuthor() != null).min(Comparator.comparing(a -> a.getAuthor().length())).get(); //this gets the article with the shortest named author
		}catch(NoSuchElementException e){
			System.out.println("Could not find an author");
		}

		List<Article> sortedByTitleLength = articles.stream().sorted(Comparator.comparing(a -> a.getTitle().length())).collect(Collectors.toList()); // sort the titles by length
		Collections.reverse(sortedByTitleLength);

		List<Article> sortedAlphabetically = articles.stream().sorted(Comparator.comparing(Article::getTitle)).collect(Collectors.toList()); //sort the titles alphabetically




		//all the Outputs


		//a
		System.out.println("\nNumber of articles: "+newsResponse.getTotalResults());


		//b
		//System.out.println(nameCountMap); //see all the providers with their numbers
		if(newsResponse.getTotalResults() > 0){
			System.out.println("\nMost articles are from: "+nameCountMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey());
		}


		//c
		try {
			System.out.println("\nShortest author name: " + shortestAuthorNameArticle.getAuthor());
		} catch (NullPointerException e){
			System.out.println("Could not find shortest name of an author");
		}


		//d   (one is for alphabet one is for length, instructions were unclear :D)
		System.out.println("\nTitles sorted by length: \n");
		sortedByTitleLength.stream().forEach(article -> System.out.println(article.getTitle()));
		System.out.println("\nTitles sorted alphabetically: \n");
		sortedAlphabetically.stream().forEach(article -> System.out.println(article.getTitle()));

		System.out.println("End process");
	}
	

	public Object getData() {
		
		return null;
	}
}
