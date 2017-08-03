package jacocoTest;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UtilTest {

    private Util serviceUnderTest = new Util();
    
    @DataProvider
    public Object[][] testData() {
        return new Object[][] { { 2, 2, 4 }, { 3, 3, 6 } };
    }

    @Test(dataProvider = "testData")
    public void shouldAdd(int a, int b, int expected) {
        Assert.assertEquals(serviceUnderTest.add(a, b), expected);
    }
}