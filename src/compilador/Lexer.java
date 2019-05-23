package compilador;

import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.System.exit;

/**
 *
 * @author 132683
 */

public class Lexer {
   private Tag auxNegativo;
   private static int n_Char = 0; //contador de char para string
   private static int n_Erros = 0;//contador de erros lexicos
   private static final int END_OF_FILE = -1; // contante para fim do arquivo
   private static int lookahead = 0; // armazena o último caractere lido do arquivo	
   public static int n_line = 1; // contador de linhas
   public static int n_column = 1; // contador de linhas
   private RandomAccessFile instance_file; // referencia para o arquivo
   private static TS tabelaSimbolos; // tabela de simbolos
   private javax.swing.JTextArea textArea1;
   private javax.swing.JTextArea textArea2;
   public String resultado = "";
   public String resultadoLinhasColunas = "";
   
    public Lexer(TS tS, String input_data) {
        
        this.tabelaSimbolos = tS;
        
        tabelaSimbolos = new TS();
		
      // Abre instance_file de input_data
      	
        // Abre instance_file de input_data
	try {
            instance_file = new RandomAccessFile(input_data, "r");
	}
	catch(IOException e) {
            System.out.println("Erro de abertura do arquivo " + input_data + "\n" + e);
            System.exit(1);
	}
	catch(Exception e) {
            System.out.println("Erro do programa ou falha da tabela de simbolos\n" + e);
            System.exit(2);
	}
    }
   // Fecha instance_file de input_data
    public void fechaArquivo() {

        try {
            instance_file.close();
        }
	catch (IOException errorFile) {
            System.out.println ("Erro ao fechar arquivo\n" + errorFile);
            System.exit(3);
	}
    }
   // Reporta erro para o usuário
   // Reporta erro para o usuário
  /* public void sinalizaErroLexico(String mensagem) {
      System.out.println("[Erro Lexico]: " + mensagem + "\n");
   }*/
  //Reporta erro para o usuário
    public void sinalizaErroLexico(String mensagem) {
        System.out.println("[Erro Lexico]: " + mensagem + "\n");
        resultado += "[Erro Lexico]: " + mensagem + "\n";
        resultadoLinhasColunas += "Linha: " + n_line + " Coluna: " + n_column + "\n";
    }
    
   // Volta uma posição do buffer de leitura
   public void retornaPonteiro() {
      try {
         // Não é necessário retornar o ponteiro em caso de Fim de Arquivo
         if(lookahead != END_OF_FILE) {
            instance_file.seek(instance_file.getFilePointer() - 1);
            n_column --;
         }    
      }
      catch(IOException e) {
         System.out.println("Falha ao retornar a leitura\n" + e);
         System.exit(4);
      }
   }
    
    /* TODO:
    //[1]   Voce devera se preocupar quando incremetar as linhas e colunas,
    //      assim como quando decrementar ou reseta-las.
    //[2]   Toda vez que voce encontrar um lexema completo, voce deve retornar
    //      um objeto new Token(Tag, "lexema", linha, coluna). Cuidado com as
    //      palavras reservadas que ja sao cadastradas na TS. Essa consulta
    //      voce devera fazer somente quando encontrar um Identificador.
    //[3]   Se o caractere lido nao casar com nenhum caractere esperado,
    //      apresentar a mensagem de erro na linha e coluna correspondente.
    //Obs.: lembre-se de usar o metodo retornaPonteiro() quando necessario. 
            lembre-se de usar o metodo sinalizaErroLexico() para mostrar
            a ocorrencia de um erro lexico.
    */

