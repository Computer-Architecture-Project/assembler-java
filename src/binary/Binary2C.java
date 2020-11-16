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
    // this.data = Long.parseLong(bin.replaceAll("0b", ""), 2);
    String s = bin.replaceAll("0b", "");
    char[] c = s.toCharArray();

    System.out.println("input: "+s);

    // Find first '1' index
    int firstOne = -1;
    for (int i = s.length() - 1 ; i >= 0; i--) {
      if (c[i] == '1') {
        firstOne = i;
        break;
      }
    }
    System.out.println(firstOne);
    // Flip bits
    for (int i = firstOne - 1; i >= 0; i--) {
      c[i] = c[i] == '0' ? '1' : '0';
    }

    String newS = (new String(c));
    this.data = Long.parseLong(newS, 2) * (s.charAt(0) == '1' ? -1 : 1);

    System.out.println("output: ["+newS+"]");
    System.out.println(this.data);

    // this.bitsEval();
  }

  public void setValue(long bin) {
    this.data = bin;
    // this.bitsEval();
  }

  public long getData() {
    // this.bitsEval();
    return data;
  }

  public String getBinString() {
    long sData = this.data >= 0 ? this.data : ~(-this.data) + 1;
    String s = Long.toBinaryString(sData);
    char[] c = s.toCharArray();
    if (s.length() > this.bits) {
      String result = new String(Arrays.copyOfRange(c, s.length() - this.bits, s.length()));
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

  public String getBinStringRange(int start, int end) {
    String s = this.getBinString();
    s = new StringBuilder(s).reverse().toString();
    s = new StringBuilder(new String(Arrays.copyOfRange(s.toCharArray(), start, end+1))).reverse().toString();
    return s;
  }

  public Binary2C getRange(int start, int end) {
    return new Binary2C(this.getBinStringRange(start, end));
  }

  public Binary2C add(Binary2C b) {
    return new Binary2C(this.getData() + b.getData());
  }

  public Binary2C sub(Binary2C b) {
    return new Binary2C(this.getData() - b.getData());
  }

  // private void bitsEval() {
  //   // Recalculate data value accroding to bit digits
  //   this.data = Long.parseLong(this.getBinString(), 2);
  // }
}