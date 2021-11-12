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
        if (Yices.versionOrdinal(version, major, patch) > Yices.versionOrdinal()) {
            String message = String.format("The required version of the yices dynamic library is %d.%d.%d, yours is %s", version, major, patch, Yices.version());
            return new YicesException(message);
        } 
        return null;
    }

}