    // Obtém próximo token: esse metodo simula um AFD
        public Token proxToken() {

      StringBuilder lexema = new StringBuilder();
      int estado = 1;
      char c;
     
      
		
      while(true) {
         c = '\u0000'; // null char
            
         // avanca caractere ou retorna token
         try {
            // read() retorna um inteiro. -1 em caso de EOF
            lookahead = instance_file.read();                
            if(lookahead != END_OF_FILE) {
               c = (char) lookahead; // conversao int para char
               n_column ++ ;
            }
         }
         catch(IOException e) {
            System.out.println("Erro na leitura do arquivo");
            System.exit(3);
         }
         if (n_Erros >= 5){
             System.out.println(" o processo de compilação foi interrompido devido" + 
                     "ao grande numero de erro :");
             exit(1);
             
           }
            
         // movimentacao do automato
         switch(estado) {
            // estado 1
             
            case 1:
               if(lookahead == END_OF_FILE)
                  return new Token(Tag.EOF, "EOF", n_line, n_column);
               else if(c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                  // Permance no estado = 1
                  if (c == '\n'){
                      n_line ++;
                      n_column = 1;
                     auxNegativo = Tag.SMB_NULL;
                      
                  }
                  else if (c== '\t'){
                     n_column += 3;
                  }
               
               }
               else if(Character.isLetter(c)) {
                  lexema.append(c);
                  estado = 17;
               }
               else if(Character.isDigit(c)) {
                  lexema.append(c);
                  estado = 19;
               }
               else if(c == '<') {
                  estado = 6;
               }
               else if(c == '>') {
                  estado = 9;
               }
               else if(c == '=') {
                  estado = 12;
               }
               else if(c == '!') {
                  estado = 15;
               }
               else if(c == '/') {
                  estado = 5;
                  
               }
               else if(c == '&') {
                  estado = 2;
                  
               }
               else if(c == '|') {
                  estado = 3;
                  
               }
               else if(c == '*') {
                  estado = 8;
                  auxNegativo = Tag.RELOP_MULT;
                  return new Token(Tag.RELOP_MULT, "*", n_line, n_column);
                  
               }
               else if(c == '+') {
                  estado = 36;
                  auxNegativo = Tag.RELOP_SUM;
                  return new Token(Tag.RELOP_SUM, "+", n_line, n_column);
               }
               else if(c == '-') {
                  estado = 4;                
               }
               else if(c == ';') {
                  estado = 37;
                  return new Token(Tag.SMB_SEMICOLON, ";", n_line, n_column);
               }
               else if(c == '(') {
                  estado = 38;
                  auxNegativo = Tag.SMB_OP;
                  return new Token(Tag.SMB_OP, "(", n_line, n_column);
               }
               else if(c == ')') {
                  estado = 39;
                  auxNegativo = Tag.SMB_CP;
                  return new Token(Tag.SMB_CP, ")", n_line, n_column);
               }
               else if(c == '{') {
                  estado = 40;
                  return new Token(Tag.SMB_OB, "{", n_line,n_column);
               }
               else if(c == '}') {
                  estado = 41;
                  return new Token(Tag.SMB_CB, "}", n_line,n_column);
               }
               
               else if(c == '"') {
                  estado = 24;
               }
               else {
                  sinalizaErroLexico("Caractere invalido " + c + " na linha " + n_line + " e coluna " + n_column);
                  n_Erros ++;
               }
               break;
            case 2:
                if(c == '&'){
                    estado = 42;
                    return new Token(Tag.RELOP_AND, "&&", n_line,n_column);
                }
                else{
                    sinalizaErroLexico("Caractere invalido " + c + " na linha " + n_line + " e coluna " + n_column);
                    n_Erros ++;
                }
                break;
            case 3:
                if(c == '|'){
                    estado = 34;
                    return new Token(Tag.RELOP_OR, "||", n_line,n_column);
                }
                else{
                    sinalizaErroLexico("Caractere invalido " + c + " na linha " + n_line + " e coluna " + n_column);
                    n_Erros ++;
                }
                break;
            case 4:
                if(auxNegativo == Tag.FLOAT||auxNegativo == Tag.ID||auxNegativo == Tag.INTEGER||auxNegativo == Tag.RELOP_MINUS||auxNegativo == Tag.SMB_OP||auxNegativo == Tag.SMB_CP){
                    retornaPonteiro();
                    estado = 51;
                    return new Token(Tag.RELOP_UNNE, "-", n_line, n_column);
                }
                else {
                    retornaPonteiro();
                     estado = 50;
                     return new Token(Tag.RELOP_MINUS,"-",n_line,n_column);
                }
            case 5:
                if (c == '/'){
                    estado = 16;
                }
                else if(c == '*'){
                estado = 18;
                
                }
                
                else{
                    retornaPonteiro();
                   return new Token(Tag.RELOP_DIV, "/", n_line, n_column);
                }
                break;
            case 6:
               if(c == '=') {
                  estado = 44;
                  return new Token(Tag.RELOP_LE, "<=", n_line, n_column);
               }
               else {
                  estado = 47;
                  retornaPonteiro();
                  return new Token(Tag.RELOP_LT, "<", n_line, n_column);
               }
                    
            case 9:
               if(c == '=') {
                  estado = 45;
                  return new Token(Tag.RELOP_GE, ">=", n_line, n_column);
               }
               else {
                  estado = 46;
                  retornaPonteiro();
                  return new Token(Tag.RELOP_GT, ">", n_line, n_column);
               }
            case 12:
               if (c == '=') {
                  estado = 52;
                  return new Token(Tag.RELOP_EQ, "==", n_line, n_column);
               }               else {
                  estado = 14;
                  retornaPonteiro();
                  return new Token(Tag.RELOP_ASSIGN, "=", n_line, n_column);
               }
            case 15:
               if( c == '=') {
                  estado = 54;
                  return new Token(Tag.RELOP_NE, "!=", n_line, n_column);
               }
               
               else {
                  retornaPonteiro();
                  estado =53;
                  return new Token(Tag.RELOP_UNNE, "!", n_line , n_column);
               }
            case 16:
                if(c == '\n'){
                   estado = 1;
                   n_line ++;
                }
                
                
                break;
            case 17:
               if(Character.isLetterOrDigit(c) || c == '_' ) {
                  lexema.append(c);
                  // Permanece no estado 17
               }
               else {
                  //estado = 18;
                  retornaPonteiro();
                  Token token = tabelaSimbolos.retornaToken(lexema.toString());
                  if  (token == null ){
                      auxNegativo = Tag.ID;
                     return new Token(Tag.ID, lexema.toString(), n_line, n_column);
                  }
                  return token;
                  
               }
               break;
            case 18:   
                //em produção
                if(c == '*'){
                    estado = 20;
                }
                else if (c == '\n'){
                    n_line ++;
                }
                else if (lookahead == END_OF_FILE){
                   sinalizaErroLexico("Comentario deve ser fechado com */  " + " na linha " + n_line + " e coluna " + n_column);
                   n_Erros ++;
                   return new Token(Tag.EOF,lexema.toString(),n_line,n_column);
                   
                }
                break;
            case 19:
               if(Character.isDigit(c)) {
                  lexema.append(c);
                  // Permanece no estado 19
               }
               else if (c == '.') {
                  lexema.append(c);
                  estado = 21;
               }
               else {
                  estado = 53;
                  retornaPonteiro();
                  auxNegativo = Tag.INTEGER;
                  return new Token(Tag.INTEGER, lexema.toString(), n_line, n_column);
               }
               break;
            case 20:
                if (c == '/'){
                    estado = 1;
                }
                else if (c == '\n'){
                    n_line ++;
                }
                else if (lookahead == END_OF_FILE){
                   sinalizaErroLexico("Comentario deve ser fechado com */  " + " na linha " + n_line + " e coluna " + n_column);
                   n_Erros ++;
                   return new Token(Tag.EOF,lexema.toString(),n_line,n_column);
                }
                break;
            case 21:
               // [TODO] continuar logica para reconhecimento de DOUBLE
                if(Character.isDigit(c)) {
                  lexema.append(c);
                  // Permanece no estado 19
               }
                else{
                    retornaPonteiro();
                    estado = 56;
                    auxNegativo = Tag.FLOAT;
                    return new Token(Tag.FLOAT, lexema.toString(), n_line, n_column);
                }
               break;
            case 22:
               // [TODO] continuar logica para reconhecimento de DOUBLE
                
               break;
            case 24:
                    if (c == '"' ){
                        if(n_Char == 0){
                    sinalizaErroLexico(" string vazia  " + " na linha " + n_line + " e coluna " + n_column);
                    n_line ++;
                    n_column = 1;
                    estado = 1;}
                        else{
                            retornaPonteiro();
                            estado = 25;
                        }
                }
                else if (c == '\n'){
                    sinalizaErroLexico(" string não fechada antes de quebra de linha  " + " na linha " + n_line + " e coluna " + n_column);
                    n_line ++;
                    n_column = 1;
                    estado =1 ;
                    n_Erros ++;
                }
                else {
                    
                    lexema.append(c);
                    n_Char ++;
                }
                break;
            case 25:
               // [TODO] continuar logica para reconhecimento de STRING
                if(c == '"'){
                    estado = 1;
                    n_Char = 0;
                    auxNegativo = Tag.STRING;
                    return new Token (Tag.STRING, lexema.toString(), n_line, n_column);
                    
                }
                
                
               break;
         } // fim switch
      } // fim while
   } // fim proxToken()

