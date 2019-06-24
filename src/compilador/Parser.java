package compilador;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Daniel e Pedro Ely
 */
public class Parser extends ParsingTable {

    private Lexer lexer;
    private Token token;
    private String top;
    private Stack<String> pilha = new Stack<>();
    private Stack<String> pilhaAux = new Stack<>();
    public boolean synch = false;
    public boolean skip = false;
    public int erros = 0;
    public No noClasse;
    private ArrayList<Token> tokenAuxList;

    public Parser(Lexer lexer) {
        this.tokenAuxList = new ArrayList();
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
                verificaSemantica(rule);
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
                System.out.println("Fim do parser: " + token.getLexema());
                verificaSemantica();
                break;
            }
            if (erros == 5) {
                break;
            }
        } while (true);
    }

    public void verificaSemantica() {
        No noClass = new No(null);
        if (!this.pilhaAux.empty()) {
            pilhaAux.stream().map((st) -> st.split(" ")).forEach((parts) -> {
                for (int x = 0; x < parts.length; x++) {
                    if (parts[x].equals("class")) {
                        noClass.setPai(tokenAuxList.get(x));
                    } else {
                        No noFilho = new No(tokenAuxList.get(x));
                        String aux = tokenAuxList.get(x).getClasse().toString();
                        switch (aux) {
                            case "KW_BOOLEAN":
                                noFilho.setTipo(100);
                                break;
                            case "KW_INTEGER":
                                noFilho.setTipo(101);
                                break;
                            case "KW_STRING":
                                noFilho.setTipo(102);
                                break;
                            case "KW_DOUBLE":
                                noFilho.setTipo(103);
                                break;
                        }
                        noClass.addFilho(noFilho);
                    }
                    if (parts[x].equals(tokenAuxList.get(x).getLexema())) {
                        System.out.println(parts[x] + " == " + tokenAuxList.get(x).getLexema());
                    } else {
                        No noFilho = new No(tokenAuxList.get(x));
                        noFilho.setTipo(104);
                        noClass.addFilho(noFilho);
                        System.out.println("ERRO semantico: Esperado " + parts[x] + ", encontrado: " + tokenAuxList.get(x).getLexema());
                    }
                }
            });
        }
        noClass.getFilhos().stream().forEach((x) -> {
            System.out.println("Nó tipo: " + x.tipo);
        });
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
//        System.out.println("splitRegra" + splitRegra[0]);
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
        tokenAuxList.add(token);
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
        System.out.println(message);
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

    public void verificaSemantica(String regra) {
        switch (regra) {
            case "public class ID { ListaMetodo Main }":
                this.pilhaAux.push("public class ID { ListaMetodo Main }");
                break;
            case "Tipo ID":
                this.pilhaAux.push("Tipo ID");
                break;
            case "ListaMetodo’":
                this.pilhaAux.push("ListaMetodo’");
                break;
            case "ε":
                this.pilhaAux.push("ε");
                break;
            case "Metodo ListaMetodo’":
                this.pilhaAux.push("Metodo ListaMetodo’");
                break;
            case "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }":
                this.pilhaAux.push("Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }");
                break;
            case "ListaParam":
                this.pilhaAux.push("ListaParam");
                break;
            case "DeclaracaoVar RegexDeclaraVar":
                this.pilhaAux.push("DeclaracaoVar RegexDeclaraVar");
                break;
            case "Param ListaParam’":
                this.pilhaAux.push("Param ListaParam’");
                break;
            case "Tipo ID ;":
                this.pilhaAux.push("Tipo ID ;");
                break;
            case "return Expressao;":
                this.pilhaAux.push("return Expressao;");
                break;
            case "public static void main ( ) { RegexDeclaraVar ListaCmd }":
                this.pilhaAux.push("public static void main ( ) { RegexDeclaraVar ListaCmd }");
                break;
            case "boolean":
                this.pilhaAux.push("boolean");
                break;
            case "int":
                this.pilhaAux.push("int");
                break;
            case "string":
                this.pilhaAux.push("string");
                break;
            case "float":
                this.pilhaAux.push("float");
                break;
            case "void":
                this.pilhaAux.push("void");
                break;
            case "ListaCmd’":
                this.pilhaAux.push("ListaCmd’");
                break;
            case "Cmd ListaCmd’":
                this.pilhaAux.push("Cmd ListaCmd’");
                break;
            case "ID Cmd’":
                this.pilhaAux.push("ID Cmd’");
                break;
            case "CmdIF":
                this.pilhaAux.push("CmdIF");
                break;
            case "CmdWhile":
                this.pilhaAux.push("CmdWhile");
                break;
            case "CmdPrint":
                this.pilhaAux.push("CmdPrint");
                break;
            case "CmdPrintln":
                this.pilhaAux.push("CmdPrintln");
                break;
            case "CmdMetodo":
                this.pilhaAux.push("CmdMetodo");
                break;
            case "CmdAtrib":
                this.pilhaAux.push("CmdAtrib");
                break;
            case "if ( Expressao ) { Cmd } CmdIF’":
                this.pilhaAux.push("if ( Expressao ) { Cmd } CmdIF’");
                break;
            case "else { Cmd }":
                this.pilhaAux.push("else { Cmd }");
                break;
            case "while ( Expressao ) { Cmd }":
                this.pilhaAux.push("while ( Expressao ) { Cmd }");
                break;
            case "print ( Expressao ) ;":
                this.pilhaAux.push("print ( Expressao ) ;");
                break;
            case "println ( Expressao ) ;":
                this.pilhaAux.push("println ( Expressao ) ;");
                break;
            case "= Expressao ;":
                this.pilhaAux.push("= Expressao ;");
                break;
            case "( RegexExp4 ) ;":
                this.pilhaAux.push("( RegexExp4 ) ;");
                break;
            case "Exp1 Exp’":
                this.pilhaAux.push("Exp1 Exp’");
                break;
            case "&& Exp1 Exp’":
                this.pilhaAux.push("&& Exp1 Exp’");
                break;
            case "|| Exp1 Exp’":
                this.pilhaAux.push("|| Exp1 Exp’");
                break;
            case "Exp2 Exp1’":
                this.pilhaAux.push("Exp2 Exp1’");
                break;
            case "< Exp2 Exp1’":
                this.pilhaAux.push("< Exp2 Exp1’");
                break;
            case "<= Exp2 Exp1’":
                this.pilhaAux.push("<= Exp2 Exp1’");
                break;
            case "> Exp2 Exp1’":
                this.pilhaAux.push("> Exp2 Exp1’");
                break;
            case ">= Exp2 Exp1’":
                this.pilhaAux.push(">= Exp2 Exp1’");
                break;
            case "== Exp2 Exp1’":
                this.pilhaAux.push("== Exp2 Exp1’");
                break;
            case "!= Exp2 Exp1’":
                this.pilhaAux.push("!= Exp2 Exp1’");
                break;
            case "Exp3 Exp2’":
                this.pilhaAux.push("Exp3 Exp2’");
                break;
            case "+ Exp3 Exp2’":
                this.pilhaAux.push("+ Exp3 Exp2’");
                break;
            case "- Exp3 Exp2’":
                this.pilhaAux.push("- Exp3 Exp2’");
                break;
            case "Exp4 Exp3’":
                this.pilhaAux.push("Exp4 Exp3’");
                break;
            case "* Exp4 Exp3’":
                this.pilhaAux.push("* Exp4 Exp3’");
                break;
            case "/ Exp4 Exp3’":
                this.pilhaAux.push("/ Exp4 Exp3’");
                break;
            case "ID Exp4’":
                this.pilhaAux.push("ID Exp4’");
                break;
            case "( Expressao)":
                this.pilhaAux.push("( Expressao)");
                break;
            case "ConstInteira":
                this.pilhaAux.push("ConstInteira");
                break;
            case "ConstReal":
                this.pilhaAux.push("ConstReal");
                break;
            case "ConstString":
                this.pilhaAux.push("ConstString");
                break;
            case "true":
                this.pilhaAux.push("true");
                break;
            case "false":
                this.pilhaAux.push("false");
                break;
            case "OpUnario Expressao":
                this.pilhaAux.push("OpUnario Expressao");
                break;
            case "( RegexExp4 )":
                this.pilhaAux.push("( RegexExp4 )");
                break;
            case "Expressao RegexExp4’":
                this.pilhaAux.push("Expressao RegexExp4’");
                break;
            case ", Expressao RegexExp4":
                this.pilhaAux.push(", Expressao RegexExp4");
                break;
            case "-":
                this.pilhaAux.push("-");
                break;
            case "!":
                this.pilhaAux.push("!");
                break;
        }
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
