package org.jbones.estimator.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import org.jbones.estimator.model.dto.Resource;
import org.jbones.estimator.model.dto.Role;
import org.jbones.estimator.model.dto.ResourceRole;
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class ResourceRoleDAOJDBC extends ADAOJDBC {

   protected static final String RESOURCE_DAO_NAME = "Resource";
   protected static final String ROLE_DAO_NAME = "Role";

   protected String getEntityName() {
      return "Resource_Role";
   }
   protected String getArchiveEntityName() {
      return "Resource_Role_Archive";
   }
   /**
   * Simple create function for a persisted object based on the DTO.
   */
   public boolean create(DTO arg) throws CreateException, UniqueException {
      Connection conn = null;
      PreparedStatement ps = null;
      long entityId;
      int result = -1;
      try {
         ResourceRole resourceRole = (ResourceRole)arg;
         conn = getConnection();
         // transaction begin
         JDBCUtil.setAutoCommit(conn,false);
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ENTITY_RESOURCE_ROLE A values(?)");
         entityId = getSequenceNextVal();
         ps.setLong(1,entityId); // used for PK in entity table
         result = ps.executeUpdate();
         if (result == 1) {
            ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "RESOURCE_ROLE A values(?,?,?)");
            ps.setLong(1,entityId);
            ps.setLong(2,resourceRole.getResource().getId());
            ps.setLong(3,resourceRole.getRole().getId());
            result = ps.executeUpdate();
         }
         return result == 1;
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new CreateException(sqlE.getMessage());
      } catch (Exception e) {
         throw new CreateException(e.getMessage());
      } finally {
         if (result == 1) {
            JDBCUtil.commit(conn);
         } else {
            JDBCUtil.rollback(conn);
         }
         JDBCUtil.release(conn,null,null,ps);
      }
   }
   /**
   * Simple create function for a persisted object based on the DTO.
   */
   private boolean createArchive(DTO arg, Connection conn) throws CreateException, UniqueException {
      PreparedStatement ps = null;
      int result = -1;
      try {
         ResourceRole resourceRole = (ResourceRole)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "RESOURCE_ROLE_ARCHIVE A values(?,?,?,?)");
         ps.setLong(1,getArchiveSequenceNextVal());
         ps.setLong(2,resourceRole.getId());
         ps.setLong(3,resourceRole.getResource().getId());
         ps.setLong(4,resourceRole.getRole().getId());
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new CreateException(sqlE.getMessage());
      } catch (Exception e) {
         throw new CreateException(e.getMessage());
      } finally {
         // jcole dont release conn here as conn is passed into this method
         JDBCUtil.release(null,null,null,ps);
      }
   }
   /**
   * Simple create function for a persisted object based on the DTO.
   */
   private boolean createFromArchive(DTO arg, Connection conn) throws CreateException, UniqueException {
      PreparedStatement ps = null;
      int result = -1;
      try {
         ResourceRole resourceRole = (ResourceRole)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "RESOURCE_ROLE A values(?,?,?)");
         ps.setLong(1,resourceRole.getId());
         ps.setLong(2,resourceRole.getResource().getId());
         ps.setLong(3,resourceRole.getRole().getId());
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new CreateException(sqlE.getMessage());
      } catch (Exception e) {
         throw new CreateException(e.getMessage());
      } finally {
         // jcole dont release conn here as conn is passed into this method
         JDBCUtil.release(null,null,null,ps);
      }
   }
   /**
   * Simple read function for a persisted object based on the long used.
   */
	public DTO read(long arg, Connection conn) throws ReadException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.RESOURCE_ROLE_ID, A.RESOURCE_ID, A.ROLE_ID from " + getSchemaName() + "." + "RESOURCE_ROLE A where A.RESOURCE_ROLE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new ResourceRole(), conn, rs);
         }
         return null;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ReadException(sqlE.getMessage());
      } catch (Exception e) {
         throw new ReadException(e.getMessage());
      } finally {
         JDBCUtil.release(null,rs,null,ps);
      }
   }
   public DTO readList(long arg, Connection conn) throws ReadException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.RESOURCE_ROLE_ID, A.RESOURCE_ID, A.ROLE_ID from " + getSchemaName() + "." + "RESOURCE_ROLE A where A.RESOURCE_ROLE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadListEntity(new ResourceRole(), conn, rs);
         }
         return null;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ReadException(sqlE.getMessage());
      } catch (Exception e) {
         throw new ReadException(e.getMessage());
      } finally {
         JDBCUtil.release(null,rs,null,ps);
      }
   }
   /**
   * Simple read function for a persisted object based on the long used.
   */
	public DTO readArchive(long arg, Connection conn) throws ReadException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.RESOURCE_ROLE_ID, A.RESOURCE_ID, A.ROLE_ID from " + getSchemaName() + "." + "RESOURCE_ROLE_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntityArchive(new ResourceRole(), conn, rs);
         }
         return null;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ReadException(sqlE.getMessage());
      } catch (Exception e) {
         throw new ReadException(e.getMessage());
      } finally {
         JDBCUtil.release(null,rs,null,ps);
      }
   }
   /**
   * Simple find function for a List of persisted objects based on the Criteria used.
   * Subclasses should implement this function.
   */
	public List find(Criteria arg) throws FindException {
      throw new FindException(new IFException().getMessage());
   }
	/**
   * Simple list function for a List of all persisted objects.
   * Subclasses should implement this function.
   */
	public List list(Connection conn) throws ListException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.RESOURCE_ROLE_ID, A.RESOURCE_ROLE_ID, A.RESOURCE_ID, A.ROLE_ID from " + getSchemaName() + "." + "RESOURCE_ROLE A order by A.RESOURCE_ROLE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<ResourceRole>();
         while (rs.next()) {
            list.add(loadListEntity(new ResourceRole(), conn, rs));
         }
         return list;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ListException(sqlE.getMessage());
      } catch (Exception e) {
         throw new ListException(e.getMessage());
      } finally {
         JDBCUtil.release(null,rs,null,ps);
      }

   }
   /**
   * Simple list function for a List of all persisted objects.
   * Subclasses should implement this function.
   */
	public List listArchive(Connection conn) throws ListException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.RESOURCE_ROLE_ID, A.RESOURCE_ID, A.ROLE_ID from " + getSchemaName() + "." + "RESOURCE_ROLE_ARCHIVE A order by A.ARCHIVE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<ResourceRole>();
         while (rs.next()) {
            list.add(loadEntityArchive(new ResourceRole(), conn, rs));
         }
         return list;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ListException(sqlE.getMessage());
      } catch (Exception e) {
         throw new ListException(e.getMessage());
      } finally {
         JDBCUtil.release(null,rs,null,ps);
      }

   }
	/**
   * Simple update of all properties for this persisted object.
   * Any properties not set prior to a call to this method will
   * result in the default values being used.  Typically this means
   * null for Strings and objects, -1 for BIGINTs etc.
   * Subclasses should implement this function.
   */
	public boolean update(DTO arg) throws UpdateException, UniqueException {
      Connection conn = null;
      PreparedStatement ps = null;
      int result = -1;
      try {
         ResourceRole resourceRole = (ResourceRole)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "RESOURCE_ROLE A set A.RESOURCE_ID = ?, A.ROLE_ID = ? where A.RESOURCE_ROLE_ID = ?");
         ps.setLong(1,resourceRole.getResource().getId());
         ps.setLong(2,resourceRole.getRole().getId());
         ps.setLong(3,resourceRole.getId());
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new UpdateException(sqlE.getMessage());
      } catch (Exception e) {
         throw new UpdateException(e.getMessage());
      } finally {
        JDBCUtil.release(conn,null,null,ps);
      }
   }
	/**
   * Archives all data based on the long used.
   * Subclasses should implement this function.
   */
	public boolean archive(long arg) throws ArchiveException, UniqueException {
      ResourceRole resourceRole;
      Connection conn = null;
      try {
         resourceRole = (ResourceRole) read(arg); // read based on the entity id
         if (null == resourceRole) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createArchive(resourceRole, conn)) {
            if (delete(arg,conn)) {
               JDBCUtil.commit(conn);
               return true;
            } else {
               JDBCUtil.rollback(conn);
               return false;
            }
         } else {
            conn.rollback();
            return false;
         }
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new ArchiveException(sqlE.getMessage());
      } catch (Exception e) {
         throw new ArchiveException(e.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   /**
   * Unarchives all data based on the long used.
   * Subclasses should implement this function.
   */
	public boolean unarchive(long arg) throws CreateException, UniqueException {
      ResourceRole resourceRole = null;
      Connection conn = null;
      try {
         resourceRole = (ResourceRole) readArchive(arg); // read based on the archive id
         if (null == resourceRole) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createFromArchive(resourceRole, conn)) {
            if (deleteArchive(arg,conn)) {
               JDBCUtil.commit(conn);
               return true;
            } else {
               JDBCUtil.rollback(conn);
               return false;
            }
         } else {
            conn.rollback();
            return false;
         }
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new CreateException(sqlE.getMessage());
      } catch (Exception e) {
         throw new CreateException(e.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   /**
   * Unarchives all data based on the long used.
   * Subclasses should implement this function.
   */
	private boolean delete(long arg, Connection conn) throws DeleteException {
      PreparedStatement ps = null;
      int result = -1;
      try {
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "RESOURCE_ROLE A where A.RESOURCE_ROLE_ID = ?");
         ps.setLong(1,arg);
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new DeleteException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new DeleteException(sqlE.getMessage());
      } catch (Exception e) {
         throw new DeleteException(e.getMessage());
      } finally {
        JDBCUtil.release(null,null,null,ps);
      }
   }
   /**
   * Unarchives all data based on the long used.
   * Subclasses should implement this function.
   */
	private boolean deleteArchive(long arg, Connection conn) throws DeleteException {
      PreparedStatement ps = null;
      int result = -1;
      try {
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "RESOURCE_ROLE_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new DeleteException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new DeleteException(sqlE.getMessage());
      } catch (Exception e) {
         throw new DeleteException(e.getMessage());
      } finally {
        JDBCUtil.release(conn,null,null,ps);
      }
   }
	/**
   * Simple create statement for data based on the Criteria used.
   * Any properties not set prior to a call to this method will
   * result in the default values being used.  Typically this means
   * null for Strings and objects, -1 for BIGINTs etc.
   * Subclasses should implement this function.
   */
	public Criteria createCriteria(DTO arg) throws CreateException {
      // make call to getName() to get sequence from sequence object
      throw new CreateException(new IFException().getMessage());
   }
	/**
   * Creates a Criteria Object to be used with this object.
   */
	public Criteria createCriteria() throws CreateException {
      // make call to getName() to get sequence from sequence object
      throw new CreateException(new IFException().getMessage());
   }
   private Resource readResource(long arg, Connection conn) throws ReadException {
      try {
         return (Resource)getDAO(RESOURCE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + RESOURCE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Role readRole(long arg, Connection conn) throws ReadException {
      try {
         return (Role)getDAO(ROLE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + ROLE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Resource readListResource(long arg, Connection conn) throws ReadException {
      try {
         return (Resource)getDAO(RESOURCE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + RESOURCE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Role readListRole(long arg, Connection conn) throws ReadException {
      try {
         return (Role)getDAO(ROLE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + ROLE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   protected DTO loadEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      ResourceRole resourceRole = (ResourceRole)entity;
      try {
         resourceRole.setId(rs.getLong(1));
         resourceRole.setResource(readResource(rs.getLong(2), conn));
         resourceRole.setRole(readRole(rs.getLong(3), conn));
         return resourceRole;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      ResourceRole resourceRole = (ResourceRole)entity;
      try {
         resourceRole.setId(rs.getLong(1));
         resourceRole.setResource(readListResource(rs.getLong(2), conn));
         resourceRole.setRole(readListRole(rs.getLong(3), conn));
         return resourceRole;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      ResourceRole resourceRole = (ResourceRole)entity;
      try {
         resourceRole.setId(rs.getLong(1));
         resourceRole.setResource(readListResource(rs.getLong(2), conn));
         resourceRole.setRole(readListRole(rs.getLong(3), conn));
         return resourceRole;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadEntityArchive(ADTO entity, Connection conn, ResultSet rs) throws ReadException {
      ResourceRole resourceRole = (ResourceRole)entity;
      try {
         resourceRole.setArchiveId(rs.getLong(1));
         resourceRole.setId(rs.getLong(2));
         resourceRole.setResource(readResource(rs.getLong(3), conn));
         resourceRole.setRole(readRole(rs.getLong(4), conn));
         return resourceRole;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }

}
