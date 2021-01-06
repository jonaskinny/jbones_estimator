package org.jbones.estimator.controller.stripes.action;

import org.stripesstuff.plugin.session.Session;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;

import net.sourceforge.stripes.controller.FlashScope;

import org.jbones.estimator.model.dto.Effort;
import org.jbones.estimator.model.dto.Estimate;
import org.jbones.estimator.model.dto.EstimateEffort;
import org.jbones.stripes.controller.action.*;
import org.jbones.core.*;
import org.jbones.core.dao.*;

import java.util.ArrayList;
import java.util.List;
@UrlBinding("/EstimateEffort.action/{$event}/{estimateEffort.id}/{estimateEffort.estimate.id}/{estimateEffort.effort.id}/{estimateEffort.identifier}")
public class EstimateEffortActionBean extends BaseActionBean implements ActionBean {

   private EstimateEffort estimateEffort;
   private List<EstimateEffort> estimateEffortList;
   private List<Effort> effortList;

   private static final String LIST = "/view/estimateeffortlist";
   private static final String VIEW = "/view/estimateeffortview";
   private static final String EDIT = "/view/estimateeffortedit";

   protected String getDAOName() {
      return "EstimateEffort";
   }
   public EstimateEffort getEstimateEffort() {
      return estimateEffort;
   }
   public void setEstimateEffort(EstimateEffort estimateEffort) {
      this.estimateEffort = estimateEffort;
   }
   public List<EstimateEffort> getEstimateEffortList() {
      return estimateEffortList;
   }
   public void setEstimateEffortList(List<EstimateEffort> estimateEffortList) {
      this.estimateEffortList = estimateEffortList;
   }
   public List<Effort> getEffortList() {
      return effortList;
   }
   public void setEffortList(List<Effort> effortList) {
      this.effortList = effortList;
   }
   protected BridgeDAO getDAO() {
      return (BridgeDAO)super.getDAO();
   }
   @DefaultHandler
   @HandlesEvent("list")
   public Resolution list() throws ListException {
      setMessagesOnActionBean("estimateEffortMessages");
      setEstimateEffortList(getEstimateEfforts());
      setEstimateEffort(null);
      return new ForwardResolution(LIST);
   }
   @HandlesEvent("createEstimateEffort")
   public Resolution createEstimateEffort() {
      boolean created = false;
      try {
         created = getDAO().create(estimateEffort);
         //return new RedirectResolution(EstimateActionBean.class, "viewEstimateDetails").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created.  {1} must be unique", getMessageParams(estimateEffort)));
            //return new RedirectResolution(EstimateActionBean.class, "viewEstimateDetails").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      } catch (CreateException ce) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created", getMessageParams(estimateEffort)));
            //return new RedirectResolution(EstimateActionBean.class, "viewEstimateDetails").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      } finally {
         if (created) {
            getContext().getMessages().add(new SimpleMessage("{0} Created", getMessageParams(estimateEffort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateEffortMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateEffortMessages");
         return new ForwardResolution(EstimateActionBean.class, "viewEstimateDetail").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      }
   }
   @HandlesEvent("updateEstimateEffort")
   public Resolution updateEstimateEffort() {
      boolean updated = false;
      try {
         updated = getDAO().update(estimateEffort);
         return new RedirectResolution(EstimateEffortActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated.  {1} must be unique", getMessageParams(estimateEffort)));
            return new RedirectResolution(EstimateEffortActionBean.class);
      } catch (UpdateException updateE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated", getMessageParams(estimateEffort)));
            return new RedirectResolution(EstimateEffortActionBean.class);
      } finally {
         if (updated) {
            getContext().getMessages().add(new SimpleMessage("{0} Updated", getMessageParams(estimateEffort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateEffortMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateEffortMessages");
      }
   }
   @HandlesEvent("editEstimateEffort")
   public Resolution editEstimateEffort() {
      try {
         setEstimateEffort((EstimateEffort)getDAO().read(estimateEffort.getId()));
         return new ForwardResolution(EDIT);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(estimateEffort)));
            return new ForwardResolution(EDIT);
      } finally {
         if (null != estimateEffort) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(estimateEffort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateEffortMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateEffortMessages");
      }
   }
   @HandlesEvent("viewEstimateEffort")
   public Resolution viewEstimateEffort() {
      try {
         setEstimateEffort((EstimateEffort)getDAO().read(estimateEffort.getId()));
         return new ForwardResolution(VIEW);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(estimateEffort)));
            return new ForwardResolution(VIEW);
      } finally {
         if (null != estimateEffort) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(estimateEffort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateEffortMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateEffortMessages");
      }
   }
   @HandlesEvent("deleteEstimateEffort")
   public Resolution deleteEstimateEffort() {
      boolean deleted = false;
      try {
         deleted = getDAO().delete(estimateEffort.getId());
         //return new ForwardResolution(EstimateActionBean.class, "viewEstimateDetail").addParameter("estimate.id", estimateEffort.getEstimate().getId());
         return new RedirectResolution(EstimateActionBean.class, "viewEstimateDetail").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      } catch (DeleteException deleteE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Deleted", getMessageParams(estimateEffort)));
            //return new ForwardResolution(EstimateActionBean.class, "viewEstimateDetail").addParameter("estimate.id", estimateEffort.getEstimate().getId());
            return new RedirectResolution(EstimateActionBean.class, "viewEstimateDetail").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      } finally {
         if (deleted) {
            getContext().getMessages().add(new SimpleMessage("{0} Deleted", getMessageParams(estimateEffort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateEffortMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateEffortMessages");
      }
   }
   @HandlesEvent("listEstimateEffortsByEstimate")
   public Resolution listEstimateEffortsByEstimate() {
      try {
         setEstimateEffortList(getEstimateEffortsByEstimate());
         //return new ForwardResolution(EstimateActionBean.class, "viewEstimate").addParameter("estimate.id", estimateEffort.getEstimate().getId());
         //return new RedirectResolution(EstimateActionBean.class, "viewEstimate").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      } finally {
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateEffort", this);
         return new ForwardResolution(EstimateActionBean.class, "viewEstimate").addParameter("estimate.id", estimateEffort.getEstimate().getId());
      }
   }
   public List getEstimateEfforts() {
      try {
         return getDAO().list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", getDAOName()));
         return null;
      }
   }
   public List getEstimateEffortsByEstimate() {
      try {
         return getDAO().listChildren(estimateEffort.getEstimate().getId());
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} findChildren failed", getDAOName()));
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateEffortMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateEffortMessages");
         return null;
      }
   }
   public List getEstimateEffortsByEffort() {
      try {
         return getDAO().listParents(estimateEffort.getEffort().getId());
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} findParents failed", getDAOName()));
         return null;
      }
   }
}