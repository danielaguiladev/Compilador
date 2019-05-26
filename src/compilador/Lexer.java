package compilador;

import java.io.*;

/**
 *
 * @author Daniel e Pedro Ely
 */
public class Lexer {

    private static final int END_OF_FILE = -1; // contante para fim do arquivo
    private static int lookahead = 0; // armazena o ultimo caractere lido do arquivo   
    public static int n_line = 1; // contador de linhas
    public static int n_column = 1; // contador de linhas
    private RandomAccessFile instance_file; // referencia para o arquivo
    private static TS tabelaSimbolos; // tabela de simbolos
    public int erros = 0; // contador de erros
    private Tag aux;
    private static int num_c = 0;

    public Lexer(TS ts, String input_data) {
        this.tabelaSimbolos = ts;
        tabelaSimbolos = new TS();
        // Abre instance_file de input_data
        try {
            instance_file = new RandomAccessFile(input_data, "r");
        } catch (IOException e) {
            System.out.println("Erro de abertura do arquivo " + input_data + "\n" + e);
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Erro do programa ou falha da Tabela de Simbolos\n" + e);
            System.exit(2);
        }
    }

    // Fecha instance_file de input_data
    public void fechaArquivo() {
        try {
            instance_file.close();
        } catch (IOException errorFile) {
            System.out.println("Erro ao fechar arquivo\n" + errorFile);
            System.exit(3);
        }
    }

    // Reporta erro para o usuário
    public void sinalizaErroLexico(String mensagem) {
        System.out.println("[Erro Lexico]: " + mensagem + "\n");
    }

    // Volta uma posicao do buffer de leitura
    public void retornaPonteiro() {
        try {
            // Nao eh necessario retornar o ponteiro em caso de Fim de Arquivo
            if (lookahead != END_OF_FILE) {
                instance_file.seek(instance_file.getFilePointer() - 1);
                n_column--;
            }
        } catch (IOException e) {
            System.out.println("Falha ao retornar a leitura\n" + e);
            System.exit(4);
        }
    }

    // função que percorre caracter por caracter
    public char percorreArquivo() {
        if (erros > 5) {
            System.out.println("Limite de erros léxicos excedidos");
            System.exit(1);
        }
        char c = '\u0000';
        try {
            // read() retorna um inteiro. -1 em caso de EOF
            lookahead = instance_file.read();

            if (lookahead != END_OF_FILE) {
                c = (char) lookahead; // conversao int para char
                n_column++;
            }
        } catch (IOException e) {
            System.out.println("Erro na leitura do arquivo");
            System.exit(3);
        }
        return c;
    }

