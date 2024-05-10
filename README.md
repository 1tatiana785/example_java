
For UI Tests you need  to use Classes

Button for buttons controls 
Windows for navigation and all actions with pages
Fields for all actions with fields

Ex: creation of UI Test 
folder Test

@Listeners({TestMethodCapture.class, Listener.class})
public class SignInTests {

    @Test
    public void SignInPass()
    {
        new SignIn().logIn("test","pass");
    }
}


Ex creation Page :

Create class instance for classes  what  you want to  use:

        
        Class Button  - use if you need  work with all  methods of button if you want  click 
        
        Class Windows - use if you want work with windows if you want change window or close
            
        Class Fields - use it you want work with field  send some text or get text from element
        
        Class Mouse - use  if you want work with mouse  click  drag and drop
         
        Class ElementProprties = use if you need  get atribute class 

Ex:
    private Button button= new Button();
    private Windows windows=new Windows();
    private Fields fields= new Fields();

    private By emailField=By.linkText("");
    private By passField=By.id("");

    public void logIn(String user, String pass)
    {
        windows.navigate("url");
        fillUserName(user);
        fillPassword(pass);
        clickSignIn();

    }

    private void fillUserName(String user)
    {
        fields.type(emailField,user);
    }

    private void fillPassword(String pass)
    {

        fields.type(passField,pass);
    }

    private void clickSignIn()
    {
        button.click(logInBtn);
    }
   
  Open new  page you  need  use  windows.navigate(url)
  All tests  write  only in category Test 
    
    
Конфигарационные данные  хранятся:
 resources->config.properties
 
headless= set  true  if  you want run browser in invise 
