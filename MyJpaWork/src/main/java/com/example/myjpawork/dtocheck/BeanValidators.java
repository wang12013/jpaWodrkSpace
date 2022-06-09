package com.example.myjpawork.dtocheck;



import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wangzy
 * @date 2022/5/13 14:54
 */
public class BeanValidators {

   //借用JSR305的validate方法，验证失败时抛出ConstraintViolationException

   public static <T> Set<ConstraintViolation<T>> validateWithException(Validator validator, T object,
                                                                       Class<?>...groups) throws ConstraintViolationException {

      Set<ConstraintViolation<T>> constraintViolations = validator.validate(object,groups);
      if (!constraintViolations.isEmpty()){
         return constraintViolations;
      }

      return null;
   }

   public static List<String> extractPropertyAndMessageAsList(ConstraintViolationException e,String separator){
      return extractPropertyAndMessageAsList(e.getConstraintViolations(), separator);
   }

   public static List<String> extractPropertyAndMessageAsList(Set<? extends ConstraintViolation> constraintViolations,
                                                              String separator){
      List<String> errorMessages =  new ArrayList<String>();
      for (ConstraintViolation violation : constraintViolations){
         errorMessages.add(violation.getPropertyPath() + separator + violation.getMessage());
      }
      return errorMessages;
   }

}
