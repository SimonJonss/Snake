public class Convert {
  public String toString(int value) {
    return Integer.toString(value);
  }

  public String toString(double value) {
    return Double.toString(value);
  }

  public String toString(float value) {
    return Float.toString(value);
  }

  public String toString(boolean state) {
    return Boolean.toString(state);
  }  

  /////////////////////////

  public int toInt(String value) {
    return Integer.parseInt(value);
  }

  public int toInt(double value) {
    System.out.println("Ran");
    return (int)value;
  }

  public int toInt(float value) {
    return (int)value;
  }

  /////////////////////////

  public double toDouble(String value) {
    return Double.parseDouble(value);
  }

  public double toDouble(int value) {
    return (double)value;
  }

  public double toDouble(float value) {
    return (double)value;
  }

  /////////////////////////

  public float toFloat(String value) {
    return Float.parseFloat(value);
  }

  public float toFloat(int value) {
    return (float)value;
  }

  public float toFloat(double value) {
    return (float)value;
  }
  
}