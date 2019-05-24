package compilador;

import java.util.Stack;

/**
 *
 * @author 132683
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
                    getProxToken();
                    skip = true;
                } else {
                    skip = false;
                    System.out.println("Matching terminal: ( " + token.getLexema() + " )");
                    resultadoParser += "Matching terminal: ( " + token.getLexema() + " )\n";
                    getProxToken();
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
        for (String terminal : terminals) {
            if (s.equals(terminal)) {
                return true;
            }
        }
        return false;
    }

    private boolean naoEhTerminal(String s) {
        for (String nonTerminal : nonTerminals) {
            if (s.equals(nonTerminal)) {
                return true;
            }
        }
        return false;
    }

    private String getProxToken() {
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

    private String getRegra(String nonTerminal, String terminal) {
        int row = getNaoTerminal(nonTerminal);
        int column = getTerminal(terminal);

        String regra = preditiveTable[row][column];

        switch (regra) {
            case "synch":
                exibeErro("Erro (Synch): ( " + token.getLexema() + " ) ");
                numErros++;
                top = pop();
                String rule = this.getRegra(top, token.getLexema());
                this.pushRegra(rule);
                skip = false;
                break;
            case "skip":
                exibeErro("Erro (Skip): ( " + token.getLexema() + " ) ");
                numErros++;
                getProxToken();
                skip = true;
                break;
            default:
                skip = false;
                break;
        }
        return regra;
    }

    private int getNaoTerminal(String nonTerminal) {
        for (int i = 0; i < nonTerminals.length; i++) {
            if (nonTerminal.equals(nonTerminals[i])) {
                return i;
            }
        }
        exibeErro(nonTerminal + " não é um não terminal");
        return -1;
    }

    private int getTerminal(String terminal) {
        for (int i = 0; i < terminals.length; i++) {
            if (terminal.equals(terminals[i])) {
                return i;
            }
        }
        exibeErro(terminal + " não é um Terminal");
        return -1;
    }
}
