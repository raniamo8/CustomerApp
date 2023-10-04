package customerapp.unittest.customerapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import customerapp.unittest.customerapp.AddressBookUnitTest;
import customerapp.unittest.customerapp.RecipientUnitTest;

/**
 * This is the test suite to run all UITests
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressBookUnitTest.class,
        RecipientUnitTest.class,
})

public class UnittestSuite { }
