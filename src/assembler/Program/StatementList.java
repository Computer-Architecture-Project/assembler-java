package assembler.Program;

import java.util.ArrayList;
import java.util.Iterator;

import assembler.AbstractSyntaxTree.Instruction.Instruction;

public class StatementList implements Iterable<Instruction<?, ?, ?>> {
  public ArrayList<Instruction<?, ?, ?>> statements;

  public StatementList() {
    this.statements = new ArrayList<Instruction<?, ?, ?>>();
  }

  public void insert(Instruction<?, ?, ?> statement) {
    this.statements.add(statement);
  }

  @Override
  public Iterator<Instruction<?, ?, ?>> iterator() {
    return this.statements.iterator();
  }
}
