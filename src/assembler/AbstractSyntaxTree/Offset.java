package assembler.AbstractSyntaxTree;

public class Offset extends AST {
    public Unary unary;
    public Integer integer;

    public Offset(Unary unary, Integer integer) {
      this.unary = unary;
      this.integer = integer;
    }
}
