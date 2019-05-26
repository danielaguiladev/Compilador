package compilador;

/**
 *
 * @author Daniel e Pedro Ely
 */

public class Compilador {
    public static TS tabelaSimbolos;
    public static void main(String[] args) {
        // TODO code application logic here
        Lexer lexer = new Lexer(tabelaSimbolos, "hellojavinha.jvn");
        Parser parser = new Parser(lexer);
        parser.initParser();
    }
}
