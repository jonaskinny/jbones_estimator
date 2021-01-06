package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

/**
   Class that represents a Client DTO.
*/
public class Client extends BaseADTO implements Copyable {
    
   private String name;
   // name is separate from the identifier ...
   // Clients can have the same name and different identifiers like 
   // locations, named satelite offices etc.
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
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
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a Client");
   }
}
