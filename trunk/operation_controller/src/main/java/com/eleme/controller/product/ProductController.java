package com.eleme.controller.product;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eleme.bean.JSONMessage;
import com.eleme.bean.product.ProductRuleDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.product.MFinanceProduct;
import com.eleme.bean.product.ProductQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.service.product.IProductService;
import com.eleme.util.pager.TbData;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {

    @Inject
    private IProductService productService;

    /**
     * 产品列表
     *
     * @param request
     * @param response
     * @param currentPage
     * @param pqb
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @UserMenu
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
                             Integer currentPage, ProductQueryBean pqb) throws Exception {
        ModelAndView mav = new ModelAndView("product/list");
        TbData tbData = productService.getProductList(currentPage, pqb);
        tbData = tbData.fillTbData("product/list", pqb, null);
        mav.addObject("tbData", tbData);
        mav.addObject("pqb", pqb);
        return mav;
    }

    /**
     * 查看产品信息
     *
     * @param request
     * @param response
     * @param fpId
     * @return
     */
    @RequestMapping(value = "/ruleList/{fpId}", method = RequestMethod.GET)
    @UserMenu
    public ModelAndView getProductInfo(HttpServletRequest request, HttpServletResponse response,
                                         @PathVariable("fpId") Integer fpId) {
        ModelAndView mav = new ModelAndView("product/ruleList");
        mav.addObject("product", productService.getProductInfoByFpId(fpId));
        return mav;
    }

    /**
     * 删除产品
     *
     * @param request
     * @param response
     * @param fpId
     * @return
     */
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public JSONMessage deleteProduct(HttpServletRequest request, HttpServletResponse response,
                                     Integer fpId) {
        int line = productService.deleteProductByFpId(fpId);
        if (line > 0) {
            return new JSONMessage(true, "删除成功");
        }
        return new JSONMessage(false, "删除失败");
    }

    /**
     * 跳转产品编辑页面
     *
     * @param request
     * @param response
     * @param fpId
     * @return
     */
    @RequestMapping(value = "/edit/{fpId}", method = RequestMethod.GET)
    @UserMenu
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("fpId") Integer fpId) {
        ModelAndView mav = new ModelAndView("product/edit");
        MFinanceProduct product = productService.getProductInfoByFpId(fpId);
        mav.addObject("pab", product);
        return mav;
    }

    /**
     * 编辑保存
     *
     * @param request
     * @param response
     * @param mfp
     * @return
     */
    @RequestMapping(value = "/editSave", method = RequestMethod.POST)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response,
                                 MFinanceProduct mfp) {
        ModelAndView mav = new ModelAndView("redirect:/product/list");
        int line = productService.editProduct(mfp);
        if (line == 0) {
            mav.setViewName("product/edit");
        }
        return mav;
    }

    /**
     * 跳转到产品添加页面
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "/addView", method = RequestMethod.GET)
    @UserMenu
    public ModelAndView addView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("product/add");
        return mav;
    }

    /**
     * 产品添加保存
     *
     * @param request
     * @param response
     * @param mfp
     * @return
     */
    @RequestMapping(value = "addSave", method = RequestMethod.POST)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response, MFinanceProduct mfp) {
        ModelAndView mav = new ModelAndView("redirect:/product/list");
        productService.addProduct(mfp);
        return mav;
    }

    /**
     * 产品规则添加
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "ruleAddView/{fpId}", method = RequestMethod.GET)
    @UserMenu
    public ModelAndView ruleAddView(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("fpId") Integer fpId){
        ModelAndView mav = new ModelAndView("product/ruleAdd");
        mav.addObject("fpId", fpId);
        return mav;
    }

    /**
     * 产品添加保存
     * @param request
     * @param response
     * @param prd
     * @return
     */
    @RequestMapping(value = "ruleAddSave", method = RequestMethod.POST)
    public ModelAndView ruleAddSave(HttpServletRequest request, HttpServletResponse response, ProductRuleDetail prd){
        ModelAndView mav = new ModelAndView("redirect:/product/ruleList/" + prd.getFpId());
        productService.addProductRule(prd);
        return mav;
    }

    /**
     * 产品规则编辑
     * @param request
     * @param response
     * @param prId
     * @return
     */
    @RequestMapping(value = "ruleEditView/{prId}", method = RequestMethod.GET)
    @UserMenu
    public ModelAndView ruleEditView(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable("prId") Integer prId){
        ModelAndView mav = new ModelAndView("product/ruleEdit");
        mav.addObject("productRule", productService.getProductRuleDetailById(prId));
        return mav;
    }

    /**
     * 产品规则编辑保存
     * @param request
     * @param response
     * @param prd
     * @return
     */
    @RequestMapping(value = "ruleEditSave", method = RequestMethod.POST)
    public ModelAndView ruleEditSave(HttpServletRequest request, HttpServletResponse response,
                                     ProductRuleDetail prd){
        Integer fpId = productService.getProductRuleDetailById(prd.getPrId()).getFpId();
        ModelAndView mav = new ModelAndView("redirect:/product/ruleList/" + fpId);
        productService.updateProductRule(prd);
        return mav;
    }

    /**
     * 删除产品规则
     * @param request
     * @param response
     * @param prId
     * @return
     */
    @RequestMapping(value = "delRule", method = RequestMethod.POST)
    @ResponseBody
    public JSONMessage delRule(HttpServletRequest request, HttpServletResponse response,
                                Integer prId){
        int line = productService.deleteProductRuleById(prId);
        if(line > 0){
            return new JSONMessage(true, "删除成功");
        }
        return new JSONMessage(false, "删除失败");
    }

}
