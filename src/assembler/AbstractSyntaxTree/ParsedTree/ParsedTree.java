package assembler.AbstractSyntaxTree.ParsedTree;

import java.util.ArrayList;

import assembler.AbstractSyntaxTree.AST;

public class ParsedTree extends AST {
  public Initial initial;
  public ArrayList<Method> methods;

  public ParsedTree(Initial initial, ArrayList<Method> methods) {
    this.initial = initial;
    if (methods != null) {
      this.methods = methods;
    } else {
      this.methods = null;
    }
  }
}
