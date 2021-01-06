package org.jbones.estimator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jbones.estimator.controller.stripes.action.ProjectActionBean;

import org.jbones.core.util.ClassName;
import org.jbones.core.*;
/**
	Main class of the jbones datastore package.
*/
public class Main {
   public static void main(String[] args) {
     System.out.println("running main in org.jbones.estimator ...");
     try {
      Collection services = ServiceLocator.getServices();
      Iterator i = services.iterator();
      while (i.hasNext()) {
         System.out.println("core service:" + ClassName.name(i.next().getClass()));
      }
      ProjectActionBean projectActionBean = new ProjectActionBean();
      // trying to break conn pool
      while (true) {
      List list = projectActionBean.getProjects();
      }
     } catch (Exception e) {
         System.err.println(e.getMessage());
     }
  }
}
