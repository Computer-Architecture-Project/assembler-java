package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.Command;

public class FillType extends Instruction<Object, Void, Void> {
  public FillType(Integer address, Command command, Object field0 ) {
    super(address, command);
    this.field0 = field0;
  }
}
