package sk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SkTest {


    public static void main(String[] args) {
        String username = null;
        String password = null;
        String path = null;

        String path1 = null;
        boolean connected = false;
        SpecifikacijaSkladista impl;
        Scanner s = new Scanner(System.in);
        List<String>list = new ArrayList<>();
        //String argument = args[0];


        try {
                Class.forName("sk.LokalnoSkladiste");
                //Class.forName("sk.GoogleDriveSkladiste");
                impl = Manager.getImpl();

            System.out.println("\nDa biste pokrenuli program ukucajte komandu mkdir zajedno za putanjom\nna kojoj zelite " +
                    "da napravite skladiste i imenom skladista");


            while (true){

                String m = s.nextLine();


                String[] a = m.split(" ");
                for (String ss : a){
                    list.add(ss);
                }

                if(list.get(0).equalsIgnoreCase("mkdir")) {
                    path = list.get(1) + "\\" + list.get(2);
                    path1 = list.get(1);

                    if(impl.checkIfRootExists(path, list.get(2))){
                        impl.loadUsers(path);
                        if(connectUser(path, impl)) {
                            path = impl.getPath();
                            System.out.println("\nUspesno ste se konektovali na skladiste, izaberite sledece opcije\n" +
                                    "1. Manipulacija skladistem komanda: -mnp\n" +
                                    "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit parameteri: arg0 (size) arg1 (filetype) arg2 (maxFiles)\n" +
                                    "3. Dodaj korisnika komanda: -addUser\n" +
                                    "4. Ulazak u folder komanda: -cd parametar: folderName\n" +
                                    "5. Izlistavanje fajlova i foldera skladista: -ls\n" +
                                    "6. Izadjite iz programa komanda: exit");
                        }else{
                            System.out.println("\nNemate pristup putanji ili niste uspesno uneli kredencijale");
                        }

                    }else {
                        connectSuperUser(path1, list.get(2), impl);
                        path = impl.getPath();
                        System.out.println(path);
                    }
                }

                switch (list.get(0)){
                    case ("-mnp"):
                        fileManipulation(path, impl);
                        break;

                    case("-cnfgEdit"):
                        if(impl.updateConfig(path, Integer.parseInt(list.get(1)), list.get(2), Integer.parseInt(list.get(3)))){
                            System.out.println("Uspesno ste promenili konfiguraciju sa kredencijalima: " + path + list.get(1) + list.get(2) + list.get(3));
                        }else {
                            System.out.println("Nemate privilegiju za promenu konfiguracije");
                        }
                        break;

                    case("-addUser"):
                        addUserProgram(path, impl);
                        break;

                    case("-ls"):
                        listProgram(path, impl);
                        break;

                    case("-cd"):
                        path = path + "\\" + list.get(1);
                        System.out.println("\nUspesno ste se usli unutar foldera " + list.get(1).toString() + " izaberite sledece opcije\n" +
                                "1. Manipulacija skladistem komanda: -mnp\n" +
                                "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit parameteri: arg0 (size) arg1 (filetype) arg2 (maxFiles)\n" +
                                "3. Dodaj korisnika komanda: -addUser\n" +
                                "4. Ulazak u folder komanda: -cd parametar: folderName\n" +
                                "5. Izlistavanje fajlova i foldera skladista: -ls\n" +
                                "6. Izadjite iz programa komanda: exit");
                        break;

                }

                if (list.get(0).equalsIgnoreCase("exit")){
                    break;
                }

                list.clear();
            }


        }catch (Exception e){

            e.printStackTrace();
        }

    }

    public static boolean connectUser(String p, SpecifikacijaSkladista impl){

        System.out.println("\nUnesite username i password kako biste se ulogovali na vase skladiste");

        String username;
        String password;
        List<String> list;
        Scanner s1 = new Scanner(System.in);

        String ar = s1.nextLine();
        list = parseFunction(ar);

        if(list.size() == 2){

            username = list.get(0);
            password = list.get(1);

            if(impl.checkUser(p, username, password)){
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

    public static void connectSuperUser(String path, String name, SpecifikacijaSkladista impl) {
        System.out.println("\nUnesite username i password kako biste se ulogovali na vase skladiste");

        String username = null;
        String password = null;
        List<String> list = new ArrayList<>();
        Scanner s1 = new Scanner(System.in);

        String ar = s1.nextLine();
        list = parseFunction(ar);

        if (list.size() == 2) {

            username = list.get(0);
            System.out.println("username: " + username);
            password = list.get(1);
            System.out.println("password: " + password);
        }

        impl.createRoot(path, name, username, password);
        System.out.println("\nVase skladiste je uspesno napravljeno, izaberite sledece opcije\n" +
                "1. Manipulacija skladistem komanda: -mnp\n" +
                "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit parameteri: arg0 (size) arg1 (filetype) arg2 (maxFiles)\n" +
                "3. Dodaj korisnika komanda: -addUser\n" +
                "4. Ulazak u folder komanda: -cd parametar: folderName\n" +
                "5. Izlistavanje fajlova i foldera skladista: -ls\n" +
                "6. Izadjite iz programa komanda: exit");

    }

    public static void listProgram(String path, SpecifikacijaSkladista impl){
        System.out.println("-Ukoliko zelite da izlistate fajlove i direktorijume: ls 1");

        List<String> list;
        Scanner s1 = new Scanner(System.in);
        String ar = s1.nextLine();
        list = parseFunction(ar);
        List<String>sorted = new ArrayList<>();

        String[] files;

        if(list.size()==2 && list.get(0).equalsIgnoreCase("ls")){
            int number = Integer.parseInt(list.get(1));

            switch (number){

                case(1):
                    files = impl.listFiles(path);

                    for (String f: files) {
                        System.out.println(f);
                    }
                    break;
                case(2):
                    files = impl.listFiles(path);
                    for(String s:files){
                        sorted.add(s);
                    }
                    Collections.sort(sorted);
                    for (String s : sorted){
                        System.out.println(s);
                    }
                    break;
                case(3):
                    files = impl.listFiles(path);
                    for(String s:files){
                        sorted.add(s);
                    }
                    Collections.sort(sorted,Collections.reverseOrder());
                    for (String s : sorted){
                        System.out.println(s);
                    }
                    break;
            }
        }
    }


     public static void addUserProgram(String path, SpecifikacijaSkladista impl){
         System.out.println("\nKako biste uneli novog korisnika ukucajte komandu: -add argumente: arg1 (name) arg2 (password) arg3 (privilege)" +
                 "\nUkoliko ne zelite da dodajete vise ukucajte komandu -ret");

         String username= null;
         String password = null;
         String privilege = null;
         List<String> list = new ArrayList<>();
         Scanner s1 = new Scanner(System.in);


         while (true) {

             String ar = s1.nextLine();
             list = parseFunction(ar);


             if (list.get(0).equalsIgnoreCase("-add") && list.size() == 4) {
                 username = list.get(1);
                 password = list.get(2);
                 privilege = list.get(3);
                 if(impl.addUser(path, username, password, privilege)){
                     System.out.println("Uspesno ste dodali user-a " + username);
                 }else{
                     System.out.println("Nemate privilegiju za dodavanje user-a");
                 }
                 list.clear();
             } else if (list.get(0).equalsIgnoreCase("-ret")) {
                 return;
             } else {
                 System.out.println("%");
                 System.out.println("\nKako biste uneli novog korisnika ukucajte komandu: -add argumente: arg1 (name) arg2 (password) arg3 (privilege)" +
                         "\nUkoliko ne zelite da dodajete vise ukucajte komandu -ret");
                 return;
             }
         }
     }

     public static void fileManipulation(String path, SpecifikacijaSkladista impl){

         System.out.println(path);

         System.out.println("Usli ste u program manipulacija skladista\n" +
                 "******\n-Ukoliko zelite da dodate fajl ukucajte komandu: -add -file argument: filename .filetype\n" +
                 "-Ukoliko zelite da dodate folder ukucajte komandu: -add -folder argument:  foldername \n" +
                 "-Ukoliko zelite da dodate vise fajlova ukucajte komandu: -add -files argument:  numberOfFiles .filetype\n" +
                 "-Ukoliko zelite da dodate vise foldera ukucajte komandu: -add -folders argument:  numberOfFolders\n\n" +
                 "******\n-Ukoliko zelite da obrisete folder ukucajte komandu: -delete -folder argument: foldername\n" +
                 "-Ukoliko zelite da obrisete fajl ukucajte komandu: -delete -file argument: filename\n\n" +
                 "******\n-Ukoliko zelite da premestite fajl iz odredjenog foldera u drugi unesite komandu: -move argument: putanjaizKogFoldera putanjauKojiFolder fileName\n" +
                 "-Ukoliko zelite da skinete/download-ujete odredjeni fajl ukucajte komadnu: -download argument: putanja imeFajla\n" +
                 "-Ukoliko zelite da kopirate fajl iz odredjenog foldera u drugi unesite komandu: -copy argument: putanjaizKogFoldera putanjauKojiFolder fileName\n");

         List<String> list;
         Scanner s1 = new Scanner(System.in);


         String filename = null;
         String foldername = null;
         String filetype = null;
         int numberOfFiles = 0;
         int numberOfFolders = 0;
         String ar = s1.nextLine();

         list = parseFunction(ar);

         if(list.get(0).equalsIgnoreCase("-add") && list.get(1).equalsIgnoreCase("-file") && list.size() == 4){
             filetype = list.get(3);
             if(filetype.equalsIgnoreCase(impl.checkConfigType(path, "filetype").toString())) {
                 System.out.println("Ovaj tip fajla nije podrzan");
             }else{
                 filename = list.get(2);
                 if(impl.createFile(path, filename + filetype)){
                     System.out.println("Uspesno ste dodali file, izasli ste iz programa za manipulaciju");
                 }else{
                     System.out.println("Nemate privilegije");
                 }
             }
             return;
         }

         if(list.get(0).equalsIgnoreCase("-add") && list.get(1).equalsIgnoreCase("-files") && list.size() == 4){
             filetype = list.get(3);
             if(filetype.equalsIgnoreCase(impl.checkConfigType(path, "filetype").toString())) {
                 System.out.println("Ovaj tip fajla nije podrzan");
             }else{
                 numberOfFiles = Integer.parseInt(list.get(2));
                 impl.createMoreFiles(path, numberOfFiles, filetype);
             }
             System.out.println("Izasli ste iz programa za manipulaciju");
             return;
         }


         if(list.get(0).equalsIgnoreCase("-add") && list.get(1).equalsIgnoreCase("-folder") && list.size() == 3){
             foldername = list.get(2);
             if(impl.createFolder(path, foldername)){
                 System.out.println("Uspesno ste dodali folder, izasli ste iz programa za manipulaciju ");
                 return;
             }else {
                 System.out.println("Nemate privilegije");
                 return;
             }
         }
         if(list.get(0).equalsIgnoreCase("-add") && list.get(1).equalsIgnoreCase("-folders") && list.size() == 3){
             numberOfFolders = Integer.parseInt(list.get(2));
             impl.createMoreFolders(path, numberOfFolders);
             System.out.println("Izasli ste iz programa za manipulaciju");
             return;
         }

         if(list.get(0).equalsIgnoreCase("-delete") && list.get(1).equalsIgnoreCase("-folder") && list.size() == 3){
             foldername = list.get(2);
             if(impl.deleteFolder(path, foldername)){
                 System.out.println("Uspesno ste obrisali folder, izasli ste iz programa za manipulaciju ");
             }else{
                 System.out.println("Nemate privilegije");
             }
             return;
         }

         if(list.get(0).equalsIgnoreCase("-delete") && list.get(1).equalsIgnoreCase("-file") && list.size() == 3){
             filename = list.get(2);
             if(impl.deleteFile(path, filename)){
                 System.out.println("Uspesno ste obrisali fajl, izasli ste iz programa za manipulaciju ");

             }else{
                 System.out.println("Nemate privilegije");

             }
             return;
         }

         if(list.get(0).equalsIgnoreCase("-move") && list.size() == 4){
             if(impl.moveFromTo(list.get(1), list.get(2), list.get(3))){
                 System.out.println("Uspesno ste premestili fajl, izasli ste iz programa za manipulaciju ");
             }else{
                 System.out.println("Nemate privilegije");
             }
             return;
         }

         if(list.get(0).equalsIgnoreCase("-copy") && list.size() == 4){
             if(impl.copyPasteFiles(list.get(1), list.get(2), list.get(3))){
                 System.out.println("Uspesno ste skinuli fajl, izasli ste iz programa za manipulaciju ");
             }else{
                 System.out.println("Nemate privilegije");
             }
             return;
         }

         if(list.get(0).equalsIgnoreCase("-download") && list.size() == 3){
             if(impl.downloadFile(list.get(1), list.get(2))){
                 System.out.println("Uspesno ste kopirali fajl, izasli ste iz programa za manipulaciju ");
             }else{
                 System.out.println("Nemate privilegije");
             }
             return;
         }

         System.out.println("Izasli ste iz programa za manipulaciju");


     }

     public static List<String> parseFunction(String s){
         List<String> list = new ArrayList<>();
         String[] ar1 = s.split(" ");
         for (String ss : ar1) {
             list.add(ss);
         }
         return list;
     }
}
