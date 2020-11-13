package assembler.Program;

import java.util.HashMap;
import java.util.Map;

import assembler.AbstractSyntaxTree.Label;

public class LabelTable {
  public Map<Label, Integer> labels;

  public LabelTable() {
    this.labels = new HashMap<Label, Integer>();
  }

  public Integer get(Label label) {
    return this.labels.get(label);
  }

  public void insert(Label label, Integer address) {
    // label already defined
    if (labels.keySet().contains(label)) {}

    this.labels.put(label, address);
  } 
}
