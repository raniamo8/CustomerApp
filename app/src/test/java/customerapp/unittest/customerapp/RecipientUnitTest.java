package customerapp.unittest.customerapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static customerapp.models.customerapp.AddressBook.PREF_RECIPIENTS_KEY;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.AddressBook;
import customerapp.models.customerapp.Recipient;

@RunWith(RobolectricTestRunner.class)
public class RecipientUnitTest {

}
