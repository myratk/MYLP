
 
import java_cup.runtime.*;
/* import sym; */
 
public class scanner {
  /* single lookahead character */
  protected static int next_char;

  private static boolean functionAdded;
  private static boolean callMade;
 
  /* advance input by one character */
  protected static void advance()
    throws java.io.IOException
    { next_char = System.in.read(); }
 
  /* initialize the scanner */
  public static void init()
    throws java.io.IOException
    { advance(); }
 
  /* recognize and return the next complete token */
  public static Symbol next_token()
    throws java.io.IOException
    {
      for (;;)
        switch (next_char)
          {
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
              /* parse a decimal integer */
              int i_val = 0;
              do {
                i_val = i_val * 10 + (next_char - '0');
                advance();
              } while (next_char >= '0' && next_char <= '9');
            return new Symbol(sym.INT, new Integer(i_val));

            case 'a': case 'b': case 'c': case 'd': case 'e':
            case 'f': case 'g': case 'h': case 'i': case 'j':
            case 'k': case 'l': case 'm': case 'n': case 'o':
            case 'p': case 'q': case 'r': case 's': case 't':
            case 'u': case 'v': case 'w': case 'x': case 'y':
            case 'z': case 'A': case 'B': case 'C': case 'D':
            case 'E': case 'F': case 'G': case 'H': case 'I':
            case 'J': case 'K': case 'L': case 'M': case 'N':
            case 'O': case 'P': case 'Q': case 'R': case 'S':
            case 'T': case 'U': case 'V': case 'W': case 'X':
            case 'Y': case 'Z':
              String fromchar;
              String inputString = "";
              do {
                fromchar = "" + (char) next_char;
                inputString += fromchar;
                advance();
              } while ((next_char>='a'&& next_char <='z') || (next_char>='A' && next_char<='Z'));
              switch (inputString) {
              case "print": return new Symbol(sym.PRINT);
              case "call": callMade=true; return new Symbol(sym.CALL);
              case "do": return new Symbol(sym.DO);
              case "while": return new Symbol(sym.WHILE);
              case "if": return new Symbol(sym.IF);
              case "then": return new Symbol(sym.THEN);
              case "var": return new Symbol(sym.VARDEC);
              case "START": return new Symbol(sym.START);
              case "FINISH": return new Symbol(sym.FINISH);
              case "end": return new Symbol(sym.END);
              case "function": functionAdded=true; return new Symbol(sym.FUNCTION);
              default:
                if(functionAdded) {
                  functionAdded=false; return new Symbol(sym.FUNCNAME, inputString); }
                else if (callMade) {
                  callMade=false; return new Symbol(sym.FUNCNAME, inputString); }
                else
                  return new Symbol(sym.ID, inputString);
              }

            case '=': advance(); return new Symbol(sym.ASSIGNS);
            case ';': advance(); return new Symbol(sym.SEMI);
            case '+': advance(); return new Symbol(sym.PLUS);
            case '-': advance(); return new Symbol(sym.MINUS);
            case ',': advance(); return new Symbol(sym.COMMA);
            case '*': advance(); return new Symbol(sym.TIMES);
            case '/': advance(); return new Symbol(sym.DIVIDE);
            case '(': advance(); return new Symbol(sym.LPAREN);
            case ')': advance(); return new Symbol(sym.RPAREN);
            case '>': advance(); return new Symbol(sym.GT);
 
            case -1: return new Symbol(sym.EOF);
 
            default:
              /* in this simple scanner we just ignore everything else */
              advance();
            break;
          }
    }
};

