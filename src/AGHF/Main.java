package AGHF;

import java.awt.EventQueue;

/*
 * Thing that you run
 */
public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					StartGameView game = new StartGameView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
