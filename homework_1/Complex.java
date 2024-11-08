public class Complex {
    private double real;
    private double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public void set_real(double real) {
        this.real = real;
    }

    public void set_imaginary(double imaginary) {
        this.imaginary = imaginary;
    }

    public double get_real() {
        return this.real;
    }

    public double get_imaginary() {
        return this.imaginary;
    }

    public Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    public Complex subtract(Complex other) {
        return new Complex(this.real - other.real, this.imaginary - other.imaginary);
    }

    public Complex multiply(Complex other) {
        return new Complex(
                this.real * other.real - this.imaginary * other.imaginary,
                this.real * other.imaginary + this.imaginary * other.real
        );
    }

    public Complex divide(Complex other) {
        return new Complex(
                (this.real * other.real + this.imaginary * other.imaginary) / (other.real * other.real + other.imaginary * other.imaginary),
                (this.imaginary * other.real - this.real * other.imaginary) / (other.real * other.real + other.imaginary * other.imaginary)
        );
    }

    public boolean equality(Complex other) {
        if ((this.real == other.real) && (this.imaginary == other.imaginary)) return true;
        return false;
    }

    public boolean unequality(Complex other) {
        if ((this.real != other.real) || (this.imaginary != other.imaginary)) return true;
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2fi)", real, imaginary);
    }
}