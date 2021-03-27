import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int menuOption = -1;
        String[] buttons = {
                " 1. Watch cats",
                " 2. Show favourites",
                " 3. Exit"
        };

        do {

            String option = (String) JOptionPane.showInputDialog(null, "Java Cats",
                    "Main Menu", JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);

            // Validate selected option by user
            for (int i = 0; i < buttons.length; i++) {
                if(option.equals(buttons[i])) {
                    menuOption = i;
                }
            }

            switch (menuOption) {
                case 0:
                    CatsService.showCats();
                    break;
                case 1:
                    Cats cat = new Cats();
                    CatsService.showFavourites(cat.getApiKey());
                default:
                    break;
            }
        } while (menuOption != 1);
    }
}
