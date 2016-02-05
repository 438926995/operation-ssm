package com.eleme.annotation.validator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.web.multipart.MultipartFile;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 图片大小校验
 * 
 * @author huwenwen
 *
 */
public class ImageSizeValidator implements ConstraintValidator<ImageSize, MultipartFile> {
  private static final Log elog = LogFactory.getLog(ImageSizeValidator.class);

  private int maxSize;
  private boolean isIgnore;
  // private ImageFormat constraintAnnotation;

  @Override
  public void initialize(ImageSize constraintAnnotation) {
    maxSize = constraintAnnotation.maxSize();
    isIgnore = constraintAnnotation.ignoreEmpty();
  }

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

    if (file == null || file.isEmpty()) {
      if (context instanceof ConstraintValidatorContextImpl) {
        try {
          Map<String, Object> attributes = new HashMap<String, Object>(
              ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAttributes());
          attributes.put("message", "请上传图片");
          ConstraintDescriptor<?> constraintDescriptor =
              ((ConstraintValidatorContextImpl) context).getConstraintDescriptor();
          Field field = constraintDescriptor.getClass().getDeclaredField("attributes");
          field.setAccessible(true);
          field.set(constraintDescriptor, attributes);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return isIgnore;
    }
    if (file.getSize() > maxSize * 1024 * 1024) {
      return false;
    }
    return true;
  }

}
