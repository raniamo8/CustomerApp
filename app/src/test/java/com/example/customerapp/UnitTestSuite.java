package com.example.customerapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressBookUnitTest.class,
        RecipientUnitTest.class,
})

public class UnitTestSuite { }