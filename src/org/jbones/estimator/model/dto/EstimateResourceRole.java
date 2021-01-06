package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

/**
   Class that represents a EstimateRole DTO.
*/
public class EstimateResourceRole extends BaseDDTO implements Copyable {
   
   private Estimate estimate;
   private ResourceRole resourceRole;

   public Estimate getEstimate() {
      return estimate;
   }
   public void setEstimate(Estimate estimate) {
      this.estimate = estimate;
   }
   public ResourceRole getResourceRole() {
      return resourceRole;
   }
   public void setResourceRole(ResourceRole resourceRole) {
      this.resourceRole = resourceRole;
   }
   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof EstimateResourceRole) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a EstimateResourceRole");
   }
}
