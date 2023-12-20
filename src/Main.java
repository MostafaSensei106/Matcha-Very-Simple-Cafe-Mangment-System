import javax.swing.*;
import java.awt.*;

public class Main {

    public static void login_page() {
        UI_Login login_page = new UI_Login();
        login_page.setContentPane(login_page.Login_panal);
        login_page.setTitle("Matcha Cafe - Login");
        int w = 500;
        int h = 300;
        login_page.setSize(new Dimension(w, h));
        login_page.setVisible(true);
        login_page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String args[]) {
        login_page();
    }
}
