package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.AST;
import assembler.AbstractSyntaxTree.Command;

public abstract class Instruction<F0, F1, F2> extends AST {
  private Integer address;
  private Command command;
  protected F0 field0;
  protected F1 field1;
  protected F2 field2;

  public Instruction(Integer address, Command command) {
    this.address = address;
    this.command = command;
    this.field0 = null;
    this.field1 = null;
    this.field2 = null;
  }

  public Integer address() {
    return this.address;
  }

  public Object command() {
    return this.command.value;
  }

  // public Object field0() {
  //   return this.field0.value;
  // }

  // public Object field1() {
  //   return this.field1.value;
  // }

  // public Object field2() {
  //   return this.field2.value;
  // }

  // private Integer offset(Integer field) {
    
  // }
}
