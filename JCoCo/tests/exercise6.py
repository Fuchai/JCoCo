import disassembler

class Rational:
    def __init__(self, numerator, denominator):
        self.numerator=numerator
        self.denominator=denominator

    def __add__(self,other):
        oversized_numerator=self.denominator*other.numerator+self.numerator*other.denominator
        oversized_denominator=self.denominator*other.denominator
        ggcd=self.gcd(oversized_denominator,oversized_numerator)
        return (Rational(oversized_numerator//ggcd,oversized_denominator//ggcd))

    def __mul__(self,other):
        oversized_numerator=self.numerator*other.numerator
        oversized_denominator=self.denominator*other.denominator
        ggcd=self.gcd(oversized_numerator,oversized_denominator)
        return (Rational(oversized_numerator//ggcd,oversized_denominator//ggcd))

    def __str__(self):
        return (str(self.numerator)+"/"+str(self.denominator))

    def gcd(self,a,b):
        if a>b:
            if b==0:
                return a
            return self.gcd(b,a%b)
        else:
            return self.gcd(b,a)

def main():
    x=Rational(1,2)2
    y=Rational(2,3)
    print(x)
    print(x+y)
    print(x*y)

disassembler.disassemble(Rational)
disassembler.disassemble(main)
