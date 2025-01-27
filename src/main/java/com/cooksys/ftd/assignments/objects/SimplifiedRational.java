package com.cooksys.ftd.assignments.objects;

import com.cooksys.ftd.assignments.objects.util.MissingImplementationException;

public class SimplifiedRational implements IRational {
    private final int numerator;
    private final int denominator;

    /**
     * Determines the greatest common denominator for the given values
     *
     * @param a the first value to consider
     * @param b the second value to consider
     * @return the greatest common denominator, or shared factor, of `a` and `b`
     * @throws IllegalArgumentException if a <= 0 or b < 0
     */
    public static int gcd(int a, int b) throws IllegalArgumentException {
        if (a <= 0 || b < 0) {
            throw new IllegalArgumentException("a must be greater than 0 and b cannot be negative.");
        }
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Simplifies the numerator and denominator of a rational value.
     * <p>
     * For example:
     * `simplify(10, 100) = [1, 10]`
     * or:
     * `simplify(0, 10) = [0, 1]`
     *
     * @param numerator   the numerator of the rational value to simplify
     * @param denominator the denominator of the rational value to simplify
     * @return a two element array representation of the simplified numerator and denominator
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public static int[] simplify(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        if (numerator == 0) {
            return new int[]{0, 1}; // 0/denominator is always 0/1
        }
        int gcdValue = gcd(Math.abs(numerator), Math.abs(denominator));
        return new int[]{numerator / gcdValue, denominator / gcdValue};
    }


    /**
     * Constructor for rational values of the type:
     * <p>
     * `numerator / denominator`
     * <p>
     * Simplification of numerator/denominator pair should occur in this method.
     * If the numerator is zero, no further simplification can be performed
     *
     * @param numerator   the numerator of the rational value
     * @param denominator the denominator of the rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public SimplifiedRational(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        int[] simplified = simplify(numerator, denominator);
        this.numerator = simplified[0];
        this.denominator = simplified[1];
    }

    /**
     * @return the numerator of this rational number
     */
    @Override
    public int getNumerator() {
        return numerator;
    }

    /**
     * @return the denominator of this rational number
     */
    @Override
    public int getDenominator() {
        return denominator;
    }

    /**
     * Specializable constructor to take advantage of shared code between Rational and SimplifiedRational
     * <p>
     * Essentially, this method allows us to implement most of IRational methods directly in the interface while
     * preserving the underlying type
     *
     * @param numerator   the numerator of the rational value to construct
     * @param denominator the denominator of the rational value to construct
     * @return the constructed rational value (specifically, a SimplifiedRational value)
     * @throws IllegalArgumentException if the given denominator is 0
     */
    @Override
    public SimplifiedRational construct(int numerator, int denominator) throws IllegalArgumentException {
        return new SimplifiedRational(numerator, denominator);
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
            return true;
        }
        if (obj instanceof SimplifiedRational) {
            SimplifiedRational that = (SimplifiedRational) obj;
            return this.numerator == that.getNumerator() && this.denominator == that.getDenominator();
        }
        return false;
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
        if (numerator < 0 && denominator < 0) {
            return Math.abs(numerator) + "/" + Math.abs(denominator);
        } else if (denominator < 0) {
            return -numerator + "/" + Math.abs(denominator);
        }
        return numerator + "/" + denominator;
    }
    @Override
    public IRational add(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("The rational number to add cannot be null.");
        }
        int newNumerator = this.numerator * that.getDenominator() + that.getNumerator() * this.denominator;
        int newDenominator = this.denominator * that.getDenominator();
        return new SimplifiedRational(newNumerator, newDenominator);
    }

    @Override
    public IRational sub(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("The rational number to subtract cannot be null.");
        }
        int newNumerator = this.numerator * that.getDenominator() - that.getNumerator() * this.denominator;
        int newDenominator = this.denominator * that.getDenominator();
        return new SimplifiedRational(newNumerator, newDenominator);
    }

    @Override
    public IRational mul(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("The rational number to multiply cannot be null.");
        }
        int newNumerator = this.numerator * that.getNumerator();
        int newDenominator = this.denominator * that.getDenominator();
        return new SimplifiedRational(newNumerator, newDenominator);
    }

    @Override
    public IRational div(IRational that) throws IllegalArgumentException {
        if (that == null) {
            throw new IllegalArgumentException("The rational number to divide cannot be null.");
        }
        if (that.getNumerator() == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        int newNumerator = this.numerator * that.getDenominator();
        int newDenominator = this.denominator * that.getNumerator();
        return new SimplifiedRational(newNumerator, newDenominator);
    }

    @Override
    public IRational invert() throws IllegalStateException {
        if (this.numerator == 0) {
            throw new IllegalStateException("Cannot invert a rational number with a numerator of zero.");
        }
        return new SimplifiedRational(this.denominator, this.numerator);
    }

}
