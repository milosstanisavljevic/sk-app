import javax.swing.plaf.multi.MultiSeparatorUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{


    public static void main(String[] args) {
        SpecifikacijaSkladista impl;
        Scanner s = new Scanner(System.in);
        List<String>list = new ArrayList<>();
        //String argument = args[0];
        String username = null;
        String password = null;

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

                    if(impl.checkIfRootExists(list.get(1), list.get(2))) {

                        System.out.println("Unesite username i password kako biste se ulogovali na vase skladiste");


                        if(list.size() == 2){

                            username = list.get(0);
                            System.out.println("username" + username);
                            password = list.get(1);
                            System.out.println("username" + password);

                        }else{
                            System.out.println("Unesite username i password kako biste se ulogovali na vase skladiste");
                        }

//                        System.out.println("Uspesno ste se konektovali na skladiste, izaberite sledece opcije\n" +
//                                "1. Manipulacija skladistem komanda: -mnp\n" +
//                                "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit\n" +
//                                "3. Dodaj korisnika komanda: -addUser\n" +
//                                "4. Izadjite iz programa komanda: dc");

                    }else {


                        if (impl.createRoot(list.get(1), list.get(2))) {

                            System.out.println("Unesite username i password kako biste se ulogovali na vase skladiste");

//                            System.out.println("Vase skladiste je uspesno napravljeno, izaberite sledece opcije\n" +
//                                    "1. Manipulacija skladistem komanda: -mnp\n" +
//                                    "2. Izmeni konfiguraciju skladista komanda: -cnfgEdit\n" +
//                                    "3. Dodaj korisnika komanda: -addUser\n" +
//                                    "4. Izadjite iz programa komanda: dc");


                        } else {
                            System.out.println("Pogresno ste zadali putanju, pokusajte ponovo!");
                        }
                    }
                }else{
                    System.out.println("Da biste pokrenuli program ukucajte komandu mkdir zajedno za putanjom\nna kojoj zelite " +
                            "da napravite skladiste i imenom skladista");
                    continue;
                }


                switch (list.get(0)){
                    case ("-mnp"):
                        //ss
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

}
