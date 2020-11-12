package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.Command;

public class OType extends Instruction<Void, Void, Void> {
  public OType(Integer address, Command command) {
    super(address, command);
  }
}
