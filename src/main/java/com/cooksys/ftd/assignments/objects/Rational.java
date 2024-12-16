package com.cooksys.ftd.assignments.objects;

import com.cooksys.ftd.assignments.objects.util.MissingImplementationException;

public class Rational implements IRational {
    private final int numerator;
    private final int denominator;

    /**
     * Constructor for rational values of the type:
     * <p>
     * `numerator / denominator`
     * <p>
     * No simplification of the numerator/denominator pair should occur in this method.
     *
     * @param numerator   the numerator of the rational value
     * @param denominator the denominator of the rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */

    public Rational(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * @return the numerator of this rational number
     */
    @Override
    public int getNumerator() {
        return this.numerator;
    }

    /**
     * @return the denominator of this rational number
     */
    @Override
    public int getDenominator() {
        return this.denominator;
    }

    /**
     * Specializable constructor to take advantage of shared code between Rational and SimplifiedRational
     * <p>
     * Essentially, this method allows us to implement most of IRational methods directly in the interface while
     * preserving the underlying type
     *
     * @param numerator the numerator of the rational value to construct
     * @param denominator the denominator of the rational value to construct
     * @return the constructed rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */
    @Override
    public Rational construct(int numerator, int denominator) throws IllegalArgumentException {
        return new Rational(numerator, denominator);
    }

    /**
     * @param obj the object to check this against for equality
     * @return true if the given obj is a rational value and its
     * numerator and denominator are equal to this rational value's numerator and denominator,
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true; // If it's the same object
        }
        if (obj instanceof Rational) {
            Rational that = (Rational) obj;
            return this.numerator == that.getNumerator() && this.denominator == that.getDenominator();
        }
        return false; // If the object is not of type Rational
    }
    /**
     * If this is positive, the string should be of the form `numerator/denominator`
     * <p>
     * If this is negative, the string should be of the form `-numerator/denominator`
     *
     * @return a string representation of this rational value
     */
    @Override
    public String toString() {
        // If both numerator and denominator are negative, the result is positive
        if (numerator < 0 && denominator < 0) {
            return Math.abs(numerator) + "/" + Math.abs(denominator);
        }
        // If only the denominator is negative, move the negative sign to the numerator
        else if (denominator < 0) {
            return -numerator + "/" + Math.abs(denominator);
        }
        // Otherwise, return the normal form
        return numerator + "/" + denominator;
    }
    @Override
    public IRational invert() throws IllegalStateException {
        if (this.numerator == 0) {
            throw new IllegalStateException("Cannot invert a rational number with a numerator of 0.");
        }
        return construct(this.denominator, this.numerator);
    }


    @Override
    public IRational add(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("Argument 'that' cannot be null.");
        }
        int num1 = this.getNumerator();
        int denom1 = this.getDenominator();
        int num2 = that.getNumerator();
        int denom2 = that.getDenominator();

        // Formula: (n1/d1) + (n2/d2) = (n1 * d2 + n2 * d1) / (d1 * d2)
        int newNumerator = num1 * denom2 + num2 * denom1;
        int newDenominator = denom1 * denom2;
        return construct(newNumerator, newDenominator);
    }


    @Override
    public IRational sub(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("Argument 'that' cannot be null.");
        }
        int num1 = this.getNumerator();
        int denom1 = this.getDenominator();
        int num2 = that.getNumerator();
        int denom2 = that.getDenominator();

        // Formula: (n1/d1) - (n2/d2) = (n1 * d2 - n2 * d1) / (d1 * d2)
        int newNumerator = num1 * denom2 - num2 * denom1;
        int newDenominator = denom1 * denom2;
        return construct(newNumerator, newDenominator);
    }


    @Override
    public IRational mul(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("Argument 'that' cannot be null.");
        }
        int newNumerator = this.getNumerator() * that.getNumerator();
        int newDenominator = this.getDenominator() * that.getDenominator();
        return construct(newNumerator, newDenominator);
    }


    @Override
    public IRational div(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("Argument 'that' cannot be null.");
        }
        if (that.getNumerator() == 0) {
            throw new IllegalArgumentException("Cannot divide by a rational number with numerator 0.");
        }
        int newNumerator = this.getNumerator() * that.getDenominator();
        int newDenominator = this.getDenominator() * that.getNumerator();
        return construct(newNumerator, newDenominator);
    }
}
