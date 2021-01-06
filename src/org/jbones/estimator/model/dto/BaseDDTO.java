package org.jbones.estimator.model.dto;

import org.jbones.datastore.DDTO;
import org.jbones.core.*;
import org.jbones.core.dao.*;
import org.jbones.core.util.*;

/**
   Class that represents a Base DDTO.
*/
public class BaseDDTO extends DDTO implements Copyable, Deletable {
    
   static {
	  uniqueFieldList.add("IDENTIFIER");               	
   }
   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof BaseDDTO) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a BaseDDTO");
   }
}
