package bot.script.randoms;

import nl.wbot.bot.Bot;
import bot.script.Random;
import bot.script.methods.Game;
import bot.script.methods.Interfaces;
import bot.script.methods.Keyboard;
import bot.script.methods.Mouse;

public class Login extends Random{

	int tried = 0;
	
	@Override
	public boolean isValid(){
		return !Game.inGame() && Bot.get().getAccount() != null;
	}

	@Override
	public String getName() {
		return "Login";
	}
	
	@Override
	public int loop(){
		if (Game.inGame()){
			sleep(2000);
			return -1;
		}
		if (tried > 2){
			log("Cannot login");
			return -1;
		}
		while(Interfaces.getParentInterfaces().length < 300){
			sleep(100);
		}
		System.out.println(Game.getLoginState());
		switch(Game.getLoginState()){
		case 0:
			Mouse.click(460, 290);
			sleep(200);
			break;
			
		case 2:
			if (!Game.getUsername().equals(Game.getAccount().getUsername()) || !Game.getPassword().equals(Game.getAccount().getPassword())){
				while(Game.getUsername().length() > 0){
					removeInvalidDetails(253, Game.getUsername().length());
				}
				while(Game.getPassword().length() > 0){
					removeInvalidDetails(269, Game.getPassword().length());
					Keyboard.pressKey('\n');
					sleep(300);
				}
			}
			if (Game.getUsername().equals("") && Game.getPassword().equals("")){
				Keyboard.typeText(Game.getAccount().getUsername(), true);
				sleep(500);
				Keyboard.typeText(Game.getAccount().getPassword(), false);
			}
			if (Game.getUsername().equals(Game.getAccount().getUsername()) && Game.getPassword().equals(Game.getAccount().getPassword())){
				Mouse.click(bot.script.util.Random.nextInt(245, 350) , bot.script.util.Random.nextInt(310, 330));
				for (int i = 0; i < 100 && !Game.inGame(); i++) sleep(60);
				tried++;
			}
			break;
			
		case 3:
			Mouse.click(380, 317);
			sleep(200);
			break;
			
			default:
				return -1;
		}
		return 1;
	}
	
	public void removeInvalidDetails(int y, int length){
		Mouse.click(435+bot.script.util.Random.nextInt(0, 76), y+bot.script.util.Random.nextInt(0, 7));
		sleep(200);
		for (int i = 0; i < length + 1; i++){
			Keyboard.pressKey('\b');
			sleep(bot.script.util.Random.nextInt(70, 150));
		}
	}
}
