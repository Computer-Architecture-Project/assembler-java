package binary;

import java.util.Arrays;

public class Binary {
  private int data;
  private int bits;

  public Binary(String bin) {
    this.setValue(bin);
  }

  public Binary(int bin) {
    this.setValue(bin);
  }

  public Binary(String bin, int bits) {
    this.setValue(bin, bits);
  }

  public Binary(int bin, int bits) {
    this.setValue(bin, bits);
  }

  public void setValue(String bin) {
    this.setValue(bin, bin.length());
  }

  public void setValue(int bin) {
    this.setValue(Integer.toString(bin,2));
  }

  public void setValue(String bin, int bits) {
    this.data = Integer.parseInt(bin, 2);
    this.bits = bits;
    this.bitsEval();
  }

  public void setValue(int bin, int bits) {
    this.data = bin;
    this.bits = bits;
    this.bitsEval();
  }

  public int getInt() {
    this.bitsEval();
    return data;
  }

  public String getBinString() {
    String s = Integer.toBinaryString(this.data);
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
        result[this.bits - 1 - i] = '0';
      }

    }
    return new String(result);
  }

  public Binary getRange(int start, int end) {
    String s = Integer.toBinaryString(this.data);
    char[] c = s.toCharArray();
    String newValue = new String(Arrays.copyOfRange(c, start, end));

    return new Binary(newValue, newValue.length());
  }

  public Binary add(Binary b) {
    return new Binary(this.getInt()+b.getInt());
  }

  public Binary sub(Binary b) {
    return new Binary(this.getInt()-b.getInt());
  }

  private void bitsEval() {
    // Recalculate data value accroding to bit digits
    this.data = Integer.parseInt(this.getBinString(), 2);
  }
}