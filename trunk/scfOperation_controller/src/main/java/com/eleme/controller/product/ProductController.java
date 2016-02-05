package com.eleme.controller.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.bean.product.ProductAddBean;
import com.eleme.bean.product.ProductApplyAddBean;
import com.eleme.bean.product.ProductApproveBean;
import com.eleme.bean.product.ProductEditBean;
import com.eleme.bean.product.ProductMaterialAddBean;
import com.eleme.bean.product.ProductQueryBean;
import com.eleme.bean.product.ProductRuleAddBean;
import com.eleme.constants.RuleName;
import com.eleme.constants.RuleType;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.basic.MBasicData;
import com.eleme.domain.mart.basic.MBasicDataKey;
import com.eleme.domain.mart.city.MCityTree;
import com.eleme.domain.mart.city.TFcoCoveredCity;
import com.eleme.domain.mart.finance.FinanceQueryBean;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.product.MFinanceProduct;
import com.eleme.domain.mart.product.MFinanceProductVo;
import com.eleme.domain.mart.product.MProductRuleDetail;
import com.eleme.domain.mart.product.ProductQueryCnd;
import com.eleme.domain.mart.product.ProductRuleDetailCnd;
import com.eleme.domain.mart.product.ProductRuleQueryCnd;
import com.eleme.domain.mart.product.TApvFinanceProductHistory;
import com.eleme.domain.mart.product.TFcoRequestRule;
import com.eleme.service.basic.IBasicService;
import com.eleme.service.file.IFileService;
import com.eleme.service.finance.IFinanceService;
import com.eleme.service.product.IProductService;
import com.eleme.util.TokenProcessor;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 金融机构产品方法控制器。
 * 
 * @author yonglin.zhu
 *
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(ProductController.class);

  @Inject
  private IFinanceService financeService;

  @Inject
  private IProductService productService;

  @Inject
  private IBasicService basicService;

  @Inject
  private IFileService fileService;

  /**
   * 跳转到添加金融机构产品的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addView", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addView(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    ModelAndView mav = new ModelAndView("product/add");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到添加金融机构产品界面", session.getUserName());
    // 查询金融机构信息
    FinanceQueryBean financeQueryBean = new FinanceQueryBean();
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
    mav.addObject("financeList", financeList);
    // 返回
    return mav;
  }

  /**
   * 融资产品添加操作
   * 
   * @param request
   * @param response
   * @param pab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("pab") ProductAddBean pab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();
    if (result.hasErrors()) {
      mav.setViewName("product/add");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      // 查询金融机构信息
      FinanceQueryBean financeQueryBean = new FinanceQueryBean();
      financeQueryBean.setFoStatus("1");
      List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
      mav.addObject("financeList", financeList);
      return mav;
    } else {
      relationCheck(pab.getStartDate(), pab.getEndDate(), pab.getMinRaitRatio(),
          pab.getMaxRaitRatio(), result);
      if (result.hasErrors()) {
        mav.setViewName("product/add");
        // 需要再次设置token
        request.getSession(false).setAttribute("token",
            TokenProcessor.getInstance().generateToken(request));
        // 查询金融机构信息
        FinanceQueryBean financeQueryBean = new FinanceQueryBean();
        financeQueryBean.setFoStatus("1");
        List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
        mav.addObject("financeList", financeList);
        return mav;
      }
    }

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加融资产品信息", session.getUserName());
    // clone数据到MFinanceProduct
    MFinanceProduct fp = new MFinanceProduct();
    BeanUtils.copyProperties(fp, pab);
    // 调用service，得到影响行数
    int lines = productService.saveproductAdd(fp);
    // 记录日志
    log.info("{}添加融资产品信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/list");
    return mav;
  }

  /**
   * 查询融资产品信息
   * 
   * @param request
   * @param pqb
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView list(HttpServletRequest request, ProductQueryBean pqb, Integer currentPage)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询融资产品列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("product/list");
    ProductQueryCnd pqc = new ProductQueryCnd();
    BeanUtils.copyProperties(pqc, pqb);
    TbData tbData = productService.selectProductList(pqc, currentPage);
    tbData = tbData.fillTbData("product/list", pqc, null);
    // 查询覆盖城市名
    List<MFinanceProductVo> mFPVs = (List<MFinanceProductVo>) tbData.getList();
    for (MFinanceProductVo mFPV : mFPVs) {
      String fpAreaIds = mFPV.getFpArea();
      String fpAreaNames = "";
      List<MCityTree> mCityList = financeService.queryMCityInfosByCityIds(fpAreaIds);
      if (mCityList.size() > 0) {
        for (MCityTree mCityTree : mCityList) {
          fpAreaNames += "," + mCityTree.getCityName();
        }
      }

      if (fpAreaNames.length() > 0) {
        fpAreaNames = fpAreaNames.substring(1, fpAreaNames.length());
      }
      mFPV.setFpArea(fpAreaNames);
    }
    // 查询金融机构信息
    FinanceQueryBean financeQueryBean = new FinanceQueryBean();
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
    mav.addObject("tbData", tbData);
    mav.addObject("financeList", financeList);
    mav.addObject("pqb", pqb);
    return mav;
  }

  /**
   * 跳转到编辑融资产品的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("product/edit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    MFinanceProductVo financeProductVo = productService.selectProductById(id);
    // 查询融资产品信息
    FinanceQueryBean financeQueryBean = new FinanceQueryBean();
    financeQueryBean.setFoStatus("1");
    List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
    if (financeProductVo == null) {
      log.info("未找到对应的融资产品");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到编辑融资产品界面", session.getUserName());
      ProductAddBean pab = new ProductAddBean();
      BeanUtils.copyProperties(pab, financeProductVo);
      mav.addObject("pab", pab);
      mav.addObject("financeList", financeList);
      // 根据id，到t_sys_upload_file中查询最新记录
      SysUploadFileBean file = fileService.getUploadFileById(financeProductVo.getUfId());
      if (file != null) {
        mav.addObject("url", file.getUrl());
      }
    }
    // 返回
    return mav;
  }

  /**
   * 编辑融资产品信息
   * 
   * @param request
   * @param response
   * @param pab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("pab") ProductEditBean pab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView();

    if (result.hasErrors()) {
      mav.setViewName("product/edit");
      // 需要再次设置token
      request.getSession(false).setAttribute("token",
          TokenProcessor.getInstance().generateToken(request));
      // 查询金融机构信息
      FinanceQueryBean financeQueryBean = new FinanceQueryBean();
      financeQueryBean.setFoStatus("1");
      List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
      mav.addObject("financeList", financeList);
      return mav;
    } else {
      // 关连验证
      relationCheck(pab.getStartDate(), pab.getEndDate(), pab.getMinRaitRatio(),
          pab.getMaxRaitRatio(), result);
      // 业务验证
      ProductQueryCnd pqc = new ProductQueryCnd();
      pqc.setFpId(pab.getFpId());
      pqc.setPtName(pab.getFpName());
      if (!productService.checkSingleProduct(pqc)) {
        result.addError(new FieldError("pab", "fpName", "产品名称已存在"));
      }
      if (result.hasErrors()) {
        mav.setViewName("product/edit");
        // 需要再次设置token
        request.getSession(false).setAttribute("token",
            TokenProcessor.getInstance().generateToken(request));
        // 查询金融机构信息
        FinanceQueryBean financeQueryBean = new FinanceQueryBean();
        financeQueryBean.setFoStatus("1");
        List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);
        mav.addObject("financeList", financeList);
        return mav;
      }
    }

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改融资产品信息", session.getUserName());
    // clone数据到MFinanceOrg
    MFinanceProductVo fp = new MFinanceProductVo();
    BeanUtils.copyProperties(fp, pab);
    fp.setOperationUserId(session.getUserId().intValue());
    // 调用service，得到影响行数
    int lines = productService.updateProductById(fp);
    // 记录日志
    log.info("{}修改融资产品信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/list");
    return mav;
  }

  /**
   * 关联check
   * 
   * @param pab
   * @param result
   */
  private void relationCheck(Date startDate, Date endDate, BigDecimal minRaitRatio,
      BigDecimal maxRaitRatio, BindingResult result) {

    // 产品上线开始时间和结束时间不为空的情况
    if (startDate != null && endDate != null) {
      // 验证开始时间是否小于等于结束时间
      if (startDate.compareTo(endDate) > 0) {
        // 验证
        result.addError(new FieldError("pab", "startDate", "开始时间不能大于结束时间"));
      }
    }
    // 验证年利率区间
    if (minRaitRatio.compareTo(maxRaitRatio) > 0) {
      // 验证
      result.addError(new FieldError("pab", "raitRatio", "年利率不正确"));
    }
  }

  /**
   * 删除融资产品信息
   * 
   * @param request
   * @param response
   * @param fpId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/del", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage deleteUser(HttpServletRequest request, HttpServletResponse response,
      Integer fpId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除融资产品信息,账户ID:{}", session.getUserName(), fpId);
    // 修改金融机构账户信息
    MFinanceProductVo financeProduct = new MFinanceProductVo();
    financeProduct.setFpId(fpId);
    financeProduct.setProductStatus(3);
    financeProduct.setOperationUserId(session.getUserId().intValue());
    // 调用service
    int lines = productService.updateProductById(financeProduct);
    // 记录日志
    log.info("{}修改融资产品信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }

  /**
   * 跳转到添加规则画面
   * 
   * @param request
   * @param response
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addRuleView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addRuleView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("product/ruleadd");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到融资产品规则添加界面", session.getUserName());
    // 查询融资产品信息
    MBasicDataKey key = new MBasicDataKey();
    key.setTypeId(RuleName.Rule_1.getIndex());
    List<MBasicData> list = basicService.queryBasicDataList(key);
    mav.addObject("list", list);
    mav.addObject("fpId", id);
    // 返回
    return mav;
  }

  /**
   * 融资产品规则添加操作
   * 
   * @param request
   * @param response
   * @param pab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addRuleSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addRuleSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("prab") ProductRuleAddBean prab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    // 303验证
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/ruleadd");
      mav.addObject("fpId", prab.getFpId());
      return mav;
    } else {
      // 关联验证
      ruleRelationCheck(prab, result);
      if (result.hasErrors()) {
        mav = initView(mav, request, "product/ruleadd");
        mav.addObject("fpId", prab.getFpId());
        return mav;
      }
    }

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加融资产品规则信息", session.getUserName());
    // 业务验证
    ProductRuleQueryCnd cnd = new ProductRuleQueryCnd();
    BeanUtils.copyProperties(cnd, prab);
    if (productService.checkSingleRule(cnd)) {
      // 验证
      result.addError(new FieldError("prab", "ruleName", "规则重复，请选择其他规则"));
      mav = initView(mav, request, "product/ruleadd");
      mav.addObject("fpId", prab.getFpId());
      return mav;
    }
    // clone数据到MFinanceOrg
    TFcoRequestRule rule = new TFcoRequestRule();
    BeanUtils.copyProperties(rule, prab);
    // 调用service，得到影响行数
    int lines = productService.ruleAdd(rule);
    // 记录日志
    log.info("{}添加融资产品规则信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/rulelist/" + prab.getFpId());
    return mav;
  }

  /**
   * 初始化画面参数
   * 
   * @param mav
   * @param request
   * @param prab
   * @return
   */
  @UserMenu
  private ModelAndView initView(ModelAndView mav, HttpServletRequest request, String viewName) {
    mav.setViewName(viewName);
    // 需要再次设置token
    request.getSession(false).setAttribute("token",
        TokenProcessor.getInstance().generateToken(request));
    // 查询融资产品信息
    MBasicDataKey key = new MBasicDataKey();
    key.setTypeId(RuleName.Rule_1.getIndex());
    List<MBasicData> list = basicService.queryBasicDataList(key);
    mav.addObject("list", list);
    return mav;
  }

  /**
   * 关联check
   * 
   * @param pab
   * @param result
   */
  private void ruleRelationCheck(ProductRuleAddBean prab, BindingResult result) {
    // 验证规则区间
    if (Integer.parseInt(prab.getFromNum()) > Integer.parseInt(prab.getEndNum())) {
      // 验证
      result.addError(new FieldError("prab", "symbol", "字段规则 不正确"));
    }
  }

  /**
   * 查询融资产品信息
   * 
   * @param request
   * @param pqb
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/rulelist/{fpId}", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView rulelist(HttpServletRequest request, @PathVariable Integer fpId,
      Integer currentPage) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询融资产品规则列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("product/rulelist");
    ProductRuleQueryCnd cnd = new ProductRuleQueryCnd();
    cnd.setFpId(fpId);
    TbData tbData = productService.selectRuleList(cnd, currentPage);
    tbData = tbData.fillTbData("product/rulelist/" + fpId.intValue(), cnd, null);
    mav.addObject("tbData", tbData);
    mav.addObject("fpId", fpId);
    return mav;
  }

  /**
   * 跳转到融资产品规则编辑画面
   * 
   * @param request
   * @param response
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editRuleView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editRuleView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("product/ruleedit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}编辑融资产品规则", session.getUserName());
    TFcoRequestRule fcoRequestRule = productService.selectProductRuleById(id);
    // 查询融资产品信息
    MBasicDataKey key = new MBasicDataKey();
    key.setTypeId(RuleName.Rule_1.getIndex());
    List<MBasicData> list = basicService.queryBasicDataList(key);

    if (fcoRequestRule == null) {
      log.info("未找到对应的融资产品规则");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到编辑融资产品规则界面", session.getUserName());
      ProductRuleAddBean prab = new ProductRuleAddBean();
      BeanUtils.copyProperties(prab, fcoRequestRule);
      mav.addObject("prab", prab);
      mav.addObject("list", list);
    }

    // 返回
    return mav;
  }

  /**
   * 编辑融资产品规则
   * 
   * @param request
   * @param response
   * @param prab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editRuleSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editRuleSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("prab") ProductRuleAddBean prab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/ruleedit");
      return mav;
    } else {
      ruleRelationCheck(prab, result);
      if (result.hasErrors()) {
        mav = initView(mav, request, "product/ruleedit");
        return mav;
      }
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改融资产品规则信息", session.getUserName());
    // 业务验证
    ProductRuleQueryCnd cnd = new ProductRuleQueryCnd();
    BeanUtils.copyProperties(cnd, prab);
    if (productService.checkSingleRule(cnd)) {
      // 验证
      result.addError(new FieldError("prab", "ruleName", "规则重复，请选择其他规则"));
      mav = initView(mav, request, "product/ruleedit");
      return mav;
    }
    // clone数据到TFcoRequestRule
    TFcoRequestRule rule = new TFcoRequestRule();
    BeanUtils.copyProperties(rule, prab);
    // 调用service，得到影响行数
    int lines = productService.updateProductRuleById(rule);
    // 记录日志
    log.info("{}修改融资产品规则信息，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/rulelist/" + prab.getFpId());
    return mav;
  }

  /**
   * 删除融资产品规则
   * 
   * @param request
   * @param response
   * @param ruleId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/ruleDel", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage ruleDel(HttpServletRequest request, HttpServletResponse response,
      Integer ruleId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除融资产品规则信息,规则ID:{}", session.getUserName(), ruleId);
    // 修改融资产品规则信息
    TFcoRequestRule rule = new TFcoRequestRule();
    rule.setRuleId(ruleId);
    rule.setIsUse(3);
    // 调用service
    int lines = productService.updateProductRuleById(rule);
    // 记录日志
    log.info("{}修改融资产品规则信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }

  /**
   * 查询申请条件
   * 
   * @param request
   * @param fpId
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/applylist/{fpId}", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView applylist(HttpServletRequest request, @PathVariable Integer fpId,
      Integer currentPage) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询融资产品申请条件列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("product/applylist");
    ProductRuleDetailCnd cnd = new ProductRuleDetailCnd();
    // 申请条件
    cnd.setRuleType(RuleType.RuleType_1.getIndex());
    // 产品ID
    cnd.setFpId(fpId);
    TbData tbData = productService.selectProductRuleDetailList(cnd, currentPage);
    tbData = tbData.fillTbData("product/applylist/" + fpId.intValue(), cnd, null);
    mav.addObject("tbData", tbData);
    mav.addObject("fpId", fpId);
    return mav;
  }

  /**
   * 跳转到添加申请条件画面
   * 
   * @param request
   * @param response
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addApplyView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addApplyView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("product/applyadd");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到融资产品申请条件添加界面", session.getUserName());
    mav.addObject("fpId", id);
    // 返回
    return mav;
  }

  /**
   * 添加申请规则操作
   * 
   * @param request
   * @param response
   * @param paab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addApplySave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addApplySave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("paab") ProductApplyAddBean paab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    // 303验证
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/applyadd");
      mav.addObject("fpId", paab.getFpId());
      return mav;
    }
    // 唯一性验证
    ProductRuleDetailCnd cnd = new ProductRuleDetailCnd();
    cnd.setFpId(paab.getFpId());
    cnd.setRuleName(paab.getRuleName());
    cnd.setRuleType(RuleType.RuleType_1.getIndex());
    if (!productService.checkSingleProductRule(cnd)) {
      result.addError(new FieldError("paab", "ruleName", "规则名称已存在"));
    }
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/applyadd");
      mav.addObject("fpId", paab.getFpId());
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加融资产品申请条件", session.getUserName());

    // clone数据到MProductRuleDetail
    MProductRuleDetail apply = new MProductRuleDetail();
    BeanUtils.copyProperties(apply, paab);
    // 调用service，得到影响行数
    int lines = productService.applyAdd(apply);
    // 记录日志
    log.info("{}添加融资产品申请条件，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/applylist/" + paab.getFpId().intValue());
    return mav;
  }

  /**
   * 跳转到编辑融资产品申请条件页面
   * 
   * @param request
   * @param response
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editApplyView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editApplyView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("product/applyedit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}编辑融资产品申请条件", session.getUserName());
    MProductRuleDetail detail = productService.selectProductRuleDetailById(id);

    if (detail == null) {
      log.info("未找到对应的融资申请条件");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到编辑融资产品申请条件界面", session.getUserName());
      ProductApplyAddBean paab = new ProductApplyAddBean();
      BeanUtils.copyProperties(paab, detail);
      mav.addObject("paab", paab);
    }

    // 返回
    return mav;
  }

  /**
   * 点击申请条件编辑按钮
   * 
   * @param request
   * @param response
   * @param paab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editApplySave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editApplySave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("paab") ProductApplyAddBean paab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/applyedit");
      return mav;
    }
    // 唯一性验证
    ProductRuleDetailCnd cnd = new ProductRuleDetailCnd();
    cnd.setFpId(paab.getFpId());
    cnd.setRuleName(paab.getRuleName());
    cnd.setRuleType(RuleType.RuleType_1.getIndex());
    cnd.setPrId(paab.getPrId());
    if (!productService.checkSingleProductRule(cnd)) {
      result.addError(new FieldError("paab", "ruleName", "规则名称已存在"));
    }
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/applyadd");
      mav.addObject("fpId", paab.getFpId());
      return mav;
    }

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改融资产品申请条件", session.getUserName());

    // clone数据到MProductRuleDetail
    MProductRuleDetail apply = new MProductRuleDetail();
    BeanUtils.copyProperties(apply, paab);
    // 调用service，得到影响行数
    int lines = productService.updateProductRuleDetailById(apply);
    // 记录日志
    log.info("{}修改融资产品申请条件，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/applylist/" + paab.getFpId().intValue());
    return mav;
  }

  /**
   * 删除产品规则详细信息
   * 
   * @param request
   * @param response
   * @param prId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/applyDel", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage applyDel(HttpServletRequest request, HttpServletResponse response,
      Integer prId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除融资产品申请条件,规则ID:{}", session.getUserName(), prId);
    // 修改融资产品申请信息
    MProductRuleDetail detail = new MProductRuleDetail();
    detail.setPrId(prId);
    detail.setDelFlag(0);
    // 调用service
    int lines = productService.updateProductRuleDetailById(detail);
    // 记录日志
    log.info("{}删除融资产品规则详细信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }

  /**
   * 查询融资产品所需材料
   * 
   * @param request
   * @param fpId
   * @param currentPage
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/materiallist/{fpId}", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView materiallist(HttpServletRequest request, @PathVariable Integer fpId,
      Integer currentPage) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询融资产品所需材料列表", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("product/materiallist");
    ProductRuleDetailCnd cnd = new ProductRuleDetailCnd();
    // 申请条件
    cnd.setRuleType(RuleType.RuleType_2.getIndex());
    // 产品ID
    cnd.setFpId(fpId);
    TbData tbData = productService.selectProductRuleDetailList(cnd, currentPage);
    tbData = tbData.fillTbData("product/materiallist/" + fpId.intValue(), cnd, null);
    mav.addObject("tbData", tbData);
    mav.addObject("fpId", fpId);
    return mav;
  }

  /**
   * 跳转到添加申请条件画面
   * 
   * @param request
   * @param response
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addMaterialView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView addMaterialView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("product/materialadd");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到融资产品申请条件添加界面", session.getUserName());
    mav.addObject("fpId", id);
    // 返回
    return mav;
  }

  /**
   * 添加融资产品所需材料操作
   * 
   * @param request
   * @param response
   * @param paab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addMaterialSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView addMaterialSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("pmab") ProductMaterialAddBean pmab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    // 303验证
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/materialadd");
      mav.addObject("fpId", pmab.getFpId());
      return mav;
    }
    // 唯一性验证
    ProductRuleDetailCnd cnd = new ProductRuleDetailCnd();
    cnd.setFpId(pmab.getFpId());
    cnd.setRuleName(pmab.getRuleName());
    cnd.setRuleType(RuleType.RuleType_2.getIndex());
    if (!productService.checkSingleProductRule(cnd)) {
      result.addError(new FieldError("pmab", "ruleName", "规则名称已存在"));
    }
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/materialadd");
      mav.addObject("fpId", pmab.getFpId());
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}添加融资产品申请条件", session.getUserName());

    // clone数据到MProductRuleDetail
    MProductRuleDetail apply = new MProductRuleDetail();
    BeanUtils.copyProperties(apply, pmab);
    // 调用service，得到影响行数
    int lines = productService.applyAdd(apply);
    // 记录日志
    log.info("{}添加融资产品所需材料，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/materiallist/" + pmab.getFpId().intValue());
    return mav;
  }

  /**
   * 跳转到融资产品所需材料编辑页面
   * 
   * @param request
   * @param response
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editMaterialView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView editMaterialView(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("product/materialedit");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}编辑融资产品所需材料", session.getUserName());
    MProductRuleDetail detail = productService.selectProductRuleDetailById(id);

    if (detail == null) {
      log.info("未找到对应的融资申请条件");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到编辑融资产品所需材料界面", session.getUserName());
      ProductMaterialAddBean pmab = new ProductMaterialAddBean();
      BeanUtils.copyProperties(pmab, detail);
      mav.addObject("pmab", pmab);
    }

    // 返回
    return mav;
  }

  /**
   * 编辑融资产品所需材料
   * 
   * @param request
   * @param response
   * @param pmab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/editMaterialSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView editMaterialSave(HttpServletRequest request, HttpServletResponse response,
      @Valid @ModelAttribute("pmab") ProductMaterialAddBean pmab, BindingResult result)
          throws Exception {
    ModelAndView mav = new ModelAndView();
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/materialedit");
      return mav;
    }
    // 唯一性验证
    ProductRuleDetailCnd cnd = new ProductRuleDetailCnd();
    cnd.setFpId(pmab.getFpId());
    cnd.setRuleName(pmab.getRuleName());
    cnd.setRuleType(RuleType.RuleType_2.getIndex());
    cnd.setPrId(pmab.getPrId());
    if (!productService.checkSingleProductRule(cnd)) {
      result.addError(new FieldError("pmab", "ruleName", "规则名称已存在"));
    }
    if (result.hasErrors()) {
      mav = initView(mav, request, "product/materialedit");
      return mav;
    }
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改融资产品所需材料", session.getUserName());

    // clone数据到MProductRuleDetail
    MProductRuleDetail apply = new MProductRuleDetail();
    BeanUtils.copyProperties(apply, pmab);
    // 调用service，得到影响行数
    int lines = productService.updateProductRuleDetailById(apply);
    // 记录日志
    log.info("{}修改融资产品所需材料，影响行数{}", session.getUserName(), lines);
    // 返回
    mav.setViewName("redirect:/product/materiallist/" + pmab.getFpId().intValue());
    return mav;
  }

  /**
   * 根据foID 查询 机构覆盖城市信息
   * 
   * @param request
   * @param response
   * @param foID
   * @return 机构覆盖城市信息 不存在返回null
   * @throws Exception
   */
  @RequestMapping(value = "/getCoverCityInfoByFoID", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getCoverCityInfoByFoID(HttpServletRequest request,
      HttpServletResponse response, int foID) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询金融机构覆盖城市", session.getUserName());

    List<TFcoCoveredCity> tFcoCoveredCityInfos = financeService.queryTFcoCoverCityInfosByFoID(foID);
    HashSet<String> provSet = new HashSet<String>();
    List<String> provs = new ArrayList<String>();
    List<String> defaultCityList = new ArrayList<String>();
    // 过滤覆盖省份
    for (TFcoCoveredCity tFcoCoveredCityInfo : tFcoCoveredCityInfos) {
      if (tFcoCoveredCityInfo.getProvName().equals("中国")) {
        provSet.add(tFcoCoveredCityInfo.getCityName());
      } else {
        provSet.add(tFcoCoveredCityInfo.getProvName());
      }
    }
    for (String prov : provSet) {
      provs.add(prov);
    }
    if (provs.size() > 0) {
      defaultCityList = financeService.queryCoveredCitiesByProvName(foID, provs.get(0));
      if (defaultCityList.size() == 0) {
        defaultCityList.add(provs.get(0));
      }
    }
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("provs", provs);
    resultMap.put("defaultCityList", defaultCityList);
    // 返回
    return resultMap;
  }

  @RequestMapping(value = "/getCoverCityInfoByFoIDFpID", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getCoverCityInfoByFoIDFpID(HttpServletRequest request,
      HttpServletResponse response, int foID, int fpID) throws Exception {
    String cityNames = "";
    MFinanceProductVo mFPV = productService.selectProductById(fpID);
    String cityIDStrs = mFPV.getFpArea();

    List<MCityTree> mCityList = financeService.queryMCityInfosByCityIds(cityIDStrs);
    if (mCityList.size() > 0) {
      for (MCityTree mCityTree : mCityList) {
        cityNames += "," + mCityTree.getCityName();
      }
    }
    if (cityNames.length() > 1) {
      cityNames = cityNames.substring(1, cityNames.length());
    }

    Map<String, Object> resultMap = getCoverCityInfoByFoID(request, response, foID);
    resultMap.put("cityNames", cityNames);
    // 返回
    return resultMap;
  }

  /**
   * 根据机构覆盖省份名 查询 机构覆盖城市列表
   * 
   * @param request
   * @param response
   * @param provName
   * @return 机构覆盖城市列表 不存在返回null
   * @throws Exception
   */
  @RequestMapping(value = "/getCoverCitiesByProvName", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getCoverCitiesByProvName(HttpServletRequest request,
      HttpServletResponse response, int foID, String provName) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询金融机构覆盖城市", session.getUserName());

    List<String> cityList = financeService.queryCoveredCitiesByProvName(foID, provName);
    if (cityList.size() == 0) {
      cityList.add(provName);
    }
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("cityList", cityList);
    // 返回
    return resultMap;
  }

  /**
   * 根据机构覆盖省份名 查询 机构覆盖城市列表
   * 
   * @param request
   * @param response
   * @param provName
   * @return 机构覆盖城市列表 不存在返回null
   * @throws Exception
   */
  @RequestMapping(value = "/getCityIdsByCityNames", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getCityIdsByCityNames(HttpServletRequest request,
      HttpServletResponse response, String cityList) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询金融机构覆盖城市id", session.getUserName());
    String cityIds = "";
    String[] cityNames = cityList.split(",");
    for (String s : cityNames) {
      int id = financeService.queryCityIdByCityName(s);
      if (id > 0) {
        cityIds += "," + id;
      }
    }
    if (cityIds.length() > 1) {
      cityIds = cityIds.substring(1, cityIds.length());
    }
    // 返回
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("cityIds", cityIds);

    return map;
  }

  /**
   * 融资产品审核页面
   * 
   * @param request
   * @param response
   * @param pab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/approveView/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView approveView(HttpServletRequest request, @PathVariable Integer id)
      throws Exception {
    ModelAndView mav = new ModelAndView("product/approve");
    // 记录日志
    SessionBean session = getSessionBean(request);
    MFinanceProductVo financeProductVo = productService.selectProductById(id);
    if (financeProductVo == null) {
      log.info("未找到对应的融资产品");
      mav.setViewName("account/notfound");
    } else {
      // 查询融资产品信息
      FinanceQueryBean financeQueryBean = new FinanceQueryBean();
      financeQueryBean.setFoStatus("1");
      List<MFinanceOrg> financeList = financeService.selectFinanceList(financeQueryBean);

      // 申请条件
      ProductRuleDetailCnd cnd1 = new ProductRuleDetailCnd();
      cnd1.setRuleType(RuleType.RuleType_1.getIndex());
      cnd1.setFpId(id);
      List<MProductRuleDetail> applyList = productService.selectProductRuleDetailList(cnd1);

      // 所需材料
      ProductRuleDetailCnd cnd2 = new ProductRuleDetailCnd();
      cnd2.setRuleType(RuleType.RuleType_2.getIndex());
      cnd2.setFpId(id);
      List<MProductRuleDetail> materialList = productService.selectProductRuleDetailList(cnd2);

      // 产品banner图链接
      SysUploadFileBean file = fileService.getUploadFileById(financeProductVo.getUfId());
      if (file != null) {
        mav.addObject("bannerUrl", file.getUrl());
      }

      log.info("{}跳转到融资产品审核界面", session.getUserName());
      mav.addObject("financeProduct", financeProductVo);
      mav.addObject("financeList", financeList);
      mav.addObject("applyList", applyList);
      mav.addObject("materialList", materialList);
    }
    // 返回
    return mav;
  }

  /**
   * 融资产品审核操作
   * 
   * @param request
   * @param response
   * @param pab
   * @param result
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/approveSave", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  @UserMenu
  public ModelAndView approveSave(HttpServletRequest request,
      @Valid @ModelAttribute("pab") ProductApproveBean pab, BindingResult result) throws Exception {
    ModelAndView mav = new ModelAndView("product/approve");
    // 记录日志
    SessionBean session = getSessionBean(request);

    MFinanceProductVo financeProductVo = productService.selectProductById(pab.getFpId());
    if (financeProductVo == null) {
      log.info("未找到对应的融资产品");
      mav.setViewName("account/notfound");
      return mav;
    }

    TApvFinanceProductHistory fph = new TApvFinanceProductHistory();
    BeanUtils.copyProperties(fph, pab);
    fph.setReallyAppUserId(session.getUserId().intValue());
    fph.setAppDate(new Date());
    productService.approveProductById(fph);
    // 记录日志
    log.info("{}添加融资产品信息，fpid{}", session.getUserName(), pab.getFpId());
    // 返回
    mav.setViewName("redirect:/product/list");
    // 返回
    return mav;
  }
}
