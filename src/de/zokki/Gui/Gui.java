package de.zokki.Gui;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.zokki.Listeners.ActionListeners;

public class Gui extends JFrame {
	
	private static final long serialVersionUID = 1588187876411078822L;
	
	private final int BORDER = 5;
	public int length = 0;
	
	String add = "hinzufügen";
	String del = "löschen";
	String edit = "bearbeiten";
	
	public JButton generate = new JButton("Zufällige Essen ausgeben");
	public JTextField count = new JTextField("7");
	public JTextArea food = new JTextArea("");
	
	private JMenuBar bar = new JMenuBar();
	public JMenu foodMenu = new JMenu("Essen");
	public JMenu categorysMenu = new JMenu("Kategorien");
	public JMenu ingredientsMenu = new JMenu("Zutaten");
	public JMenu recipeMenu = new JMenu("Rezepte");
	public JMenuItem foodAdd = new JMenuItem(add);
	public JMenuItem foodEdit = new JMenuItem(edit);
	public JMenuItem foodDel = new JMenuItem(del);
	public JMenuItem categorysAdd = new JMenuItem(add);
	public JMenuItem categorysEdit = new JMenuItem(edit);
	public JMenuItem categorysDel = new JMenuItem(del);
	public JMenuItem ingredientsAdd = new JMenuItem(add);
	public JMenuItem ingredientsEdit = new JMenuItem(edit);
	public JMenuItem ingredientsDel = new JMenuItem(del);
	public JMenuItem recipeAdd = new JMenuItem(add);
	public JMenuItem recipeEdit = new JMenuItem(edit);
	public JMenuItem recipeDel = new JMenuItem(del);
	
	JScrollPane scroll = null;
	
	public Gui(String name) {
		super(name);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 350, 350);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		setLayout(null);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setSize();
				setFont();
			}
		});
		
		addComponents();
		
		setVisible(true);
	}
	
	private void addComponents() {
		ActionListeners al = new ActionListeners();
		
		foodAdd.addActionListener(al);
		foodMenu.add(foodAdd);
		foodEdit.addActionListener(al);
		foodMenu.add(foodEdit);
		foodDel.addActionListener(al);
		foodMenu.add(foodDel);
		
		ingredientsAdd.addActionListener(al);
		ingredientsMenu.add(ingredientsAdd);
		ingredientsEdit.addActionListener(al);
		ingredientsMenu.add(ingredientsEdit);
		ingredientsDel.addActionListener(al);
		ingredientsMenu.add(ingredientsDel);
		
		categorysAdd.addActionListener(al);
		categorysMenu.add(categorysAdd);
		categorysEdit.addActionListener(al);
		categorysMenu.add(categorysEdit);
		categorysDel.addActionListener(al);
		categorysMenu.add(categorysDel);
		
		recipeAdd.addActionListener(al);
		recipeMenu.add(recipeAdd);
		recipeEdit.addActionListener(al);
		recipeMenu.add(recipeEdit);
		recipeDel.addActionListener(al);
		recipeMenu.add(recipeDel);
		
		bar.add(foodMenu);
		bar.add(categorysMenu);
		bar.add(ingredientsMenu);
		bar.add(recipeMenu);
		setJMenuBar(bar);
		
		add(count);
		
		generate.addActionListener(al);
		add(generate);
		
		food.setEditable(false);
		
		scroll = new JScrollPane(food);
		
		add(scroll);
	}
	
	public void setSize() {
		count.setBounds(BORDER, 20, getContentPane().getWidth() / 3, 25);
		
		generate.setLocation(count.getX() + count.getWidth() + BORDER, 20);
		generate.setSize(getWidth() - generate.getX() - BORDER, 25);
		
		scroll.setBounds(BORDER, count.getY() + count.getHeight() + BORDER, getContentPane().getWidth() - BORDER * 2,
				getContentPane().getHeight() - count.getY() - count.getHeight() - BORDER * 2);
	}
	
	public void setFont() {
		food.setFont(new Font("Tahoma", Font.PLAIN, scroll.getHeight() / food.getLineCount()));
	}
}