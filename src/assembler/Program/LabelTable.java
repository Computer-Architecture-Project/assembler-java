package assembler.Program;

import java.util.HashMap;
import java.util.Map;

import assembler.AbstractSyntaxTree.Label;

public class LabelTable {
  public Map<String, Integer> labels;

  public LabelTable() {
    this.labels = new HashMap<String, Integer>();
  }

  public Integer get(Label label) {
    return this.labels.get((String) label.value);
  }

  public void insert(Label label, Integer address) {
    // label already defined
    if (labels.keySet().contains((String) label.value)) {}

    this.labels.put((String) label.value, address);
  } 
}
