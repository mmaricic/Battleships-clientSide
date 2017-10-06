package battleships.commands;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import battleships.client.BattleshipsPlayer;

public class OponentsImage extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		String [] helpString = command.split(" ");
		String name = helpString[1].substring(1, helpString[1].length()-1);
		helpString = helpString[2].substring(1, helpString[2].length()-1).split(",");
		BufferedImage bufferedImage = null;
		System.out.println("usao u Image deo, duzina stringa koji je stigao je "+helpString.length);
		if(helpString.length >= 64*64*3){
			System.out.println("STIGLA SLIKA");
		bufferedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
		int count = 0;
		for(int i = 0; i <64; i++)
			for(int j = 0; j < 64;j++){
				int rgb = new Color(Integer.parseInt(helpString[count++]),Integer.parseInt(helpString[count++]),Integer.parseInt(helpString[count++])).getRGB();
				bufferedImage.setRGB(i, j, rgb);
			}
		}
	
		if(!player.getName().equals(name))
			player.insertPlayer(name, bufferedImage);
		else
			player.getControler().updateImage(bufferedImage);
	}

}
