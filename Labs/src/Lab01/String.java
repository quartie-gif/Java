package Lab01;

public class String {


    public static int dlugosc(java.lang.String s1) {
        return s1.length();
    }

    public static int ile_razy_literka_wystepuje(java.lang.String s1, char a) {
        int ile=0;
        for(int i=0; i< s1.length();++i)
            if (s1.charAt(i) == a) ile++;
        return ile;
    }

    public static boolean isPalindrome(java.lang.String text) {
        java.lang.String clean = text.replaceAll("\\s+", "").toLowerCase();
        StringBuilder plain = new StringBuilder(clean);
        StringBuilder reverse = plain.reverse();
        return (reverse.toString()).equals(clean);
    }

    public static boolean czy_plaindrom(java.lang.String otto) {
        return isPalindrome(otto);
    }

    public static boolean czy_takie_same(java.lang.String test1, java.lang.String test11) {
        return test1.equals(test11);
    }

    public static java.lang.String wspak(java.lang.String to_bedzie_wspak) {
        StringBuilder str = new StringBuilder(to_bedzie_wspak);
        return str.reverse().toString();

    }

    public static boolean czy_abecadlowe(java.lang.String a) {
        char charBefore = a.charAt(0);

        for (int i = 1; i < a.length(); i++) {
            if (charBefore > a.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO!!!!!
     * @return java.lang.String
     */
    public static java.lang.String rot13(java.lang.String textToRot13) {
        StringBuilder str = new StringBuilder();
        for(int i=0;i<textToRot13.length();++i)
        {
            str.append((char)(textToRot13.charAt(i) + 13));
        }
        return str.toString();
    }
}
