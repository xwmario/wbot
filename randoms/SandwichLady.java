package bot.script.randoms;

import bot.script.Random;
import bot.script.methods.Interfaces;
import bot.script.methods.Npcs;
import bot.script.methods.Players;
import bot.script.wrappers.NPC;
import bot.script.util.Filter;

public class SandwichLady extends Random{
	public int LADY = 3117;
	public boolean valid = false;
	String ladyText;
	
	@Override
	public boolean isValid() {
		NPC sl = Npcs.getNearest(new Filter<NPC>(){
			@Override
			public boolean accept(NPC t) {
				return t.getId() == LADY && t.getSpokenText() != null &&  t.getSpokenText().contains(Players.getLocal().getName());
			}
		});
		if (sl != null){
			valid = true;
		}
		return valid;
	}

	@Override
	public String getName() {
		return "Sandwich Lady";
	}
	
	@Override
	public int loop(){
		NPC sl = Npcs.getNearest(LADY);
		if (sl == null){
			valid = false;
			return -1;
		}
		sl.interact("Talk-to");
		for (int i = 0; i < 100 && !Interfaces.getInterface(4887, 4890).getText().contains("look hungry to me"); i++){
			if (Players.getLocal().isMoving()) i = 0;
			sleep(50);
		}
		if (Interfaces.getInterface(4887, 4890).getText().contains("look hungry to me")){
			String ladyText = Interfaces.getInterface(4887, 4891).getText();
			Interfaces.getInterface(4887, 4892).click();
			sleep(4000);
			if (ladyText.contains("redberry pie")){
				Interfaces.getInterface(16135, 16137).click();
			}else if (ladyText.contains("kebab")){
				Interfaces.getInterface(16135, 16138).click();
			}else if (ladyText.contains("chocolate bar")){
				Interfaces.getInterface(16135, 16139).click();
			}else if (ladyText.contains("baguette")){
				Interfaces.getInterface(16135, 16140).click();
			}else if (ladyText.contains("triangle sandwich")){
				Interfaces.getInterface(16135, 16141);
			}else if (ladyText.contains("square sandwic")){
				Interfaces.getInterface(16135, 16142).click();
			}else if (ladyText.contains("bread roll")){
				Interfaces.getInterface(16135, 16143).click();
			}
			return -1;
		}
		return 1;
	}
}
