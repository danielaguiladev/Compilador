package compilador;

/**
 *
 * @author Daniel e Pedro Ely
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class ParserBottomUp {

    static String rawInput;
    static int number_Of_Tokens = 0;

    public static void errorMessage() {
        System.out.println("Syntax Error");
        System.exit(0);
    }

    public static int getIndex(String token) throws IOException {
        int indexA = -1;
        switch (token) {
            case "id":
                indexA = 0;
                break;
            case "+":
                indexA = 1;
                break;
            case "*":
                indexA = 2;
                break;
            case "(":
                indexA = 3;
                break;
            case ")":
                indexA = 4;
                break;
            case "$":
                indexA = 5;
                break;
            case "public":
                indexA = 6;
                break;
            case "static":
                indexA = 7;
                break;
            case "void":
                indexA = 8;
                break;
            case "main":
                indexA = 9;
                break;
            case "Classe":
                indexA = 10;
                break;
            default:
                errorMessage();
        }
        return indexA;
    }

    public static String[][] ParseTable() {
        String[][] parseTable = 
        {
            {
                "Classe", "synch", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "public class ID { ListaMetodo Main }", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "synch", "skip",
                "Tipo ID ;", "Tipo ID ;", "Tipo ID ;", "Tipo ID ;",
                "Tipo ID ;", "synch", "skip", "synch", "synch",
                "synch", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "ListaMetodo’", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "ListaMetodo’", " ListaMetodo’", "ListaMetodo’",
                "ListaMetodo’", "ListaMetodo’", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "ε", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "Metodo ListaMetodo’",
                "Metodo ListaMetodo’", "Metodo ListaMetodo’", "Metodo ListaMetodo’",
                "Metodo ListaMetodo’", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "synch", "skip",
                "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                "synch", "skip", "synch", "synch", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "ε", "skip", "skip", "skip",
                "ListaParam", "ListaParam", "ListaParam", "ListaParam",
                "ListaParam", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "ε", "skip", "ε", "skip",
                "skip", "skip", "skip", "ε", "skip", "DeclaracaoVar RegexDeclaraVar",
                "DeclaracaoVar RegexDeclaraVar", "DeclaracaoVar RegexDeclaraVar",
                "DeclaracaoVar RegexDeclaraVar", "DeclaracaoVar RegexDeclaraVar",
                "ε", "skip", "ε", "ε", "ε", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "synch", "skip", "skip", "skip",
                "Param ListaParam’", "Param ListaParam’",
                "Param ListaParam’", "Param ListaParam’",
                "Param ListaParam’", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "ε", "ListaParam", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "synch", "synch", "skip", "skip",
                "Tipo ID", "Tipo ID", "Tipo ID", "Tipo ID", "Tipo ID",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "ε",
                "skip", "skip", "skip", "skip", "return Expressao;",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip"
            },
            {
                "public static void main ( ) { RegexDeclaraVar ListaCmd }",
                "skip", "skip", "skip", "skip", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "boolean", "int", "string", "float", "void", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "ListaCmd’", "skip",
                "ListaCmd’", "skip", "skip", "skip", "skip",
                "ListaCmd’", "skip", "skip", "skip", "skip",
                "skip", "skip", "ListaCmd’", "skip", "ListaCmd’",
                "ListaCmd’", "ListaCmd’", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "Cmd ListaCmd’", "skip",
                "ε", "skip", "skip", "skip", "skip", "ε", "skip",
                "skip", "skip", "skip", "skip", "skip", "Cmd ListaCmd’",
                "skip", "Cmd ListaCmd’", "Cmd ListaCmd’", "Cmd ListaCmd’",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "ID Cmd’", "skip", "synch",
                "skip", "skip", "skip", "skip", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "CmdIF", "skip", "CmdWhile",
                "CmdPrint", "CmdPrintln", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "synch", "skip",
                "CmdMetodo", "skip", "skip", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "synch", "skip", "synch",
                "synch", "synch", "CmdAtrib", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "synch", "skip",
                "skip", "skip", "skip", "synch", "skip", "skip", "skip",
                "skip", "skip", "skip", "if ( Expressao ) { Cmd } CmdIF’",
                "skip", "synch", "synch", "synch", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "ε", "skip", "ε", "skip",
                "skip", "skip", "skip", "ε", "skip", "skip", "skip",
                "skip", "skip", "skip", "ε", "else { Cmd }", "ε", "ε",
                "ε", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "synch",
                "skip", "skip", "skip", "skip", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "synch", "skip",
                "while ( Expressao ) { Cmd }", "synch", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "synch",
                "skip", "skip", "skip", "skip", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "synch",
                "skip", "synch", "print ( Expressao ) ;", "synch",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "synch", "skip",
                "skip", "skip", "skip", "synch", "skip", "skip", "skip",
                "skip", "skip", "skip", "synch", "skip", "synch", "skip",
                "println ( Expressao ) ;", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "synch",
                "skip", "skip", "skip", "skip", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "synch",
                "skip", "synch", "synch", "synch", "= Expressao ;",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "synch",
                "skip", "( RegexExp4 ) ;", "skip", "skip", "synch",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "synch", "skip", "synch", "synch", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "Exp1 Exp’", "skip", "skip",
                "synch", "Exp1 Exp’", "synch", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "Exp1 Exp’", "Exp1 Exp’", "Exp1 Exp’",
                "Exp1 Exp’", "Exp1 Exp’", "Exp1 Exp’", "Exp1 Exp’"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip", "ε",
                "skip", "ε", "ε", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "&& Exp1 Exp’", "|| Exp1 Exp’",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "Exp2 Exp1’", "skip", "skip",
                "synch", "Exp2 Exp1’", "synch", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "synch", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "Exp2 Exp1’", "Exp2 Exp1’", "Exp2 Exp1’",
                "Exp2 Exp1’", "Exp2 Exp1’", "Exp2 Exp1’", "Exp2 Exp1’"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip", "ε",
                "skip", "ε", "ε", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "ε", "ε", "< Exp2 Exp1’", "<= Exp2 Exp1’ ",
                "> Exp2 Exp1’", ">= Exp2 Exp1’", "== Exp2 Exp1’",
                "!= Exp2 Exp1’", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "Exp3 Exp2’", "skip", "skip",
                "synch", "Exp3 Exp2’", "synch", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "synch", "synch", "synch",
                "synch", "synch", "synch", "synch", "synch", "skip", "skip",
                "skip", "skip", "Exp3 Exp2’", "Exp3 Exp2’", "Exp3 Exp2’",
                "Exp3 Exp2’", "Exp3 Exp2’", "Exp3 Exp2’", "Exp3 Exp2’"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip", "ε",
                "skip", "ε", "ε", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "+ Exp3 Exp2’",
                "- Exp3 Exp2’", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "Exp4 Exp3’", "skip", "skip",
                "synch", "Exp4 Exp3’", "synch", "synch", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "synch", "synch", "synch",
                "synch", "synch", "synch", "synch", "synch", "synch", "synch",
                "skip", "skip", "Exp4 Exp3’", "Exp4 Exp3’", "Exp4 Exp3’",
                "Exp4 Exp3’", "Exp4 Exp3’", "Exp4 Exp3’", "Exp4 Exp3’"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip", "ε",
                "skip", "ε", "ε", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε",
                "* Exp4 Exp3’", "/ Exp4 Exp3’", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "ID Exp4’", "skip", "skip",
                "synch", "( Expressao)", "synch", "synch", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "synch", "synch", "synch", "synch", "synch", "synch",
                "synch", "synch", "synch", "synch", "synch", "synch",
                "ConstInteira", "ConstReal", "ConstString", "true", "false",
                "OpUnario Expressao", "OpUnario Expressao"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip", "ε",
                "( RegexExp4 )", "ε", "ε", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "ε", "ε", "ε", "ε", "ε", "ε",
                "ε", "ε", "ε", "ε", "ε", "ε", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip"
            },
            {
                "skip", "skip", "skip", "Expressao RegexExp4’", "skip",
                "skip", "skip", "Expressao RegexExp4’", "ε", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "Expressao RegexExp4’",
                "Expressao RegexExp4’", "Expressao RegexExp4’",
                "Expressao RegexExp4’", "Expressao RegexExp4’",
                "Expressao RegexExp4’", "Expressao RegexExp4’"
            },
            {
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "ε", ", Expressao RegexExp4", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip"
            },
            {
                "skip", "skip", "skip", "synch", "skip", "skip",
                "skip", "synch", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "skip",
                "skip", "skip", "skip", "skip", "skip", "d",
                "synch", "synch", "synch", "synch", "-", "!"
            }
        };
        return parseTable;
    }

    public static String rule(String action) {
        String r = "";
        switch (action) {
            case "r1":
                r = "Programa → Classe $";
                break;
            case "r2":
                r = "Classe → public class ID { ListaMetodo Main }";
                break;
            case "r3":
                r = "DeclaracaoVar → Tipo ID ;";
                break;
            case "r4":
                r = "ListaMetodo → ListaMetodo’";
                break;
            case "r5":
                r = "ListaMetodo’ → Metodo ListaMetodo’";
                break;
            case "r6":
                r = "ListaMetodo’ → ε";
                break;
            case "r7":
                r = "Metodo → Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }";
                break;
            case "r8":
                r = "RegexListaParam → ListaParam";
                break;
            case "r9":
                r = "RegexListaParam → ε";
                break;
            case "r10":
                r = "RegexDeclaraVar → DeclaracaoVar RegexDeclaraVar";
                break;
            case "r11":
                r = "RegexDeclaraVar → ε";
                break;
            case "r12":
                r = "ListaParam → Param ListaParam’";
                break;
            case "r13":
                r = "ListaParam’ → , ListaParam";
                break;
            case "r14":
                r = "ListaParam’ → ε";
                break;
            case "r15":
                r = "Param → Tipo ID";
                break;
            case "r16":
                r = "Retorno → return Expressao;";
                break;
            case "r17":
                r = "Retorno → ε";
                break;
            case "r18":
                r = "Main → public static void main ( ) { RegexDeclaraVar ListaCmd }";
                break;
            case "r19":
                r = "Tipo → boolean";
                break;
            case "r20":
                r = "Tipo → int";
                break;
            case "r21":
                r = "Tipo → string";
                break;
            case "r22":
                r = "Tipo → float";
                break;
            case "r23":
                r = "Tipo → void";
                break;
            case "r24":
                r = "ListaCmd → ListaCmd’";
                break;
            case "r25":
                r = "ListaCmd’ → Cmd ListaCmd’";
                break;
            case "r26":
                r = "ListaCmd’ → ε";
                break;
            case "r27":
                r = "Cmd → CmdIF";
                break;
            case "r28":
                r = "Cmd → CmdWhile";
                break;
            case "r29":
                r = "Cmd → CmdPrint";
                break;
            case "r30":
                r = "Cmd → CmdPrintln";
                break;
            case "r31":
                r = "Cmd → ID Cmd’";
                break;
            case "r32":
                r = "Cmd’ → CmdAtrib";
                break;
            case "r33":
                r = "Cmd’ → CmdMetodo";
                break;
            case "r34":
                r = "CmdIF → if ( Expressao ) { Cmd } CmdIF’";
                break;
            case "r35":
                r = "CmdIF’ → else { Cmd }";
                break;
            case "r36":
                r = "CmdIF’ → ε";
                break;
            case "r37":
                r = "CmdWhile → while ( Expressao ) { Cmd }";
                break;
            case "r38":
                r = "CmdPrint → print ( Expressao ) ;";
                break;
            case "r39":
                r = "CmdPrintln → println ( Expressao ) ;";
                break;
            case "r40":
                r = "CmdAtrib → = Expressao ;";
                break;
            case "r41":
                r = "CmdMetodo → ( RegexExp4 ) ;";
                break;
            case "r42":
                r = "Expressao → Exp1 Exp’";
                break;
            case "r43":
                r = "Exp’ → && Exp1 Exp’";
                break;
            case "r44":
                r = "Exp’ → || Exp1 Exp’";
                break;
            case "r45":
                r = "Exp’ → ε";
                break;
            case "r46":
                r = "Exp1 → Exp2 Exp1’";
                break;
            case "r47":
                r = "Exp1’ → < Exp2 Exp1’";
                break;
            case "r48":
                r = "Exp1’ → <= Exp2 Exp1’}";
                break;
            case "r49":
                r = "Exp1’ → > Exp2 Exp1’";
                break;
            case "r50":
                r = "Exp1’ → >= Exp2 Exp1’";
                break;
            case "r51":
                r = "Exp1’ → == Exp2 Exp1’";
                break;
            case "r52":
                r = "Exp1’ → != Exp2 Exp1’}";
                break;
            case "r53":
                r = "Exp1’ → ε";
                break;
            case "r54":
                r = "Exp2 → Exp3 Exp2’";
                break;
            case "r55":
                r = "Exp2’ → + Exp3 Exp2’";
                break;
            case "r56":
                r = "Exp2’ → - Exp3 Exp2’";
                break;
            case "r57":
                r = "Exp2’ → ε";
                break;
            case "r58":
                r = "Exp3 → Exp4 Exp3’";
                break;
            case "r59":
                r = "Exp3’ → * Exp4 Exp3’";
                break;
            case "r60":
                r = "Exp3’ → / Exp4 Exp3’ ";
                break;
            case "r61":
                r = "Exp3’ → ε";
                break;
            case "r62":
                r = "Exp4 → ID Exp4’";
                break;
            case "r63":
                r = "Exp4 → ConstInteira";
                break;
            case "r64":
                r = "Exp4 → ConstReal";
                break;
            case "r65":
                r = "Exp4 → ConstString";
                break;
            case "r66":
                r = "Exp4 → true";
                break;
            case "r67":
                r = "Exp4 → false";
                break;
            case "r68":
                r = "Exp4 → OpUnario Expressao";
                break;
            case "r69":
                r = "Exp4 → ( Expressao)";
                break;
            case "r70":
                r = "Exp4’ → ( RegexExp4 )";
                break;
            case "r71":
                r = "Exp4’ → ε";
                break;
            case "r72":
                r = "RegexExp4 → Expressao RegexExp4’";
                break;
            case "r73":
                r = "RegexExp4 → ε";
                break;
            case "r74":
                r = "RegexExp4’ → , Expressao RegexExp4’";
                break;
            case "r75":
                r = "RegexExp4’ → ε";
                break;
            case "r76":
                r = "OpUnario → -";
                break;
            case "r77":
                r = "OpUnario → !";
                break;
        }
        return r;
    }

    public static String remainingInput(String inputString, int number_Of_Tokens, int tokenCount) {
        StringTokenizer st = new StringTokenizer(inputString);
        String remaining = "";
        for (int j = 0; j < number_Of_Tokens; j++) {
            st.nextToken();
        }
        for (int j = number_Of_Tokens; j < tokenCount; j++) {
            remaining = remaining.concat(st.nextToken());
        }
        return remaining;
    }

    public static void main(String[] args) throws IOException {
        String[][] parseTable = ParseTable();
        File f = new File("input.txt");
        Scanner input = new Scanner(f);
        while (input.hasNext()) {
            rawInput = input.next();
        }
        String inputString = rawInput;
        StringTokenizer st = new StringTokenizer(inputString);
        Stack s = new Stack();
        s.push("0");
        int tokenCount = st.countTokens();
        String str = st.nextToken();
        do {
            int index = getIndex(str);
            String action = parseTable[Integer.parseInt((String) s.peek())][index];
            switch (action) {
                case "skip":
                    String number = parseTable[Integer.parseInt((String) s.peek())][index].substring(1);
                    String ri = remainingInput(inputString, number_Of_Tokens, tokenCount);
                    number_Of_Tokens++;
                    System.out.printf("%-30s %20s %s %s\n", s.toString(), ri, "Shift by  ", action);
                    s.push(str);
                    s.push(number);
                    str = st.nextToken();
                    break;
                case "sync":
                    number = parseTable[Integer.parseInt((String) s.peek())][index].substring(1);
                    String rule = rule(action);
                    String rin = remainingInput(inputString, number_Of_Tokens, tokenCount);
                    System.out.printf("%-30s %20s %s %s %s %s %s\n", s.toString(), rin, "Reduce by  ", rule, "(", action, ")");
                    int popTimes = 6;
                    if (Integer.parseInt(number) % 2 == 0) {
                        popTimes = 2;
                    }
                    for (int i = 0; i < popTimes; i++) {
                        s.pop();
                    }
                    String ls = "";
                    switch (Integer.parseInt(number)) {
                        case 1:
                            ls = "Programa";
                            break;
                        case 2:
                            ls = "Classe";
                            break;
                        case 3:
                            ls = "DeclaracaoVar";
                            break;
                        case 4:
                            ls = "ListaMetodo";
                            break;
                        case 5:
                            ls = "ListaMetodo’";
                            break;
                        case 6:
                            ls = "ListaMetodo’";
                            break;
                        case 7:
                            ls = "Metodo ";
                            break;
                        case 8:
                            ls = "RegexListaParam";
                            break;
                        case 9:
                            ls = "RegexListaParam";
                            break;
                        case 10:
                            ls = "RegexDeclaraVar";
                            break;
                        case 11:
                            ls = "RegexDeclaraVar";
                            break;
                        case 12:
                            ls = "ListaParam";
                            break;
                        case 13:
                            ls = "ListaParam’";
                            break;
                        case 14:
                            ls = "ListaParam’";
                            break;
                        case 15:
                            ls = "Param";
                            break;
                        case 16:
                            ls = "Retorno";
                            break;
                        case 17:
                            ls = "Retorno";
                            break;
                        case 18:
                            ls = "Main";
                            break;
                        case 19:
                            ls = "Tipo";
                            break;
                        case 20:
                            ls = "Tipo";
                            break;
                        case 21:
                            ls = "Tipo";
                            break;
                        case 22:
                            ls = "Tipo";
                            break;
                        case 23:
                            ls = "Tipo";
                            break;
                        case 24:
                            ls = "ListaCmd";
                            break;
                        case 25:
                            ls = "ListaCmd’";
                            break;
                        case 26:
                            ls = "ListaCmd’";
                            break;
                        case 27:
                            ls = "Cmd";
                            break;
                        case 28:
                            ls = "Cmd";
                            break;
                        case 29:
                            ls = "Cmd";
                            break;
                        case 30:
                            ls = "Cmd";
                            break;
                        case 31:
                            ls = "Cmd";
                            break;
                        case 32:
                            ls = "Cmd’";
                            break;
                        case 33:
                            ls = "Cmd’";
                            break;
                        case 34:
                            ls = "CmdIF";
                            break;
                        case 35:
                            ls = "CmdIF’";
                            break;
                        case 36:
                            ls = "CmdIF’";
                            break;
                        case 37:
                            ls = "CmdWhile";
                            break;
                        case 38:
                            ls = "CmdPrint";
                            break;
                        case 39:
                            ls = "CmdPrintln";
                            break;
                        case 40:
                            ls = "CmdAtrib";
                            break;
                        case 41:
                            ls = "CmdMetodo";
                            break;
                        case 42:
                            ls = "Expressao";
                            break;
                        case 43:
                            ls = "Exp’";
                            break;
                        case 44:
                            ls = "Exp’";
                            break;
                        case 45:
                            ls = "Exp’";
                            break;
                        case 46:
                            ls = "Exp1";
                            break;
                        case 47:
                            ls = "Exp1’";
                            break;
                        case 48:
                            ls = "Exp1’";
                            break;
                        case 49:
                            ls = "Exp1’";
                            break;
                        case 50:
                            ls = "Exp1’";
                            break;
                        case 51:
                            ls = "Exp1’";
                            break;
                        case 52:
                            ls = "Exp1’";
                            break;
                        case 53:
                            ls = "Exp1’";
                            break;
                        case 54:
                            ls = "Exp2";
                            break;
                        case 55:
                            ls = "Exp2’";
                            break;
                        case 56:
                            ls = "Exp2’";
                            break;
                        case 57:
                            ls = "Exp2’";
                            break;
                        case 58:
                            ls = "Exp3";
                            break;
                        case 59:
                            ls = "Exp3’";
                            break;
                        case 60:
                            ls = "Exp3’";
                            break;
                        case 61:
                            ls = "Exp3’";
                            break;
                        case 62:
                            ls = "Exp4";
                            break;
                        case 63:
                            ls = "Exp4";
                            break;
                        case 64:
                            ls = "Exp4";
                            break;
                        case 65:
                            ls = "Exp4";
                            break;
                        case 66:
                            ls = "Exp4";
                            break;
                        case 67:
                            ls = "Exp4";
                            break;
                        case 68:
                            ls = "Exp4";
                            break;
                        case 69:
                            ls = "Exp4";
                            break;
                        case 70:
                            ls = "Exp4’";
                            break;
                        case 71:
                            ls = "Exp4’";
                            break;
                        case 72:
                            ls = "RegexExp4";
                            break;
                        case 73:
                            ls = "RegexExp4";
                            break;
                        case 74:
                            ls = "RegexExp4’";
                            break;
                        case 75:
                            ls = "RegexExp4’";
                            break;
                        case 76:
                            ls = "OpUnario";
                            break;
                        case 77:
                            ls = "OpUnario";
                            break;
                    }
                    int indexA = 6;
                    switch (ls) {
                        case "public":
                            indexA = 6;
                            break;
                        case "static":
                            indexA = 7;
                            break;
                        case "void":
                            indexA = 8;
                            break;
                        case "main":
                            indexA = 9;
                            break;
                        case "Classe":
                            indexA = 10;
                            break;
                    }
                    int num = Integer.parseInt(parseTable[Integer.parseInt((String) s.peek())][indexA]);
                    s.push(ls + "");
                    s.push(num + "");
                    break;
                default:
                    System.out.printf("%-49s %s\n", s.toString(), "$ ACCEPT");
                    System.exit(0);
            }
        } while (true);
    }
}
