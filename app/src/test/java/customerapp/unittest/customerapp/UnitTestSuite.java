package customerapp.unittest.customerapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the test suite to run all UnitTests
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressBookUnitTest.class,
        RecipientUnitTest.class,
})

public class UnitTestSuite { }