package edu.uta.cse.personaltutorservice;

import android.app.Application;
import android.test.AndroidTestRunner;
import android.test.ApplicationTestCase;
import android.support.test.runner.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}