package water;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class H2OTest {
  @Test
  public void testGetSysProperty() {
    assertEquals("default1", H2O.getSysProperty("test.testGetSysProperty", "default1"));
    System.setProperty(H2O.ARGS.SYSTEM_PROP_PREFIX + "test.testGetSysProperty", "value1");
    assertEquals("value1", H2O.getSysProperty("test.testGetSysProperty", "default1"));
  }

  @Test
  public void testGetSysBoolProperty() {
    assertFalse(H2O.getSysBoolProperty("test.testGetSysBoolProperty", false));
    assertTrue(H2O.getSysBoolProperty("test.testGetSysBoolProperty", true));
    System.setProperty(H2O.ARGS.SYSTEM_PROP_PREFIX + "test.testGetSysBoolProperty", "true");
    assertTrue(H2O.getSysBoolProperty("test.testGetSysBoolProperty", false));
  }

  @Test
  public void testCalcNextUniqueObjectId() {
    AtomicLong seq = new AtomicLong(41);
    checkObjectId(H2O.calcNextUniqueObjectId("test-type", seq, "test-desc"), 42);
    checkObjectId(H2O.calcNextUniqueObjectId("test-type", seq, "test-desc"), 43);
  }

  private void checkObjectId(String id, int seq) {
    assertTrue(id.startsWith("test-desc_test-type"));
    assertTrue(id.endsWith("_" + seq));
  }

  @Test
  public void testPropertiesMatching() throws Exception {
      // Any string with a dot after `ai.h2o.` must fail
      assertFalse(H2O.IGNORED_PROPERTIES.matcher("ai.ai.h2o.anythingwithdotwillfail.").matches());
      assertFalse(H2O.IGNORED_PROPERTIES.matcher("ai.ai.h2o.org.eclipse.jetty.LEVEL").matches());
      assertFalse(H2O.IGNORED_PROPERTIES.matcher("ai.h2o.org.eclipse.jetty.util.log.class").matches());
      assertFalse(H2O.IGNORED_PROPERTIES.matcher("ai.h2o.org.eclipse.jetty.util.log.StdErrLog").matches());
      
      assertTrue(H2O.IGNORED_PROPERTIES.matcher("ai.h2o.hdfs_config").matches());
  }
  
}
