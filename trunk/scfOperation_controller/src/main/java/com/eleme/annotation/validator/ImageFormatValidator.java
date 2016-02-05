package com.eleme.annotation.validator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.web.multipart.MultipartFile;

public class ImageFormatValidator implements ConstraintValidator<ImageFormat, MultipartFile>{

  private String format;
  private boolean isIgnore;
  
  @Override
  public void initialize(ImageFormat constraintAnnotation) {
   format = constraintAnnotation.format();
   isIgnore = constraintAnnotation.ignoreEmpty();
  
  }

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if(file == null || file.isEmpty()){
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
    
    String[] split = format.split("/");
    //得到file后缀
    int dot = file.getOriginalFilename().lastIndexOf(".");
    String extensionName = file.getOriginalFilename().substring(dot + 1);
    for(int i = 0; i < split.length; i++){
      if(split[i].trim().equalsIgnoreCase(extensionName)){
        return true;
      }
    }
    return false;
  }

}
