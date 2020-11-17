package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Label;
import assembler.AbstractSyntaxTree.Register;

public class IType extends Instruction<Register, Register, Object> {
  public IType(Integer address, Command command, Register field0, Register field1, Object field2) {
    super(address, command);
    this.field0 = field0;
    this.field1 = field1;
    this.field2 = field2;
  }

  public Object field2() {
    if (this.offset(2) instanceof Label) {
      return (Label) field2;
    } else {
      return (Integer) this.offset(2);
    }
  }
}
