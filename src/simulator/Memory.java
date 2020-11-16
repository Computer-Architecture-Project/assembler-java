package simulator;

import java.util.ArrayList;
import java.util.Iterator;


public class Memory implements Iterable<Long> {
  // Instruction of binary
  private ArrayList<Long> memory;

  public Memory(ArrayList<Long> statements) {
    this.memory = statements;
  }

  // increase memory size until can set memory at address
  private void allocateMemory(Integer space) {
    for (int i = 0; i < space; i++) {
      this.memory.add(null);
    }
  }

  public <T> T getMemory(Integer address) {
    Long mem = this.memory.get(address);
    return (T) mem;
  }

  public void setMemory(Integer address, Long data) {
    if (address >= this.memory.size()) {
      Integer space = address - this.memory.size() + 1;
      this.allocateMemory(space);
    }

    this.memory.set(address, data);
  }

  @Override
  public Iterator<Long> iterator() {
    return this.memory.iterator();
  }
}
