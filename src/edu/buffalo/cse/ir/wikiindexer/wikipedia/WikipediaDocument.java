/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nikhillo
 * This class acts as a container for a single wikipedia page.
 * It has a structure analogous to how a page looks in a browser
 * The class has various getters and setters for the different fields
 * You would call the setters from within your parser code to 
 * populate the fields as you parse them.
 * 
 * The getters would be used by your indexer(s) as and when you implement them
 *
 */
public class WikipediaDocument {
	/* This is the timestamp */
	private Date publishDate;
	
	/* Contributor: username or ip */
	private String author;
	
	/* Page id, not revision nor parent id */
	private int id;
	
	/* Page title */
	private String title;
	
	/* Look at the Section class below. Every page is a collection of sections */
	private List<Section> sections;
	
	/* This is a set of all links referenced by this page */
	private Set<String> links;
	
	/* This is a list of all categories of the page */
	private List<String> categories;
	
	/* A map representation of all language links. The key is the language code, value is the url */
	private Map<String, String> langLinks;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	/**
	 * Default constructor.
	 * @param idFromXml: The parsed id from the xml
	 * @param timestampFromXml: The parsed timestamp from the xml
	 * @param authorFromXml: The parsed author from the xml
	 * @param ttl: The title of the page
	 * @throws ParseException If the timestamp isn't in the expected format
	 */
	public WikipediaDocument(int idFromXml, String timestampFromXml, String authorFromXml, String ttl) throws ParseException {
		this.id = idFromXml;
		this.publishDate = (timestampFromXml == null) ? null : sdf.parse(timestampFromXml);
		this.author = (authorFromXml == null) ? null : authorFromXml;
		this.title = (ttl == null) ? null : ttl;
		sections = new ArrayList<WikipediaDocument.Section>();
		links = new HashSet<String>();
		categories = new ArrayList<String>();
		langLinks = new HashMap<String, String>();
	}
	
	/**
	 * Method to add a section to the given document
	 * @param title: The parsed title of the section
	 * @param text: The parsed text of the section
	 */
	protected void addSection(String title, String text) {
		sections.add(new Section(title, text));
	}
	
	/**
	 * Method to add a link to the set of links referenced by this document
	 * @param link: The page name for the link
	 */
	protected void addLink(String link) {
		links.add(link);
	}
	
	/**
	 * Method to bulk add links to the set of links referenced by this document
	 * @param links: The collection of links to be added, each referenced by the page name
	 */
	protected void addLInks(Collection<String> links) {
		this.links.addAll(links);
	}
	
	/**
	 * Method to add a category to the list of categories that classify this document
	 * @param category: The category to be added
	 */
	protected void addCategory(String category) {
		categories.add(category);
	}
	
	/**
	 * Method to bulk add categories to the list of categories classifying this document
	 * @param categories: The collection of categories to be added
	 */
	protected void addCategories(Collection<String> categories) {
		this.categories.addAll(categories);
	}
	
	/**
	 * Method to add a given language to link mapping to the list of language mappings for this document
	 * @param langCode: The language code that references the link
	 * @param langLink: The link to be added
	 */
	protected void addLangLink(String langCode, String langLink) {
		langLinks.put(langCode, langLink);
	}
	
	/**
	 * Method to bulk add language links to the list of mappings for this document
	 * @param links: The map containing the mappings to be added
	 */
	protected void addLangLinks(Map<String, String> links) {
		langLinks.putAll(links);
	}
	
	/**
	 * @return the publishDate
	 */
	public Date getPublishDate() {
		return publishDate;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the sections
	 */
	public List<Section> getSections() {
		return sections;
	}

	/**
	 * @return the links
	 */
	public Set<String> getLinks() {
		return links;
	}

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @return the langLinks
	 */
	public Map<String, String> getLangLinks() {
		return langLinks;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/*
	 * Class to mimic a section of a page
	 */
	public class Section {
		private String title;
		private String text;
		
		/**
		 * Default constructor. Please do not change visibility of the method.
		 * @param parsedTitle: The parsed section title
		 * @param parsedText: The parsed section text
		 */
		private Section(String parsedTitle, String parsedText) {
			this.title = parsedTitle;
			this.text = parsedText;
		}

		/**
		 * @return the title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
	}
	
}
