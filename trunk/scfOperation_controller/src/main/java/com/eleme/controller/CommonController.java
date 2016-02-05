package com.eleme.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleme.domain.mart.city.MCityTree;
import com.eleme.service.basic.ICityService;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

@Controller
public class CommonController extends BaseController {
  private static Log log = LogFactory.getLog(CommonController.class);

  @Inject
  private ICityService cityService;


  @RequestMapping(value = "/cityList/{provinceId}")
  @ResponseBody
  public List<MCityTree> getCityList(@PathVariable Integer provinceId) {
    return cityService.getCityListByProvinceId(provinceId);
  }
}
