package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Register;

public class JType extends Instruction<Register, Register, Void> {
  public JType(Integer address, Command command, Register field0, Register field1) {
    super(address, command);
    this.field0 = field0;
    this.field1 = field1;
  }
}
