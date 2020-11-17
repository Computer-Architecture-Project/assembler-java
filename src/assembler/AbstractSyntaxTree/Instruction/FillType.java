package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Label;

public class FillType extends Instruction<Object, Void, Void> {
  public FillType(Integer address, Command command, Object field0 ) {
    super(address, command);
    this.field0 = field0;
  }

  public Object field0() {
    if (this.offset(0) instanceof Label) {
      return (Label) this.offset(0);
    } else {
      return (Integer) this.offset(0);
    }
  }
}
