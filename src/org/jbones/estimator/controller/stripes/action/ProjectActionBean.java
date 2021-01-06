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

import org.jbones.estimator.model.dto.Project;
import org.jbones.estimator.model.dto.ProjectType;
import org.jbones.stripes.controller.action.*;
import org.jbones.core.*;
import org.jbones.core.dao.*;

import java.util.ArrayList;
import java.util.List;
@UrlBinding("/Project.action/{$event}/{project.id}/{project.archiveId}")
public class ProjectActionBean extends BaseActionBean implements ActionBean {

   private Project project;
   private List<Project> projectList;
   private List<Project> projectArchiveList;
   private List<ProjectType> projectTypeList;
   private static final String LIST = "/view/projectlist";
   private static final String VIEW = "/view/projectview";
   private static final String EDIT = "/view/projectedit";
   private static final String LIST_ARCHIVE = "/view/projectarchivelist";

   protected String getDAOName() {
      return "Project";
   }
   public Project getProject() {
      return project;
   }
   public void setProject(Project project) {
      this.project = project;
   }
   public List<Project> getProjectList() {
      return projectList;
   }
   public void setProjectList(List<Project> projectList) {
      this.projectList = projectList;
   }
   public List<Project> getProjectArchiveList() {
      return projectArchiveList;
   }
   public void setProjectArchiveList(List<Project> projectArchiveList) {
      this.projectArchiveList = projectArchiveList;
   }
   public List<ProjectType> getProjectTypeList() {
      return projectTypeList;
   }
   public void setProjectTypeList(List<ProjectType> projectTypeList) {
      this.projectTypeList = projectTypeList;
   }
   protected ADAO getDAO() {
      return (ADAO)super.getDAO();
   }
   @DefaultHandler
   @HandlesEvent("list")
   public Resolution list() throws ListException {
      setMessagesOnActionBean("projectMessages");
      setProjectList(getProjects());
      setProjectTypeList(getProjectTypes());
      setProject(null);
      return new ForwardResolution(LIST);
   }
   @HandlesEvent("listArchive")
   public Resolution listArchive() throws ListException {
      setMessagesOnActionBean("projectMessages");
      setProjectArchiveList(getProjectsArchive());
      setProject(null);
      return new ForwardResolution(LIST_ARCHIVE);
   }
   public List<Project> getProjects() {
      try {
         return getDAO().list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", getDAOName()));
         return null;
      }
   }
   public List<ProjectType> getProjectTypes() {
      try {
         return getDAO("ProjectType").list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", "ProjectType"));
         return null;
      }
   }
   public List<Project> getProjectsArchive() {
      try {
         return getDAO().listArchive();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list archive failed", getDAOName()));
         return null;
      }
   }
   @HandlesEvent("archiveProject")
   public Resolution archiveProject() {
      boolean archived = false;
      try {
         setProject((Project)getDAO().read(project.getId()));
         archived = getDAO().archive(project.getId());
         return new RedirectResolution(ProjectActionBean.class);
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived.  {1} must be unique", getMessageParams(project)));
         return new RedirectResolution(ProjectActionBean.class);
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(project)));
         return new RedirectResolution(ProjectActionBean.class);
      } catch (ArchiveException ae) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(project)));
         return new RedirectResolution(ProjectActionBean.class);
      } finally {
         if (archived) {
            getContext().getMessages().add(new SimpleMessage("{0} Archived", getMessageParams(project)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("projectMessages", getContext().getMessages());
         setMessagesOnActionBean("projectMessages");
      }
   }
   @HandlesEvent("unarchiveProject")
   public Resolution unarchiveProject() {
      boolean unarchived = false;
      try {
         setProject((Project)getDAO().readArchive(project.getArchiveId()));
         unarchived = getDAO().unarchive(project.getArchiveId());
         return new RedirectResolution(ProjectActionBean.class, "listArchive");
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived.  {1} must be unique", getMessageParams(project)));
         return new RedirectResolution(ProjectActionBean.class, "listArchive");
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(project)));
         return new RedirectResolution(ProjectActionBean.class);
      } catch (CreateException ce) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(project)));
         return new RedirectResolution(ProjectActionBean.class);
      } finally {
         if (unarchived) {
            getContext().getMessages().add(new SimpleMessage("{0} Un-Archived", getMessageParams(project)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("projectMessages", getContext().getMessages());
         setMessagesOnActionBean("projectMessages");
      }
   }
   @HandlesEvent("createProject")
   public Resolution createProject() {
      boolean created = false;
      try {
         created = getDAO().create(project);
         return new RedirectResolution(ProjectActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created.  {1} must be unique", getMessageParams(project)));
            return new RedirectResolution(ProjectActionBean.class);
      } catch (CreateException ce) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created", getMessageParams(project)));
            return new RedirectResolution(ProjectActionBean.class);
      } finally {
         if (created) {
            getContext().getMessages().add(new SimpleMessage("{0} Created", getMessageParams(project)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("projectMessages", getContext().getMessages());
         setMessagesOnActionBean("projectMessages");
      }
   }
   @HandlesEvent("updateProject")
   public Resolution updateProject() {
      boolean updated = false;
      try {
         updated = getDAO().update(project);
         return new RedirectResolution(ProjectActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated.  {1} must be unique", getMessageParams(project)));
            return new RedirectResolution(ProjectActionBean.class);
      } catch (UpdateException updateE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated", getMessageParams(project)));
            return new RedirectResolution(ProjectActionBean.class);
      } finally {
         if (updated) {
            getContext().getMessages().add(new SimpleMessage("{0} Updated", getMessageParams(project)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("projectMessages", getContext().getMessages());
         setMessagesOnActionBean("projectMessages");
      }
   }
   @HandlesEvent("editProject")
   public Resolution editProject() {
      try {
         setProject((Project)getDAO().read(project.getId()));
         setProjectTypeList(getProjectTypes());
         return new ForwardResolution(EDIT);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(project)));
            return new RedirectResolution(ProjectActionBean.class);
      } finally {
         if (null != project) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(project)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("projectMessages", getContext().getMessages());
         setMessagesOnActionBean("projectMessages");
      }
   }
   @HandlesEvent("viewProject")
   public Resolution viewProject() {
      try {
         setProject((Project)getDAO().read(project.getId()));
         return new ForwardResolution(VIEW);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(project)));
            return new RedirectResolution(ProjectActionBean.class);
      } finally {
         if (null != project) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(project)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("projectMessages", getContext().getMessages());
         setMessagesOnActionBean("projectMessages");
      }
   }
}