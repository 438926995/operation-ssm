package com.eleme.util.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.eleme.util.CommonUtil;
import com.eleme.util.FileUtil;
import com.eleme.util.HttpClientUtils;


public class TagSoupUtils {
  /**
   * log
   */
  private static Logger logger = LoggerFactory.getLogger(TagSoupUtils.class);

  /**
   * 获取xml文件连接路径
   * 
   * @param url 连接地址
   * @param encoding 字符编码
   * @return xml内容
   */
  public static String getTagSoupContentByUrl(String url, String encoding) {
    String xml = null;
    try {
      String source = FileUtil.readFileByChars(url, encoding);
      if (source == null) {
        source = "";
      }
      xml = parse(source, encoding);
    } catch (Exception e) {
      logger.error(CommonUtil.getErrorMessage(e));
    }
    return xml;
  }

  public static String getTagSoupContentByHTTPUrl(final String url, final String encoding)
      throws Exception {
    String source = HttpClientUtils.getContentByUrl(url, encoding);
    if (source == null) {
      source = "";
    }
    String xml = null;
    try {
      xml = parse(source, encoding);
    } catch (Exception e) {
      logger.error(CommonUtil.getErrorMessage(e));
      throw e;
    }
    return xml;
  }

  /**
   * parse html
   * 
   * @param src 内容
   * @param encoding 编码
   * @return 转换后的内容
   * @throws IOException
   * @throws SAXException
   */
  public static String parse(String src, String encoding) throws IOException, SAXException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    XMLWriter x = new XMLWriter(new OutputStreamWriter(os, encoding));
    x.setOutputProperty(XMLWriter.ENCODING, "UTF-8");

    InputSource s = new InputSource();
    s.setCharacterStream(new StringReader(src));
    s.setEncoding(encoding);

    XMLReader r = new Parser();
    r.setProperty(Parser.schemaProperty, new HTMLSchema());
    r.setFeature(Parser.ignorableWhitespaceFeature, true);
    r.setContentHandler(x);
    r.parse(s);
    return new String(os.toByteArray(), encoding);
  }
}
