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
import org.jbones.estimator.model.dto.UnitType;
import org.jbones.estimator.model.dto.Complexity;
import org.jbones.estimator.model.dto.Phase;
import org.jbones.stripes.controller.action.*;
import org.jbones.core.*;
import org.jbones.core.dao.*;

import java.util.ArrayList;
import java.util.List;
@UrlBinding("/Effort.action/{$event}/{effort.id}/{effort.archiveId}")
public class EffortActionBean extends BaseActionBean implements ActionBean {

   private Effort effort;
   private List<Effort> effortList;
   private List<Effort> effortArchiveList;
   private List<UnitType> unitTypeList;
   private List<Complexity> complexityList;
   private List<Phase> phaseList;
   private static final String LIST = "/view/effortlist";
   private static final String VIEW = "/view/effortview";
   private static final String EDIT = "/view/effortedit";
   private static final String LIST_ARCHIVE = "/view/effortarchivelist";

   protected String getDAOName() {
      return "Effort";
   }
   public Effort getEffort() {
      return effort;
   }
   public void setEffort(Effort effort) {
      this.effort = effort;
   }
   public List<Effort> getEffortList() {
      return effortList;
   }
   public void setEffortList(List<Effort> effortList) {
      this.effortList = effortList;
   }
   public List<Effort> getEffortArchiveList() {
      return effortArchiveList;
   }
   public void setEffortArchiveList(List<Effort> effortArchiveList) {
      this.effortArchiveList = effortArchiveList;
   }
   public List<UnitType> getUnitTypeList() {
      return unitTypeList;
   }
   public void setUnitTypeList(List<UnitType> unitTypeList) {
      this.unitTypeList = unitTypeList;
   }
   public List<Complexity> getComplexityList() {
      return complexityList;
   }
   public void setComplexityList(List<Complexity> complexityList) {
      this.complexityList = complexityList;
   }
   public List<Phase> getPhaseList() {
      return phaseList;
   }
   public void setPhaseList(List<Phase> phaseList) {
      this.phaseList = phaseList;
   }
   protected ADAO getDAO() {
      return (ADAO)super.getDAO();
   }
   @DefaultHandler
   @HandlesEvent("list")
   public Resolution list() throws ListException {
      setMessagesOnActionBean("effortMessages");
      setEffortList(getEfforts());
      setUnitTypeList(getUnitTypes());
      setComplexityList(getComplexities());
      setPhaseList(getPhases());
      setEffort(null);
      return new ForwardResolution(LIST);
   }
   @HandlesEvent("listArchive")
   public Resolution listArchive() throws ListException {
      setMessagesOnActionBean("effortMessages");
      setEffortArchiveList(getEffortsArchive());
      setEffort(null);
      return new ForwardResolution(LIST_ARCHIVE);
   }
   public List<Effort> getEfforts() {
      try {
         return getDAO().list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", getDAOName()));
         return null;
      }
   }
   public List<Effort> getEffortsArchive() {
      try {
         return getDAO().listArchive();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list archive failed", getDAOName()));
         return null;
      }
   }
   public List<UnitType> getUnitTypes() {
      try {
         return getDAO("UnitType").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "UnitType"));
         return null;
      }
   }
   public List<Complexity> getComplexities() {
      try {
         return getDAO("Complexity").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Complexity"));
         return null;
      }
   }
   public List<Phase> getPhases() {
      try {
         return getDAO("Phase").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "Phase"));
         return null;
      }
   }
   @HandlesEvent("archiveEffort")
   public Resolution archiveEffort() {
      boolean archived = false;
      try {
         setEffort((Effort)getDAO().read(effort.getId()));
         archived = getDAO().archive(effort.getId());
         return new RedirectResolution(EffortActionBean.class);
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived.  {1} must be unique", getMessageParams(effort)));
         return new RedirectResolution(EffortActionBean.class);
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(effort)));
         return new RedirectResolution(EffortActionBean.class);
      } catch (ArchiveException ae) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(effort)));
         return new RedirectResolution(EffortActionBean.class);
      } finally {
         if (archived) {
            getContext().getMessages().add(new SimpleMessage("{0} Archived", getMessageParams(effort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("effortMessages", getContext().getMessages());
         setMessagesOnActionBean("effortMessages");
      }
   }
   @HandlesEvent("unarchiveEffort")
   public Resolution unarchiveEffort() {
      boolean unarchived = false;
      try {
         setEffort((Effort)getDAO().readArchive(effort.getArchiveId()));
         unarchived = getDAO().unarchive(effort.getArchiveId());
         return new RedirectResolution(EffortActionBean.class, "listArchive");
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived.  {1} must be unique", getMessageParams(effort)));
         return new RedirectResolution(EffortActionBean.class, "listArchive");
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(effort)));
         return new RedirectResolution(EffortActionBean.class);
      } catch (CreateException ce) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(effort)));
         return new RedirectResolution(EffortActionBean.class);
      } finally {
         if (unarchived) {
            getContext().getMessages().add(new SimpleMessage("{0} Un-Archived", getMessageParams(effort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("effortMessages", getContext().getMessages());
         setMessagesOnActionBean("effortMessages");
      }
   }
   @HandlesEvent("createEffort")
   public Resolution createEffort() {
      boolean created = false;
      try {
         created = getDAO().create(effort);
         return new RedirectResolution(EffortActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created.  {1} must be unique", getMessageParams(effort)));
            return new RedirectResolution(EffortActionBean.class);
      } catch (CreateException ce) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created", getMessageParams(effort)));
            return new RedirectResolution(EffortActionBean.class);
      } finally {
         if (created) {
            getContext().getMessages().add(new SimpleMessage("{0} Created", getMessageParams(effort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("effortMessages", getContext().getMessages());
         setMessagesOnActionBean("effortMessages");
      }
   }
   @HandlesEvent("updateEffort")
   public Resolution updateEffort() {
      boolean updated = false;
      try {
         updated = getDAO().update(effort);
         return new RedirectResolution(EffortActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated.  {1} must be unique", getMessageParams(effort)));
            return new RedirectResolution(EffortActionBean.class);
      } catch (UpdateException updateE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated", getMessageParams(effort)));
            return new RedirectResolution(EffortActionBean.class);
      } finally {
         if (updated) {
            getContext().getMessages().add(new SimpleMessage("{0} Updated", getMessageParams(effort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("effortMessages", getContext().getMessages());
         setMessagesOnActionBean("effortMessages");
      }
   }
   @HandlesEvent("editEffort")
   public Resolution editEffort() {
      try {
         setEffort((Effort)getDAO().read(effort.getId()));
         setUnitTypeList(getUnitTypes());
         setComplexityList(getComplexities());
         setPhaseList(getPhases());
         return new ForwardResolution(EDIT);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(effort)));
            return new RedirectResolution(EffortActionBean.class);
      } finally {
         if (null != effort) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(effort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("effortMessages", getContext().getMessages());
         setMessagesOnActionBean("effortMessages");
      }
   }
   @HandlesEvent("viewEffort")
   public Resolution viewEffort() {
      try {
         setEffort((Effort)getDAO().read(effort.getId()));
         return new ForwardResolution(VIEW);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(effort)));
            return new RedirectResolution(EffortActionBean.class);
      } finally {
         if (null != effort) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(effort)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("effortMessages", getContext().getMessages());
         setMessagesOnActionBean("effortMessages");
      }
   }
}