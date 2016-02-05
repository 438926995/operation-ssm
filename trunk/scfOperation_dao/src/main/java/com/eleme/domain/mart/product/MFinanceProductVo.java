package com.eleme.domain.mart.product;

import java.io.Serializable;

import com.eleme.domain.mart.sys.Journals;

/**
 * 放贷金融机构发布的产品封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MFinanceProductVo extends MFinanceProduct implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1397035651204641215L;

  // 机构名称
  private String foName;
  // 操作人
  private Integer operationUserId;

  public String getFoName() {
    return foName;
  }

  public void setFoName(String foName) {
    this.foName = foName;
  }

  public Integer getOperationUserId() {
    return operationUserId;
  }

  public void setOperationUserId(Integer operationUserId) {
    this.operationUserId = operationUserId;
  }

  public Journals bulidJournals(MFinanceProductVo oldRecord) {
    Journals journals = new Journals();
    journals.setJournalizedId(String.valueOf(getFpId()));
    journals.setJournalizedType("M_FINANCE_PRODUCT");
    journals.setIsOps(1);

    journals.addAttrJournalsDetail("FP_NAME", getFpName(), oldRecord.getFpName())
        .addAttrJournalsDetail("FP_NAME", getFpName(), oldRecord.getFpName())
        .addAttrJournalsDetail("FP_AREA", getFpArea(), oldRecord.getFpArea())
        .addAttrJournalsDetail("IS_MORTGAGE", getIsMortgage(), oldRecord.getIsMortgage())
        .addAttrJournalsDetail("MORTGAGE_CONTENT", getMortgageContent(), oldRecord.getMortgageContent())
        .addAttrJournalsDetail("IS_WARRANT", getIsWarrant(), oldRecord.getIsWarrant())
        .addAttrJournalsDetail("WARRANT_CONTENT", getWarrantContent(), oldRecord.getWarrantContent())
        .addAttrJournalsDetail("RECEIVING_DATE", getReceivingDate(), oldRecord.getReceivingDate())
        .addAttrJournalsDetail("MIN_LOAN_AMOUNT", getMinLoanAmount(), oldRecord.getMinLoanAmount())
        .addAttrJournalsDetail("MAX_LOAN_AMOUNT", getMaxLoanAmount(), oldRecord.getMaxLoanAmount())
        .addAttrJournalsDetail("PAY_LIMIT", getPayLimit(), oldRecord.getPayLimit())
        .addAttrJournalsDetail("RAIT_UNIT", getRaitUnit(), oldRecord.getRaitUnit())
        .addAttrJournalsDetail("MIN_RAIT_RATIO", getMinRaitRatio(), oldRecord.getMinRaitRatio())
        .addAttrJournalsDetail("MAX_RAIT_RATIO", getMaxRaitRatio(), oldRecord.getMaxRaitRatio())
        .addAttrJournalsDetail("PRODUCT_STATUS", getProductStatus(), oldRecord.getProductStatus())
        .addAttrJournalsDetail("FO_ID", getFoId(), oldRecord.getFoId())
        .addAttrJournalsDetail("START_DATE", getStartDate(), oldRecord.getStartDate())
        .addAttrJournalsDetail("END_DATE", getEndDate(), oldRecord.getEndDate())
        .addAttrJournalsDetail("PROFIT_TYPE", getProfitType(), oldRecord.getProfitType())
        .addAttrJournalsDetail("OTHER_RAIT", getOtherRait(), oldRecord.getOtherRait())
        .addAttrJournalsDetail("CREDENTIALS_REQUIRE", getCredentialsRequire(), oldRecord.getCredentialsRequire())
        .addAttrJournalsDetail("OTHER_DESC", getOtherDesc(), oldRecord.getOtherDesc());

    return journals;
  }
}