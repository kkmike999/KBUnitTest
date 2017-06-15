package android.content;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by kkmike999 on 2017/06/15.
 */
public class ShadowContextTest {

    ShadowContext shadowContext;

    @Before
    public void setUp() throws Exception {
        shadowContext = new ShadowContext(null);
    }

    @Test
    public void deleteAllTempDir() throws Exception {
        ShadowContext.deleteAllTempDir();
    }

}