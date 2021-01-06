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

import org.jbones.estimator.model.dto.Client;
import org.jbones.stripes.controller.action.*;
import org.jbones.core.*;
import org.jbones.core.dao.*;

import java.util.ArrayList;
import java.util.List;
@UrlBinding("/Client.action/{$event}/{client.id}/{client.archiveId}")
public class ClientActionBean extends BaseActionBean implements ActionBean {

   private Client client;
   private List<Client> clientList;
   private List<Client> clientArchiveList;
   private static final String LIST = "/view/clientlist";
   private static final String VIEW = "/view/clientview";
   private static final String VIEW_ARCHIVE = "/view/clientarchiveview";
   private static final String EDIT = "/view/clientedit";
   private static final String LIST_ARCHIVE = "/view/clientarchivelist";

   protected String getDAOName() {
      return "Client";
   }
   public Client getClient() {
      return client;
   }
   public void setClient(Client client) {
      this.client = client;
   }
   public List<Client> getClientList() {
      return clientList;
   }
   public void setClientList(List<Client> clientList) {
      this.clientList = clientList;
   }
   public List<Client> getClientArchiveList() {
      return clientArchiveList;
   }
   public void setClientArchiveList(List<Client> clientArchiveList) {
      this.clientArchiveList = clientArchiveList;
   }
   protected ADAO getDAO() {
      return (ADAO)super.getDAO();
   }
   @DefaultHandler
   @HandlesEvent("list")
   public Resolution list() {
      setMessagesOnActionBean("clientMessages");
      setClientList(getClients());
      setClient(null);
      return new ForwardResolution(LIST);
   }
   @HandlesEvent("listArchive")
   public Resolution listArchive() {
      setMessagesOnActionBean("clientMessages");
      setClientArchiveList(getClientsArchive());
      setClient(null);
      return new ForwardResolution(LIST_ARCHIVE);
   }
   public List<Client> getClients() {
      try {
         return getDAO().list();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list failed", getDAOName()));
         return null;
      }
   }
   public List<Client> getClientsArchive() {
      try {
         return getDAO().listArchive();
      } catch (ListException le) {
         getContext().getMessages().add(new SimpleMessage("{0} list archive failed", getDAOName()));
         return null;
      }
   }
   @HandlesEvent("archiveClient")
   public Resolution archiveClient() {
      boolean archived = false;
      try {
         setClient((Client)getDAO().read(client.getId()));
         archived = getDAO().archive(client.getId());
         return new RedirectResolution(ClientActionBean.class);
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived.  {1} must be unique", getMessageParams(client)));
         return new RedirectResolution(ClientActionBean.class);
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(client)));
         return new RedirectResolution(ClientActionBean.class);
      } catch (ArchiveException ae) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Archived", getMessageParams(client)));
         return new RedirectResolution(ClientActionBean.class);
      } finally {
         if (archived) {
            getContext().getMessages().add(new SimpleMessage("{0} Archived", getMessageParams(client)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("clientMessages", getContext().getMessages());
         setMessagesOnActionBean("clientMessages");
      }
   }
   @HandlesEvent("unarchiveClient")
   public Resolution unarchiveClient() {
      boolean unarchived = false;
      try {
         setClient((Client)getDAO().readArchive(client.getArchiveId()));
         unarchived = getDAO().unarchive(client.getArchiveId());
         return new RedirectResolution(ClientActionBean.class, "listArchive");
      } catch (UniqueException ue) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived.  {1} must be unique", getMessageParams(client)));
         return new RedirectResolution(ClientActionBean.class, "listArchive");
      } catch (ReadException re) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(client)));
         return new RedirectResolution(ClientActionBean.class);
      } catch (CreateException ce) {
         getContext().getMessages().add(new SimpleMessage("{0} Not Un-Archived", getMessageParams(client)));
         return new RedirectResolution(ClientActionBean.class);
      } finally {
         if (unarchived) {
            getContext().getMessages().add(new SimpleMessage("{0} Un-Archived", getMessageParams(client)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("clientMessages", getContext().getMessages());
         setMessagesOnActionBean("clientMessages");
      }
   }
   @HandlesEvent("createClient")
   public Resolution createClient() {
      boolean created = false;
      try {
         created = getDAO().create(client);
         return new RedirectResolution(ClientActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created.  {1} must be unique", getMessageParams(client)));
            return new RedirectResolution(ClientActionBean.class);
      } catch (CreateException ce) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Created", getMessageParams(client)));
            return new RedirectResolution(ClientActionBean.class);
      } finally {
         if (created) {
            getContext().getMessages().add(new SimpleMessage("{0} Created", getMessageParams(client)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("clientMessages", getContext().getMessages());
         setMessagesOnActionBean("clientMessages");
      }
   }
   @HandlesEvent("updateClient")
   public Resolution updateClient() {
      boolean updated = false;
      try {
         updated = getDAO().update(client);
         return new RedirectResolution(ClientActionBean.class);
      } catch (UniqueException ue) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated.  {1} must be unique", getMessageParams(client)));
            return new RedirectResolution(ClientActionBean.class);
      } catch (UpdateException updateE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Updated", getMessageParams(client)));
            return new RedirectResolution(ClientActionBean.class);
      } finally {
         if (updated) {
            getContext().getMessages().add(new SimpleMessage("{0} Updated", getMessageParams(client)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("clientMessages", getContext().getMessages());
         setMessagesOnActionBean("clientMessages");
      }
   }
   @HandlesEvent("editClient")
   public Resolution editClient() {
      try {
         setClient((Client)getDAO().read(client.getId()));
         return new ForwardResolution(EDIT);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(client)));
            return new RedirectResolution(ClientActionBean.class);
      } finally {
         if (null != client) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(client)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("clientMessages", getContext().getMessages());
         setMessagesOnActionBean("clientMessages");
      }
   }
   @HandlesEvent("viewClient")
   public Resolution viewClient() {
      try {
         setClient((Client)getDAO().read(client.getId()));
         return new ForwardResolution(VIEW);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(client)));
            return new RedirectResolution(ClientActionBean.class);
      } finally {
         if (null != client) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(client)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("clientMessages", getContext().getMessages());
         setMessagesOnActionBean("clientMessages");
      }
   }
   @HandlesEvent("viewClientArchive")
   public Resolution viewClientArchive() {
      try {
         setClient((Client)getDAO().readArchive(client.getArchiveId()));
         return new ForwardResolution(VIEW_ARCHIVE);
      } catch (ReadException readE) {
            getContext().getMessages().add(new SimpleMessage("{0} Not Located", getMessageParams(client)));
            return new RedirectResolution(ClientActionBean.class);
      } finally {
         if (null != client) {
            getContext().getMessages().add(new SimpleMessage("{0} Located", getMessageParams(client)));
         }
         FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
         fs.put("clientMessages", getContext().getMessages());
         setMessagesOnActionBean("clientMessages");
      }
   }
}