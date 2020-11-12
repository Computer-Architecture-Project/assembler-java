package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Register;

public class RType extends Instruction<Register, Register, Register> {
  public RType(Integer address, Command command, Register field0, Register field1, Register field2) {
    super(address, command);
    this.field0 = field0;
    this.field1 = field1;
    this.field2 = field2;
  }
}
