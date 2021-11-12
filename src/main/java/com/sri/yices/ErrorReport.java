package com.sri.yices;

//since 2.6.4
//
// The ErrorReport is a final field of a thrown YicesException.

public class ErrorReport {

    public final int code;
    public final int line;
    public final int column;
    
    public final int term1;
    public final int type1;

    public final int term2;
    public final int type2;

    public final long badval;

    // Called in YicesException.
    protected ErrorReport() {
        this.code = 0;
        this.line = 0;
        this.column = 0;
        this.term1 = 0;
        this.type1 = 0;
        this.term2 = 0;
        this.type2 = 0;
        this.badval = 0;
    }

    // N.B. this is called in yicesJNI.cpp so don't modify it, please.
    protected ErrorReport(int code, int line, int column, int term1, int type1, int term2, int type2, long badval){
        this.code = code;
        this.line = line;
        this.column = column;
        this.term1 = term1;
        this.type1 = type1;
        this.term2 = term2;
        this.type2 = type2;
        this.badval = badval;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.term1 > 0){
            sb.append("Term 1: ").append(Terms.toString(this.term1)).append("\n");
        }
        if (this.type1 > 0){
            sb.append("Type 1: ").append(Terms.toString(this.type1)).append("\n");
        }
        if (this.term2 > 0){
            sb.append("Term 2: ").append(Terms.toString(this.term2)).append("\n");
        }
        if (this.type2 > 0){
            sb.append("Type 2: ").append(Terms.toString(this.type2)).append("\n");
        }
        return sb.toString();
        /*
        return String.format("code = %d\nline = %d\ncolumn = %d\nterm 1 = %d\ntype 1 = %d\nterm 2 = %d\ntype 2 = %d\nbad val = %d\n",
                             this.code,
                             this.line,
                             this.column,
                             this.term1,
                             this.type1,
                             this.term2,
                             this.type2,
                             this.badval);
        */
    }


}
