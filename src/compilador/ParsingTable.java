package compilador;

/**
 *
 * @author 132683
 */
public class ParsingTable {

    //Tabela Preditiva   
    String[][] preditiveTable
            = {
                //Programa
                {"Classe", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip"},
                //Classe
                {"public class ID { ListaMetodo Main }", "synch", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip"},
                //DeclaracaoVar
                {"skip", "skip", "skip", "synch", "skip", "skip", "skip", "skip", "skip",
                    "skip", "synch", "skip", "Tipo ID ;", "Tipo ID ;", "Tipo ID ;", "Tipo ID ;",
                    "Tipo ID ;", "synch", "skip", "synch", "synch", "synch", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //ListaMetodo
                {" ListaMetodo’", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", " ListaMetodo’", " ListaMetodo’",
                    "ListaMetodo’", " ListaMetodo’", " ListaMetodo’", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip"},
                //ListaMetodo’
                {"ε", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", " Metodo ListaMetodo’", "Metodo ListaMetodo’",
                    "Metodo ListaMetodo’", "Metodo ListaMetodo’", "Metodo ListaMetodo’",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip"},
                //Metodo
                {"skip", "skip", "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip",
                    "synch", "skip",
                    "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                    "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                    "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                    "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                    "Tipo ID ( RegexListaParam ) { RegexDeclaraVar ListaCmd Retorno }",
                    "synch", "skip", "synch", "synch", "synch", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip"},
                //RegexListaParam
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "ε", "skip",
                    "skip", "skip", "ListaParam", "ListaParam", "ListaParam", "ListaParam",
                    "ListaParam", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //RegexDeclaraVar
                {"skip", "skip", "skip", "ε", "skip", "ε", "skip", "skip", "skip", "skip", "ε",
                    "skip", "DeclaracaoVar RegexDeclaraVar", "DeclaracaoVar RegexDeclaraVar",
                    "DeclaracaoVar RegexDeclaraVar", "DeclaracaoVar RegexDeclaraVar",
                    "DeclaracaoVar RegexDeclaraVar", "ε", "skip", "ε", "ε", "ε", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //ListaParam
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "synch",
                    "skip", "skip", "skip", "Param ListaParam’ ", "Param ListaParam’ ",
                    "Param ListaParam’ ", "Param ListaParam’ ", "Param ListaParam’ ", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip"},
                //ListaParam’
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "ε",
                    ",  ListaParam", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //Param
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "synch",
                    "synch", "skip", "skip", "Tipo ID", "Tipo ID", "Tipo ID", "Tipo ID",
                    "Tipo ID", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //Retorno
                {"skip", "skip", "skip", "skip", "skip", "ε", "skip", "skip", "skip", "skip",
                    "return Expressao;", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip"},
                //Main
                {"public static void main ( ) { RegexDeclaraVar ListaCmd }", "skip",
                    "skip", "skip", "skip", "synch", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip"},
                //Tipo
                {"skip", "skip", "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "boolean", "int", "string", "float", "void", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip"},
                //ListaCmd
                {"skip", "skip", "skip", "ListaCmd’", "skip", "ListaCmd’", "skip", "skip",
                    "skip", "skip", "ListaCmd’", "skip", "skip", "skip", "skip", "skip", "skip",
                    "ListaCmd’", "skip", "ListaCmd’", "ListaCmd’", "ListaCmd’", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //ListaCmd’
                {"skip", "skip", "skip", "Cmd ListaCmd’", "skip", "ε", "skip", "skip", "skip",
                    "skip", "ε", "skip", "skip", "skip", "skip", "skip", "skip", "Cmd ListaCmd’",
                    "skip", "Cmd ListaCmd’", "Cmd ListaCmd’", "Cmd ListaCmd’", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //Cmd
                {"skip", "skip", "skip", "ID Cmd’", "skip", "synch", "skip", "skip", "skip",
                    "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "CmdIF",
                    "skip", "CmdWhile", "CmdPrint", "CmdPrintln", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip"},
                //Cmd’
                {"skip", "skip", "skip", "synch", "skip", "synch", "skip", "CmdMetodo", "skip",
                    "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "synch",
                    "skip", "synch", "synch", "synch", "CmdAtrib", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip"},
                //CmdIF
                {"skip", "skip", "skip", "synch", "skip", "synch", "skip", "skip", "skip",
                    "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip",
                    "if ( Expressao ) { Cmd } CmdIF’", "skip", "synch", "synch",
                    "synch", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip"},
                //CmdIF’
                {"skip", "skip", "skip", "ε", "skip", "ε", "skip", "skip", "skip", "skip", "ε",
                    "skip", "skip", "skip", "skip", "skip", "skip", "ε", "else { Cmd }", "ε", "ε",
                    "ε", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip"},
                //CmdWhile
                {"skip", "skip", "skip", "synch", "skip", "synch", "skip", "skip", "skip",
                    "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "synch",
                    "skip", "while ( Expressao ) { Cmd }", "synch", "synch", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //CmdPrint
                {"skip", "skip", "skip", "synch", "skip", "synch", "skip", "skip", "skip",
                    "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "synch",
                    "skip", "synch", "print ( Expressao ) ;", "synch", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //CmdPrintln
                {"skip", "skip", "skip", "synch", "skip", "synch", "skip", "skip", "skip",
                    "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "synch",
                    "skip", "synch", "skip", "println ( Expressao ) ;", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //CmdAtrib
                {"skip", "skip", "skip", "synch", "skip", "synch", "skip", "skip", "skip",
                    "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "synch",
                    "skip", "synch", "synch", "synch", "= Expressao ;", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip"},
                //CmdMetodo
                {"skip", "skip", "skip", "synch", "skip", "synch", "skip", "( RegexExp4 ) ;",
                    "skip", "skip", "synch", "skip", "skip", "skip", "skip", "skip", "skip",
                    "synch", "skip", "synch", "synch", "synch", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip"},
                //Expressao
                {"skip", "skip", "skip", "Exp1 Exp’", "skip", "skip", "synch", "Exp1 Exp’",
                    "synch", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "Exp1 Exp’", "Exp1 Exp’", "Exp1 Exp’", "Exp1 Exp’", "Exp1 Exp’",
                    "Exp1 Exp’", "Exp1 Exp’"},
                //Exp’
                {"skip", "skip", "skip", "skip", "skip", "skip", "ε", "skip", "ε", "ε", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "&& Exp1 Exp’", "|| Exp1 Exp’", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip"},
                //Exp1
                {"skip", "skip", "skip", "Exp2 Exp1’", "skip", "skip", "synch", "Exp2 Exp1’",
                    "synch", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "synch", "synch", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "Exp2 Exp1’", "Exp2 Exp1’", "Exp2 Exp1’", "Exp2 Exp1’", "Exp2 Exp1’",
                    "Exp2 Exp1’", "Exp2 Exp1’"},
                //Exp1’
                {"skip", "skip", "skip", "skip", "skip", "skip", "ε", "skip", "ε", "ε", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "ε", "ε", "< Exp2 Exp1’", "<= Exp2 Exp1’ ", " > Exp2 Exp1’",
                    ">= Exp2 Exp1’", "== Exp2 Exp1’", "!= Exp2 Exp1’", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //Exp2
                {"skip", "skip", "skip", "Exp3 Exp2’", "skip", "skip", "synch", "Exp3 Exp2’",
                    "synch", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "synch", "synch", "synch",
                    "synch", "synch", "synch", "synch", "synch", "skip", "skip", "skip", "skip",
                    "Exp3 Exp2’", "Exp3 Exp2’", "Exp3 Exp2’", "Exp3 Exp2’", "Exp3 Exp2’",
                    "Exp3 Exp2’", "Exp3 Exp2’"},
                //Exp2’
                {"skip", "skip", "skip", "skip", "skip", "skip", "ε", "skip", "ε", "ε", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "+ Exp3 Exp2’",
                    "- Exp3 Exp2’", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip"},
                //Exp3
                {"skip", "skip", "skip", "Exp4 Exp3’", "skip", "skip", "synch", "Exp4 Exp3’",
                    "synch", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "synch", "synch", "synch",
                    "synch", "synch", "synch", "synch", "synch", "synch", "synch", "skip", "skip",
                    "Exp4 Exp3’", "Exp4 Exp3’", "Exp4 Exp3’", "Exp4 Exp3’", "Exp4 Exp3’",
                    "Exp4 Exp3’", "Exp4 Exp3’"},
                //Exp3’
                {"skip", "skip", "skip", "skip", "skip", "skip", "ε", "skip", "ε", "ε", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "* Exp4 Exp3’",
                    "/ Exp4 Exp3’", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //Exp4
                {"skip", "skip", "skip", "ID Exp4’", "skip", "skip", "synch", "( Expressao)",
                    "synch", "synch", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "synch", "synch", "synch",
                    "synch", "synch", "synch", "synch", "synch", "synch", "synch", "synch",
                    "synch", "ConstInteira", "ConstReal", "ConstString", "true", "false",
                    "OpUnario Expressao", "OpUnario Expressao"},
                //Exp4’
                {"skip", "skip", "skip", "skip", "skip", "skip", "ε", "( RegexExp4 )", "ε", "ε",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε", "ε",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                //RegexExp4
                {"skip", "skip", "skip", "Expressao RegexExp4’", "skip", "skip", "skip",
                    "Expressao RegexExp4’", "ε", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "Expressao RegexExp4’", "Expressao RegexExp4’", "Expressao RegexExp4’",
                    "Expressao RegexExp4’", "Expressao RegexExp4’", "Expressao RegexExp4’",
                    "Expressao RegexExp4’"},
                //RegexExp4’
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "ε",
                    ", Expressao RegexExp4", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip"},
                //OpUnario
                {"skip", "skip", "skip", "synch", "skip", "skip", "skip", "synch", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip",
                    "skip", "skip", "skip", "skip", "skip", "skip", "d", "synch", "synch", "synch",
                    "synch", "-", "!"},};

    //Não Terminais da Gramática
    static String[] nonTerminals = {"Programa", "Classe", "DeclaracaoVar",
        "ListaMetodo", "ListaMetodo’", "Metodo",
        "RegexListaParam", "RegexDeclaraVar",
        "ListaParam", "ListaParam’", "Param", "Retorno",
        "Main", "Tipo", "ListaCmd", "ListaCmd’", "Cmd",
        "Cmd’", "CmdIF", "CmdIF’", "CmdWhile",
        "CmdPrint", "CmdPrintln", "CmdAtrib",
        "CmdMetodo", "Expressao", "Exp’", "Exp1",
        "Exp1’", "Exp2", "Exp2’", "Exp3", "Exp3’",
        "Exp4", "Exp4’", "RegexExp4", "RegexExp4’",
        "OpUnario"};

    //Terminais da Gramática
    static String[] terminals = {"public", "static", "class", "ID", "{", "}", ";",
        "(", ")", ",", "return", "main", "boolean",
        "int", "string", "float", "void", "if",
        "else", "while", "print", "println", "=",
        "&&", "||", "<", "<=", ">", ">=", "“==”", "!=",
        "+", "-", "*", "/", "ConstInteira", "ConstReal",
        "ConstString", "true", "false", "- (negação)",
        "!"};
}
