package Lab02;

public class Complex {

    public final static Complex I = new Complex(0., 1.);
    public final static Complex ZERO = new Complex(0., 0.);
    public final static Complex ONE = new Complex(1., 0.);

    private double re, im;

    public Complex() {
        this.re = 0.;
        this.im = 0.;
    }

    public Complex(double re) {
        this.re = re;
        this.im = 0.;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex divide(Complex c1, Complex c2) {
        double commonDenominator = c2.getRe()*c2.getRe() + c2.getIm()*c2.getIm();
        return new Complex((c1.getRe()*c2.getRe() + c1.getIm()*c2.getIm())/commonDenominator,
                (c1.getIm()*c2.getRe() - c1.getRe()*c2.getIm())/commonDenominator);
    }


    public double getRe(){
        return this.re;
    }

    public double getIm(){
        return this.im;
    }

    @Override
    public String toString(){
        String ret = "";
        if(getIm() == 0.) ret = String.valueOf(getRe());
        else if(getRe() == 0.) ret = String.valueOf(getIm()) + "i";
        else ret = getRe() + " + " + getIm() + "i";
        return ret;
    }


    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.getRe() + c2.getRe(), c1.getIm() + c2.getIm());
    }


    public static Complex subtract(Complex c1, Complex c2) {
        return new Complex(c1.getRe() - c2.getRe(), c1.getIm() - c2.getIm());
    }

    public static Complex multiply(Complex c1, Complex c2) {
        return new Complex(c1.getRe()*c2.getRe() - c1.getIm() * c2.getIm(), c1.getRe()*c2.getIm() + c1.getIm()*c2.getRe());
    }

    public static Complex multiply(Complex c1, double b)
    {
        return new Complex(c1.getRe()*b, c1.getIm()*b);
    }

    public double mod()
    {
        return Math.sqrt(re * re + im * im);
    }

    public boolean equals(Complex c2)
    {
        return (im==c2.getIm() && re==c2.getRe());
    }

    public static Complex sqrt(double val)
    {
        if(val<0)
            return new Complex(0.0, Math.sqrt(-1. * val));
        else
            return new Complex(Math.sqrt(val), 0.0);
    }

    public void conjugate()
    {
        im *= -1.;
    }

    public void opposite()
    {
        re *= -1.;
        im *= -1.;
    }

    public boolean czyCzescZespolona()
    {
        return getIm()!=0.;
    }


}
