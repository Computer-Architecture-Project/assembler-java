package simulator;

import simulator.Register;
import java.util.ArrayList;
import binary.Instruction;
import simulator.Memory;

public class Simulator {
    public Integer pc;
    public Register register;
    public Memory memory;
    private Boolean end;
    public Integer count;

    public Simulator(ArrayList<Instruction> statements) {
        this.pc = 0;
        this.register = new Register();
        this.memory = new Memory(statements);
        this.end = false;
        this.count = 0;
    }

    public void executeBinary(Instruction instruction) {
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

    public void nextInsttruction() {
        this.pc += 1;
    }

    public Object loadReg(Object field) {
        // Integer reg = field.Int();
        // Object data = this.register.getRegister(reg);
        // return data;
        return null;
    }

    public void writeReg(Object data, Object field) {
        // Integer reg = field.Int();
        // this.register.setRegister(reg, data);

    }

    //Execute Instrruction
    public void executeAdd(Instruction instruction) {
        Object rsData = this.loadReg(instruction.field0);
        Object rtData = this.loadReg(instruction.field1);

        // Object data = rsData + rtData;

        // this.writeReg(data, inst.field2);
        this.nextInsttruction();
    }

    public void executeNand(Instruction instruction) {
        Object rsData = this.loadReg(instruction.field0);
        Object rtData = this.loadReg(instruction.field1);

        // Object data = rsData & rtData;

        // this.writeReg(data, inst.field2);
        this.nextInsttruction();
    }

    public void executeLw(Instruction instruction) {
        Object rsData = this.loadReg(instruction.field0);
        Object offset = instruction.field2;

        // Object addr = rsData + offset;

        // Object data = this.memory.getMemory(addr.Int, TwoComplement);
        // this.writeReg(data, inst.field1);
        this.nextInsttruction();
    }

    public void executeSw(Instruction instruction) {
        Object rsData = this.loadReg(instruction.field0);
        Object rtData = this.loadReg(instruction.field1);
        Object offset = instruction.field2;

        // Object addr = offset + rsData;

        // this.memory.setMemory(addr.Int, rtData.Int);
        this.nextInsttruction();
    }

    public void executeBeq(Instruction instruction) {
        Object rsData = this.loadReg(instruction.field0);
        Object rtData = this.loadReg(instruction.field1);
        Object offset = instruction.field2;

        if(rsData == rtData) {
            // this.pc += offset.Int + 1;
        }else {
            this.nextInsttruction();
        }
    }

    public void executeJalr(Instruction instruction) {
        Object rsData = this.loadReg(instruction.field0);

        Object data = this.pc + 1;
        this.writeReg(data, instruction.field1);
        // this.pc = rsData.Int;
    }

    public void executeNoop(Instruction instruction) {
        this.nextInsttruction();
    }

    public void executeHalt(Instruction instruction) {
        this.end = true;
        this.nextInsttruction();
    }

    public String initMemoryLog() {
        String list = "";
        Integer index = 0;
        for(Instruction mem: this.memory) {
            // TODO: Check for Mem 
            list += "mem[" + index + "]:" + mem.binary().getInt() + "\n";
            index++;
        }
        return list;
    }

    public String simulationLogs() {
        Integer index = 0;
        String list = "\n@@@\nstate:\n\t";
        list += "pc " + this.pc + "\n";

        list += "\tmemory:\n";
        for(Object mem: this.memory) {
            // TODO: Check for Mem: Instruction
            list += "\t\tmem[" + index + "]:" + mem + "\n";
            index++;
        }

        index=0;
        list += "\tregisters:\n";
        for(Object reg: this.register) {
            // TODO: Check for Reg : TwoComplement 
            list += "\t\treg[" + index + "]:" + reg + "\n";
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
            Instruction instruction = this.memory.getMemory(this.pc);
            logs += this.simulationLogs();
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
