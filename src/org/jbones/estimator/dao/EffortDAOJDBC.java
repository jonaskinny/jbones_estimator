package org.jbones.estimator.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import org.jbones.estimator.model.dto.Effort;
import org.jbones.estimator.model.dto.UnitType;
import org.jbones.estimator.model.dto.Complexity;
import org.jbones.estimator.model.dto.Phase;
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class EffortDAOJDBC extends ADAOJDBC {
   protected static final String UNITTYPE_DAO_NAME = "UnitType";
   protected static final String COMPLEXITY_DAO_NAME = "Complexity";
   protected static final String PHASE_DAO_NAME = "Phase";
   protected String getEntityName() {
      return "Effort";
   }
   protected String getArchiveEntityName() {
      return "Effort_Archive";
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
         Effort effort = (Effort)arg;
         conn = getConnection();
         // transaction begin
         JDBCUtil.setAutoCommit(conn,false);
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ENTITY_EFFORT A values(?)");
         entityId = getSequenceNextVal();
         ps.setLong(1,entityId); // used for PK in entity table
         result = ps.executeUpdate();
         if (result == 1) {
            ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "EFFORT A values(?,?,?,?,?,?)");
            ps.setLong(1,entityId);
            ps.setString(2,effort.getIdentifier());
            ps.setLong(3,effort.getUnitType().getId());
            ps.setLong(4,effort.getComplexity().getId());
            ps.setLong(5,effort.getPhase().getId());
            ps.setInt(6,effort.getEffortHours());
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
         Effort effort = (Effort)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "EFFORT_ARCHIVE A values(?,?,?,?,?,?,?)");
         ps.setLong(1,getArchiveSequenceNextVal());
         ps.setLong(2,effort.getId());
         ps.setString(3,effort.getIdentifier());
         ps.setLong(4,effort.getUnitType().getId());
         ps.setLong(5,effort.getComplexity().getId());
         ps.setLong(6,effort.getPhase().getId());
         ps.setInt(7,effort.getEffortHours());
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
         Effort effort = (Effort)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "EFFORT A values(?,?,?,?,?,?)");
         ps.setLong(1,effort.getId());
         ps.setString(2,effort.getIdentifier());
         ps.setLong(3,effort.getUnitType().getId());
         ps.setLong(4,effort.getComplexity().getId());
         ps.setLong(5,effort.getPhase().getId());
         ps.setInt(6,effort.getEffortHours());
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
         ps = conn.prepareStatement("select A.EFFORT_ID, A.IDENTIFIER, A.UNIT_TYPE_ID, A.COMPLEXITY_ID, A.PHASE_ID, A.EFFORT_HOURS from " + getSchemaName() + "." + "EFFORT A where A.EFFORT_ID = ? order by A.EFFORT_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new Effort(), conn, rs);
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
         ps = conn.prepareStatement("select A.EFFORT_ID, A.IDENTIFIER, A.UNIT_TYPE_ID, A.COMPLEXITY_ID, A.PHASE_ID, A.EFFORT_HOURS from " + getSchemaName() + "." + "EFFORT A where A.EFFORT_ID = ? order by A.EFFORT_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadListEntity(new Effort(), conn, rs);
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.EFFORT_ID, A.IDENTIFIER, A.UNIT_TYPE_ID, A.COMPLEXITY_ID, A.PHASE_ID, A.EFFORT_HOURS from " + getSchemaName() + "." + "EFFORT_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntityArchive(new Effort(), conn, rs);
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
         ps = conn.prepareStatement("select A.EFFORT_ID, A.IDENTIFIER, A.UNIT_TYPE_ID, A.COMPLEXITY_ID, A.PHASE_ID, A.EFFORT_HOURS from " + getSchemaName() + "." + "EFFORT A order by A.EFFORT_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Effort>();
         while (rs.next()) {
            list.add(loadListEntity(new Effort(), conn, rs));
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.EFFORT_ID, A.IDENTIFIER, A.UNIT_TYPE_ID, A.COMPLEXITY_ID, A.PHASE_ID, A.EFFORT_HOURS from " + getSchemaName() + "." + "EFFORT_ARCHIVE A order by A.ARCHIVE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Effort>();
         while (rs.next()) {
            list.add(loadEntityArchive(new Effort(), conn, rs));
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
         Effort effort = (Effort)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "EFFORT A set A.IDENTIFIER = ?, A.UNIT_TYPE_ID = ?, A.COMPLEXITY_ID = ?, A.PHASE_ID = ?, A.EFFORT_HOURS = ? where A.EFFORT_ID = ?");
         ps.setString(1,effort.getIdentifier());
         ps.setLong(2,effort.getUnitType().getId());
         ps.setLong(3,effort.getComplexity().getId());
         ps.setLong(4,effort.getPhase().getId());
         ps.setInt(5,effort.getEffortHours());
         ps.setLong(6,effort.getId());
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
      Effort effort;
      Connection conn = null;
      try {
         effort = (Effort) read(arg); // read based on the entity id
         if (null == effort) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createArchive(effort, conn)) {
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
      Effort effort = null;
      Connection conn = null;
      try {
         effort = (Effort) readArchive(arg); // read based on the archive id
         if (null == effort) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createFromArchive(effort, conn)) {
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "EFFORT A where A.EFFORT_ID = ?");
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "EFFORT_ARCHIVE A where A.ARCHIVE_ID = ?");
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
   private UnitType readUnitType(long arg, Connection conn) throws ReadException {
      try {
         return (UnitType)getDAO(UNITTYPE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + UNITTYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Complexity readComplexity(long arg, Connection conn) throws ReadException {
      try {
         return (Complexity)getDAO(COMPLEXITY_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + COMPLEXITY_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Phase readPhase(long arg, Connection conn) throws ReadException {
      try {
         return (Phase)getDAO(PHASE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + PHASE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private UnitType readListUnitType(long arg, Connection conn) throws ReadException {
      try {
         return (UnitType)getDAO(UNITTYPE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + UNITTYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Complexity readListComplexity(long arg, Connection conn) throws ReadException {
      try {
         return (Complexity)getDAO(COMPLEXITY_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + COMPLEXITY_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Phase readListPhase(long arg, Connection conn) throws ReadException {
      try {
         return (Phase)getDAO(PHASE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + PHASE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   protected DTO loadEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Effort effort = (Effort)entity;
      try {
         effort.setId(rs.getLong(1));
         effort.setIdentifier(rs.getString(2));
         effort.setUnitType(readUnitType(rs.getLong(3),conn));
         effort.setComplexity(readComplexity(rs.getLong(4),conn));
         effort.setPhase(readPhase(rs.getLong(5),conn));
         effort.setEffortHours(rs.getInt(6));
         return effort;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Effort effort = (Effort)entity;
      try {
         effort.setId(rs.getLong(1));
         effort.setIdentifier(rs.getString(2));
         return effort;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Effort effort = (Effort)entity;
      try {
         effort.setId(rs.getLong(1));
         effort.setIdentifier(rs.getString(2));
         effort.setUnitType(readListUnitType(rs.getLong(3),conn));
         effort.setComplexity(readListComplexity(rs.getLong(4),conn));
         effort.setPhase(readListPhase(rs.getLong(5),conn));
         effort.setEffortHours(rs.getInt(6));
         return effort;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadEntityArchive(ADTO entity, Connection conn, ResultSet rs) throws ReadException {
      Effort effort = (Effort)entity;
      try {
         effort.setArchiveId(rs.getLong(1));
         effort.setId(rs.getLong(2));
         effort.setIdentifier(rs.getString(3));
         effort.setUnitType(readUnitType(rs.getLong(4),conn));
         effort.setComplexity(readComplexity(rs.getLong(5),conn));
         effort.setPhase(readPhase(rs.getLong(6),conn));
         effort.setEffortHours(rs.getInt(7));
         return effort;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
}
