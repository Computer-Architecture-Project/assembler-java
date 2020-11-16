package simulator;

import java.util.ArrayList;
import java.util.Iterator;

import binary.Instruction;


public class Memory<I> implements Iterable<Instruction> {
  // Instruction of binary
  private ArrayList<Instruction> memory;

  public Memory(ArrayList<Instruction> statements) {
    this.memory = statements;
  }

  // increase memory size until can set memory at address
  private void allocateMemory(Integer space) {
    for (int i = 0; i < space; i++) {
      this.memory.add(null);
    }
  }

  public I getMemory(Integer address) {
    Instruction mem = this.memory.get(address);
    return (I) mem;
  }

  public void setMemory(Integer address, Instruction data) {
    if (address >= this.memory.size()) {
      Integer space = address - this.memory.size() + 1;
      this.allocateMemory(space);
    }

    this.memory.set(address, data);
  }

  @Override
  public Iterator<Instruction> iterator() {
    return this.memory.iterator();
  }
}
