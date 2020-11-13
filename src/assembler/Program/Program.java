package assembler.Program;

public class Program {
  public LabelTable labels;
  public StatementList statements;

  public Program(LabelTable labels, StatementList statements) {
    this.labels = labels;
    this.statements = statements;
  }
}
