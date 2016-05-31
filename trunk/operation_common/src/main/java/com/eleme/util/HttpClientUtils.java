package com.eleme.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * HttpClient工具类
 * 
 * @author huwenwen
 *
 */
public class HttpClientUtils {

  private static Log log = LogFactory.getLog(HttpClientUtils.class);

  private static HttpClientUtils instance = null;

  /**
   * 获取实例
   * 
   * @return
   */
  public static HttpClientUtils getInstance() {
    if (instance == null) {
      instance = new HttpClientUtils();
    }
    return instance;
  }

  private static final String CHARSET_UTF8 = "UTF-8";

  // 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
  private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
    // 自定义的恢复策略
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
      // 设置恢复策略，在发生异常时候将自动重试3次
      if (executionCount >= 3) {
        // Do not retry if over max retry count
        return false;
      }
      if (exception instanceof NoHttpResponseException) {
        // Retry if the server dropped connection on us
        return true;
      }
      if (exception instanceof SSLHandshakeException) {
        // Do not retry on SSL handshake exception
        return false;
      }
      HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
      boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
      if (!idempotent) {
        // Retry if the request is considered idempotent
        return true;
      }
      return false;
    }
  };

  /**
   * 获取DefaultHttpClient实例
   * 
   * @param charset 参数编码集, 可空
   * @return DefaultHttpClient 对象
   */
  private static DefaultHttpClient getDefaultHttpClient(final String charset) {
    DefaultHttpClient httpclient = new DefaultHttpClient();
    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
    // 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
    httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
    httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
    httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
        charset == null ? CHARSET_UTF8 : charset);
    httpclient.setHttpRequestRetryHandler(requestRetryHandler);
    httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
    httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
    return httpclient;
  }

  /**
   * 功能：检测当前URL是否可连接或是否有效, 描述：最多连接网络 3 次, 如果 3 次都不成功，视为该地址不可用
   * 
   * @param urlStr 指定URL网络地址
   * @return URL
   */
  public static synchronized boolean isConnect(String urlStr) {
    if (urlStr == null || urlStr.length() <= 0) {
      return false;
    }
    int counts = 0;
    while (counts < 3) {
      try {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        int state = con.getResponseCode();
        if (state == 200) {
          log.info(urlStr + "--可用");
          return true;
        }
        break;
      } catch (Exception ex) {
        counts++;
        log.error("连接第 " + counts + " 次，" + urlStr + "--不可用");
        log.error(CommonUtil.getErrorMessage(ex));
        continue;
      }
    }
    return false;
  }

  /**
   * 获取URL内容 cookie暂不考虑
   * 
   * @return
   */
  @SuppressWarnings("finally")
  public static String getContentByUrl(String url, String encoding) {
    HttpClient httpclient = getDefaultHttpClient(encoding);

    if (url == null || !url.startsWith("http://"))
      url = "http://" + url;
    url = url.replaceAll(" ", "%20");// 某些url里含有空格
    String result = "";
    try {
      HttpGet httpget = new HttpGet(url);
      httpget.setHeader("User-Agent",
          "   Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
      HttpResponse response = httpclient.execute(httpget);
      int statusCode = response.getStatusLine().getStatusCode();
      if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
          || (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
          || (statusCode == HttpStatus.SC_SEE_OTHER)
          || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
        // 此处重定向处理 此处还未验证
        String newUri = response.getLastHeader("Location").getValue();
        httpclient = new DefaultHttpClient();
        httpget = new HttpGet(newUri);
        response = httpclient.execute(httpget);
      }
      HttpEntity entity = response.getEntity();
      byte[] bytes = EntityUtils.toByteArray(entity);
      result = new String(bytes, encoding);
    } catch (Exception e) {
      log.error(CommonUtil.getErrorMessage(e));
    } finally {
      httpclient.getConnectionManager().shutdown();
    }
    return result;
  }

}
