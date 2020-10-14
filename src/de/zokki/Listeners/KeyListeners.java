package de.zokki.Listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListeners extends KeyAdapter{
	
	public static enum possibleKeys{
		DIGITS, ALPHABET, NULL
	}
	
	possibleKeys keys;
	
	public KeyListeners(possibleKeys allowedKeys) {
		keys = allowedKeys;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
	}

	@Override
	public void keyTyped(KeyEvent event) {
		switch (keys) {
			case DIGITS:
				if(!Character.isDigit(event.getKeyChar())) {
					event.consume();
				}
				break;
			
			case ALPHABET:
				if(!Character.isAlphabetic(event.getKeyChar()) && (int) event.getKeyChar() != 32) {
					event.consume();
				}
				break;
				
			case NULL:
				event.consume();
				break;
				
			default:
				break;
		}
	}	
}