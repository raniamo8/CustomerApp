package com.example.customerapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IntroUITest.class,
        QRCodeUITest.class,
})

public class UITestSuite { }
