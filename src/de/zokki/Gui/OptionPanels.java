package de.zokki.Gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.zokki.Main;
import de.zokki.FileManager.XMLWriter;
import de.zokki.FileManager.XMLWriter.units;
import de.zokki.Listeners.KeyListeners;
import de.zokki.Listeners.KeyListeners.possibleKeys;

public class OptionPanels {
	private JLabel newNameLabel = new JLabel("Neuer Name:");
	
	public Component[] getFoodAddComponents() {	
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
				
		JLabel message = new JLabel("Bitte eine " + Main.FOODNAME + " eingeben!");
		
		JTextField foodTf = new JTextField();
		foodTf.addKeyListener(new KeyListeners(possibleKeys.ALPHABET));
		
		panel.add(message, grid);
		
		grid.gridy = 1;
		panel.add(foodTf, grid);
		
		return new Component[] {panel, foodTf};
	}
	
	public Component[] getFoodEditComponents(String defaultValue) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JTextField foodTf = new JTextField();
		foodTf.addKeyListener(new KeyListeners(possibleKeys.ALPHABET));
		
		JComboBox<String> foods = new JComboBox<String>(XMLWriter.getFoods());
		
		foods.setPrototypeDisplayValue("EIN WIRKLICH LANGER STRING HIER");
		foods.setSelectedItem(defaultValue);
				
		grid.insets = new Insets(2, 2, 2, 2);
		grid.gridwidth = 2;
		panel.add(foods, grid);
		
		grid.gridwidth = 1;
		grid.gridy = 1;
		panel.add(newNameLabel, grid);
		panel.add(foodTf, grid);
		
