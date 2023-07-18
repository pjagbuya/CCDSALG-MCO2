


// PAUL - Some painting of some strings to be printed
public class Paint {


    public static void turnOnCyan(){
        System.out.print("\033[1;36m");
    }
    public static void turnOnOrange(){
        System.out.print("\033[1;38;5;202m");
    }
    public static void turnOnRed(){
        System.out.print("\033[1;31m");
    }
    public static void turnOnYellow(){
        System.out.print("\033[1;33m");
    }
    public static void turnOnGreen(){
        System.out.print("\033[1;32m");
    }
    public static void turnOffColor(){
        System.out.print("\033[0m");
    }


    public static String paintTextOrange(String sText)
    {
        return "\033[1;38;5;202m" + sText + "\033[0m";
    }

    public static String paintTextCyan(String sText)
    {
        return "\033[1;36m" + sText + "\033[0m";
    }

    public static String paintTextRed(String sText)
    {
        return "\033[1;31m" + sText + "\033[0m";
    }
    
    public static String paintTextYellow(String sText)
    {
        return "\033[1;33m" + sText + "\033[0m";
    }
    public static String paintTextMagenta(String sText)
    {
        return "\033[1;35m" + sText + "\033[0m";
    }
    public static String paintTextGreen(String sText)
    {
        return "\033[1;32m" + sText + "\033[0m";
    }

    public static void paintWinner(String sText)
    {
        if (sText.contains("A")) {

            turnOnCyan();
            System.out.println("   ____   U  ___ u  _   _     ____     ____        _       _____   ____     ");
            System.out.println("U /\"___|   \\/\"_ \\/ | \\ |\"| U /\"___|uU |  _\"\\ u U  /\"\\  u  |_ \" _| / __\"| u  ");
            System.out.println("\\| | u     | | | |<|  \\| |>\\| |  _ / \\| |_) |/  \\/ _ \\/     | |  <\\___ \\/   ");
            System.out.println(" | |/__.-,_| |_| |U| |\\  |u | |_| |   |  _ <    / ___ \\    /| |\\  u___) |   ");
            System.out.println("  \\____|\\_)-\\___/  |_| \\_|   \\____|   |_| \\_\\  /_/   \\_\\  u |_|U  |____/>>  ");
            System.out.println(" _// \\\\      \\\\    ||   \\\\,-._)(|_    //   \\\\_  \\\\    >>  _// \\\\_  )(  (__) ");
            System.out.println("(__)(__)    (__)   (_\")  (_/(__)__)  (__)  (__)(__)  (__)(__) (__)(__)      ");
            System.out.println();
            System.out.println("  ____      _         _      __   __U _____ u   ____            _           ");
            System.out.println("U|  _\"\\ u  |\"|    U  /\"\\  u  \\ \\ / /\\| ___\"|/U |  _\"\\ u     U  /\"\\  u       ");
            System.out.println("\\| |_) |/U | | u   \\/ _ \\/    \\ V /  |  _|\"   \\| |_) |/      \\/ _ \\/        ");
            System.out.println(" |  __/   \\| |/__  / ___ \\   U_|\"|_u | |___    |  _ <        / ___ \\        ");
            System.out.println(" |_|       |_____|/_/   \\_\\    |_|   |_____|   |_| \\_\\      /_/   \\_\\       ");
            System.out.println(" ||>>_     //  \\\\  \\\\    >>.-,//|(_  <<   >>   //   \\\\_      \\\\    >>       ");
            System.out.println("(__)__)   (_\")(\"_)(__)  (__)\\_) (__)(__) (__) (__)  (__)    (__)  (__)      ");
            turnOffColor();
            
        }
        else{

            turnOnOrange();
            System.out.println("   ____   U  ___ u  _   _     ____     ____        _       _____   ____     ");
            System.out.println("U /\"___|   \\/\"_ \\/ | \\ |\"| U /\"___|uU |  _\"\\ u U  /\"\\  u  |_ \" _| / __\"| u  ");
            System.out.println("\\| | u     | | | |<|  \\| |>\\| |  _ / \\| |_) |/  \\/ _ \\/     | |  <\\___ \\/   ");
            System.out.println(" | |/__.-,_| |_| |U| |\\  |u | |_| |   |  _ <    / ___ \\    /| |\\  u___) |   ");
            System.out.println("  \\____|\\_)-\\___/  |_| \\_|   \\____|   |_| \\_\\  /_/   \\_\\  u |_|U  |____/>>  ");
            System.out.println(" _// \\\\      \\\\    ||   \\\\,-._)(|_    //   \\\\_  \\\\    >>  _// \\\\_  )(  (__) ");
            System.out.println("(__)(__)    (__)   (_\")  (_/(__)__)  (__)  (__)(__)  (__)(__) (__)(__)      ");
            System.out.println();
            System.out.println("  ____      _         _      __   __U _____ u   ____           ____         ");
            System.out.println("U|  _\"\\ u  |\"|    U  /\"\\  u  \\ \\ / /\\| ___\"|/U |  _\"\\ u     U | __\")u       ");
            System.out.println("\\| |_) |/U | | u   \\/ _ \\/    \\ V /  |  _|\"   \\| |_) |/      \\|  _ \\/       ");
            System.out.println(" |  __/   \\| |/__  / ___ \\   U_|\"|_u | |___    |  _ <         | |_) |       ");
            System.out.println(" |_|       |_____|/_/   \\_\\    |_|   |_____|   |_| \\_\\        |____/        ");
            System.out.println(" ||>>_     //  \\\\  \\\\    >>.-,//|(_  <<   >>   //   \\\\_      _|| \\\\_        ");
            System.out.println("(__)__)   (_\")(\"_)(__)  (__)\\_) (__)(__) (__) (__)  (__)    (__) (__)       ");
            turnOffColor();
        }


    }


    public static void paintRoundWinner(String sText){
        if (sText.contains("A")) {
            turnOnCyan();
            System.out.println(" █████      ██     ██ ██ ███    ██ ███████ ");
            System.out.println("██   ██     ██     ██ ██ ████   ██ ██      ");
            System.out.println("███████     ██  █  ██ ██ ██ ██  ██ ███████ ");
            System.out.println("██   ██     ██ ███ ██ ██ ██  ██ ██      ██ ");
            System.out.println("██   ██      ███ ███  ██ ██   ████ ███████ ");
            System.out.println("                                           ");
            turnOffColor();
        }
        else{
            turnOnOrange();
            System.out.println("██████      ██     ██ ██ ███    ██ ███████ ");
            System.out.println("██   ██     ██     ██ ██ ████   ██ ██      ");
            System.out.println("██████      ██  █  ██ ██ ██ ██  ██ ███████ ");
            System.out.println("██   ██     ██ ███ ██ ██ ██  ██ ██      ██ ");
            System.out.println("██████       ███ ███  ██ ██   ████ ███████ ");
            System.out.println("                                           ");
            turnOffColor();
        }
    }
}
