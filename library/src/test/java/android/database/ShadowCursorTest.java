package android.database;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kkmike999 on 2017/06/14.
 */
public class ShadowCursorTest {

    @Test
    public void getString() throws Exception {
        List<String> columns = Arrays.asList("first", "second", "third");
        List<Object> datas   = Arrays.asList("test", null, new Object());

        ShadowCursor cursor = new ShadowCursor(columns, Arrays.asList(datas));

        cursor.moveToNext();

        Assert.assertEquals("test", cursor.getString(0));
        Assert.assertEquals(null, cursor.getString(1));
    }

}