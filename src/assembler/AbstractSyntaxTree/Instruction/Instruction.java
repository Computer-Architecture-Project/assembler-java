package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.AST;
import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Register;

public abstract class Instruction<F0 extends Register, F1 extends Register, F2 extends Register> extends AST {
  private Integer address;
  private Command command;
  private F0 field0;
  private F1 field1;
  private F2 field2;

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

  public Object field0() {
    return this.field0.value;
  }

  public Object field1() {
    return this.field1.value;
  }

  public Object field2() {
    return this.field2.value;
  }

  // private Integer offset(Integer field) {
   
  // }
}
