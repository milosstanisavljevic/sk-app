import java.util.Scanner;

public class Main{


    public static void main(String[] args) {
        SpecifikacijaSkladista impl;
        Scanner s = new Scanner(System.in);
        System.out.println("uslo 1");

        try {
            System.out.println("uslo 2");
            Class.forName("LokalnoSkladiste");
            impl = Manager.getImpl();
            //impl.createRoot(args[0],args[1]);
            while (true){
                System.out.println("uslo3");
                String a = s.next();
                //String[] line = a.split(" ");
                //String[] g = a.split(" ", 5);
                //System.out.println(a);
                //System.out.println(line[1]);
                //System.out.println(line[1]);
                /**NE RADI AAAAAAA*/
                if(a.equalsIgnoreCase("mkdir")) {
                    System.out.println(impl);
                }
                if (a.equalsIgnoreCase("dc")){
                    break;
                }

            }


        }catch (Exception e){

            e.printStackTrace();
        }

    }

}
