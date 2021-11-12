package com.sri.yices;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assume.assumeTrue;

public class TestVersion {
    @Test
    public void testVersion() {
        assumeTrue(TestAssumptions.IS_YICES_INSTALLED);
        StringBuilder sb = new StringBuilder();
        sb.append("Loaded Yices version ").append(Yices.version()).append("\n");
        sb.append("Yices version ordinal ").append(Yices.versionOrdinal()).append("\n");
        sb.append("Built for ").append(Yices.buildArch()).append("\n");
        sb.append("Build mode: ").append(Yices.buildMode()).append("\n");
        sb.append("Build date: ").append(Yices.buildDate()).append("\n");
        sb.append("MCSat supported: ").append(Yices.hasMcsat()).append("\n").append("\n");

        String message = sb.toString();
        System.out.println(message);

        YicesException error = YicesException.checkVersion(3, 7, 9);
        System.out.println(error);

        Assert.assertTrue(Yices.versionOrdinal() >= Yices.versionOrdinal(2, 6, 1));

    }
}
