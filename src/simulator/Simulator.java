package simulator;

import java.util.ArrayList;

import binary.Binary;
import binary.Binary2C;
import binary.Instruction;

public class Simulator {
    public Integer pc;
    public Register register;
    public Memory memory;
    private Boolean end;
    public Integer count;

    public Simulator(ArrayList<Long> statements) {
        this.pc = 0;
        this.register = new Register();
        this.memory = new Memory(statements);
        this.end = false;
        this.count = 0;
    }

    public void executeBinary(Instruction instruction) {
        // System.out.println(instruction.command);
        switch(instruction.command) {
            case "add": this.executeAdd(instruction); break;
            case "nand": this.executeNand(instruction); break;
            case "lw": this.executeLw(instruction); break;
            case "sw": this.executeSw(instruction); break;
            case "beq": this.executeBeq(instruction); break;
            case "jalr": this.executeJalr(instruction); break;
            case "noop": this.executeNoop(instruction); break;
            case "halt": this.executeHalt(instruction); break;
        }
    }

    public void nextInstruction() {
        this.pc += 1;
    }

    public Binary2C loadReg(Object field) {
        // long regNo = ((Binary2C)field).getData();
        long regNo;
        if (field instanceof Binary) {
            regNo = ((Binary)field).getData();
        } else {
            regNo = ((Binary2C)field).getData();
        }
        Binary2C data = this.register.getRegister((int) regNo);
        return data;
        // return null;
    }

    public void writeReg(Integer data, Object field) {
        long regNo;
        if (field instanceof Binary) {
            regNo = ((Binary)field).getData();
        } else {
            regNo = ((Binary2C)field).getData();
        }
        
        this.register.setRegister((int) regNo, data);

    }

    //Execute Instrruction
    public void executeAdd(Instruction instruction) {
        Binary2C rsData = this.loadReg(instruction.field0);
        Binary2C rtData = this.loadReg(instruction.field1);

        // System.out.println("[ADD] " + rsData.getData());
        // System.out.println("[ADD] " + rtData.getData());
        Binary2C data = rsData.add(rtData);
        // System.out.println("[ADD] " + data.getData());

        this.writeReg((int) data.getData(), instruction.field2);
        this.nextInstruction();
    }

    public void executeNand(Instruction instruction) {
        Binary2C rsData = this.loadReg(instruction.field0);
        Binary2C rtData = this.loadReg(instruction.field1);

        long data = ~(rsData.getData() & rtData.getData());

        this.writeReg((int) data, instruction.field2);
        this.nextInstruction();
    }

    public void executeLw(Instruction instruction) {
        Binary2C rsData = this.loadReg(instruction.field0);
        Binary2C offset = (Binary2C) instruction.field2;
        
        // System.out.println("[ADD] " + rsData.getData());
        // System.out.println("[ADD] " + offset.getData());
        Binary2C addr = rsData.add(offset);
        // System.out.println("[ADD] " + addr.getData());


        // Object data = this.memory.getMemory((int) addr.getData(), TwoComplement);
        Long mem = this.memory.getMemory((int) addr.getData());
        long data = (new Binary2C(mem)).getData();
        this.writeReg((int) data, instruction.field1);
        this.nextInstruction();
    }

    public void executeSw(Instruction instruction) {
        Binary2C rsData = this.loadReg(instruction.field0);
        Binary2C rtData = this.loadReg(instruction.field1);
        Binary2C offset = (Binary2C) instruction.field2;

        Binary2C addr = offset.add(rsData);

        this.memory.setMemory((int) addr.getData(), rtData.getData());
        this.nextInstruction();
    }

    public void executeBeq(Instruction instruction) {
        Binary2C rsData = this.loadReg(instruction.field0);
        Binary2C rtData = this.loadReg(instruction.field1);
        Binary2C offset = (Binary2C) instruction.field2;

        // System.out.println(offset.getData());
        if(rsData.getData() == rtData.getData()) {
            // System.out.println("[BEQ OK]");
            
            this.pc += ((int) offset.getData()) + 1;
        }else {
            this.nextInstruction();
        }
    }

    public void executeJalr(Instruction instruction) {
        Binary2C rsData = this.loadReg(instruction.field0);

        Integer data = this.pc + 1;
        this.writeReg(data, instruction.field1);
        this.pc = (int) rsData.getData();
    }

    public void executeNoop(Instruction instruction) {
        this.nextInstruction();
    }

    public void executeHalt(Instruction instruction) {
        this.end = true;
        this.nextInstruction();
    }

    public String initMemoryLog() {
        String list = "";
        Integer index = 0;
        for(Long mem: this.memory) {
            // TODO: Check for Mem 
            list += "mem[" + index + "]:" + mem + "\n";
            index++;
        }
        return list;
    }

    public String simulationLogs() {
        Integer index = 0;
        String list = "\n@@@\nstate:\n\t";
        list += "pc " + this.pc + "\n";

        list += "\tmemory:\n";
        for(Long mem: this.memory) {
            // TODO: Check for Mem: Instruction
            list += "\t\tmem[" + index + "]:" + mem + "\n";
            index++;
        }

        index=0;
        list += "\tregisters:\n";
        for(Binary2C reg: this.register) {
            // TODO: Check for Reg : TwoComplement 
            list += "\t\treg[" + index + "]:" + reg.getData() + "\n";
            index++;
        }

        list += "end state\n";
        return list;
    }

    public String simulationConclusion() {
        String list = "machine halted\ntotal of " + this.count + " instructions executed\nfinal state of machine:\n";
        return list;
    }

    public String execute() {
        String logs = this.initMemoryLog();

        while(true) {
            Long mem = this.memory.getMemory(this.pc);
            Instruction instruction = new Instruction(mem);
            logs += this.simulationLogs();
            System.out.println(this.pc);
            this.executeBinary(instruction);
            this.count += 1 ;
            if(this.end) {
                break;
            }
        }
        logs += this.simulationConclusion();

        logs += this.simulationLogs();
        return logs;
    }

}
