import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

import assembler.Interpreter;
import assembler.Lexer;
import assembler.Parser;
import assembler.SemanticAnalyzer;
import assembler.AbstractSyntaxTree.Instruction.Instruction;
import assembler.AbstractSyntaxTree.ParsedTree.Method;
import assembler.AbstractSyntaxTree.ParsedTree.ParsedTree;
import assembler.Program.Program;
import binary.Binary;

public class Compiler {
  public static void main(String[] args) throws Exception {
    Path path = Path.of("src/examples/assembly.txt");
    String text = Files.readString(path);
    // System.out.println(text);
    Lexer lexer = new Lexer(text);
    // lexer.log();
    Parser parser = new Parser(lexer);
    ParsedTree tree = parser.parse();
    // for (Instruction<?, ?, ?> statement : tree.initial.statements) {
    //   System.out.print(statement.address() + " ");
    //   System.out.print(statement.command() + " ");
    //   System.out.print(statement.field0() + " ");
    //   System.out.print(statement.field1() + " ");
    //   System.out.println(statement.field2());
    // }

    // for (Method method : tree.methods) {
    //   System.out.println(method.label.value);
    //   for (Instruction<?, ?, ?> statement : method.statements) {
    //     System.out.print(statement.address() + " ");
    //     System.out.print(statement.command() + " ");
    //     System.out.print(statement.field0() + " ");
    //     System.out.print(statement.field1() + " ");
    //     System.out.println(statement.field2());
    //   }
    // }
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(tree);
    Program program = semanticAnalyzer.analyze();
    // for (Instruction<?, ?, ?>statement : program.statements) {
    //   System.out.print(statement.address() + " ");
    //   System.out.print(statement.command() + " ");
    //   System.out.print(statement.field0() + " ");
    //   System.out.print(statement.field1() + " ");
    //   System.out.println(statement.field2() + " ");
    // }
    Interpreter interpreter = new Interpreter(program);
    ArrayList<binary.Instruction> binary = interpreter.interpret();
    for(binary.Instruction bin: binary) {
      System.out.println(bin.binary().getBinString());
    }
  }
}
