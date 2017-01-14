package com.wen.controller;

import com.wen.AutoInjectResource;
import com.wen.bean.ResourceBean;
import com.wen.exception.InjectResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author huwenwen
 * @since 16/12/28
 */
@RestController
public class ResourceInjectController {

  private static final Logger logger = LoggerFactory.getLogger(ResourceInjectController.class);

  @Inject
  private AutoInjectResource autoInjectResource;

  @RequestMapping(value = "/inject")
  public List<ResourceBean> test() throws InjectResourceException {
    return autoInjectResource.saveResource();
  }

}


