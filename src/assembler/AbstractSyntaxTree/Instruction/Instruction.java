package assembler.AbstractSyntaxTree.Instruction;

import assembler.AbstractSyntaxTree.AST;
import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Label;
import assembler.AbstractSyntaxTree.Offset;
import assembler.AbstractSyntaxTree.Register;
import assembler.AbstractSyntaxTree.Unary;
import assembler.AbstractSyntaxTree.Number;

public abstract class Instruction<F0, F1, F2> extends AST {
  public Integer address;
  public Command command;
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

  public String command() {
    return (String) this.command.value;
  }

  public Object field0() {
    if (this.field0 instanceof Register) {
      return (Integer) ((Register)this.field0).value;
    }

    return null;
  }

  public Object field1() {
    if (this.field1 instanceof Register) {
      return (Integer) ((Register)this.field1).value;
    }

    return null;
  }

  public Object field2() {
    if (this.field2 instanceof Register) {
      return (Integer) ((Register)this.field2).value;
    } else if (this.field2 instanceof Label) {
      return ((Label)this.field2).value;
    }
  
    return null;
  }

  protected Object offset(Integer field) {
    switch(field) {
      case 0: {
        if (this.field0 instanceof Label) {
          
          return (Label) this.field0;

        } else if (this.field0 instanceof Offset) {
          Unary unary = ((Offset)this.field0).unary;
          Object operator = unary != null ? unary.value : '+';
          Number integer = ((Offset)this.field0).integer;

          return operator.equals('+') ? (Integer)integer.value : -((Integer)integer.value);
        }
      }

      case 1: {
        if (this.field1 instanceof Label) {

          return (Label) this.field1;

        } else if (this.field1 instanceof Offset) {
          Unary unary = ((Offset)this.field1).unary;
          Object operator = unary != null ? unary.value : '+';
          Number integer = ((Offset)this.field1).integer;

          return operator.equals('+') ? (Integer)integer.value : -((Integer)integer.value);
        }
      }

      case 2: {
        if (this.field2 instanceof Label) {

          return (Label) this.field2;

        } else if (this.field2 instanceof Offset) {
          Unary unary = ((Offset)this.field2).unary;
          Object operator = unary != null ? unary.value : '+';
          Number integer = ((Offset)this.field2).integer;

          return operator.equals('+') ? (Integer)integer.value : -((Integer)integer.value);
        }
      }

      default: return null;
    }
  }
}
