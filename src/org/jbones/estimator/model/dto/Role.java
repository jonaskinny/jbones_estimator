package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

/**
   Class that represents a Role DTO.
*/
public class Role extends BaseADTO implements Copyable {
    
   private String description;
   
   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }
   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof Client) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a Role");
   }
}
