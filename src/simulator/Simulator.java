package simulator;

import simulator.Register;
import java.util.ArrayList;
import binary.Instruction;
import simulator.Memory;

public class Simulator extends Object {
    public int pc;
    public Register register;
    public Memory memory;
    private boolean end;
    public int count;

    public Simulator(ArrayList<Object> statements) {
        this.pc = 0;
        this.register = new Register();
        this.memory = new Memory(statements);
        this.end = false;
        this.count = 0;
    }

    public void executeBinary(Instruction inst) {
        String methodName = "execute" + inst.command;
        // visitor = getattr(this, methodName);
        // visitor(inst);
    }

    public void nextInst() {
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
    public void executeAdd(Instruction inst) {
        Object rsData = this.loadReg(inst.field0);
        Object rtData = this.loadReg(inst.field1);

        // Object data = rsData + rtData;

        // this.writeReg(data, inst.field2);
        this.nextInst();
    }

    public void executeNand(Instruction inst) {
        Object rsData = this.loadReg(inst.field0);
        Object rtData = this.loadReg(inst.field1);

        // Object data = rsData & rtData;

        // this.writeReg(data, inst.field2);
        this.nextInst();
    }

    public void executeLw(Instruction inst) {
        Object rsData = this.loadReg(inst.field0);
        Object offset = inst.field2;

        // Object addr = rsData + offset;

        // Object data = this.memory.getMemory(addr.Int, TwoComplement);
        // this.writeReg(data, inst.field1);
        this.nextInst();
    }

    public void executeSw(Instruction inst) {
        Object rsData = this.loadReg(inst.field0);
        Object rtData = this.loadReg(inst.field1);
        Object offset = inst.field2;

        // Object addr = offset + rsData;

        // this.memory.setMemory(addr.Int, rtData.Int);
        this.nextInst();
    }

    public void executeBeq(Instruction inst) {
        Object rsData = this.loadReg(inst.field0);
        Object rtData = this.loadReg(inst.field1);
        Object offset = inst.field2;

        if(rsData == rtData) {
            // this.pc += offset.Int + 1;
        }else {
            this.nextInst();
        }
    }

    public void executeJalr(Instruction inst) {
        Object rsData = this.loadReg(inst.field0);

        Object data = this.pc + 1;
        this.writeReg(data, inst.field1);
        // this.pc = rsData.Int;
    }

    public void executeNoop(Instruction inst) {
        this.nextInst();
    }

    public void executeHalt(Instruction isnt) {
        this.end = true;
        this.nextInst();
    }

    public String initMemoryLog() {
        //To do something
        return null;
    }

    public String simulationLogs() {
        //To do something
        return null;
    }

    public String simulationConclusion() {
        //To do something
        return null;
    }

    public String execute() {
        String logs = this.initMemoryLog();

        while(true) {
            // Object inst = this.memory.getMemory(this.pc, Instruction);
            logs += this.simulationLogs();
            // this.executeBinary(inst);
            this.count += 1 ;
            if(this.end) {break;}
        }
        logs += this.simulationConclusion();

        logs += this.simulationLogs();
        return logs;
    }

}
