package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

import java.util.List;

/**
   Class that represents a ProjectTypeUnitType DTO.
*/
public class UnitTypeProjectType extends BaseDDTO implements Copyable {
    
   private UnitType unitType;
   private ProjectType projectType;
   
   public UnitType getUnitType() {
      return unitType;
   }
   public void setUnitType(UnitType unitType) {
      this.unitType = unitType;
   }
   public ProjectType getProjectType() {
      return projectType;
   }
   public void setProjectType(ProjectType projectType) {
      this.projectType = projectType;
   }
   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof UnitTypeProjectType) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a UnitTypeProjectType");
   }
}
