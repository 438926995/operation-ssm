package com.eleme.util.ftl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.eleme.ftl.FtlFilePosition;
import com.eleme.ftl.ajax.AjaxFtlPosition;
import com.eleme.ftl.email.EmailFtlPosition;
import com.eleme.ftl.other.OtherFtlPosition;
import com.eleme.util.StringUtil;
import com.eleme.util.mail.GlobalConstants;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.Version;

/**
 * Spring 与Freemarker结合工具类.
 * 
 * @author penglau
 *
 */
public class FreemarkerUtils {

  private static Configuration config;

  static {
    if (config == null) {
      config = new Configuration(new Version("2.3.23"));
    }
  }

  /**
   * 根据模板文件根文件夹路径和模板在文件夹下路径得到模板对象
   * 
   * @param basePath 模板文件根文件夹路径
   * @param filePath 模板在文件夹下路径
   * @return freemarker模版
   * @throws IOException
   */
  private synchronized static Template getTemplate(String basePath, String fileName)
      throws Exception {
    if (StringUtil.isEmpty(basePath, fileName)) {
      return null;
    }
    // 获得文件夹
    config.setDirectoryForTemplateLoading(new File(basePath));
    config.setObjectWrapper(new DefaultObjectWrapper(new Version("2.3.23")));
    Template temp = config.getTemplate(fileName, "UTF-8");
    return temp;
  }

  /**
   * 得到模板内容.
   * 
   * @param fileName 文件名称.
   * @param root 值.
   * @return
   * @throws Exception
   */
  public synchronized static String getText(String fileName, Object root) throws Exception {
    return getText(FtlFilePosition.PATH, fileName, root);
  }

  /**
   * 使用spring获得模板内容字符串.
   * 
   * @param basePath 根路径.
   * @param fileName ftl文件名称.
   * @param root 参数存放对象
   * @return
   * @throws Exception
   */
  public synchronized static String getText(String basePath, String fileName, Object root)
      throws Exception {
    if (StringUtil.isEmpty(basePath, fileName)) {
      return "";
    }
    Template temp = getTemplate(basePath, fileName);
    if (temp == null) {
      return "";
    }
    return FreeMarkerTemplateUtils.processTemplateIntoString(temp, root);
  }

  /**
   * 使用spring获得模板内容字符串.
   * 
   * @param basePath 根路径.
   * @param fileName ftl文件名称.
   * @param root 参数存放对象
   * @return
   * @throws Exception
   */
  private synchronized static String getText(String basePath, String fileName,
      Map<String, Object> root) throws Exception {
    if (StringUtil.isEmpty(basePath, fileName)) {
      return "";
    }
    Template temp = getTemplate(basePath, fileName);
    if (temp == null) {
      return "";
    }
    root.put("basePath", GlobalConstants.PROJECT_URL);
    return FreeMarkerTemplateUtils.processTemplateIntoString(temp, root);
  }

  /**
   * 得到模板内容.
   * 
   * @param fileName 文件名称.
   * @param root 值.
   * @return
   * @throws Exception
   */
  public synchronized static String getAJAXTemplate(String packageName, String fileName,
      Map<String, Object> root) throws Exception {
    return getText(AjaxFtlPosition.PATH + packageName + "/", fileName, root);
  }

  /**
   * 得到（其他）模板内容.
   * 
   * @param fileName 文件名称.
   * @param root 值.
   * @return
   * @throws Exception
   */
  public synchronized static String getOtherTemplate(String fileName, Map<String, Object> root)
      throws Exception {
    return getText(OtherFtlPosition.PATH, fileName, root);
  }

  /**
   * 获取邮件模板
   * 
   * @param fileName
   * @param root
   * @return
   * @throws Exception
   */
  public synchronized static String getEmailTPL(String fileName, Object root) throws Exception {
    return getText(EmailFtlPosition.PATH, fileName, root);
  }


  /**
   * 创建数据模板到控制台
   * 
   * @param temp
   * @param obj
   * @throws Exception
   */
  public synchronized static void createDataModel(Template temp, Object obj) throws Exception {
    /* 将模板和数据模型合并 */
    Writer out = new OutputStreamWriter(System.out);
    temp.process(obj, out);
    out.flush();
  }

  /**
   * 设置日期格式
   * 
   * @param dateFormat
   * @throws Exception
   */
  public synchronized static void setDateFormat(String dateFormat) throws Exception {
    config.setDateFormat(dateFormat);
  }

  /**
   * 设置数字格式
   * 
   * @param dateFormat
   * @throws Exception
   */
  public synchronized static void setNumberFormat(String numberFormat) throws Exception {
    config.setNumberFormat(numberFormat);
  }

}
