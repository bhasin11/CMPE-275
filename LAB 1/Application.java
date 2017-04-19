import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Application {
	public static void main(String[] args) {
		String fileName = "/beans.xml";
		ApplicationContext ctx = new FileSystemXmlApplicationContext(fileName);
		
		Hello hello = (Hello) ctx.getBean("greeter");
		
		System.out.print(hello.getGreeting());
	}
}