		return new Component[] {panel, foodTf, foods};
	}
	
	public Component[] getFoodDelComponents() {
		return new Component[] {};
	}
	
	public Component[] getCategoriesAddComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel message = new JLabel("Bitte eine " + Main.CATEGORYNAME + " eingeben!");
		
		JTextField categoryTf = new JTextField();
		categoryTf.addKeyListener(new KeyListeners(possibleKeys.ALPHABET));
		
		panel.add(message, grid);
		
		grid.gridy = 1;
		panel.add(categoryTf, grid);
		
		return new Component[] {panel, categoryTf};
	}
	
	public Component[] getCategoriesEditComponents(String defaultValue) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JTextField categoryTf = new JTextField();
		categoryTf.addKeyListener(new KeyListeners(possibleKeys.ALPHABET));
		
		JComboBox<String> categories = new JComboBox<String>(XMLWriter.getCategories(false));
		
		categories.setPrototypeDisplayValue("EIN WIRKLICH LANGER STRING HIER");
		categories.setSelectedItem(defaultValue);
		
		grid.insets = new Insets(2, 2, 2, 2);
		grid.gridwidth = 2;
		panel.add(categories, grid);
		
		grid.gridwidth = 1;
		grid.gridy = 1;
		panel.add(newNameLabel, grid);
		panel.add(categoryTf, grid);
		
		return new Component[] {panel, categoryTf, categories};
	}
	
	public Component[] getCategoriesDelComponents() {
		return new Component[] {};
	}
	
	public Component[] getIngredientsAddComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JTextField ingredientTf = new JTextField();
		ingredientTf.addKeyListener(new KeyListeners(possibleKeys.ALPHABET));
		
		JComboBox<String> categories = new JComboBox<String>(XMLWriter.getCategories());
		JComboBox<String> unit = new JComboBox<String>(units.getUnitArray());
		
		categories.setPrototypeDisplayValue("EIN categories STRING");
		unit.setPrototypeDisplayValue("EIN units      STRING");
		
		JLabel ingredientsLabel = new JLabel("Name der " + Main.INGREDIENTNAME + "?");
		JLabel categoryLabel = new JLabel("Welche " + Main.CATEGORYNAME + "?");
		JLabel unitLabel = new JLabel("Welche Einheit?");
		
		grid.insets = new Insets(5, 5, 5, 5);
		panel.add(ingredientsLabel, grid);
		panel.add(ingredientTf, grid); 
		
		grid.gridy = 1;
		panel.add(categoryLabel, grid);
		panel.add(categories, grid);
		
		grid.gridy = 2;
		panel.add(unitLabel, grid);
		panel.add(unit, grid);
		
		return new Component[] {panel, unit, categories, ingredientTf};
	}
	
	public Component[] getIngredientsEditComponents(String defaultValue) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JTextField newName = new JTextField();	
		newName.setPreferredSize(new Dimension(100, 25));
		
		JComboBox<String> categories = new JComboBox<String>(XMLWriter.getCategories());
		JComboBox<String> ingredients = new JComboBox<String>(XMLWriter.getIngredients());
		JComboBox<String> unit = new JComboBox<String>(units.getUnitArray());
		
		categories.setPrototypeDisplayValue("EIN categories STRING");
		ingredients.setPrototypeDisplayValue("EIN LANGER ingredients STRING");
		unit.setPrototypeDisplayValue("units");
		
		grid.insets = new Insets(2, 2, 2, 2);
		grid.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(ingredients, grid);
		
		grid.gridwidth = 1;
		grid.gridy = 1;
		panel.add(newNameLabel, grid);
		panel.add(newName, grid);
		
		grid.gridy = 2;
		panel.add(unit, grid);
		panel.add(categories, grid);		
		
		return new Component[] {panel, newName, ingredients, categories, unit};
	}
	
	public Component[] getIngredientsDelComponents() {
		return new Component[] {};
	}
	
	
	public Component[] getRecipeAddComponents(String defaultValue) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JComboBox<String> foods = new JComboBox<String>(XMLWriter.getFoods());
		JComboBox<String> ingredients = new JComboBox<String>(XMLWriter.getIngredients());
		
		foods.setPrototypeDisplayValue("EIN MITTLERER foods");
		foods.setSelectedItem(defaultValue);
		
		JTextField count = new JTextField("1");
		count.setHorizontalAlignment(JTextField.RIGHT);
		count.addKeyListener(new KeyListeners(possibleKeys.DIGITS));
		
		ingredients.setPrototypeDisplayValue("EIN LANGER STRING");
		
		JLabel ingredientLabel = new JLabel(Main.INGREDIENTNAME + ": ");
		JLabel countLabel = new JLabel("Menge: ");
		JTextArea foodLabel = new JTextArea("Zu welcher " + Main.FOODNAME + " soll das \n" + Main.RECIPENAME + " hinzugefügt werden?");
		foodLabel.setEditable(false);
		foodLabel.setBackground(Main.GUI.getBackground());
		foodLabel.setFont(ingredientLabel.getFont());
		
		
		grid.insets = new Insets(2, 2, 2, 2);
		grid.gridwidth = 2;
		panel.add(foodLabel, grid);
		
		grid.gridx = 2;
		panel.add(foods, grid);
		
		grid.gridwidth = 1;
		grid.gridx = 0;
		grid.gridy = 1;
		panel.add(ingredientLabel, grid);
		
		grid.gridx = 1;
		grid.gridy = 1;
		panel.add(ingredients, grid);
		
		grid.gridx = 2;
		grid.gridy = 1;
		panel.add(countLabel, grid);
		
		grid.gridx = 3;
		grid.gridy = 1;
		panel.add(count, grid);
		return new Component[] {panel, foods, ingredients, count};
	}
	
	public Component[] getRecipeEditComponents(String defaultValue) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel foodLabel = new JLabel("Bei welche " + Main.FOODNAME + "?");
		JLabel countLabel = new JLabel("Neue Menge: ");
		JTextField ingredientsAmount = new JTextField();
		ingredientsAmount.addKeyListener(new KeyListeners(KeyListeners.possibleKeys.DIGITS));
		
		JComboBox<String> foods = new JComboBox<String>(XMLWriter.getFoods());
		JComboBox<String> ingredients = new JComboBox<String>(XMLWriter.getRecipes((String) foods.getSelectedItem()));
		
		ingredientsAmount.setEditable(!((String) ingredients.getSelectedItem()).equalsIgnoreCase(XMLWriter.noIngredient.replaceAll("_", " ")));
		
		foods.setSelectedItem(defaultValue);
		foods.setPrototypeDisplayValue("foodsTEXT LÄNGE");

		ingredients.setPrototypeDisplayValue("INGREDIE LÄNGE");

		foods.addItemListener(new ItemListener() {
			short i = 0;
			
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(i % 2 == 0) {
					if(i > 10) i = 0;
					String[] foodStrings = XMLWriter.getRecipes((String) foods.getSelectedItem());
					ingredients.setModel(new DefaultComboBoxModel<String>(foodStrings));
				}
				i++;
			}
		});

		grid.insets = new Insets(0, 5, 5, 5);
		panel.add(foodLabel, grid);
		grid.gridwidth = GridBagConstraints.REMAINDER;;
		panel.add(foods, grid);
		
		grid.insets = new Insets(5, 2, 0, 2);
		grid.gridy = 1;
		grid.gridwidth = 1;
		panel.add(ingredients, grid);
		grid.gridx = 1;
		panel.add(countLabel, grid);
		grid.gridx = 2;
		grid.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(ingredientsAmount, grid);
		
		return new Component[] {panel, foods, ingredientsAmount, ingredients};
	}
	
	public Component[] getRecipeDelComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel foodLabel = new JLabel(Main.FOODNAME);
		JLabel recipeLabel = new JLabel(Main.RECIPENAME);
		
		JComboBox<String> foods = new JComboBox<String>(XMLWriter.getFoods());
		JComboBox<String> ingredients = new JComboBox<String>(XMLWriter.getRecipes((String) foods.getSelectedItem()));
		
		foods.setPrototypeDisplayValue("foodsTEXT LÄNGE");
		ingredients.setPrototypeDisplayValue("INGREDIE LÄNGE");
		
		foods.addItemListener(new ItemListener() {
			int i = 0;
			
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(i % 2 == 0) {
					if(i > 10) i = 0;
					String[] foodStrings = XMLWriter.getRecipes((String) foods.getSelectedItem());
					ingredients.setModel(new DefaultComboBoxModel<String>(foodStrings));
				}
				i++;
			}
		});
		
		grid.insets = new Insets(5, 2, 5, 2);
		grid.gridy = 0;
		panel.add(foodLabel, grid);
		panel.add(foods, grid);
		
		grid.gridy = 1;
		panel.add(recipeLabel, grid);
		panel.add(ingredients, grid);
		return new Component[] {panel, foods, ingredients};
	}
}