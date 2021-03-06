package de.zokki.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.zokki.Main;
import de.zokki.ConsoleColors.ConsoleColors;

public class XMLWriter {
	
	public static final String rootFoodName = "Speisen";
	public static final String rootCategoryName = "Kategorie";
	public static final String rootIngredientsName = "Zutaten";
	public static final String nameName = "Name";
	public static final String countName = "Anzahl";
	public static final String recipeName = "Rezept";
	public static final String unitName = "Einheit";
	public static final String noIngredient = "Keine_Zutaten_vorhanden";
	public static final String noCategory = "Keine_Kategorie";
	
	static DocumentBuilderFactory documentFactory;
	static DocumentBuilder documentBuilder;
	static Document document;
	
	static Element root;
	static Element rootFood;
	static Element rootCategory;
	static Element rootIngredients;
	
	static TransformerFactory transformerFactory;
	static Transformer transformer;
	static DOMSource domSource;
	static StreamResult streamResult;
	
	public static File file = new File("Essen.xml");
	
	public enum units {
		g("Gramm"), Stck("Stück"), l("Liter");
		
		String unit;
		
		units(String unit) {
			this.unit = unit;
		}
		
		public String getUnit() {
			return unit;
		}
		
		public static units getFromUnit(String unit) {
			switch (unit) {
				case "Gramm":
					return g;
				
				case "Stück":
					return Stck;
				
				case "Liter":
					return l;
				
				default:
					System.out.println(ConsoleColors.RED_BOLD + "XMLWriter.units.getFromUnit() in ’default’" + ConsoleColors.RESET);
					showInvalidInputError("NULL");
					return null;
			}
		}
		
		public static String[] getUnitArray() {
			String[] strings = new String[units.values().length];
			
			for(int i = 0; i < units.values().length; i++) {
				strings[i] = units.values()[i].getUnit();
			}
			
			return strings;
		}
	}
	
	public XMLWriter() {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.XMLWriter()" + ConsoleColors.RESET);
		
		try {
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
			try {
				document = documentBuilder.parse(file);
				root = (Element) document.getElementsByTagName("root").item(0);
				rootFood = (Element) document.getElementsByTagName(rootFoodName).item(0);
				rootCategory = (Element) document.getElementsByTagName(rootCategoryName).item(0);
				rootIngredients = (Element) document.getElementsByTagName(rootIngredientsName).item(0);
				
				System.out.println(ConsoleColors.GREEN + "Done reading XML File" + ConsoleColors.RESET);
			} catch (Exception e) {
				String message = file.exists() ? "Datei ersetzen?" : "Neue Datei erstellen?";
				String title = file.exists() ? "Error" : "Info";
				
				if(JOptionPane.showConfirmDialog(null, message, title, JOptionPane.ERROR_MESSAGE) != 0) {
					JOptionPane.showMessageDialog(null, "Programm wird geschlossen", "Info", 0);
					System.exit(-1);
				}
				
				file.createNewFile();
				
				document = documentBuilder.newDocument();
				
				// root element
				root = document.createElement("root");
				
				rootFood = document.createElement(rootFoodName);
				rootCategory = document.createElement(rootCategoryName);
				rootIngredients = document.createElement(rootIngredientsName);
				
				root.appendChild(rootFood);
				root.appendChild(rootCategory);
				root.appendChild(rootIngredients);
				
				document.appendChild(root);
				
				System.out.println(ConsoleColors.GREEN + "Done creating new XML File" + ConsoleColors.RESET);
			}
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			
			create();
			
			System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.XMLWriter()" + ConsoleColors.RESET);
		} catch (Exception e) {
			if(Main.admin)
				e.printStackTrace();
			else
				System.err.println(e);
		}
	}
	
