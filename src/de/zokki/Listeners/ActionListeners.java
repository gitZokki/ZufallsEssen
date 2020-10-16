package de.zokki.Listeners;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.zokki.Main;
import de.zokki.ConsoleColors.ConsoleColors;
import de.zokki.FileManager.XMLWriter;
import de.zokki.FileManager.XMLWriter.units;
import de.zokki.Gui.OptionPanels;

@SuppressWarnings("unchecked")
public class ActionListeners implements ActionListener {
	String[] options = { "Weitere", "Ok", "Abbrechen" };
	OptionPanels op = new OptionPanels();
	
	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.actionPerformed()" + ConsoleColors.RESET);
		
		if(Main.GUI.foodAdd == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "foodAdd clicked" + ConsoleColors.RESET);
			addFood();
			
		} else if(Main.GUI.foodEdit == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "foodEdit clicked" + ConsoleColors.RESET);
			editFood("");
			
		} else if(Main.GUI.foodDel == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "foodDel clicked" + ConsoleColors.RESET);
			if(!hasFoods()) { return; }
			
			String input = (String) JOptionPane.showInputDialog(Main.GUI, "Welche " + Main.FOODNAME + " soll entfernt werden?", "Eine " + Main.FOODNAME + " entfernen", JOptionPane.CLOSED_OPTION, null,
					XMLWriter.getFoods(), "Fehler 404");
			
			if(input != null) {
				XMLWriter.delFood(input);
			} else {
				System.out.println(ConsoleColors.CYAN + "In foodDel - input NULL" + ConsoleColors.RESET);
			}
			
		} else if(Main.GUI.categorysAdd == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "categorysAdd clicked" + ConsoleColors.RESET);
			addCategorys();
			
		} else if(Main.GUI.categorysEdit == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "categorysEdit clicked" + ConsoleColors.RESET);
			editCategorys("");
			
		} else if(Main.GUI.categorysDel == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "categorysDel clicked" + ConsoleColors.RESET);
			if(!hasCategories()) { return; }			
			
			String input = (String) JOptionPane.showInputDialog(Main.GUI, "Welche " + Main.CATEGORYNAME + " soll entfernt werden?", "Eine " + Main.CATEGORYNAME + " entfernen", JOptionPane.CLOSED_OPTION, null,
					XMLWriter.getCategories(false), "Fehler 404");
			
			if(input != null) {
				XMLWriter.delCategorys(input);
			} else {
				System.out.println(ConsoleColors.CYAN + "In categorysDel - input NULL" + ConsoleColors.RESET);
			}
			
		} else if(Main.GUI.ingredientsAdd == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "ingredientsAdd clicked" + ConsoleColors.RESET);
			addIngredients();
			
		} else if(Main.GUI.ingredientsEdit == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "ingredientsEdit clicked" + ConsoleColors.RESET);
			editIngredients("");
			
		} else if(Main.GUI.ingredientsDel == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "ingredientsDel clicked" + ConsoleColors.RESET);
			delIngredients();
			
		} else if(Main.GUI.recipeAdd == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "recipeAdd clicked" + ConsoleColors.RESET);
			addRecipe("");
			
		} else if(Main.GUI.recipeEdit == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "recipeEdit clicked" + ConsoleColors.RESET);
			editRecipe("");
			
		} else if(Main.GUI.recipeDel == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "recipeDel clicked" + ConsoleColors.RESET);
			delRecipe();
			
		} else if(Main.GUI.generate == event.getSource()) {
			System.out.println(ConsoleColors.GREEN + "generate clicked" + ConsoleColors.RESET);
			generate();
			
		}
	}
	
	private void addFood() {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.addFood()" + ConsoleColors.RESET);
		
		JPanel panel = null;
		JTextField food = null;
		
		for(Component comp : op.getFoodAddComponents()) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JTextField) {
				food = (JTextField) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(null, panel, "Eine " + Main.FOODNAME + " hinzufügen", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.addFood(food.getText());
				addFood();
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.addFood(food.getText());
				break;
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.addRecipe()" + ConsoleColors.RESET);
	}
	
	private void editFood(String defaultValue) {
		if(!hasFoods()) { return; }
		
		JPanel panel = null;
		JTextField newName = null;
		JComboBox<String> foods = null;
		
		for(Component comp : op.getFoodEditComponents(defaultValue)) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JTextField) {
				newName = (JTextField) comp;
			} else if(comp instanceof JComboBox<?>) {
				foods = (JComboBox<String>) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(Main.GUI, panel, Main.RECIPENAME + " hinzufügen!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.editFood((String) foods.getSelectedItem(), newName.getText());
				editFood((String) foods.getSelectedItem());
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.editFood((String) foods.getSelectedItem(), newName.getText());
				break;
		}
		
	}
	
	private void addCategorys() {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.addCategorys()" + ConsoleColors.RESET);
		
		JPanel panel = null;
		JTextField ingredientsText = null;
		
		for(Component comp : op.getCategoriesAddComponents()) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JTextField) {
				ingredientsText = (JTextField) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(Main.GUI, panel, Main.CATEGORYNAME + " hinzufügen!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.addCategorys(ingredientsText.getText());
				addCategorys();
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.addCategorys(ingredientsText.getText());
				break;
		}
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.addCategorys()" + ConsoleColors.RESET);
	}
	
	private void editCategorys(String defaultValue) {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.editCategorys()" + ConsoleColors.RESET);
		if(!hasCategories()) { return; }
		
		JPanel panel = null;
		JTextField text = null;
		JComboBox<String> categories = null;
		
		for(Component comp : op.getCategoriesEditComponents(defaultValue)) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JTextField) {
				text = (JTextField) comp;
			} else {
				categories = (JComboBox<String>) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(Main.GUI, panel, Main.CATEGORYNAME + " bearbeiten!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.editCategorys((String) categories.getSelectedItem(), text.getText());
				editCategorys((String) categories.getSelectedItem());
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.editCategorys((String) categories.getSelectedItem(), text.getText());
				break;
		}
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.editCategorys()" + ConsoleColors.RESET);
	}
	
	private void addIngredients() {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.addIngredients()" + ConsoleColors.RESET);
		
		JPanel panel = null;
		JComboBox<String> unitBox = null;
		JComboBox<String> categories = null;
		JTextField ingredientsText = null;
		
		for(Component comp : op.getIngredientsAddComponents()) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JComboBox<?>) {
				if(((JComboBox<String>) comp).getPrototypeDisplayValue().contains("units"))
					unitBox = (JComboBox<String>) comp;
				else
					categories = (JComboBox<String>) comp;
			} else if(comp instanceof JTextField) {
				ingredientsText = (JTextField) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(Main.GUI, panel, Main.INGREDIENTNAME + " hinzufügen!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.addIngredients(ingredientsText.getText(), units.getFromUnit((String) unitBox.getSelectedItem()), (String) categories.getSelectedItem());
				addIngredients();
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.addIngredients(ingredientsText.getText(), units.getFromUnit((String) unitBox.getSelectedItem()), (String) categories.getSelectedItem());
				break;
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.addIngredients()" + ConsoleColors.RESET);
	}
	
	private void editIngredients(String defaultValue) {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.editIngredients()" + ConsoleColors.RESET);
		if(!hasIngredients()) { return; }
		
		JPanel panel = null;
		JTextField newName = null;
		JComboBox<String> categories = null;
		JComboBox<String> ingredients = null;
		JComboBox<String> unitBox = null;
		
		for(Component comp : op.getIngredientsEditComponents(defaultValue)) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JTextField) {
				newName = (JTextField) comp;
			} else if(comp instanceof JComboBox<?>) {
				if(((JComboBox<String>) comp).getPrototypeDisplayValue().contains("units"))
					unitBox = (JComboBox<String>) comp;
				else if(((JComboBox<String>) comp).getPrototypeDisplayValue().contains("ingredients"))
					ingredients = (JComboBox<String>) comp;
				else
					categories = (JComboBox<String>) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(Main.GUI, panel, Main.INGREDIENTNAME + " bearbeiten!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.editIngredients((String) ingredients.getSelectedItem(), newName.getText(), units.getFromUnit((String) unitBox.getSelectedItem()),
						(String) categories.getSelectedItem());
				editIngredients((String) ingredients.getSelectedItem());
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.editIngredients((String) ingredients.getSelectedItem(), newName.getText(), units.getFromUnit((String) unitBox.getSelectedItem()),
						(String) categories.getSelectedItem());
				break;
		}
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.editIngredients()" + ConsoleColors.RESET);
	}
	
	private void delIngredients() {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.delIngredients()" + ConsoleColors.RESET);
		if(!hasIngredients()) { return; }
		
		String input = (String) JOptionPane.showInputDialog(Main.GUI, "Welche " + Main.INGREDIENTNAME + " soll entfernt werden?", "Eine " + Main.INGREDIENTNAME + " entfernen", JOptionPane.CLOSED_OPTION, null,
				XMLWriter.getIngredients(), "Fehler 404");
		
		if(input != null) {
			XMLWriter.delIngredients(input);
		} else {
			System.out.println(ConsoleColors.CYAN + "In delIngredients - input NULL" + ConsoleColors.RESET);
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.delIngredients()" + ConsoleColors.RESET);
	}
	
	private void addRecipe(String defaultValue) {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.addRecipe()" + ConsoleColors.RESET);
		if(!hasFoods() || !hasIngredients()) { return; }
		
		JPanel panel = null;
		JComboBox<String> foods = null;
		JComboBox<String> ingredients = null;
		JTextField count = null;
		
		for(Component comp : op.getRecipeAddComponents(defaultValue)) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JComboBox<?>) {
				if(((JComboBox<String>) comp).getPrototypeDisplayValue().contains("foods"))
					foods = (JComboBox<String>) comp;
				else
					ingredients = (JComboBox<String>) comp;
			} else if(comp instanceof JTextField) {
				count = (JTextField) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(Main.GUI, panel, Main.RECIPENAME + " hinzufügen!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		String food = (String) foods.getSelectedItem();
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.addRecipe(food, (String) ingredients.getSelectedItem(), Integer.parseInt(count.getText().trim()));
				addRecipe(food);
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.addRecipe(food, (String) ingredients.getSelectedItem(), Integer.parseInt(count.getText().trim()));
				break;
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.addRecipe()" + ConsoleColors.RESET);
	}
	
	private void editRecipe(String defaultValue) {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.editRecipe()" + ConsoleColors.RESET);
		if(!hasFoods() || !hasIngredients()) { return; }
		
		JPanel panel = null;
		JTextField ingredientsAmount = null;
		JComboBox<String> foods = null;
		JComboBox<String> ingredients = null;
		
		for(Component comp : op.getRecipeEditComponents(defaultValue)) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JTextField) {
				ingredientsAmount = (JTextField) comp;
			} else if(comp instanceof JComboBox<?>) {
				if(((JComboBox<String>) comp).getPrototypeDisplayValue().contains("foods"))
					foods = (JComboBox<String>) comp;
				else
					ingredients = (JComboBox<String>) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(null, panel, "Ein " + Main.RECIPENAME + " bearbeiten", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		if(ingredientsAmount.getText().length() == 0) {
			System.out.println(ConsoleColors.CYAN + "Finished ActionListeners.editRecipe() with empty amount" + ConsoleColors.RESET);
			return;
		}
		
		if(ingredientsAmount.getText().equals(XMLWriter.getIngredientAmount((String) foods.getSelectedItem(), (String) ingredients.getSelectedItem()) + "")
				&& (input == 0 || input == 1)) {
			JOptionPane.showMessageDialog(Main.GUI, "Es sollte auch ein neuer Wert eingegeben werden", "Fehler", JOptionPane.ERROR_MESSAGE);
			System.out.println(ConsoleColors.CYAN + "Finished ActionListeners.editRecipe() with same amount -> no edit" + ConsoleColors.RESET);
			return;
		}
		
		switch (input) {
			case 0: // 'Weitere' button
				XMLWriter.editRecipe((String) foods.getSelectedItem(), (String) ingredients.getSelectedItem(), Integer.parseInt(ingredientsAmount.getText()));
				editRecipe((String) foods.getSelectedItem());
				break;
			case 1: // 'OK' button (and 'Weiter')
				XMLWriter.editRecipe((String) foods.getSelectedItem(), (String) ingredients.getSelectedItem(), Integer.parseInt(ingredientsAmount.getText()));
				break;
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.editRecipe()" + ConsoleColors.RESET);
	}
	
	private void delRecipe() {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.delRecipe()" + ConsoleColors.RESET);
		if(!hasFoods() || !hasIngredients()) { return; }
		
		JPanel panel = new JPanel();
		JComboBox<String> foods = new JComboBox<String>(XMLWriter.getFoods());
		JComboBox<String> ingredients = new JComboBox<String>(XMLWriter.getRecipes((String) foods.getSelectedItem()));
		
		for(Component comp : op.getRecipeDelComponents()) {
			if(comp instanceof JPanel) {
				panel = (JPanel) comp;
			} else if(comp instanceof JComboBox<?>) {
				if(((JComboBox<String>) comp).getPrototypeDisplayValue().contains("foods"))
					foods = (JComboBox<String>) comp;
				else
					ingredients = (JComboBox<String>) comp;
			}
		}
		
		int input = JOptionPane.showOptionDialog(Main.GUI, panel, Main.RECIPENAME + " löschen!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				new String[] { options[1], options[2] }, null);
		
		if(ingredients.getSelectedIndex() == -1)
			return;
		
		switch (input) {
			case 0:
				XMLWriter.delRecipes((String) foods.getSelectedItem(), (String) ingredients.getSelectedItem());
				break;
			default:
				break;
		}
		
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.delRecipe()" + ConsoleColors.RESET);
	}
	
	private void generate() {
		System.out.println(ConsoleColors.GREEN + "In ActionListeners.generate()" + ConsoleColors.RESET);
		var foodWrapper = new Object() {
			int length = 0;
		};
		
		Main.GUI.count.getText().chars().filter(f -> Character.isDigit(f)).forEach(e -> foodWrapper.length += Character.getNumericValue(e));
		if(foodWrapper.length < 8) {
			Main.GUI.food.setText("");
			String[] foods = XMLWriter.getFoods();
			List<Integer> foodInt = new ArrayList<Integer>();
			HashMap<String, Integer> recipe = new HashMap<String, Integer>();
			for(int i = 0; i < foodWrapper.length; i++) {
				int rand = ThreadLocalRandom.current().nextInt(0, foods.length);
				if(!foodInt.contains(rand) || foods.length < foodWrapper.length) {
					foodInt.add(rand);
					String text = Main.GUI.food.getText() + (i + 1) + " " + foods[rand];
					if(i + 1  < foodWrapper.length) { text += "\n"; }
					Main.GUI.food.setText(text);
					for(String s : XMLWriter.getRecipes(foods[rand])) {
						s = XMLWriter.hasUnit(s) ? XMLWriter.removeUnits(s) : s;
						if(XMLWriter.getIngredientAmount(foods[rand], s) != -1) {
							if(!recipe.containsKey(s))
								recipe.put(s, 0);
							recipe.put(s, recipe.get(s) + XMLWriter.getIngredientAmount(foods[rand], s));
						}
					}
					XMLWriter.addCount(foods[rand]);
				} else {
					i--;
				}
			}
			var panelWrapper = new Object() {
				JPanel panel = new JPanel();
				int row = 0;
			};
			panelWrapper.panel.setLayout(new GridBagLayout());
			
			recipe.forEach((s, i) -> {
				GridBagConstraints grid = new GridBagConstraints();
				grid.fill = GridBagConstraints.HORIZONTAL;
				
				JLabel labels = new JLabel();
				labels = new JLabel(i + units.valueOf(XMLWriter.getUnitOf(s)).getUnit() + " " + s);
				grid.gridy = panelWrapper.row;
				panelWrapper.panel.add(labels, grid);
				
				panelWrapper.row++;
			});
			if(panelWrapper.panel.getComponentCount() != 0)
				JOptionPane.showMessageDialog(Main.GUI, panelWrapper.panel, "Benötigte " + Main.INGREDIENTSNAME, JOptionPane.PLAIN_MESSAGE);
			
			Main.GUI.setFont();
		} else
			Main.GUI.food.setText("Mehr als 7Tage ist doch etwas zu viel");
		
		System.out.println(ConsoleColors.GREEN + "Finished ActionListeners.generate()" + ConsoleColors.RESET);
	}
	
	private boolean hasFoods() {
		if(XMLWriter.getFoods().length == 0) {
			JOptionPane.showMessageDialog(Main.GUI, "Noch keine " + Main.FOODSNAME + " vorhanden!", Main.FOODSNAME + " fehlen!", JOptionPane.INFORMATION_MESSAGE);
			System.out.println(ConsoleColors.CYAN + "Finished ActionListeners.hasFoods - with no food" + ConsoleColors.RESET);
			return false;
		}
		return true;
	}
	
	private boolean hasIngredients() {
		if(XMLWriter.getIngredients().length == 0) {
			JOptionPane.showMessageDialog(Main.GUI, "Noch keine " + Main.INGREDIENTSNAME + " vorhanden!", Main.INGREDIENTSNAME + " fehlen!", JOptionPane.INFORMATION_MESSAGE);
			System.out.println(ConsoleColors.CYAN + "Finished ActionListeners.hasIngredients() - with no ingredients" + ConsoleColors.RESET);
			return false;
		}
		return true;
	}
	
	private boolean hasCategories() {
		if(XMLWriter.getCategories(false).length == 0) {
			JOptionPane.showMessageDialog(Main.GUI, "Noch keine " + Main.CATEGORIESNAME + " vorhanden!", Main.CATEGORIESNAME + " fehlen!", JOptionPane.INFORMATION_MESSAGE);
			System.out.println(ConsoleColors.CYAN + "Finished ActionListeners.hasCategories - with no categorie" + ConsoleColors.RESET);
			return false;
		}
		return true;
	}
}