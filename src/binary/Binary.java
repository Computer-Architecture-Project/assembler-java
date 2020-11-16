package binary;

import java.util.Arrays;

import jdk.javadoc.internal.tool.Start;

public class Binary {
  private long data;
  private int bits = 32;

  public Binary(long bin) {
    this.setValue(bin);
  }

  public Binary(String bin) {
    this.setValue(bin);
  }

  public void setValue(String bin) {
    this.data = Long.parseLong(bin.replaceAll("0b", ""), 2);
    this.bitsEval();
  }

  public void setValue(long bin) {
    this.data = bin;
    this.bitsEval();
  }

  public long getData() {
    this.bitsEval();
    return data;
  }

  public String getBinString() {
    String s = Long.toBinaryString(this.data >= 0 ? this.data : this.data * -1);
    char[] c = s.toCharArray();
    if (s.length() >= this.bits) {
      return new String(Arrays.copyOfRange(c, 0, s.length() - (s.length() - this.bits)));
    }
    char[] result = new char[this.bits];
    for (int i = this.bits - 1; i >= 0; i--) {
      int cI = s.length() - 1 - i;
      if (cI >= 0) {
        result[this.bits - 1 - i] = c[cI];
      } else {
        result[this.bits - 1 - i] = (this.data >= 0 ? '0' : '1');
      }

    }
    return new String(result);
  }

  public String getBinStringRange(int start, int end) {
    String s = this.getBinString();
    s = new StringBuilder(s).reverse().toString();
    s = new StringBuilder(new String(Arrays.copyOfRange(s.toCharArray(), start, end+1))).reverse().toString();
    return s;
  }

  public Binary getRange(int start, int end) {
    return new Binary(this.getBinStringRange(start, end));
  }

  public Binary add(Binary b) {
    return new Binary(this.getData() + b.getData());
  }

  public Binary sub(Binary b) {
    return new Binary(this.getData() - b.getData());
  }

  private void bitsEval() {
    // Recalculate data value accroding to bit digits
    this.data = Long.parseLong(this.getBinString(), 2);
  }
}