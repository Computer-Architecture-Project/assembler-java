package assembler;

import java.util.ArrayList;

import assembler.AbstractSyntaxTree.Instruction.Instruction;
import assembler.AbstractSyntaxTree.ParsedTree.Initial;
import assembler.AbstractSyntaxTree.ParsedTree.Method;
import assembler.AbstractSyntaxTree.ParsedTree.ParsedTree;
import assembler.Program.LabelTable;
import assembler.Program.Program;
import assembler.Program.StatementList;

public class SemanticAnalyzer {
  public ParsedTree tree;
  public LabelTable labels;
  public StatementList statements;

  public SemanticAnalyzer(ParsedTree tree) {
    this.tree = tree;
    this.labels = new LabelTable();
    this.statements = new StatementList();
  }

  private void statements(ArrayList<Instruction<?, ?, ?>> statements) {
    for (Instruction<?, ?, ?> statement : statements) {
      this.statements.insert(statement);
    }
  }

  private void methods(ArrayList<Method> methods) {
    for (Method method : methods) {
      this.labels.insert(method.label, method.address());
      this.statements(method.statements);
    }
  }

  private void visitInitial(Initial node) {
    this.statements(node.statements);
  }

  private void visitParsedTree(ParsedTree node) {
    if (node.initial != null) {
      this.visitInitial(node.initial);
    }
    this.methods(node.methods);
  }

  public Program analyze() {
    if (this.tree == null) {
      return null;
    }
    
    this.visitParsedTree(this.tree);
    return new Program(this.labels, this.statements);
  }
}
