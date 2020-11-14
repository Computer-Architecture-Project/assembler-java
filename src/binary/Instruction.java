package binary;

import assembler.AbstractSyntaxTree.Command;

public class Instruction {
  public Command command;
  public Instruction(Command command, Object ...args) {
    this.command = command;
  }
}
