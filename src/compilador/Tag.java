package compilador;

/**
 *
 * @author Daniel e Pedro Ely
 */
public enum Tag {
    
    // fim de arquivo
    EOF,
    
    //Operadores
    RELOP_LT,       // <
    RELOP_PIPE2,    //||
    RELOP_AND2,     //&&
    RELOP_LE,       // <=
    RELOP_GT,       // >
    RELOP_GE,       // >=
    RELOP_EQ,       // ==
    RELOP_NE,       // !=
    RELOP_ASSIGN,   // =
    RELOP_SUM,      // +
    RELOP_MINUS,    // -
    RELOP_MULT,     // *
    RELOP_DIV,      // /
    RELOP_UNNE,     // -
    RELOP_U,        //!
    RELOP_OP,       // (
    RELOP_CP,       // )
    RELOP_CHAD,     // {
    RELOP_CHAE,     // }
    RELOP_VIRG,     // ,
    RELOP_SEMICOLON,  // ;
    RELOP_NULL,
    ID,
    ConstInteira,
    INTEGER,
    FLOAT,
    ConstReal,
    char_const,
    STRING,
    ConstString,
    KW,
    KW_PUBLIC,
    KW_CLASS,
    KW_END,
    KW_INTEGER,
    KW_DOUBLE,
    KW_STRING,
    KW_BOOLEAN,
    KW_FLOAT,
    KW_SYSTEMOUTDISPLN;
      
}
