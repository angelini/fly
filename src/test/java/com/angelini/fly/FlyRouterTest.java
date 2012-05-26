package com.angelini.fly;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FlyRouterTest extends TestCase{

    public FlyRouterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite( FlyRouterTest.class );
    }

    public void testRouteTreeBuilder() {
        assertTrue(false);
    }

}
