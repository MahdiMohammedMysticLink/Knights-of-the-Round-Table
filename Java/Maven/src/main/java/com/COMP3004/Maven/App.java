package com.COMP3004.Maven;

import java.util.Timer;

public class App {

	Timer timer;
	public static boolean nextTurn = false;

	public static void main(String[] args) {
		
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(UI.class);
			}
		}.start();
		final UI ui = UI.waitForUI();
		ui.loadCards();
		
		Model model;
		
		model = new Model(ui);
		
		if(model.isServer) {
		   model.play();
		}
		
		
	}
}