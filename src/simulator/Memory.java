package simulator;

import java.util.ArrayList;
import java.util.Iterator;


public class Memory<I> implements Iterable<Object> {
  // Instruction of binary
  private ArrayList<Object> memory;

  public Memory(ArrayList<Object> statements) {
    this.memory = statements;
  }

  // increase memory size until can set memory at address
  private void allocateMemory(Integer space) {
    for (int i = 0; i < space; i++) {
      this.memory.add(0);
    }
  }

  public I getMemory(Integer address) {
    Object mem = this.memory.get(address);
    return (I) mem;
  }

  public void setMemory(Integer address, Integer data) {
    if (address >= this.memory.size()) {
      Integer space = address - this.memory.size() + 1;
      this.allocateMemory(space);
    }

    this.memory.set(address, data);
  }

  @Override
  public Iterator<Object> iterator() {
    return this.memory.iterator();
  }
}
