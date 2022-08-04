package com.sri.yices;

/**
 * Catch all exception thrown by the Yices wrapper when something goes wrong.
 * We make this a RuntimeException to be consistent with the Galois AST visitors.
 * The visitors don't allow checked Exceptions.
 */
public class YicesException extends RuntimeException {

    // never null, sometimes not interesting (code == 0) (i.e. a bad version exception)
    public final ErrorReport errorReport;

    // construct an exception form the Yices internal error string
    // then clear the internal error.
    protected YicesException() {
        super(Yices.errorString());
        this.errorReport = Yices.errorReport();
        // since we (qua BD) reset it here, we need to record it before.
        Yices.resetError();
    }

    private YicesException(String message) {
        super(message);
        // better to have something there, rather than null
        this.errorReport = new ErrorReport();
    }

    /**
     * We use this to create an exception to throw if an operation requires a more recent 
     * version of yices.
     * @param  version  (a number between 0 and less than 100) the required version number.
     * @param  major    (a number between 0 and less than 100) the required major number.
     * @param  patch    (a number between 0 and less than 100) the required patch number.
     *
     */
    protected static YicesException checkVersion(int version, int major, int patch) {
        long requiredVersion = Yices.versionOrdinal(version, major, patch);
        long actualVersion = Yices.versionOrdinal();
        if (requiredVersion > actualVersion) {
            String message = versionMismatchMsg(version, major, patch);   
            return new YicesException(message);
        } 
        return null;
    }

    private static String ordinal2String(long ordinal) {
        long x = ordinal / 10000;
        long rem = ordinal % 10000;
        long y = rem / 100;
        rem = rem % 100;
        long z = rem;
        return String.format("%d.%d.%d", x, y, z);
    }

    private static String versionMismatchMsg(int version, int major, int patch) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Version Mismatch -\nThe required version of the Yices is: %d.%d.%d\n", version, major, patch));
        sb.append(String.format("Your Yices dynamic library version is: %s\n", Yices.version()));
        sb.append(String.format("Your Yices java bindings were compiled against a Yices with version: %s\n", ordinal2String(Yices.versionOrdinal())));
        sb.append("All three versions need to match.\n");
        return sb.toString();
    }

}
