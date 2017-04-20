Description:

In this lab, you build a HelloWorld Java console application with Spring’s application context. 

You will create a greeting bean with the ID “greeter”, which must be specified in your application context’s configuration file, named beans.xml. The class of the greeting bean implements the following interface:

  interface Greeter {
	void setName(String name); // names of the author
	String getGreeting();
  }

The value of the name property should be specified in beans.xml as well. 

After instantiating the application context, your applications gets the greeting bean by its ID, calls its getGreeting() method to get the greeting message, and prints it on the console.

If the author is Alice, the greeting message should be “Hello world from Alice!” 


Student Details:
Name:      Sidharth Bhasin


Instructions to execute the application:
1. Please extract all the following 4 files:
Application.java
beans.xml
Greeter.java
Hello.java

2. Copy them to an existing Java Project or you can create a new Java Project.

3. Import all Spring JAR files and Spring Common Logging Files.

4. Simply run the Applicaion.java file.

You should get the following output- (Date/Time will be different)

Feb 20, 2017 10:50:39 PM org.springframework.context.support.FileSystemXmlApplicationContext prepareRefresh
INFO: Refreshing org.springframework.context.support.FileSystemXmlApplicationContext@e2144e4: startup date [Mon Feb 20 22:50:39 PST 2017]; root of context hierarchy
Feb 20, 2017 10:50:39 PM org.springframework.beans.factory.xml.XmlBeanDefinitionReader loadBeanDefinitions
INFO: Loading XML bean definitions from file [/Users/sidharth/Documents/workspace/Practice/beans.xml]
Hello world from Alice!