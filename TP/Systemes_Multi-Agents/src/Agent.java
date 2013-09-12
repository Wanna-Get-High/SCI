

public class Agent {

	protected int x;
	protected int y;
	protected Environment environment;
	
	public Agent(Environment env) {
		this.environment = env;
		this.init();
	}
	
	private void init() {
		this.environment.getPlace(this);
	}
	
	public void decide() {
		// TODO
	}
	
	public void x (int x){
		this.x = x;
	}
	
	public void y (int y) {
		this.y = y;
	}
}