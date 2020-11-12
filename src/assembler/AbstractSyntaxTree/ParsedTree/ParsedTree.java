package assembler.AbstractSyntaxTree.ParsedTree;

import assembler.AbstractSyntaxTree.AST;

public class ParsedTree extends AST {
  Initial initial;
  Method[] methods;
  public ParsedTree(Initial initial, Method[] methods) {
    this.initial = initial;
    if (methods != null) {
      this.methods = methods;
    } else {
      this.methods = null;
    }
  }
}