    // Indetificação de tokens e erros
    public Token proxToken() {

        StringBuilder lexema = new StringBuilder();
        int estado = 1;
        char c;

        while (true) {
            c = percorreArquivo();
            switch (estado) {
                case 1:
                    if (lookahead == END_OF_FILE) {
                        return new Token(Tag.EOF, "EOF", n_line, n_column);
                    } else if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                        if (c == '\n') {
                            n_line++;
                            n_column = 1;
                            aux = Tag.RELOP_NULL;
                        } else if (c == '\t') {
                            n_column += 3;
                        }
                    } else if (Character.isLetter(c)) {
                        lexema.append(c);
                        estado = 2;
                    } else if (Character.isDigit(c)) {
                        lexema.append(c);
                        estado = 3;
                    } else if (c == '<') {
                        estado = 4;
                    } else if (c == '>') {
                        estado = 5;
                    } else if (c == '=') {
                        estado = 6;
                    } else if (c == '!') {
                        estado = 7;
                    } else if (c == '/') {
                        estado = 8;
                    } else if (c == '&') {
                        estado = 9;
                    } else if (c == '|') {
                        estado = 10;
                    } else if (c == '*') {
                        aux = Tag.RELOP_MULT;
                        return new Token(Tag.RELOP_MULT, "*", n_line, n_column);
                    } else if (c == '+') {
                        aux = Tag.RELOP_SUM;
                        return new Token(Tag.RELOP_SUM, "+", n_line, n_column);
                    } else if (c == '-') {
                        estado = 11;
                    } else if (c == ';') {
                        return new Token(Tag.RELOP_SEMICOLON, ";", n_line, n_column);
                    } else if (c == '(') {
                        aux = Tag.RELOP_OP;
                        return new Token(Tag.RELOP_OP, "(", n_line, n_column);
                    } else if (c == ')') {
                        aux = Tag.RELOP_CP;
                        return new Token(Tag.RELOP_CP, ")", n_line, n_column);
                    } else if (c == '{') {
                        return new Token(Tag.RELOP_CHAD, "{", n_line, n_column);
                    } else if (c == '}') {
                        return new Token(Tag.RELOP_CHAE, "}", n_line, n_column);
                    } else if (c == '"') {
                        estado = 12;
                    } else {
                        sinalizaErroLexico("Caractere invalido " + c + " na linha " + n_line + " e coluna " + n_column);
                        erros++;
                    }
                break;
                case 2:
                    if (Character.isLetterOrDigit(c) || c == '_') {
                        lexema.append(c);
                    } else {
                        retornaPonteiro();
                        Token token = tabelaSimbolos.retornaToken(lexema.toString());
                        if (token == null) {
                            aux = Tag.ID;
                            return new Token(Tag.ID, lexema.toString(), n_line, n_column);
                        }
                        return token;
                    }
                break;
                case 3:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                    } else if (c == '.') {
                        lexema.append(c);
                        estado = 16;
                    } else {
                        retornaPonteiro();
                        aux = Tag.INTEGER;
                        return new Token(Tag.INTEGER, lexema.toString(), n_line, n_column);
                    }
                break;
                case 4:
                    if (c == '=') {
                        return new Token(Tag.RELOP_LE, "<=", n_line, n_column);
                    } else {
                        retornaPonteiro();
                        return new Token(Tag.RELOP_LT, "<", n_line, n_column);
                    }
                case 5:
                    if (c == '=') {
                        return new Token(Tag.RELOP_GE, ">=", n_line, n_column);
                    } else {
                        retornaPonteiro();
                        return new Token(Tag.RELOP_GT, ">", n_line, n_column);
                    }
                case 6:
                    if (c == '=') {
                        return new Token(Tag.RELOP_EQ, "==", n_line, n_column);
                    } else {
                        retornaPonteiro();
                        return new Token(Tag.RELOP_ASSIGN, "=", n_line, n_column);
                    }
                case 7:
                    if (c == '=') {
                        return new Token(Tag.RELOP_NE, "!=", n_line, n_column);
                    } else {
                        retornaPonteiro();
                        return new Token(Tag.RELOP_U, "!", n_line, n_column);
                    }
                case 8:
                    if (c == '/') {
                        estado = 13;
                    } else if (c == '*') {
                        estado = 14;
                    } else {
                        retornaPonteiro();
                        return new Token(Tag.RELOP_DIV, "/", n_line, n_column);
                    }
                break;
                case 9:
                    if (c == '&') {
                        return new Token(Tag.RELOP_AND2, "&&", n_line, n_column);
                    } else {
                        sinalizaErroLexico("Caractere invalido " + c + " na linha " + n_line + " e coluna " + n_column);
                        erros++;
                    }
                break;
                case 10:
                    if (c == '|') {
                        return new Token(Tag.RELOP_PIPE2, "||", n_line, n_column);
                    } else {
                        sinalizaErroLexico("Caractere invalido " + c + " na linha " + n_line + " e coluna " + n_column);
                        erros++;
                    }
                break;
                case 11:
                    if (aux == Tag.FLOAT || aux == Tag.ID || aux == Tag.INTEGER || aux == Tag.RELOP_MINUS || aux == Tag.RELOP_OP || aux == Tag.RELOP_CP) {
                        retornaPonteiro();
                        return new Token(Tag.RELOP_MINUS, "-", n_line, n_column);
                    } else {
                        retornaPonteiro();
                        return new Token(Tag.RELOP_MINUS, "-", n_line, n_column);
                    }
                case 12:
                    if (c == '"') {
                        if (num_c == 0) {
                            sinalizaErroLexico(" string vazia  " + " na linha " + n_line + " e coluna " + n_column);
                            n_line++;
                            n_column = 1;
                            estado = 1;
                        } else {
                            retornaPonteiro();
                            estado = 17;
                        }
                    } else if (c == '\n') {
                        sinalizaErroLexico(" string não fechada antes de quebra de linha  " + " na linha " + n_line + " e coluna " + n_column);
                        n_line++;
                        n_column = 1;
                        estado = 1;
                        erros++;
                    } else {
                        lexema.append(c);
                        num_c++;
                    }
                break;
                case 13:
                    if (c == '\n') {
                        estado = 1;
                        n_line++;
                    }
                break;
                case 14:
                    if (c == '*') {
                        estado = 15;
                    } else if (c == '\n') {
                        n_line++;
                    } else if (lookahead == END_OF_FILE) {
                        sinalizaErroLexico("Comentario deve ser fechado com */  " + " na linha " + n_line + " e coluna " + n_column);
                        erros++;
                        return new Token(Tag.EOF, lexema.toString(), n_line, n_column);
                    }
                break;
                case 15:
                    if (c == '/') {
                        estado = 1;
                    } else if (c == '\n') {
                        n_line++;
                    } else if (lookahead == END_OF_FILE) {
                        sinalizaErroLexico("Comentario deve ser fechado com */  " + " na linha " + n_line + " e coluna " + n_column);
                        erros++;
                        return new Token(Tag.EOF, lexema.toString(), n_line, n_column);
                    }
                break;
                case 16:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                    } else {
                        retornaPonteiro();
                        aux = Tag.FLOAT;
                        return new Token(Tag.FLOAT, lexema.toString(), n_line, n_column);
                    }
                break;
                case 17:
                    if (c == '"') {
                        estado = 1;
                        num_c = 0;
                        aux = Tag.STRING;
                        return new Token(Tag.STRING, lexema.toString(), n_line, n_column);
                    }
                break;
            }
        }
    }
}
