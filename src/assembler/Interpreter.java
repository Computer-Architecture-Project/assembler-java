package assembler;

import java.util.ArrayList;

import assembler.AbstractSyntaxTree.Label;
import assembler.AbstractSyntaxTree.Instruction.FillType;
import assembler.AbstractSyntaxTree.Instruction.IType;
import assembler.AbstractSyntaxTree.Instruction.Instruction;
import assembler.AbstractSyntaxTree.Instruction.JType;
import assembler.AbstractSyntaxTree.Instruction.OType;
import assembler.AbstractSyntaxTree.Instruction.RType;
import assembler.Program.Program;
import assembler.Token.TokenType;

public class Interpreter {
  public Program program;

  public Interpreter(Program program) {
    this.program = program;
  }

  private Integer getAddressWithLabel(Label label) {
    return this.program.labels.get(label);
  }

  private binary.Instruction instructStatement(Instruction<?, ?, ?> statement) {
    if (statement instanceof RType) {
      return this.initRType((RType) statement);
    }

    if (statement instanceof IType) {
      return this.initIType((IType) statement);
    }

    if (statement instanceof JType) {
      return this.initJType((JType) statement);
    }

    if (statement instanceof OType) {
      return this.initOType((OType) statement);
    }

    if (statement instanceof FillType) {
      return this.initFillType((FillType) statement);
    }

    return null;
  }

  private binary.Instruction initRType(RType statement) {
    return new binary.Instruction(
      statement.command(),
      (Integer) statement.field0(),
      (Integer) statement.field1(),
      (Integer) statement.field2()
    );
  }

  private binary.Instruction initIType(IType statement) {
    Integer field2;
  
    if (statement.field2() instanceof Label) {
      Label label = (Label) statement.field2();
      Integer address = this.getAddressWithLabel(label);

      if (statement.command.token.type == TokenType.BEQ) {
        field2 = address - statement.address() - 1;
      } else {
        field2 = address;
        // return null;
      }
    } else {
      field2 = (Integer) statement.field2();
    }

    return new binary.Instruction(
      statement.command(),
      (Integer) statement.field0(),
      (Integer) statement.field1(),
      field2
    );
  }

  private binary.Instruction initJType(JType statement) {
    return new binary.Instruction(
      statement.command(),
      (Integer) statement.field0(),
      (Integer) statement.field1()
    );
  }

  private binary.Instruction initOType(OType statement) {
    return new binary.Instruction(statement.command());
  }

  private binary.Instruction initFillType(FillType statement) {
    Integer field0;
    if (statement.field0() instanceof Label) {
      Label label = (Label) statement.field0();
      field0 = this.getAddressWithLabel(label);
    } else {
      field0 = (Integer) statement.field0();
    }

    return new binary.Instruction(
      statement.command(),
      field0
    );
  }

  public ArrayList<binary.Instruction> interpret() {
    ArrayList<binary.Instruction> machineLang = new ArrayList<binary.Instruction>();

    for(Instruction<?, ?, ?> statement : program.statements) {
      binary.Instruction machineCode = this.instructStatement(statement);
      machineLang.add(machineCode);
    }

    return machineLang;
  }
}
