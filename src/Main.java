import javax.swing.*;
import java.awt.*;

public class Main {

    public  static  void  login_page (){
        UI_Login login = new UI_Login();
        login.setContentPane(login.Login_panal);
        login.setTitle("Matcha Cafe - Login");
        int w ;
        int h ;
        w =800;
        h=800;
        login.setSize(new Dimension(w, h));
        login.setVisible(true);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
     login_page();
    }

}