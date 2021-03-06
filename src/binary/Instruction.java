package binary;

public class Instruction {
  public String command;
  public String type;
  public Binary opcode;
  public Object field0;
  public Object field1;
  public Object field2;

  public Instruction(String command, Integer ...args) {
    this.command = command;

    // System.out.println(this.command);
    // for (Integer arg: args) System.out.println(arg);
    // System.out.println();

    // TODO: handle error
    try {
      this.setInstruction(this.command, args);
    } catch (IllegalArgumentException exception) {
      Binary opcode = this.getOpcodeWithCommand(this.command);
      Binary bin = new Binary(opcode.getBinString());
      this.initWithBin(bin);
    }
  }

  public Instruction(Long dec) {
    // System.out.println("[Extract]" + dec);
    Binary bin = new Binary(dec);
    this.initWithBin(bin);
  }

  private void setInstruction(String command, Integer... args) {
    // System.out.println("[Set Instruction]");
    String type = this.getTypeWithCommand(command);
    switch (type) {
      case "RType": this.setRType(command, args); break;
      case "IType": this.setIType(command, args); break;
      case "JType": this.setJType(command, args); break;
      case "OType": this.setOType(command, args); break;
      case "FillType": this.setFillType(command, args); break;
      default: throw new IllegalArgumentException();
    }
  }

  // public String bin() {
  //   return this.binary().getBinString();
  // }

  public Long toInteger() {
    Object bin = this.binary();
    if (bin instanceof Binary) {
      // System.out.println(((Binary)bin).getBinString());
      return ((Binary)bin).getData();
    }

    if (bin instanceof Binary2C) {
      // System.out.println(((Binary2C)bin).getBinString());
      return ((Binary2C)this.binary()).getData();
    }

    return null;
  }

  public void initWithBin(Binary bin) {
    // System.out.println("[Init With Bin]");
    this.opcode = bin.getRange(22, 24);
    this.command = this.getCommandWithOpcode((int) this.opcode.getData());
    String type = this.getTypeWithCommand(this.command);

    switch (type) {
      case "RType": this.extractBinRType(bin); break;
      case "IType": this.extractBinIType(bin); break;
      case "JType": this.extractBinJType(bin); break;
      case "OType": this.extractBinOType(bin); break;
    }
  }

  private void extractBinRType(Binary bin) {
    this.field0 = bin.getRange(19, 21);
    this.field1 = bin.getRange(16, 18);
    // this.field2 = bin.getRange(16, 18);
    this.field2 = bin.getRange(0, 2);
  }

  private void extractBinIType(Binary bin) {
    this.field0 = bin.getRange(19, 21);
    this.field1 = bin.getRange(16, 18);
    // TwoComplement
    
    this.field2 = new Binary2C(bin.getData()).getRange(0, 15);
  }

  private void extractBinJType(Binary bin) {
    this.field0 = bin.getRange(19, 21);
    this.field1 = bin.getRange(16, 18);
  }

  private void extractBinOType(Binary bin) {
    return;
  }

  private void setRType(String command, Integer... params) {
    this.opcode = this.getOpcodeWithCommand(command);
    this.type = getTypeWithCommand(command);
    
    this.field0 = new Binary(params[0]);
    this.field1 = new Binary(params[1]);
    this.field2 = new Binary(params[2]);
  }

  private void setIType(String command, Integer... params) {
    this.opcode = this.getOpcodeWithCommand(command);
    this.type = getTypeWithCommand(command);
    
    this.field0 = new Binary(params[0]);
    this.field1 = new Binary(params[1]);
    this.field2 = new Binary2C(params[2]); // TODO: Change to TwoComplement
  }

  private void setJType(String command, Integer... params) {
    this.opcode = this.getOpcodeWithCommand(command);
    this.type = getTypeWithCommand(command);
    
    this.field0 = new Binary(params[0]);
    this.field1 = new Binary(params[1]);
  }

  private void setOType(String command, Integer... params) {
    this.opcode = this.getOpcodeWithCommand(command);
    this.type = getTypeWithCommand(command);
  }

  private void setFillType(String command, Integer... params) {
    this.type = getTypeWithCommand(command);
    
    // System.out.println(params[0]);
    this.field0 = new Binary2C(params[0]); // TwoComplement
  }

  public Object binary() {
    String type = this.getTypeWithCommand(this.command);
    switch (type) {
      case "RType": 
        return this.binRType();
        
      case "IType": 
        return this.binIType();

      case "JType": 
        return this.binJType();

      case "OType": 
        return this.binOType();

      case "FillType":
        return this.binFillType();

      default:
        return null;
    }
  }

  private Binary binRType() {
    return new Binary(
      "0b0000000" +  
      this.opcode.getBinStringRange(0, 2) + 
      ((Binary)this.field0).getBinStringRange(0, 2) + 
      ((Binary)this.field1).getBinStringRange(0, 2) + 
      "0000000000000" + 
      ((Binary)this.field2).getBinStringRange(0, 2)
    );
  }

  private Binary binIType() {
    // System.out.println(this.opcode.getBinStringRange(0, 2));
    return new Binary(
      "0b0000000" +  
      this.opcode.getBinStringRange(0, 2) + 
      ((Binary)this.field0).getBinStringRange(0, 2) + 
      ((Binary)this.field1).getBinStringRange(0, 2) +
      ((Binary2C)this.field2).getBinStringRange(0, 15) 
    );
  }

  private Binary binJType() {
    return new Binary(
      "0b0000000" +  
      this.opcode.getBinStringRange(0, 2) + 
      ((Binary)this.field0).getBinStringRange(0, 2) + 
      ((Binary)this.field1).getBinStringRange(0, 2) + 
      "0000000000000000"
    );
  }

  private Binary binOType() {
    return new Binary(
      "0b0000000" +  
      this.opcode.getBinStringRange(0, 2) + 
      "0000000000000000000000"
    );
  }

  private Binary2C binFillType() {
    // System.out.println(((Binary2C)this.field0).getBinString() + "\n\n");
    return new Binary2C(
      "0b" +
      ((Binary2C)this.field0).getBinString()
    );
  }
  
  private String getCommandWithOpcode(Integer binValue) {
    switch (binValue) {
      case 0b000: 
        return "add";

      case 0b001: 
        return "nand";

      case 0b010: 
        return "lw";

      case 0b011: 
        return "sw";

      case 0b100: 
        return "beq";

      case 0b101: 
        return "jalr";

      case 0b110: 
        return "halt";

      case 0b111: 
        return "noop";

      default:
        return null;
    }
  }

  private String getTypeWithCommand(String command) {
    switch (command) {
      case "add":
        return "RType";

      case "nand":
        return "RType";

      case "lw":
        return "IType";

      case "sw":
        return "IType";

      case "beq":
        return "IType";

      case "jalr":
        return "JType";

      case "halt":
        return "OType";

      case "noop":
        return "OType";
    
      case ".fill":
        return "FillType";
  
      default:
        return null;
    }
  }

  private Binary getOpcodeWithCommand(String command) {
    switch (command) {
      case "add":
        return new Binary(0b000);

      case "nand":
        return new Binary(0b001);

      case "lw":
        return new Binary(0b010);

      case "sw":
        return new Binary(0b011);

      case "beq":
        return new Binary(0b100);

      case "jalr":
        return new Binary(0b101);

      case "halt":
        return new Binary(0b110);

      case "noop":
        return new Binary(0b111);

      case ".fill":
        return null;

      default:
        return null;
    }
  }
}
