package com.wen.test;

import com.wen.AutoInjectResource;
import com.wen.bean.ResourceBean;
import com.wen.exception.InjectResourceException;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

/**
 * @author huwenwen
 * @since 17/1/14
 */
public class InjectResourceTest extends BaseTest {

  @Inject
  private AutoInjectResource autoInjectResource;

  @Test
  public void injectTest() throws InjectResourceException {
    List<ResourceBean> resourceBeen = autoInjectResource.saveResource();
    logger.info("------{}", resourceBeen.size());
  }

}
