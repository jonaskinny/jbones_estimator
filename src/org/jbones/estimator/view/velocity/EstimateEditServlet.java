package org.jbones.estimator.view.velocity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.StringWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.app.Velocity;

import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.IteratorTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.view.servlet.VelocityViewServlet;

import org.jbones.estimator.controller.stripes.action.EstimateActionBean;

import org.jbones.stripes.controller.action.MessageConverter;

import org.jbones.core.*;
import org.jbones.core.util.*;

public class EstimateEditServlet extends VelocityViewServlet {
   
   public Template handleRequest(HttpServletRequest request,HttpServletResponse response, Context context) {
      try {
         context.put("contextRoot", request.getContextPath());
         EstimateActionBean estimateActionBean = (EstimateActionBean)(request.getAttribute("actionBean"));
         // jcole fix npe here where user manually enters update url but no EstimateActionBean is in request yet.
         context.put("estimate", estimateActionBean.getEstimate());
         context.put("clientList", estimateActionBean.getClientList());
         context.put("projectList", estimateActionBean.getProjectList());
         context.put("resourceList", estimateActionBean.getResourceList());
         context.put("phaseList", estimateActionBean.getPhaseList());
         context.put("factorList", estimateActionBean.getFactorList());
         context.put("effortList", estimateActionBean.getEffortList());
         context.put("estimateMessageList", MessageConverter.convert(estimateActionBean.getContext().getMessages()));

         Template t = null;
         
         t = getTemplate("EstimateEdit.vm");

         return t;

      } catch(Exception e) {
            //Log.getLog(Log.ERR).log("problem in main : " + e.getMessage());
            throw new RuntimeException(CoreException.getStackTrace(e));
      }
   }

}