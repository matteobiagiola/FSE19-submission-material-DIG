package code.coverage;

public class CoverageInfo {

    private double percentage;
    private String name;
    private String fraction;
    private int numerator;
    private int denominator;

    public CoverageInfo(){
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name: " + this.getName() + " Percentage: " + this.getPercentage() + "%" + " Fraction: " + this.getFraction());
        return buffer.toString();
    }
}
