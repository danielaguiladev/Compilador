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

    public int qtdErrosSintaticos = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.token = lexer.proxToken();
    }

    public void initParser() {

        push("Programa");

        top = null;

        do {
            if (skip == false) {
                top = pop();
            }

            getTokenByRegra(top, token);

            if (isNonTerminal(top)) {
                String rule = this.getRegra(top, token.getLexema());
                this.pushRegra(rule);
            } else if (isTerminal(top)) {
                if (!top.equals(token.getLexema())) {
                    resultadoParser += "Erro Sintático (Skip): Token Inesperado: ( " + token.getLexema() + " ) " + "\n";
                    getNextToken();
                    skip = true;
                } else {
                    skip = false;
                    System.out.println("Matching terminal: ( " + token.getLexema() + " )");
                    resultadoParser += "Matching terminal: ( " + token.getLexema() + " )\n";
                    getNextToken();
                }
            }

            if (token.getLexema().equals("EOF")) {
                resultadoParser += "Matching terminal: ( " + token.getLexema() + " )\n";
                break; // Fim da análise sintática
            }

            if (qtdErrosSintaticos == 5) {
                break; // Encerra a análise sintática
            }

        } while (true);

    }

    //  Alterando o Lexema caso o TOKEN case com o topo pra avanço da entrada
    //  (Exemplo: se lexema for "varchar", logo, lexema é um id, para comparação
    /*
     Topo da Pilha:
     'literal' para qualquer ASCII entre aspas duplas
     'id' para qualquer palavra começando com letra seguido de letra ou número
     'num_const' para qualquer número (double ou inteiro)
     'char_const' para qualquer caractere ASCII entre aspas simples    
     */
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

    // Empilha regra da gramática na pilha (ao inverso)
    private void pushRegra(String regra) {
        // Divide a string contendo a regra em um array
        String[] splitRegra = regra.split("\\s+");
        // Saída completa /* (DESCOMENTAR LINHA ABAIXO)*/
        resultadoParser += regra + "\n";
        for (int i = splitRegra.length - 1; i >= 0; i--) {
            String regraAt = splitRegra[i];
            push(regraAt);
        }
    }

    // Verifica se elemento do topo é um terminal
    private boolean isTerminal(String s) {
        for (String terminal : terminals) {
            if (s.equals(terminal)) {
                return true;
            }
        }
        return false;
    }

    // Verifica se elemento do topo é um não-terminal
    private boolean isNonTerminal(String s) {
        for (String nonTerminal : nonTerminals) {
            if (s.equals(nonTerminal)) {
                return true;
            }
        }
        return false;
    }

    // Advance
    // Recebe o próximo Token
    private String getNextToken() {
        token = lexer.proxToken();
        String tokenLexema = token.getLexema();
        return tokenLexema;
    }

    // Empilha elemento no topo da pilha
    private void push(String s) {
        this.pilha.push(s);
    }

    // Desempilha elemento do topo
    private String pop() {
        return this.pilha.pop();
    }

    // Exibe mensagem de erro
    private void exibeErro(String message) {
        resultadoParser += message + "\n";
        System.out.println(resultadoParser);
    }

    // Recupera a regra da tabela conforme o index[nonTerm][term]
    private String getRegra(String nonTerminal, String terminal) {

        int row = getNonTerminalIndex(nonTerminal);
        int column = getTerminalIndex(terminal);

        String regra = preditiveTable[row][column];

        switch (regra) {
            case "synch":
                exibeErro("Erro Sintático (Synch): Token Inesperado: ( " + token.getLexema() + " ) ");
                qtdErrosSintaticos++;
                top = pop();
                String rule = this.getRegra(top, token.getLexema());
                this.pushRegra(rule);
                skip = false;
                break;
            case "skip":
                exibeErro("Erro Sintático (Skip): Token Inesperado: ( " + token.getLexema() + " ) ");
                qtdErrosSintaticos++;
                getNextToken();
                skip = true;
                break;
            default:
                skip = false;
                break;
        }

        return regra;
    }

    // Recupera o index do não terminal de interesse
    private int getNonTerminalIndex(String nonTerminal) {

        for (int i = 0; i < nonTerminals.length; i++) {
            if (nonTerminal.equals(nonTerminals[i])) {
                return i;
            }
        }

        exibeErro(nonTerminal + " não é um nonTerminal");
        return -1;
    }

    // Recupera o index do terminal de interesse
    private int getTerminalIndex(String terminal) {

        for (int i = 0; i < terminals.length; i++) {
            if (terminal.equals(terminals[i])) {
                return i;
            }
        }
        exibeErro(terminal + " não é um Terminal");
        return -1;
    }
}
