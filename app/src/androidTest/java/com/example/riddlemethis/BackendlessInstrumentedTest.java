package com.example.riddlemethis;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BackendlessInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.riddlemethis", appContext.getPackageName());
    }
    @Test
    public void login_works() {
        final BackendlessUser example = new BackendlessUser();
        example.setEmail("email@example.com");
        example.setPassword("example");
        example.setProperty("username","Example User - Ignore");
        Backendless.UserService.login("email@example.com", "example", new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        assertEquals("Logging out failed", 1, 2);
                    }
                });
                assertEquals(example, response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                assertEquals("Login attempt failed entirely", 1, 2);
            }
        });
    }
}
