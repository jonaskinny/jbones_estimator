package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

import java.util.List;

/**
   Class that represents a Effort DTO.
*/
public class Effort extends BaseADTO implements Copyable {
    
   private UnitType unitType;
   private Complexity complexity;
   private Phase phase;
   private int effortHours;

   public UnitType getUnitType() {
      return unitType;
   }
   public void setUnitType(UnitType unitType) {
      this.unitType = unitType;
   }
   public Complexity getComplexity() {
      return complexity;
   }
   public void setComplexity(Complexity complexity) {
      this.complexity = complexity;
   }
   public Phase getPhase() {
      return phase;
   }
   public void setPhase(Phase phase) {
      this.phase = phase;
   }
   public int getEffortHours() {
      return effortHours;
   }
   public void setEffortHours(int effortHours) {
      this.effortHours = effortHours;
   }

   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof Effort) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a Effort");
   }
}
