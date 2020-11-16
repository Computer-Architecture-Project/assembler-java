package binary;

import java.util.Arrays;

public class Binary2C {
  // Two's Complimentary Binary
  private long data;
  private int bits = 32;

  public Binary2C(long bin) {
    this.setValue(bin);
  }

  public Binary2C(String bin) {
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
    long sData = this.data >= 0 ? this.data : ~(-this.data) + 1;
    String s = Long.toBinaryString(sData);
    char[] c = s.toCharArray();
    if (s.length() > this.bits) {
      String result = new String(Arrays.copyOfRange(c, s.length() - this.bits , s.length()));
      return result;
    }
    if (s.length() == this.bits) {
      return s;
    }
    char[] result = new char[this.bits];
    for (int i = this.bits - 1; i >= 0; i--) {
      int cI = s.length() - 1 - i;
      if (cI >= 0) {
        result[this.bits - 1 - i] = c[cI];
      } else {
        result[this.bits - 1 - i] = '0';
      }

    }
    return new String(result);
  }

  public Binary getRange(int start, int end) {
    String s = Long.toBinaryString(this.data);
    char[] c = s.toCharArray();
    String newValue = new String(Arrays.copyOfRange(c, start, end));

    return new Binary(newValue);
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