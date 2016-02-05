package com.eleme.controller.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.SessionBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.city.CityAndProvBean;
import com.eleme.domain.mart.city.CityCompareBean;
import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.MCityTreeVo;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.service.basic.ICityService;
import com.eleme.soa.gaia.GaiaService;
import com.eleme.soa.gaia.TCity;
import com.eleme.soa.gaia.TCityQuery;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 城市信息管理
 * 
 * @author huwenwen
 *
 */
@Controller
@RequestMapping(value = "/city")
public class CityController extends BaseController {
  /**
   * log
   */
  private static Log log = LogFactory.getLog(CityController.class);

  @Inject
  private ICityService cityService;

  @Inject
  private GaiaService gaiaService;

  /**
   * 城市列表
   * 
   * @param request
   * @param response
   * @throws Exception
   * @return
   */
  @RequestMapping(value = "/list")
  @UserMenu
  public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    ModelAndView mav = new ModelAndView("/city/list");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到城市列表界面", session.getUserName());
    List<CityAndProvBean> cityInfo = cityService.getCityInfo();
    mav.addObject("cityInfo", cityInfo);
    return mav;
  }

  /**
   * 获得城市信息
   * 
   * @param request
   * @param response
   * @param cityID
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/getCityInfo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getCityInfo(HttpServletRequest request, HttpServletResponse response,
      Integer cityID) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}根据城市ID{}获取的信息", session.getUserName(), cityID);
    // 将得到的信息封装成Map集合
    List<MFinanceOrg> foInfo = cityService.getFoInfo(cityID);
    List<MFinanceProduct> productInfo = cityService.getProductInfo(cityID);
    StringBuilder foName = new StringBuilder();
    StringBuilder productName = new StringBuilder();
    for (int i = 0; i < foInfo.size(); i++) {
      foName.append(foInfo.get(i).getFoName() + "  ");
    }
    for (int i = 0; i < productInfo.size(); i++) {
      productName.append(productInfo.get(i).getFpName() + "  ");
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("foName", foName);
    map.put("productName", productName);
    map.put("foInfo", foInfo);
    return map;
  }

  /**
   * 同步napos城市信息
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/keepNaposCityInfo")
  @UserMenu
  public ModelAndView keepNaposCityInfo(HttpServletRequest request, HttpServletResponse response,
      Integer falg) throws Exception {
    ModelAndView mav = new ModelAndView("/city/changelist");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}进入同步napos城市信息页面", session.getUserName());
    // 封装查询napos城市信息条件，只查有效的
    TCityQuery cityQuery = new TCityQuery();
    cityQuery.setIs_valid(true);
    // 得到napos城市信息
    List<TCity> query_city = gaiaService.query_city(cityQuery);
    // 将napos城市信息转换
    List<CityCompareBean> naposCity = new ArrayList<CityCompareBean>();
    for (TCity city : query_city) {
      CityCompareBean cityCompare = new CityCompareBean();
      cityCompare.setCityId(city.getId());
      cityCompare.setCityName(city.getName());
      cityCompare.setCityPinyin(city.getPinyin());
      naposCity.add(cityCompare);
    }
    // 得到我们自己的城市信息
    List<CityCompareBean> eCity = cityService.getAllCityInfo();
    // 改变和增加的城市
    List<CityCompareBean> changeCity = naposCity;
    changeCity.removeAll(eCity);
    // 用来存改变的城市
    List<CityCompareBean> updateCity = new ArrayList<CityCompareBean>();
    // 用来存增加的城市
    List<CityCompareBean> insertCity = new ArrayList<CityCompareBean>();
    // 得到napos城市重复的名字
    Set<String> setNapos = new HashSet<String>();
    Set<String> setRepeat = new HashSet<String>();
    for (TCity napos : query_city) {
      if (setNapos.contains(napos.getName())) {
        setRepeat.add(napos.getName());
      } else {
        setNapos.add(napos.getName());
      }
    }
    // 存需要更新，但有重复名字的城市
    List<CityCompareBean> repeatCity = new ArrayList<CityCompareBean>();
    for (CityCompareBean change : changeCity) {
      int isUpdate = 0;
      for (CityCompareBean e : eCity) {
        if (change.getCityName().equals(e.getCityName())) {
          // 有重复名字的城市
          if (setRepeat.contains(change.getCityName())) {
            change.setOriginalCityId(e.getCityId());
            repeatCity.add(change);
            isUpdate = 2;
            break;
          }
          isUpdate++;
          change.setOriginalCityId(e.getCityId());
          break;
        }
      }
      // 改变的城市
      if (isUpdate == 1) {
        updateCity.add(change);
      } else if (isUpdate == 0) {
        // 增加的城市
        insertCity.add(change);
      }
    }
    // 判断是否要批量更行
    if (falg != null && updateCity.size() > 0) {
      List<CityCompareBean> needUpdateCity = updateCity;
      // 将重复的城市以和我们这边相同的城市Id 更行名字和拼音
      for (CityCompareBean rCity : repeatCity) {
        if (rCity.getCityId().equals(rCity.getOriginalCityId())) {
          needUpdateCity.add(rCity);
        }
      }
      log.info("{}批量更行城市信息", session.getUserName());
      int lines = cityService.batchUpdateCity(needUpdateCity);
      log.info("{}批量更新城市信息，影响行数{}", session.getUserName(), lines);
      updateCity = new ArrayList<CityCompareBean>();
    }
    mav.addObject("repeatCity", repeatCity);
    mav.addObject("updateCity", updateCity);
    mav.addObject("insertCity", insertCity);
    return mav;
  }

  /**
   * 批量更新城市数据
   *
   * @param request
   * @param response
   * @param cityTree
   * @return
   */
  @RequestMapping(value = "/batchUpdateCity")
  public ModelAndView updateCity(HttpServletRequest request, HttpServletResponse response,
      MCityTree cityTree) {
    ModelAndView mav = new ModelAndView("redirect:/city/keepNaposCityInfo");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}批量更新城市信息", session.getUserName());
    int lines = cityService.updateMCityTree(cityTree);
    log.info("{}批量更新城市信息，影响行数{}", session.getUserName(), lines);
    return mav;
  }

  /**
   * 跳转到城市插入页面
   * 
   * @param request
   * @param response
   * @param cityTree
   * @return
   */
  @RequestMapping(value = "/insertCity")
  @AvoidDuplicateSubmission(needSaveToken = true)
  public ModelAndView insertCity(HttpServletRequest request, HttpServletResponse response,
      MCityTree cityTree) {
    ModelAndView mav = new ModelAndView("city/insertCity");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到城市插入界面", session.getUserName());
    // 得到省份集合
    List<MCityTree> provinceList = cityService.getProvinceList();
    mav.addObject("provinceList", provinceList);
    mav.addObject("city", cityTree);
    // 获得权限的集合
    return mav;
  }

  /**
   * 插入napos城市
   * 
   * @param request
   * @param response
   * @param cityTree
   * @return
   */
  @RequestMapping(value = "/insert", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  public ModelAndView insertCitySave(HttpServletRequest request, HttpServletResponse response,
      MCityTree cityTree) {
    ModelAndView mav = new ModelAndView("redirect:/city/keepNaposCityInfo");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}插入napos城市信息", session.getUserName());
    int lines = cityService.insertMCityTree(cityTree);
    log.info("{}插入napos城市信息，影响行数{}", session.getUserName(), lines);
    return mav;
  }

  /**
   * 城市基础表维护列表
   * 
   * @param request
   * @param response
   * @param cityTreeVo
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/mCityTree/list")
  @UserMenu
  public ModelAndView mCityTreeList(HttpServletRequest request, HttpServletResponse response,
      MCityTreeVo cityTreeVo, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("/city/mCityTree/list");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}进入城市基础表维护列表", session.getUserName());
    TbData tbData = cityService.getProvinceListPaging(currentPage, cityTreeVo);
    tbData.fillTbData("city/mCityTree/list", cityTreeVo, null);
    mav.addObject("tbData", tbData);
    mav.addObject("cityTreeVo", cityTreeVo);
    return mav;
  }

  /**
   * 跳转到省份编辑页面
   * 
   * @param request
   * @param response
   * @param cityTree
   * @return
   */
  @RequestMapping(value = "/mCityTree/edit", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  public ModelAndView mcityEdit(HttpServletRequest request, HttpServletResponse response,
      MCityTree cityTree) {
    ModelAndView mav = new ModelAndView("city/mCityTree/edit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到省份编辑界面", session.getUserName());
    // 得到所以的省份
    List<MCityTree> provinceList = cityService.getProvinceList();
    mav.addObject("cityTree", cityTree);
    mav.addObject("provinceList", provinceList);
    return mav;
  }

  /**
   * 省份编辑保存
   * 
   * @param request
   * @param response
   * @param cityTree
   * @return
   */
  @RequestMapping(value = "/mCityTree/editSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  public ModelAndView mcityEditSave(HttpServletRequest request, HttpServletResponse response,
      MCityTree cityTree) {
    ModelAndView mav = new ModelAndView("redirect:/city/mCityTree/list");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}编辑城市基础表省份信息", session.getUserName());
    int lines = cityService.updateMCityTreeProvince(cityTree);
    log.info("{}编辑城市基础表省份信息，影响行数{}", session.getUserName(), lines);
    return mav;
  }

  /**
   * 城市基础表省份下城市列表
   * 
   * @param request
   * @param response
   * @param cityTreeVo
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/mCityTree/citylist/{parentID}")
  public ModelAndView cityList(HttpServletRequest request, HttpServletResponse response,
      MCityTreeVo cityTreeVo, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("city/mCityTree/citylist");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}城市基础表，查看省份对应的城市信息", session.getUserName());
    TbData tbData = cityService.getCityListInProvincePaging(currentPage, cityTreeVo);
    tbData.fillTbData("city/mCityTree/citylist/" + cityTreeVo.getParentID(), cityTreeVo, null);
    mav.addObject("tbData", tbData);
    mav.addObject("cityTreeVo", cityTreeVo);
    return mav;
  }

  /**
   * 进入城市编辑页面
   * 
   * @param request
   * @param response
   * @param cityID
   * @return
   */
  @RequestMapping(value = "/mCityTree/cityedit/{cityID}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  public ModelAndView cityedit(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer cityID) {
    ModelAndView mav = new ModelAndView("city/mCityTree/cityedit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到城市编辑界面", session.getUserName());
    // 得到该城市信息
    MCityTree cityTree = cityService.getCityInfoByCityId(cityID);
    // 得到所以省份信息
    List<MCityTree> provinceList = cityService.getProvinceList();
    mav.addObject("cityTree", cityTree);
    mav.addObject("provinceList", provinceList);
    return mav;
  }

  /**
   * 城市编辑保存
   * 
   * @param request
   * @param response
   * @param cityTree
   * @return
   */
  @RequestMapping(value = "/mCityTree/cityeditSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  public ModelAndView cityeditSave(HttpServletRequest request, HttpServletResponse response,
      MCityTree cityTree) {
    ModelAndView mav =
        new ModelAndView("redirect:/city/mCityTree/citylist/" + cityTree.getParentID());
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}编辑城市基础表城市信息", session.getUserName());
    // 城市变为省份
    if (cityTree.getCityType() == 1) {
      cityTree.setParentID(0);
      cityTree.setParentName("中国");
    }
    int lines = cityService.updateMCityTree(cityTree);
    log.info("{}编辑城市基础表城市信息，影响行数{}", session.getUserName(), lines);
    return mav;
  }
}
