package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

import java.util.List;

/**
   Class that represents a EstimateEffort DTO.
*/
public class EstimateEffort extends BaseDDTO implements Copyable {
    
   private Effort effort;
   private Estimate estimate;

   public Effort getEffort() {
      return effort;
   }
   public void setEffort(Effort effort) {
      this.effort = effort;
   }
   public Estimate getEstimate() {
      return estimate;
   }
   public void setEstimate(Estimate estimate) {
      this.estimate = estimate;
   }
   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof EstimateEffort) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a EstimateEffort");
   }
}
