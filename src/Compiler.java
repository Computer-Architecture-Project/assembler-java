import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import assembler.Interpreter;
import assembler.Lexer;
import assembler.Parser;
import assembler.SemanticAnalyzer;
import assembler.AbstractSyntaxTree.ParsedTree.ParsedTree;
import assembler.Program.Program;
import simulator.Simulator;

public class Compiler {
  public static void main(String[] args) throws Exception {
    Path path = Path.of("src/examples/assembly1.txt");
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
    //     if (statement.field0() instanceof Label) {
    //       System.out.print(((Label) statement.field0()).value + " ");
    //     } else {
    //       System.out.print(((Integer) statement.field0()) + " ");
    //     }
    //     System.out.print(statement.field1() + " ");
    //     if (statement.field2() instanceof Label) {
    //       System.out.println(((Label) statement.field2()).value);
    //     } else {
    //       System.out.println(((Integer) statement.field2()));
    //     }
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
    ArrayList<Long> binary = interpreter.interpret();
    for(Long bin: binary) {
      System.out.println(bin);
    }


    Simulator simulator = new Simulator(binary);
    String logs = simulator.execute();
    BufferedWriter writer = new BufferedWriter(new FileWriter("src/results/logs.txt"));
    writer.write(logs);
    writer.close();
  }
}
