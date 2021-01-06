package org.jbones.estimator.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import org.jbones.estimator.model.dto.ProjectType;
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class ProjectTypeDAOJDBC extends ADAOJDBC {
   protected String getEntityName() {
      return "Project_Type";
   }
   protected String getArchiveEntityName() {
      return "ProjectType_Archive";
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
         ProjectType projectType = (ProjectType)arg;
         conn = getConnection();
         // transaction begin
         JDBCUtil.setAutoCommit(conn,false);
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ENTITY_PROJECT_TYPE A values(?)");
         entityId = getSequenceNextVal();
         ps.setLong(1,entityId); // used for PK in entity table
         result = ps.executeUpdate();
         if (result == 1) {
            ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "PROJECT_TYPE A values(?,?)");
            ps.setLong(1,entityId);
            ps.setString(2,projectType.getIdentifier());
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
         ProjectType projectType = (ProjectType)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "PROJECT_TYPE_ARCHIVE A values(?,?,?)");
         ps.setLong(1,getArchiveSequenceNextVal());
         ps.setLong(2,projectType.getId());
         ps.setString(3,projectType.getIdentifier());
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
   private boolean createFromArchive(ADTO arg, Connection conn) throws CreateException, UniqueException {
      PreparedStatement ps = null;
      int result = -1;
      try {
         ProjectType projectType = (ProjectType)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "PROJECT_TYPE A values(?,?)");
         ps.setLong(1,projectType.getId());
         ps.setString(2,projectType.getIdentifier());
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
         ps = conn.prepareStatement("select A.PROJECT_TYPE_ID, A.IDENTIFIER from " + getSchemaName() + "." + "PROJECT_TYPE A where A.PROJECT_TYPE_ID = ? order by A.PROJECT_TYPE_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new ProjectType(), conn, rs);
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
         ps = conn.prepareStatement("select A.PROJECT_TYPE_ID, A.IDENTIFIER from " + getSchemaName() + "." + "PROJECT_TYPE A where A.PROJECT_TYPE_ID = ? order by A.PROJECT_TYPE_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadListEntity(new ProjectType(), conn, rs);
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.PROJECT_TYPE_ID, A.IDENTIFIER from " + getSchemaName() + "." + "PROJECT_TYPE_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntityArchive(new ProjectType(), conn, rs);
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
         ps = conn.prepareStatement("select A.PROJECT_TYPE_ID, A.IDENTIFIER from " + getSchemaName() + "." + "PROJECT_TYPE A order by A.PROJECT_TYPE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<ProjectType>();
         while (rs.next()) {
            list.add(loadListEntity(new ProjectType(), conn, rs));
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.PROJECT_TYPE_ID, A.IDENTIFIER from " + getSchemaName() + "." + "PROJECT_TYPE_ARCHIVE A order by A.ARCHIVE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<ProjectType>();
         while (rs.next()) {
            list.add(loadEntityArchive(new ProjectType(), conn, rs));
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
         ProjectType projectType = (ProjectType)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "PROJECT_TYPE A set A.IDENTIFIER = ? where A.PROJECT_TYPE_ID = ?");
         ps.setString(1,projectType.getIdentifier());
         ps.setLong(2,projectType.getId());
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
      ProjectType projectType;
      Connection conn = null;
      try {
         projectType = (ProjectType) read(arg); // read based on the entity id
         if (null == projectType) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createArchive(projectType, conn)) {
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
      ProjectType projectType = null;
      Connection conn = null;
      try {
         projectType = (ProjectType) readArchive(arg); // read based on the archive id
         if (null == projectType) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createFromArchive(projectType, conn)) {
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "PROJECT_TYPE A where A.PROJECT_TYPE_ID = ?");
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "PROJECT_TYPE_ARCHIVE A where A.ARCHIVE_ID = ?");
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
   protected DTO loadEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      ProjectType projectType = (ProjectType)entity;
      try {
         projectType.setId(rs.getLong(1));
         projectType.setIdentifier(rs.getString(2));
         return projectType;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      ProjectType projectType = (ProjectType)entity;
      try {
         projectType.setId(rs.getLong(1));
         projectType.setIdentifier(rs.getString(2));
         return projectType;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      ProjectType projectType = (ProjectType)entity;
      try {
         projectType.setId(rs.getLong(1));
         projectType.setIdentifier(rs.getString(2));
         return projectType;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadEntityArchive(ADTO entity, Connection conn, ResultSet rs) throws ReadException {
      ProjectType projectType = (ProjectType)entity;
      try {
         projectType.setArchiveId(rs.getLong(1));
         projectType.setId(rs.getLong(2));
         projectType.setIdentifier(rs.getString(3));
         return projectType;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }

}
