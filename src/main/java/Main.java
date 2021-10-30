public class Main{


    public static void main(String[] args) {
        SpecifikacijaSkladista impl;
        try {
            Class.forName("LokalnoSkladiste");
            impl = Manager.getImpl();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
