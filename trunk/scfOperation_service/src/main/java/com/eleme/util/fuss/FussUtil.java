package com.eleme.util.fuss;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.inject.Inject;

import org.springframework.web.multipart.MultipartFile;

import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.soa.fuss.FussException;
import com.eleme.soa.fuss.FussFile;
import com.eleme.soa.fuss.FussService;
import com.eleme.util.FileUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * Fuss 相关的方法
 * 
 * @author huwenwen
 *
 */
public class FussUtil {
  /**
   * log
   */
  private static Log logger = LogFactory.getLog(FileUtil.class);

  @Inject
  private FussService fussService;

  /**
   * 得到fuss上传之后返回的 hash字符串
   * 
   * @param mfile
   * @return
   * @throws IOException
   * @throws FussException
   */
  public String getFussHashStringByMultipartFile(MultipartFile mfile)
      throws IOException, FussException {
    // 转换成ByteBuffer
    ByteBuffer content = ByteBuffer.wrap(mfile.getBytes());
    // fuss文件
    FussFile fuss_file = new FussFile();
    fuss_file.setContent(content);
    // 得到文件的扩展名
    int dot = mfile.getOriginalFilename().lastIndexOf(".");
    String extensionName = mfile.getOriginalFilename().substring(dot + 1);
    fuss_file.setExtension(extensionName);
    // 得到上传返回的 hash字符串
    String hashString = fussService.file_upload(fuss_file);
    return hashString;
  }
  /**
   * 
   * @param mfile
   * @param id 分类的主键
   * @param fileType 属于哪个分类
   * IsOps 为1 是后台人员
   * @return
   * @throws Exception
   */
  public SysUploadFileBean getSysUploadFileBean(MultipartFile mfile, Integer id, String fileType) throws Exception{
    SysUploadFileBean sfb = new SysUploadFileBean();
    if(mfile == null){
      return sfb;
    }
    String fileEditName = getFussHashStringByMultipartFile(mfile);
    // 得到文件的保存路径
    String url = fussService.file_get(fileEditName);
    // 后缀
    int dot = mfile.getOriginalFilename().lastIndexOf(".");
    String postfix = mfile.getOriginalFilename().substring(dot + 1);
    sfb.setProjectName("scf");
    sfb.setContextPath("logo");
    sfb.setSourceName(mfile.getOriginalFilename());
    sfb.setFileEditName(fileEditName);
    sfb.setMimeType(mfile.getContentType());
    sfb.setPostFix(postfix);
    sfb.setFileSize(mfile.getSize());
    sfb.setUrl(url);
    sfb.setContainId(id);
    sfb.setContainType(fileType);
    sfb.setIsOps(1);
    return sfb;
  }
}
