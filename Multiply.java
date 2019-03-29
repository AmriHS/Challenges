public long multiply(int x, int y){
    // check if either of them is negative, but not both
    int sign = (x < 0 ^ y < 0) ? -1 : 1; 
    // get the absolute value and later we assign the sign
    long x1  = Math.abs((long)x);
    long y1 = Math.abs((long)y);
    long prod = 0;
    while(y1>0){
        if ((y1&1) == 1) prod+=x1;
        x1<<=1;
        y1>>=1;
    }
    return sign*prod;
}
