package de.nenick.espressomacchiato.testbase;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.FailureHandler;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.File;

import de.nenick.espressomacchiato.test.views.BaseActivity;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

public class EspScreenshotFailureHandlerTest extends EspressoTestCase<BaseActivity> {

    private EspScreenshotFailureHandler espScreenshotFailureHandler = new EspScreenshotFailureHandler(InstrumentationRegistry.getTargetContext());

    @Test
    public void testScreenshot() {
        try {
            espScreenshotFailureHandler.handle(new TestException(), isRoot());
            fail("should throw given exception by delegating it to default failure handler");
        } catch (TestException e) {
            File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "test-screenshots/Failed-EspScreenshotFailureHandlerTest.testScreenshot.png");
            assertThat(screenshot.exists(), is(true));

            //noinspection ResultOfMethodCallIgnored
            screenshot.delete();
        }
    }

    @Test
    public void testForCoverage() {
        espScreenshotFailureHandler.delegate = new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher) {

            }
        };

        espScreenshotFailureHandler.handle(new TestException(), isRoot());
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "test-screenshots/Failed-EspScreenshotFailureHandlerTest.testForCoverage.png");
        assertThat(screenshot.exists(), is(true));

        //noinspection ResultOfMethodCallIgnored
        screenshot.delete();
    }

    private static class TestException extends RuntimeException {
    }
}