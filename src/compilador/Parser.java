package compilador;

import java.util.Stack;

/**
 *
 * @author Daniel e Pedro Ely
 */
public class Parser extends ParsingTable {

    private final Lexer lexer;
    private Token token;
    private String resultadoParser = "";
    private String top;
    private Stack<String> pilha = new Stack<>();
    public boolean synch = false;
    public boolean skip = false;
    public int numErros = 0;

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
            if (naoEhTerminal(top)) {
                String rule = this.getRegra(top, token.getLexema());
                this.pushRegra(rule);
            } else if (ehTerminal(top)) {
                if (!top.equals(token.getLexema())) {
                    resultadoParser += "Erro Sintático (Skip): Token Inesperado: ( " + token.getLexema() + " ) " + "\n";
                    ProxToken();
                    skip = true;
                } else {
                    skip = false;
                    System.out.println("Matching terminal: ( " + token.getLexema() + " )");
                    resultadoParser += "Matching terminal: ( " + token.getLexema() + " )\n";
                    ProxToken();
                }
            }
            if (token.getLexema().equals("EOF")) {
                resultadoParser += "Matching terminal: ( " + token.getLexema() + " )\n";
                break;
            }

            if (numErros == 5) {
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
        resultadoParser += regra + "\n";
        for (int i = splitRegra.length - 1; i >= 0; i--) {
            String regraAt = splitRegra[i];
            push(regraAt);
        }
    }

    private boolean ehTerminal(String s) {
        for (String terminal : terminais) {
            if (s.equals(terminal)) {
                return true;
            }
        }
        return false;
    }

    private boolean naoEhTerminal(String s) {
        for (String nonTerminal : naoTerminais) {
            if (s.equals(nonTerminal)) {
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
        resultadoParser += message + "\n";
        System.out.println(resultadoParser);
    }
    
    private void sync() {
        exibeErro("Erro (Synch): ( " + token.getLexema() + " ) ");
        numErros++;
        top = pop();
        String rule = this.getRegra(top, token.getLexema());
        this.pushRegra(rule);
        skip = false;
    }
    
    private void skip() {
        exibeErro("Erro (Skip): ( " + token.getLexema() + " ) ");
        numErros++;
        ProxToken();
        skip = true;
    }

    private String getRegra(String nT, String terminal) {
        int row = getNaoTerminal(nT);
        int column = getTerminal(terminal);

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

    private int getNaoTerminal(String nT) {
        for (int i = 0; i < naoTerminais.length; i++) {
            if (nT.equals(naoTerminais[i])) {
                return i;
            }
        }
        exibeErro(nT + " não é um não terminal");
        return -1;
    }

    private int getTerminal(String terminal) {
        for (int i = 0; i < terminais.length; i++) {
            if (terminal.equals(terminais[i])) {
                return i;
            }
        }
        exibeErro(terminal + " não é um Terminal");
        return -1;
    }
}
