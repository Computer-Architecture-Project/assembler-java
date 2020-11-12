package assembler.AbstractSyntaxTree;

public class Offset extends AST {
    public Unary unary;
    public Number integer;

    public Offset(Unary unary, Number integer) {
      this.unary = unary;
      this.integer = integer;
    }
}
