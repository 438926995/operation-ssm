package com.eleme.util.mail;

/**
 * 全局常量类.
 * 
 * @author penglau
 *
 */
public class GlobalConstants extends ConfigurableConstants {

  /**
   * 加载属性文件.
   */
  static {
    init("variable.properties");
    init("mail.properties");
  }

  // 开发模式,development 开发,test 测试,production 生产,bak 备机 四种取值.
  public static final String DEVMODEL = getProperty("devModel");

  public static final String DEVMODEL_DEVELOP = "development";
  public static final String DEVMODEL_ALPHA = "alpha";
  public static final String DEVMODEL_PRODUCTION = "production";
  public static final String DEVMODEL_BETA = "beta";
  // 项目路径
  public static final String PROJECT_URL = getProperty("project_url");
  public static final String ATTACHMENT_PATH = getProperty("attachment_path");

  // 开发、测试阶段，邮件接收人
  public static final String TEST_SENDTO = getProperty("test_sendTo");
  public static final String emailHost = getProperty("smtp.host");
  public static final String emailFrom = getProperty("mailFrom");
  public static final String emailSmtpPort = getProperty("mailSMTPPort");
  public static final String emailUserName = getProperty("smtp.username");
  public static final String emailPassword = getProperty("smtp.password");

  public static final String mailSellerLoanExcelExportPath =
      getProperty("mail.sellerLoan.excel.exportPath");

}
