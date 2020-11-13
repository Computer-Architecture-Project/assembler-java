import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import assembler.Lexer;
import assembler.Parser;
import assembler.AbstractSyntaxTree.Instruction.Instruction;
import assembler.AbstractSyntaxTree.ParsedTree.Method;
import assembler.AbstractSyntaxTree.ParsedTree.ParsedTree;
import assembler.Token.TokenType;

public class Compiler {
  public static void main(String[] args) throws IOException {
    Path path = Path.of("src/examples/assembly.txt");
    String text = Files.readString(path);
    Lexer lexer = new Lexer(text);
    Parser parser = new Parser(lexer);
    ParsedTree tree = parser.parse();
    
    for (Instruction<?, ?, ?> statement : tree.initial.statements) {
      System.out.print(statement.address() + " ");
      System.out.print(statement.command() + " ");
      System.out.print(statement.field0() + " ");
      System.out.print(statement.field1() + " ");
      System.out.println(statement.field2());
    }

    for (Method method : tree.methods) {
      System.out.println(method.label.value);
      for (Instruction<?, ?, ?> statement : method.statements) {
        System.out.print(statement.address() + " ");
        System.out.print(statement.command() + " ");
        System.out.print(statement.field0() + " ");
        System.out.print(statement.field1() + " ");
        System.out.println(statement.field2());
      }
    }
    
  }
}
