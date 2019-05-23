package compilador;

/**
 *
 * @author 132683
 */
public enum Tag {
    
    // fim de arquivo
    EOF,
    
    //Operadores
    RELOP_LT,       // <
    RELOP_OR,       //||
    RELOP_AND,      //&&
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
    RELOP_UNESC,    //!
    
    //Simbolos
    SMB_OP,         // (
    SMB_CP,         // )
    SMB_OB,         // {
    SMB_CB,         // }
    SMB_COMMA,      // ,
    SMB_SEMICOLON,  // ;
    SMB_NULL,       // zerar auxnegativo
    
    //identificador
    ID,


    //numeros
    ConstInteira,
    INTEGER,
    FLOAT,
    ConstReal,
    
    //String em aspas simples
    //strings
    char_const,
	
    
    //String em aspas duplas
    STRING,
    ConstString,
    
    // palavra reservada
    KW,
    
      
}
