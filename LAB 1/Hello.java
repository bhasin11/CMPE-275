public class Hello implements Greeter{

	private String name;

	public void setName(String name){
		this.name = name;
	}
	public String getGreeting(){
		return "Hello world from " + this.name + "!";
	}
}