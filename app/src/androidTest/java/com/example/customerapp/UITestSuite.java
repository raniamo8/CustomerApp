package com.example.customerapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the test suite to run all UITests
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IntroUITest.class,
        QRCodeUITest.class,
})

public class UITestSuite { }
