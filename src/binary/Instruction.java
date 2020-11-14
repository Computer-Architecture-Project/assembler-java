package binary;

import assembler.AbstractSyntaxTree.Command;

public class Instruction<F0, F1, F2> {
  public Command command;
  public String type;
  public Binary opcode;
  public F0 field0;
  public F1 field1;
  public F2 field2;

  public Instruction(Command command, Object ...args) {
    this.command = command;
  }
}
