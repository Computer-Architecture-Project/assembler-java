import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import assembler.Lexer;

public class Compiler {
  public static void main(String[] args) throws IOException {
    Path path = Path.of("src/examples/assembly.txt");
    String text = Files.readString(path);
    Lexer lexer = new Lexer(text);
  }
}
