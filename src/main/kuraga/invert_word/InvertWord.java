package kuraga.invert_word;

public class InvertWord {
    public InvertWord() {}

    public void startLoop() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        while(true) {
            System.out.print("   Enter phrase to invert or \"$quit\":\n\t");
            String str = sc.nextLine();
            if(str.compareTo("$quit") == 0) {
                break;
            }
            System.out.println("   Inverted phrase:\n\t" + InvertWord.invert(str) + "\n");
        }
        sc.close();
    }

    public static String invert(String str) {
        return String.valueOf(new StringBuilder(str).reverse());
    }
}
