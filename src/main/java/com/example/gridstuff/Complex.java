package com.example.gridstuff;

public record Complex(double real, double imaginary) {
    public Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    public Complex subtract(Complex other) {
        return new Complex(this.real - other.real, this.imaginary - other.imaginary);
    }

    public Complex multiply(Complex other) {
        double realPart = this.real * other.real - this.imaginary * other.imaginary;
        double imaginaryPart = this.real * other.imaginary + this.imaginary * other.real;
        return new Complex(realPart, imaginaryPart);
    }

    public Complex divide(Complex other) {
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        double realPart = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
        double imaginaryPart = (this.imaginary * other.real - this.real * other.imaginary) / denominator;
        return new Complex(realPart, imaginaryPart);
    }

    public double getAngle() {
        return Math.atan2(imaginary, real);
    }

    public double modulus() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    public Complex conjugate() {
        return new Complex(this.real, -this.imaginary);
    }

    @Override
    public String toString() {
        if (imaginary >= 0) {
            return real + " + " + imaginary + "i";
        } else {
            return real + " - " + Math.abs(imaginary) + "i";
        }
    }

    public Complex power(int n) {
        double realPart = Math.pow(this.modulus(), n) * Math.cos(n * Math.atan2(this.imaginary, this.real));
        double imaginaryPart = Math.pow(this.modulus(), n) * Math.sin(n * Math.atan2(this.imaginary, this.real));
        return new Complex(realPart, imaginaryPart);
    }

    // Power of the complex number raised to a complex exponent
    public Complex power(Complex exponent) {
        double exponentReal = exponent.real();
        double exponentImaginary = exponent.imaginary();
        double modulus = this.modulus();
        double theta = Math.atan2(this.imaginary, this.real);
        double realPart = Math.pow(modulus, exponentReal) * Math.exp(-exponentImaginary * theta) * Math.cos(exponentReal * theta + exponentImaginary * Math.log(modulus));
        double imaginaryPart = Math.pow(modulus, exponentReal) * Math.exp(-exponentImaginary * theta) * Math.sin(exponentReal * theta + exponentImaginary * Math.log(modulus));
        return new Complex(realPart, imaginaryPart);
    }
}
