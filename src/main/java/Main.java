import javax.swing.plaf.multi.MultiSeparatorUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        String username = null;
        String password = null;
        String path = null;
        boolean connected = false;
        SpecifikacijaSkladista impl;
        Scanner s = new Scanner(System.in);
        List<String>list = new ArrayList<>();
        //String argument = args[0];


        try {

            System.out.println("Da biste pokrenuli program ukucajte komandu mkdir zajedno za putanjom\nna kojoj zelite " +
                    "da napravite skladiste i imenom skladista");


            Class.forName("LokalnoSkladiste");
            impl = Manager.getImpl();

            while (true){

                String m = s.nextLine();

                String[] a = m.split(" ");
                for (String ss : a){
                    list.add(ss);
                }

                if(list.get(0).equalsIgnoreCase("mkdir")) {
                    System.out.println(impl);
                    path = list.get(1) + "\\" + list.get(2);

                    if(impl.checkIfRootExists(path)){

                        if(connectUser(path, impl)) {
                            connected = true;
                            System.out.println("Uspesno ste se konektovali na skladiste, izaberite sledece opcije\n" +
                                    "1. Manipulacija skladistem komanda: -mnp\n" +
                                    "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit parameteri: arg0 (size) arg1 (filetype) arg2 (maxFiles)\n" +
                                    "3. Dodaj korisnika komanda: -addUser\n" +
                                    "4. Izadjite iz programa komanda: dc");
                        }else{
                            System.out.println("Nemate pristup putanji ili niste uspesno uneli kredencijale");
                        }

                    }else {
                        connectSuperUser(path, impl);
                    }
                }

                switch (list.get(0)){
                    case ("-mnp"):
                        System.out.println("mnp");
                        break;

                    case("-cnfgEdit"):
                        System.out.println("config");
                        break;

                    case("-addUser"):
                        System.out.println("addUser");
                        break;

                }

                if (list.get(0).equalsIgnoreCase("dc")){
                    break;
                }

                list.clear();
            }


        }catch (Exception e){

            e.printStackTrace();
        }

    }

    public static boolean connectUser(String path, SpecifikacijaSkladista impl){

        System.out.println("Unesite username i password kako biste se ulogovali na vase skladiste");

        String username = null;
        String password = null;
        List<String> list = new ArrayList<>();
        Scanner s1 = new Scanner(System.in);

        String ar = s1.nextLine();
        String[] ar1 = ar.split(" ");

        for (String ss : ar1){
            list.add(ss);
        }

        if(list.size() == 2){

            username = list.get(0);
            System.out.println("username: " + username);
            password = list.get(1);
            System.out.println("password: " + password);

            if(impl.checkUser(path, username, password)){
                list.clear();
                return true;
            }
                list.clear();
                return false;
        }else{
            list.clear();
            return false;
        }
    }

    public static void connectSuperUser(String path, SpecifikacijaSkladista impl) {
        System.out.println("Unesite username i password kako biste se ulogovali na vase skladiste");

        String username = null;
        String password = null;
        List<String> list = new ArrayList<>();
        Scanner s1 = new Scanner(System.in);

        String ar = s1.nextLine();
        String[] ar1 = ar.split(" ");

        for (String ss : ar1) {
            list.add(ss);
        }

        if (list.size() == 2) {

            username = list.get(0);
            System.out.println("username: " + username);
            password = list.get(1);
            System.out.println("password: " + password);
        }

        impl.createRoot(path, username, password);
        System.out.println("Vase skladiste je uspesno napravljeno, izaberite sledece opcije\n" +
                "1. Manipulacija skladistem komanda: -mnp\n" +
                "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit parameteri: arg0 (size) arg1 (filetype) arg2 (maxFiles)\n" +
                "3. Dodaj korisnika komanda: -addUser\n" +
                "4. Izadjite iz programa komanda: dc");

    }
}
