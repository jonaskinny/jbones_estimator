package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

/**
   Class that represents a ResourceRole DTO.
*/
public class ResourceRole extends BaseADTO implements Copyable {
   
   private Resource resource;
   private Role role;

   public Resource getResource() {
      return resource;
   }
   public void setResource(Resource resource) {
      this.resource = resource;
   }
   public Role getRole() {
      return role;
   }
   public void setRole(Role role) {
      this.role = role;
   }
   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof Project) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a ResourceRole");
   }
}