    /**
     * @param args the command line arguments
     */
   /*public static void main(String[] args) {
      LexerAluno lexer = new LexerAluno("HelloJavinha.txt"); // recebe o programa fonte para analise
      Token token;
      tabelaSimbolos = new TS();

      // Enquanto nao houver erros ou nao for fim de arquivo:
      do {
         token = lexer.proxToken();
            
         // Imprime token
         if(token != null) {
            System.out.println("Token: " + token.toString() + "\t Linha: " + n_line + "\t Coluna: " + n_column);
         }
	     
      } while(token != null && token.getClasse() != Tag.EOF);
	
      lexer.fechaArquivo();
        
      // Imprime a tabela de simbolos
      System.out.println("");
      System.out.println("Tabela de simbolos:");
      System.out.println(tabelaSimbolos.toString());
   } */
   public void leituraArquivoToken(javax.swing.JTextArea textArea2) {
        
	Token token;
        
        n_column = 1;
        n_line = 1;

	// Enquanto não houver erros ou não for fim de arquivo:
	do {
            
            token = proxToken();
            
            // Imprime token
	    if(token != null ) {
                
                System.out.println("Token: " + token.toString() + "\t\t Linha: " + n_line + "\t Coluna: " + n_column);
                
                resultado += "Token: " + token.toString() + "\t Linha: " + n_line + "\t Coluna: " + n_column + "\n";
                
//                resultadoLinhasColunas += "Linha: " + n_line + " Coluna: " + n_column + "\n";
                    
                
            } else {
                
                resultado += "";
                
                System.out.println("");
            }
	     
	} while(token != null && token.getClasse() != Tag.EOF);
        
        textArea2.setText(resultado);
//        textArea1.setText(resultadoLinhasColunas);
//	fechaArquivo();
        
        //// Imprimir a tabela de simbolos
        //System.out.println("");
        //System.out.println("Tabela de simbolos:");
        //System.out.println(tabelaSimbolos.toString());
    }
    
}