	private static String addUnits(String input, units unit) {
		try {
			return input + " (" + unit.getUnit() + ")";
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Main.GUI, "FEHLER 406 BITTE MELDEN!!!", "ERROR 406", JOptionPane.ERROR_MESSAGE);
			System.out.println(ConsoleColors.RED_BOLD + "XMLWriter.addUnits() error - TRY" + ConsoleColors.RESET);
			return null;
		}
	}
	
	private static boolean hasInvalidChars(String... input) {
		System.out.println(ConsoleColors.GREEN + "In Writer.hasInvalidChars()" + ConsoleColors.RESET);
		try {
			for(String i : input) {
				document.createElement(i);
			}
			System.out.println(ConsoleColors.GREEN + "Finished Writer.hasInvalidChars()" + ConsoleColors.RESET);
			return false;
		} catch (Exception e) {
			System.out.println(ConsoleColors.RED + "Finished Writer.hasInvalidChars() with InvalidChars" + ConsoleColors.RESET);
			return true;
		}
	}
	
	private static boolean hasInvalidInput(String... input) {
		return hasInvalidInput(false, input);
	}
	
	private static boolean hasInvalidInput(boolean checkChars, String... input) {
		System.out.println(ConsoleColors.GREEN + "In Writer.hasInvalidInput()" + ConsoleColors.RESET);
		for(int i = 0; i < input.length; i++) {
			if(input[i] == null) {
				System.out.println(ConsoleColors.RED + "Writer.hasInvalidInput() has Invalid NULL error" + ConsoleColors.RESET);
				if(Main.admin) {
					showInvalidInputError("NULL");
				}
				return true;
			}
			if(input[i].length() == 0) {
				System.out.println(ConsoleColors.RED + "Writer.hasInvalidInput() has Invalid length error" + ConsoleColors.RESET);
				if(Main.admin) {
					showInvalidInputError("length");
				}
				return true;
			}
			if(checkChars)
				if(hasInvalidChars(input[i])) {
					System.out.println(ConsoleColors.RED + "Writer.hasInvalidInput() has InvalidChars" + ConsoleColors.RESET);
					showInvalidCharError(input[i]);
					return true;
				}
		}
		System.out.println(ConsoleColors.GREEN + "Finished Writer.hasInvalidInput()" + ConsoleColors.RESET);
		return false;
	}
	
	private static void showInvalidCharError(String input) {
		System.out.println(ConsoleColors.RED + "In Writer.showInvalidCharError()" + ConsoleColors.RESET);
		JOptionPane.showMessageDialog(Main.GUI, "'" + input.replaceAll("_", " ") + "' enthält ein ungültiges Zeichen!", "Fehler", JOptionPane.ERROR_MESSAGE);
		System.out.println(ConsoleColors.RED + "Finished Writer.showInvalidCharError()" + ConsoleColors.RESET);
	}
	
	private static void showInvalidInputError(String input) {
		System.out.println(ConsoleColors.RED + "In Writer.showInvalidInputError()" + ConsoleColors.RESET);
		JOptionPane.showMessageDialog(Main.GUI, "’" + input.replaceAll("_", " ") + "’ FEHLER MIT DER EINGABE IN WRITER!", "Fehler", JOptionPane.ERROR_MESSAGE);
		System.out.println(ConsoleColors.RED + "Finished Writer.showInvalidInputError()" + ConsoleColors.RESET);
	}
	
	private static void showAlreadyExisting(String input) {
		System.out.println(ConsoleColors.YELLOW + "In Writer.showAlreadyExisting()" + ConsoleColors.RESET);
		JOptionPane.showMessageDialog(Main.GUI, "'" + input.replaceAll("_", " ") + "' ist bereits vorhanden!", "Fehler", JOptionPane.ERROR_MESSAGE);
		System.out.println(ConsoleColors.YELLOW + "Finished Writer.showAlreadyExisting()" + ConsoleColors.RESET);
	}
	
	private static void showUnknownError(String input) {
		System.out.println(ConsoleColors.YELLOW + "In Writer.showUnknownError()" + ConsoleColors.RESET);
		JOptionPane.showMessageDialog(Main.GUI, "Unbekannter Fehler: '" + input.replaceAll("_", " ") + "'!", "Fehler", JOptionPane.ERROR_MESSAGE);
		System.out.println(ConsoleColors.YELLOW + "Finished Writer.showUnknownError()" + ConsoleColors.RESET);
	}
	
	private static NodeList getNodeList(String xPathString) {
		try {
			XPathExpression expr = XPathFactory.newInstance().newXPath().compile(xPathString); // XPathExpressionException
			
			return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		} catch (Exception e) {
			System.out.println(ConsoleColors.RED_BOLD + "ERROR in XMLWriter.getNodeList() " + xPathString + ConsoleColors.RED_BOLD);
			return null;
		}
	}
	
	private static NodeList getNodeList(String... xPathString) {
		String path = "";
		for(String string : xPathString) {
			path += string + "/";
		}
		return getNodeList(path.substring(0, path.length() - 1));
	}
	
	public static units getUnitOf(String unit) {
		return units.valueOf(getNodeList("root", rootIngredientsName, unit.replaceAll(" ", "_"), unitName).item(0).getTextContent());
	}
	
	public static String removeUnits(String input) {
		try {
			return input.substring(0, input.indexOf("(") - 1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Main.GUI, "FEHLER 405 BITTE MELDEN!!!", "ERROR 405", JOptionPane.ERROR_MESSAGE);
			System.out.println(ConsoleColors.RED_BOLD + "XMLWriter.removeUnits() error - TRY" + ConsoleColors.RESET);
			return null;
		}
	}
	
	public static boolean hasUnit(String hasUnit) {
		var wrapper = new Object() {
			boolean value = false;
		};
		Arrays.asList(units.getUnitArray()).forEach(u -> {
			if(hasUnit.contains(u)) {
				wrapper.value = true;
			}
		});
		return wrapper.value;
	}
	
	public static void create() {
		System.out.println(ConsoleColors.GREEN + "In Writer.create()" + ConsoleColors.RESET);
		try {
			domSource = new DOMSource(document);
			streamResult = new StreamResult(file);
			
			transformer.transform(domSource, streamResult);
			System.out.println(ConsoleColors.GREEN + "Finished Writer.create()" + ConsoleColors.RESET);
		} catch (Exception e) {
			if(Main.admin)
				e.printStackTrace();
			else
				System.err.println(e);
		}
	}

	public static void addFood(String food) {
		System.out.println(ConsoleColors.GREEN + "In Writer.addFood()" + ConsoleColors.RESET);
		
		food = food.replaceAll(" ", "_");
		if(hasInvalidInput(true, food)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.addFood() has InvalidInput" + ConsoleColors.RESET);
			return;
		}
		
		if(rootFood.getElementsByTagName(food).getLength() == 0) {
			Element foodElemet = document.createElement(food);
			Element nameElement = document.createElement(nameName);
			Element countElement = document.createElement(countName);
			
			nameElement.appendChild(document.createTextNode(food));
			countElement.appendChild(document.createTextNode("0"));
			
			foodElemet.appendChild(nameElement);
			foodElemet.appendChild(countElement);
			foodElemet.appendChild(document.createElement(recipeName));
			
			rootFood.appendChild(foodElemet);
			create();
		} else {
			System.out.println(ConsoleColors.YELLOW + "AlreadyExist " + food + " in Writer.addFood()" + ConsoleColors.RESET);
			showAlreadyExisting(food);
		}
		System.out.println(ConsoleColors.GREEN + "Finished Writer.addFood()" + ConsoleColors.RESET);
	}
	
	public static void editFood(String oldFood, String newFood) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.editFood()");
		
		oldFood = oldFood.replaceAll(" ", "_");
		newFood = newFood.replaceAll(" ", "_");
		if(hasInvalidInput(true, newFood)) {
			System.out.println(ConsoleColors.RED_BOLD + "XMLWriter.editFood() has InvalidInput");
			return;
		}
		
		if(getNodeList("root", rootFoodName, newFood).getLength() == 0) {
			Element foodNode = (Element) getNodeList("root", rootFoodName, oldFood).item(0);
			document.renameNode(foodNode, null, newFood);
			
			foodNode.getElementsByTagName(nameName).item(0).setTextContent(newFood);
			
			create();
		} else {
			System.out.println(ConsoleColors.YELLOW + "AlreadyExist " + newFood + " in Writer.editFood()" + ConsoleColors.RESET);
			showAlreadyExisting(newFood);
		}
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.editFood()");
	}

	public static void delFood(String input) {
		System.out.println(ConsoleColors.GREEN + "In Writer.delFood()" + ConsoleColors.RESET);
		
		rootFood.removeChild(getNodeList("root", rootFoodName, input.replaceAll(" ", "_")).item(0));
		
		create();
		System.out.println(ConsoleColors.GREEN + "Finished Writer.delFood()" + ConsoleColors.RESET);
	}

	public static String[] getFoods() {
		System.out.println(ConsoleColors.GREEN + "In Writer.getFoods()" + ConsoleColors.RESET);
		
		NodeList nodeList = rootFood.getChildNodes();
		String[] strings = new String[nodeList.getLength()];
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			strings[i] = nodeList.item(i).getNodeName().replaceAll("_", " ");
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished Writer.getFoods()" + ConsoleColors.RESET);
		return strings;
	}
	
	public static void addCategorys(String category) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.addCategorys()" + ConsoleColors.RESET);
		
		category = category.replaceAll(" ", "_");
		if(hasInvalidInput(true, category)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.addCategorys() has InvalidInput" + ConsoleColors.RESET);
			return;
		}
		if(category.equalsIgnoreCase(noCategory)) {
			System.out.println(ConsoleColors.YELLOW + "Finished XMLWriter.addCategorys() with no Ingredients" + ConsoleColors.RESET);
			JOptionPane.showMessageDialog(Main.GUI, "Also wirklich...", "Nein!", JOptionPane.QUESTION_MESSAGE);
			return;
		}		
		
		if(getNodeList("root", rootCategoryName, category).getLength() == 0) {
			Element ingredientElement = document.createElement(category);
			ingredientElement.setTextContent(category);
			
			rootCategory.appendChild(ingredientElement);
			create();
		} else {
			showAlreadyExisting(category);
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.addCategorys()" + ConsoleColors.RESET);
	}
	
	public static void editCategorys(String category, String newCategory) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.editCategorys()" + ConsoleColors.RESET);
		
		category = category.replaceAll(" ", "_");
		newCategory = newCategory.replaceAll(" ", "_");
		if(hasInvalidInput(true, newCategory)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.editCategorys() has InvalidInput" + ConsoleColors.RESET);
			return;
		}
		if(newCategory.equalsIgnoreCase(noCategory)) {
			System.out.println(ConsoleColors.YELLOW + "Finished XMLWriter.addCategorys() with no Ingredients" + ConsoleColors.RESET);
			JOptionPane.showMessageDialog(Main.GUI, "Also wirklich...", "Nein!", JOptionPane.QUESTION_MESSAGE);
			return;
		}
		
		if(getNodeList("root", rootCategoryName, newCategory).getLength() == 0) {
			NodeList newNode = getNodeList("root", rootIngredientsName, "/node()[" + rootCategoryName + "='" + category + "']");
			for(int i = 0; i < newNode.getLength(); i++) {
				System.out.println(newCategory);
				System.out.println(newNode.item(i).getNodeName());
				((Element) newNode.item(i)).getElementsByTagName(rootCategoryName).item(0).setTextContent(newCategory);
			}
			
			Node node = getNodeList("root", rootCategoryName, category).item(0);
			node.setTextContent(newCategory);
			document.renameNode(node, null, newCategory);
			
			create();
			
		} else {
			System.out.println(ConsoleColors.YELLOW + "AlreadyExist " + newCategory + " in Writer.editFood()" + ConsoleColors.RESET);
			showAlreadyExisting(newCategory);
		}
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.addIngredients()" + ConsoleColors.RESET);
	}
	
	public static void delCategorys(String category) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.delCategorys()" + ConsoleColors.RESET);
		
		category = category.replaceAll(" ", "_");
		NodeList newNode = getNodeList("root", rootIngredientsName, "/node()[" + rootCategoryName + "='" + category + "']");
		for(int i = 0; i < newNode.getLength(); i++) {
			((Element) newNode.item(i)).getElementsByTagName(rootCategoryName).item(0).setTextContent(noCategory);
		}
		
		Node node = getNodeList("root", rootCategoryName, category).item(0);
		rootCategory.removeChild(node);
		
		create();
		
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.delCategorys()" + ConsoleColors.RESET);
	}
	
	public static String[] getCategories() {
		return getCategories(true);
	}
	
	public static String[] getCategories(boolean defaultValue) {
		System.out.println(ConsoleColors.GREEN + "In Writer.getCategorys()" + ConsoleColors.RESET);
		
		int startValue = defaultValue ? 1 : 0;
		
		NodeList nodeList = rootCategory.getChildNodes();
		String[] strings = new String[nodeList.getLength() + startValue];
		
		if(strings.length != 0) {
			strings[0] = noCategory.replaceAll("_", " ");
		
			for(int i = startValue; i < nodeList.getLength() + startValue; i++) {
				strings[i] = nodeList.item(i - startValue).getNodeName().replaceAll("_", " ");
			}
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished Writer.getCategorys()" + ConsoleColors.RESET);
		return strings;
	}
	
	public static String getCategoriesOf(String ingredient) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.getCategoriesOf()" + ConsoleColors.RESET);
		
		NodeList nodes = getNodeList("root", rootIngredientsName, ingredient.replaceAll(" ", "_"), rootCategoryName);
		
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.getCategoriesOf()" + ConsoleColors.RESET);
		return nodes.item(0).getTextContent().replaceAll("_", " ");
	}
	
	public static void addIngredients(String ingredient, units unit, String category) {
		System.out.println(ConsoleColors.GREEN + "In Writer.addIngredients()" + ConsoleColors.RESET);
		
		ingredient = ingredient.replaceAll(" ", "_");
		category = category.replaceAll(" ", "_");
		if(hasInvalidInput(true, ingredient)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.addIngredients() has InvalidInput" + ConsoleColors.RESET);
			return;
		}
		if(ingredient.equalsIgnoreCase(noIngredient)) {
			System.out.println(ConsoleColors.YELLOW + "Finished XMLWriter.addIngredients() with no Ingredients" + ConsoleColors.RESET);
			JOptionPane.showMessageDialog(Main.GUI, "Also wirklich...", "Nein!", JOptionPane.QUESTION_MESSAGE);
			return;
		}
		
		if(rootIngredients.getElementsByTagName(ingredient).getLength() == 0) {
			Element ingredientElemet = document.createElement(ingredient);
			Element unitElement = document.createElement(unitName);
			Element categoryElement = document.createElement(rootCategoryName);
			
			unitElement.setTextContent(unit.toString());
			categoryElement.setTextContent(category);
			
			ingredientElemet.appendChild(unitElement);
			ingredientElemet.appendChild(categoryElement);
			
			rootIngredients.appendChild(ingredientElemet);
			create();
		} else {
			System.out.println(ConsoleColors.YELLOW + "AlreadyExist " + ingredient + " in Writer.addIngredients()" + ConsoleColors.RESET);
			showAlreadyExisting(ingredient);
		}
		System.out.println(ConsoleColors.GREEN + "Finished Writer.addIngredients()" + ConsoleColors.RESET);
	}
	
	public static void editIngredients(String oldIngredient, String newIngredient, units unit, String category) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.editIngredients()" + ConsoleColors.RESET);
		if(hasUnit(oldIngredient)) {
			oldIngredient = removeUnits(oldIngredient);
		}
		if(newIngredient.isEmpty())
			newIngredient = oldIngredient;
		
		oldIngredient = oldIngredient.replaceAll(" ", "_");
		newIngredient = newIngredient.replaceAll(" ", "_");
		category = category.replaceAll(" ", "_");
		if(hasInvalidInput(true, newIngredient)) {
			System.out.println(ConsoleColors.RED_BOLD + "XMLWriter.editIngredients() has InvalidInput" + ConsoleColors.RESET);
			return;
		}
		
		if(newIngredient.equalsIgnoreCase(noIngredient)) {
			System.out.println(ConsoleColors.YELLOW + "Finished XMLWriter.editIngredients() with no Ingredients" + ConsoleColors.RESET);
			JOptionPane.showMessageDialog(Main.GUI, "Also wirklich...", "Nein!", JOptionPane.QUESTION_MESSAGE);
			return;
		}
		
		if(rootIngredients.getElementsByTagName(newIngredient).getLength() == 0 || newIngredient.contentEquals(oldIngredient)) {
			delIngredients(false, oldIngredient);
			addIngredients(newIngredient, unit, category);
			
			NodeList newNode = getNodeList("root", rootFoodName, "node()", recipeName, oldIngredient);
			for(int i = 0; i < newNode.getLength(); i++) {
				Element newElement = (Element) newNode.item(i);
				
				if(newElement.getParentNode().getNodeName().contentEquals(recipeName)) {
					document.renameNode(newElement, null, newIngredient);
				}
			}
			
			create();
		} else {
			System.out.println(ConsoleColors.YELLOW + "AlreadyExist " + newIngredient + " in Writer.editFood()" + ConsoleColors.RESET);
			showAlreadyExisting(newIngredient);
			return;
		}
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.editIngredients()" + ConsoleColors.RESET);
	}
	
	public static void delIngredients(String ingredint) {
		delIngredients(true, ingredint);
	}
	
	public static void delIngredients(boolean delEvery, String ingredient) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.delIngredients()" + ConsoleColors.RESET);
		if(hasUnit(ingredient)) {
			ingredient = removeUnits(ingredient);
		}
		
		ingredient = ingredient.replaceAll(" ", "_");	
		
		if(delEvery) {
			NodeList nodes = getNodeList("root", rootFoodName, "node()", recipeName, ingredient);
			for(int i = 0; i < nodes.getLength(); i++) {
				nodes.item(i).getParentNode().removeChild(nodes.item(i));
			}
		}
			
		rootIngredients.removeChild(rootIngredients.getElementsByTagName(ingredient).item(0));
		
		create();
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.delIngredients()" + ConsoleColors.RESET);
	}
	
	public static String[] getIngredients() {
		return getIngredients(true);
	}
	
	public static String[] getIngredients(boolean addUnits) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.getIngredients()" + ConsoleColors.RESET);
		
		NodeList nodeList = rootIngredients.getChildNodes();
		String[] strings = new String[nodeList.getLength()];
		for(int i = 0; i < nodeList.getLength(); i++) {
			String name = nodeList.item(i).getNodeName().replaceAll("_", " ");
			if(addUnits) {
				strings[i] = addUnits(name, getUnitOf(name));
			} else {
				strings[i] = name;
			}
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.getIngredients()" + ConsoleColors.RESET);
		return strings;
	}

	public static String[] getPossibleIngredientsOf(String food) {
		System.out.println(ConsoleColors.GREEN + "In XMLWriter.getPossibleIngredientsOf()" + ConsoleColors.RESET);
		
		food = food.replaceAll(" ", "_");
		
		NodeList nodes = getNodeList("root", rootFoodName, food, recipeName, "node()");
		
		String[] ingredients = getIngredients(false);
		Stack<String> possivles = new Stack<String>();
		
		for(String string : ingredients) {
			boolean isIn = false;
			for(int i = 0; i < nodes.getLength(); i++) {
				if(nodes.item(i).getNodeName().replaceAll("_", " ").contentEquals(string)) {
					isIn = true;
					break;
				}
			}
			if(!isIn) {
				possivles.add(addUnits(string, getUnitOf(string)));
			}
		}
		if(possivles.size() == 0) {
			possivles.add(noIngredient.replaceAll("_", " "));
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished XMLWriter.getPossibleIngredientsOf()" + ConsoleColors.RESET);
		return possivles.toArray(new String[possivles.size()]);
	}
	
	public static int getIngredientAmount(String food, String ingredient) {
		System.out.println(ConsoleColors.GREEN + "In Writer.getIngredientAmount()" + ConsoleColors.RESET);
		if(hasUnit(ingredient)) {
			ingredient = removeUnits(ingredient);
		}
		
		food = food.replaceAll(" ", "_");
		ingredient = ingredient.replaceAll(" ", "_");
		if(hasInvalidInput(food, ingredient)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.getIngredientAmount() has InvalidInput" + ConsoleColors.RESET);
			return -1;
		}

		Element element = (Element) getNodeList("root", rootFoodName, food, recipeName).item(0);
		if(element.getTextContent().length() == 0) {
			System.out.println(ConsoleColors.CYAN + "Finished XMLWriter.getIngredientAmount() without ingredients" + ConsoleColors.RESET);
			return -1;
		}
		
		try {
			int i = Integer.parseInt(element.getElementsByTagName(ingredient).item(0).getTextContent());
			System.out.println(ConsoleColors.GREEN + "Finished Writer.getIngredientAmount()" + ConsoleColors.RESET);
			return i;
		} catch (Exception e) {
			System.out.println(ConsoleColors.RED + "Writer.getIngredientAmount() error with ContentParse" + ConsoleColors.RESET);
			return -1;
		}
	}
	
	public static void addCount(String food) {
		addCount(food, 1);
	}
	
	public static void addCount(String food, int amount) {
		System.out.println(ConsoleColors.GREEN + "In Writer.addCount()" + ConsoleColors.RESET);
		
		food = food.replaceAll(" ", "_");
		if(hasInvalidInput(food)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.addCount() has InvalidInput" + ConsoleColors.RESET);
			return;
		}
		
		Node countNode = ((Element) rootFood.getElementsByTagName(food).item(0)).getElementsByTagName(countName).item(0);
		countNode.setTextContent(Integer.parseInt(countNode.getTextContent()) + amount + "");
		create();
		
		System.out.println(ConsoleColors.GREEN + "Finished Writer.addCount()" + ConsoleColors.RESET);
	}
	
	public static void resetCount() {
		System.out.println(ConsoleColors.GREEN + "In Writer.resetCount()" + ConsoleColors.RESET);
		NodeList nodes = getNodeList("root", rootFoodName, "node()", countName);
		for(int i = 0; i < nodes.getLength(); i++) {
			nodes.item(i).setTextContent("0");
		}
		
		create();
		System.out.println(ConsoleColors.GREEN + "Finished Writer.resetCount()" + ConsoleColors.RESET);
	}
	
	public static void addRecipe(String inputFood, String recipeName, int amount) {
		System.out.println(ConsoleColors.GREEN + "In Writer.addRecipe()" + ConsoleColors.RESET);
		if(amount < 1) {
			System.out.println(ConsoleColors.YELLOW + "Writer.addRecipe() amount to small" + ConsoleColors.RESET);
			return;
		}
		if(hasUnit(recipeName)) {
			recipeName = removeUnits(recipeName);
		}
		
		inputFood = inputFood.replaceAll(" ", "_");
		recipeName = recipeName.replaceAll(" ", "_");
		if(hasInvalidInput(inputFood, recipeName)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.addRecipe() has InvalidInput" + ConsoleColors.RESET);
			return;
		}
		
		if(getNodeList("root", rootFoodName, inputFood, XMLWriter.recipeName, recipeName).getLength() == 0) {
			Element element = document.createElement(recipeName);
			Element node = (Element) getNodeList("root", rootFoodName, inputFood).item(0);
			Element recipe = (Element) node.getElementsByTagName(XMLWriter.recipeName).item(0);
			//TODO maybe a shorter way??!?!?
			element.setTextContent(amount + "");
			recipe.appendChild(element);
			node.appendChild(recipe);
		} else {
			showAlreadyExisting(recipeName);
			return;
		}
		create();
		
		System.out.println(ConsoleColors.GREEN + "Finished Writer.addRecipe()" + ConsoleColors.RESET);
	}
	
	public static void editRecipe(String food, String ingredient, int newValue) {
		System.out.println(ConsoleColors.GREEN + "In Writer.editRecipe()" + ConsoleColors.RESET);		
		if(newValue < 1) {
			System.out.println(ConsoleColors.YELLOW + "Writer.editRecipe() amount to small" + ConsoleColors.RESET);
			delRecipes(food, ingredient);
			return;
		}
		if(hasUnit(ingredient)) {
			ingredient = removeUnits(ingredient);
		}
		
		food = food.replaceAll(" ", "_");
		ingredient = ingredient.replaceAll(" ", "_");
		if(hasInvalidInput(food, ingredient)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.editRecipe() has InvalidChars" + ConsoleColors.RESET);
			return;
		}
		if(ingredient.equalsIgnoreCase(noIngredient)) {
			System.out.println(ConsoleColors.YELLOW + "Finished XMLWriter.editRecipe() with no ingredient" + ConsoleColors.RESET);
			return;
		}
		
		Element element = (Element) getNodeList("root", rootFoodName, food, recipeName, ingredient).item(0);
		if(element != null) {
			element.setTextContent(newValue + "");			
		} else {
			showUnknownError(ingredient + " nicht gefunden");
			System.out.println(ConsoleColors.RED_BOLD + "Finished XMLWriter.editRecipe() but ingredient isn't found" + ConsoleColors.RED_BOLD);
			return;
		}
		create();
		
		System.out.println(ConsoleColors.GREEN + "Finished Writer.editRecipe()" + ConsoleColors.RESET);
	}

	public static void delRecipes(String food, String recipe) {
		System.out.println(ConsoleColors.GREEN + "In Writer.delRecipes()" + ConsoleColors.RESET);
		if(hasUnit(recipe)) {
			recipe = removeUnits(recipe);
		}
		
		recipe = recipe.replaceAll(" ", "_");
		food = food.replaceAll(" ", "_");
		if(hasInvalidInput(food, recipe)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.delRecipes() has InvalidChars" + ConsoleColors.RESET);
			return;
		}
		if(recipe.equalsIgnoreCase(noIngredient)) {
			System.out.println(ConsoleColors.YELLOW + "Finished XMLWriter.editRecipe() with no ingredient" + ConsoleColors.RESET);
			return;
		}
		
		NodeList nodeList = getNodeList("root", rootFoodName, food, recipeName, recipe);
		
		((Element) getNodeList("root", rootFoodName, food, recipeName).item(0).getChildNodes()).removeChild(nodeList.item(0));
		create();
		
		System.out.println(ConsoleColors.GREEN + "Finished Writer.delRecipes()" + ConsoleColors.RESET);
	}
	
	public static String[] getRecipes(String food) {
		System.out.println(ConsoleColors.GREEN + "In Writer.getRecipes()" + ConsoleColors.RESET);
		
		food = food.replaceAll(" ", "_");
		if(hasInvalidInput(food)) {
			System.out.println(ConsoleColors.RED_BOLD + "Writer.getRecipes() has InvalidInput" + ConsoleColors.RESET);
			return new String[] { "error - line " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " in " + XMLWriter.class.getSimpleName() };
		}
		
		List<String> strings = new ArrayList<String>();
		NodeList nodeList = getNodeList("root", rootFoodName, food, recipeName, "node()");
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			String nodeName = nodeList.item(i).getNodeName();
			strings.add(addUnits(nodeName.replaceAll("_", " "), units.valueOf(getNodeList("root", rootIngredientsName, nodeName, unitName).item(0).getTextContent())));
		}
		
		if(strings.size() != 0) {
			System.out.println(ConsoleColors.GREEN + "Finished Writer.getRecipes()" + ConsoleColors.RESET);
			return strings.toArray(new String[strings.size()]);
		} else {
			System.out.println(ConsoleColors.CYAN + "Finished Writer.getRecipes() with no recipe" + ConsoleColors.RESET);
			return new String[] { noIngredient.replaceAll("_", " ") };
		}
	}
}