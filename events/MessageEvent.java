package bot.script.events;

public class MessageEvent{
	private static final long serialVersionUID = -2029419489540924081L;
	
	private String sender;
	private String message;
	private int type;
	
	public MessageEvent(String sender, String message, int type) {
		this.sender = sender;
		this.message = message;
		this.type = type;
	}
	
	public String getSender(){
		return sender;
	}
	
	public String getMessage(){
		return message;
	}
	
	public int getType(){
		return type;
	}
	
	public boolean isAutomated(){
		return sender == null;
	}
}
