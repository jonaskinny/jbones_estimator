package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

/**
   Class that represents a Project DTO.
*/
public class Project extends BaseADTO implements Copyable {
    
   private String description;
   private ProjectType projectType;

   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
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
      if (o instanceof Project) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a Project");
   }
}
