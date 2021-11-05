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
        List<Object>list = new ArrayList<>();
        //String argument = args[0];


        try {

            System.out.println("\nDa biste pokrenuli program ukucajte komandu mkdir zajedno za putanjom\nna kojoj zelite " +
                    "da napravite skladiste i imenom skladista");


            Class.forName("LokalnoSkladiste");
            impl = Manager.getImpl();

            while (true){

                String m = s.nextLine();

                String[] a = m.split(" ");
                for (String ss : a){
                    list.add(ss);
                }

                if(list.get(0).toString().equalsIgnoreCase("mkdir")) {
                    System.out.println(impl);
                    path = list.get(1) + "\\" + list.get(2);

                    if(impl.checkIfRootExists(path)){

                        if(connectUser(path, impl)) {
                            connected = true;
                            System.out.println("\nUspesno ste se konektovali na skladiste, izaberite sledece opcije\n" +
                                    "1. Manipulacija skladistem komanda: -mnp\n" +
                                    "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit parameteri: arg0 (size) arg1 (filetype) arg2 (maxFiles)\n" +
                                    "3. Dodaj korisnika komanda: -addUser\n" +
                                    "4. Izadjite iz programa komanda: dc");
                        }else{
                            System.out.println("\nNemate pristup putanji ili niste uspesno uneli kredencijale");
                        }

                    }else {
                        connectSuperUser(path, impl);
                    }
                }

                switch (list.get(0).toString()){
                    case ("-mnp"):
                        System.out.println("mnp");
                        break;

                    case("-cnfgEdit"):
                        impl.updateConfig(path, (int) list.get(1), (String) list.get(2), (int) list.get(3));
                        System.out.println(path + list.get(1) + list.get(2) + list.get(3));
                        break;

                    case("-addUser"):
                        addUserProgram(path, impl);
                        break;

                }

                if (list.get(0).toString().equalsIgnoreCase("dc")){
                    break;
                }

                list.clear();
            }


        }catch (Exception e){

            e.printStackTrace();
        }

    }

    public static boolean connectUser(String path, SpecifikacijaSkladista impl){

        System.out.println("\nUnesite username i password kako biste se ulogovali na vase skladiste");

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
        System.out.println("\nUnesite username i password kako biste se ulogovali na vase skladiste");

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
        System.out.println("\nVase skladiste je uspesno napravljeno, izaberite sledece opcije\n" +
                "1. Manipulacija skladistem komanda: -mnp\n" +
                "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit parameteri: arg0 (size) arg1 (filetype) arg2 (maxFiles)\n" +
                "3. Dodaj korisnika komanda: -addUser\n" +
                "4. Izadjite iz programa komanda: dc");

    }
     public static void addUserProgram(String path, SpecifikacijaSkladista impl){
         System.out.println("\nKako biste uneli novog korisnika ukucajte komandu: -add argumente: arg1 (name) arg2 (password) arg3 (privilege)" +
                 "\nUkoliko ne zelite da dodajete vise ukucajte komandu -ret");

         String username= null;
         String password = null;
         String privilege = null;
         List<String> list = new ArrayList<>();
         Scanner s1 = new Scanner(System.in);

         String ar = s1.nextLine();
         String[] ar1 = ar.split(" ");

         for (String ss : ar1) {
             list.add(ss);
         }

         username = list.get(1);
         password = list.get(2);
         privilege = list.get(3);

         if(list.get(0).toString().equalsIgnoreCase("-add") && list.size()==4){
             impl.addUser(path, username, password, privilege);
             list.clear();
         }else if(list.get(0).toString().equalsIgnoreCase("-ret")){
             return;
         }else{
             System.out.println("\nKako biste uneli novog korisnika ukucajte komandu: -add argumente: arg1 (name) arg2 (password) arg3 (privilege)" +
                     "\nUkoliko ne zelite da dodajete vise ukucajte komandu -ret");
         }
     }
}
