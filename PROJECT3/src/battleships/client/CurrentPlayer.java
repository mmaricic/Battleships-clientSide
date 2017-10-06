package battleships.client;

import java.awt.image.BufferedImage;

import battleships.common.Table;

public class CurrentPlayer {

	private String name;
	private BufferedImage image;
	

	public CurrentPlayer(String name, BufferedImage image){
		this.name = name;
		this.image = image;
	}
	
	public CurrentPlayer(CurrentPlayer currentPlayer) {
		this.name = new String(currentPlayer.getName());
		this.image = currentPlayer.getImage();
	}

	public String getName(){
		return name;
	}
	
	public BufferedImage getImage(){
		return image;
	}

	public void setImage(BufferedImage temp) {
		image = temp;
	}
}
