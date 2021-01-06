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

import org.jbones.estimator.model.dto.Estimate;
import org.jbones.estimator.model.dto.Client;
import org.jbones.estimator.model.dto.Factor;
import org.jbones.estimator.model.dto.Phase;
import org.jbones.estimator.model.dto.Project;
import org.jbones.estimator.model.dto.Resource;
import org.jbones.estimator.model.dto.Effort;
import org.jbones.stripes.controller.action.*;
import org.jbones.core.*;
import org.jbones.core.dao.*;

import java.util.ArrayList;
import java.util.List;
@UrlBinding("/Estimate.action/{$event}/{estimate.id}/{estimate.archiveId}")
public class EstimateActionBean extends BaseActionBean implements ActionBean {

   private Estimate estimate;
   private List<Estimate> estimateList;
   private List<Estimate> estimateArchiveList;
   private List<Client> clientList;
   private List<Project> projectList;
   private List<Resource> resourceList;
   private List<Phase> phaseList;
   private List<Factor> factorList;
   private List<Effort> effortList;

   private static final String LIST = "/view/estimatelist";
   private static final String VIEW = "/view/estimateview";
   private static final String EDIT = "/view/estimateedit";
   private static final String LIST_ARCHIVE = "/view/estimatearchivelist";

   protected String getDAOName() {
      return "Estimate";
   }
   public Estimate getEstimate() {
      return estimate;
   }
   public void setEstimate(Estimate estimate) {
      this.estimate = estimate;
   }
   public List<Estimate> getEstimateList() {
      return estimateList;
   }
   public void setEstimateList(List<Estimate> estimateList) {
      this.estimateList = estimateList;
   }
   public List<Estimate> getEstimateArchiveList() {
      return estimateArchiveList;
   }
   public void setEstimateArchiveList(List<Estimate> estimateArchiveList) {
      this.estimateArchiveList = estimateArchiveList;
   }
   public List<Client> getClientList() {
      return clientList;
   }
   public void setClientList(List<Client> clientList) {
      this.clientList = clientList;
   }
   public List<Project> getProjectList() {
      return projectList;
   }
   public void setProjectList(List<Project> projectList) {
      this.projectList = projectList;
   }
   public List<Resource> getResourceList() {
      return resourceList;
   }
   public void setResourceList(List<Resource> resourceList) {
      this.resourceList = resourceList;
   }
   public List<Phase> getPhaseList() {
      return phaseList;
   }
   public void setPhaseList(List<Phase> phaseList) {
      this.phaseList = phaseList;
   }
   public List<Factor> getFactorList() {
      return factorList;
   }
   public void setFactorList(List<Factor> factorList) {
      this.factorList = factorList;
   }
   public List<Effort> getEffortList() {
      return effortList;
   }
   public void setEffortList(List<Effort> effortList) {
      this.effortList = effortList;
   }
   protected ADAO getDAO() {
      return (ADAO)super.getDAO();
   }
   @DefaultHandler
   @HandlesEvent("list")
   public Resolution list() throws ListException {
      setMessagesOnActionBean("estimateMessages");
      setEstimateList(getEstimates());
      setClientList(getClients());
      setProjectList(getProjects());
      setResourceList(getResources());
      setPhaseList(getPhases());
      setFactorList(getFactors());
      setEstimate(null);
      return new ForwardResolution(LIST);
   }
   @HandlesEvent("listArchive")
   public Resolution listArchive() throws ListException {
      setMessagesOnActionBean("estimateMessages");
      setEstimateArchiveList(getEstimatesArchive());
      setEstimate(null);
      return new ForwardResolution(LIST_ARCHIVE);
   }
   @HandlesEvent("archiveEstimate")
   public Resolution archiveEstimate() {
      boolean archived = false;
      try {
         setEstimate((Estimate)getDAO().read(estimate.getId()));
         archived = getDAO().archive(estimate.getId());
         return new RedirectResolution(EstimateActionBean.class);
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived.  {1} must be unique", getMessageParams(estimate)));
         return new RedirectResolution(EstimateActionBean.class);
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(estimate)));
         return new RedirectResolution(EstimateActionBean.class);
      } catch (ArchiveException ae) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(estimate)));
         return new RedirectResolution(EstimateActionBean.class);
      } finally {
         if (archived) {
            getContext().getMessages().add(new SimpleMessage("{0} Archived", getMessageParams(estimate)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateMessages");
      }
   }
   @HandlesEvent("unarchiveEstimate")
   public Resolution unarchiveEstimate() {
      boolean unarchived = false;
      try {
         setEstimate((Estimate)getDAO().readArchive(estimate.getArchiveId()));
         unarchived = getDAO().unarchive(estimate.getArchiveId());
         return new RedirectResolution(EstimateActionBean.class, "listArchive");
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived.  {1} must be unique", getMessageParams(estimate)));
         return new RedirectResolution(EstimateActionBean.class, "listArchive");
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(estimate)));
         return new RedirectResolution(EstimateActionBean.class);
      } catch (CreateException ce) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(estimate)));
         return new RedirectResolution(EstimateActionBean.class);
      } finally {
         if (unarchived) {
            getContext().getMessages().add(new SimpleMessage("{0} Un-Archived", getMessageParams(estimate)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateMessages");
      }
   }
   @HandlesEvent("createEstimate")
   public Resolution createEstimate() {
      boolean created = false;
      try {
         created = getDAO().create(estimate);
         return new RedirectResolution(EstimateActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created.  {1} must be unique", getMessageParams(estimate)));
            return new RedirectResolution(EstimateActionBean.class);
      } catch (CreateException ce) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created", getMessageParams(estimate)));
            return new RedirectResolution(EstimateActionBean.class);
      } finally {
         if (created) {
            getContext().getMessages().add(new SimpleMessage("{0} Created", getMessageParams(estimate)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateMessages");
      }
   }
   @HandlesEvent("updateEstimate")
   public Resolution updateEstimate() {
      boolean updated = false;
      try {
         updated = getDAO().update(estimate);
         return new RedirectResolution(EstimateActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated.  {1} must be unique", getMessageParams(estimate)));
            return new RedirectResolution(EstimateActionBean.class);
      } catch (UpdateException updateE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated", getMessageParams(estimate)));
            return new RedirectResolution(EstimateActionBean.class);
      } finally {
         if (updated) {
            getContext().getMessages().add(new SimpleMessage("{0} Updated", getMessageParams(estimate)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateMessages");
      }
   }
   @HandlesEvent("editEstimate")
   public Resolution editEstimate() {
      try {
         setEstimate((Estimate)getDAO().read(estimate.getId()));
         setEstimateList(getEstimates());
         setClientList(getClients());
         setProjectList(getProjects());
         setResourceList(getResources());
         setPhaseList(getPhases());
         setFactorList(getFactors());
         setEffortList(getEfforts());
         return new ForwardResolution(EDIT);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(estimate)));
            return new RedirectResolution(EstimateActionBean.class);
      } catch (ListException listE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(estimate)));
            return new RedirectResolution(EstimateActionBean.class);
      } finally {
         if (null != estimate) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(estimate)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateMessages");
      }
   }
   @HandlesEvent("viewEstimate")
   public Resolution viewEstimate() {
      try {
         setEstimate((Estimate)getDAO().read(estimate.getId()));
         return new ForwardResolution(VIEW);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(estimate)));
            return new RedirectResolution(EstimateActionBean.class);
      } finally {
         if (null != estimate) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(estimate)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("estimateMessages", getContext().getMessages());
         setMessagesOnActionBean("estimateMessages");
      }
   }
   @HandlesEvent("viewEstimateDetail")
   public Resolution viewEstimateDetail() {
      try {
         setEffortList(getEfforts());
         return new ForwardResolution(EstimateEffortActionBean.class, "listEstimateEffortsByEstimate")
         .addParameter("estimateEffort.estimate.id", estimate.getId());
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} viewEstimateDetail failed", getDAOName()));
         return new RedirectResolution(EstimateActionBean.class);
      }
   }
   public List<Estimate> getEstimates() throws ListException {
      try {
         return getDAO().list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", getDAOName()));
         return null;
      }
   }
   public List<Estimate> getEstimatesArchive() throws ListException {
      try {
         return getDAO().listArchive();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list archive failed", getDAOName()));
         return null;
      }
   }
   public List<Client> getClients() throws ListException {
      try {
         return getDAO("Client").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Client"));
         return null;
      }
   }
   public List<Project> getProjects() throws ListException {
      try {
         return getDAO("Project").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Project"));
         return null;
      }
   }
   public List<Resource> getResources() throws ListException {
      try {
         return getDAO("Resource").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Resource"));
         return null;
      }
   }
   public List<Phase> getPhases() throws ListException {
      try {
         return getDAO("Phase").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Phase"));
         return null;
      }
   }
   public List<Factor> getFactors() throws ListException {
      try {
         return getDAO("Factor").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Factor"));
         return null;
      }
   }
   public List<Effort> getEfforts() throws ListException {
      try {
         return getDAO("Effort").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Effort"));
         return null;
      }
   }
}