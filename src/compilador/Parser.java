package compilador;

import java.util.Stack;

/**
 *
 * @author Daniel e Pedro Ely
 */
public class Parser extends ParsingTable {

    private final Lexer lexer;
    private Token token;
    private String top;
    private Stack<String> pilha = new Stack<>();
    public boolean synch = false;
    public boolean skip = false;
    public int erros = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.token = lexer.proxToken();
    }

    public void initParser() {

        push("Classe");
        top = null;
        do {
            if (skip == false) {
                top = pop();
            }
            getTokenByRegra(top, token);
            if (verifica(top, naoTerminais)) {
                String rule = this.getRegra(top, token.getLexema());
                this.pushRegra(rule);
            } else if (verifica(top, terminais)) {
                if (!top.equals(token.getLexema())) {
                    exibeErro("Erro Sintático (Skip): Token Inesperado: ( " + token.getLexema() + " ) ");
                    ProxToken();
                    skip = true;
                } else {
                    skip = false;
                    System.out.println("Terminal: ( " + token.getLexema() + " )");
                    ProxToken();
                }
            }
            if (token.getLexema().equals("EOF")) {
                System.out.println("Fim da análise: " + token.getLexema());
                break;
            }

            if (erros == 5) {
                break;
            }
        } while (true);
    }

    public void getTokenByRegra(String top, Token token) {
        switch (token.getClasse()) {
            case ID:
                token.setLexema("ID");
                break;
            case STRING:
                token.setLexema("ConstString");
                break;
            case INTEGER:
                token.setLexema("ConstInteira");
                break;
            case FLOAT:
                token.setLexema("ConstReal");
                break;
            case ConstString:
                token.setLexema("ConstString");
                break;
            default:
                break;
        }
    }

    private void pushRegra(String regra) {
        String[] splitRegra = regra.split("\\s+");
        for (int i = splitRegra.length - 1; i >= 0; i--) {
            String regraAt = splitRegra[i];
            push(regraAt);
        }
    }

    private boolean verifica(String s, String[] ss) {
        for (String x : ss) {
            if (s.equals(x)) {
                return true;
            }
        }
        return false;
    }

    private String ProxToken() {
        token = lexer.proxToken();
        String tokenLexema = token.getLexema();
        return tokenLexema;
    }

    private void push(String s) {
        this.pilha.push(s);
    }

    private String pop() {
        return this.pilha.pop();
    }

    private void exibeErro(String message) {
        System.out.println("Erro semântico: " + message);
    }

    private void sync() {
        exibeErro("(Synch): ( " + token.getLexema() + " ) ");
        erros++;
        top = pop();
        String rule = this.getRegra(top, token.getLexema());
        this.pushRegra(rule);
        skip = false;
    }

    private void skip() {
        exibeErro("(Skip): ( " + token.getLexema() + " ) ");
        erros++;
        ProxToken();
        skip = true;
    }

    private String getRegra(String nT, String terminal) {
        int row = busca(nT, naoTerminais);
        int column = busca(terminal, terminais);

        String regra = tabelaPreditiva[row][column];

        switch (regra) {
            case "synch":
                sync();
                break;
            case "skip":
                skip();
                break;
            default:
                skip = false;
                break;
        }
        return regra;
    }

    private int busca(String terminal, String[] ss) {
        for (int i = 0; i < ss.length; i++) {
            if (terminal.equals(ss[i])) {
                return i;
            }
        }
        exibeErro("ERROR" + terminal);
        return -1;
    }
}
