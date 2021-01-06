package org.jbones.estimator.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import org.jbones.estimator.model.dto.Factor;
import org.jbones.estimator.model.dto.FactorType;
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class FactorDAOJDBC extends ADAOJDBC {
   protected static final String FACTORTYPE_DAO_NAME = "FactorType";
   protected String getEntityName() {
      return "Factor";
   }
   protected String getArchiveEntityName() {
      return "Factor_Archive";
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
         Factor factor = (Factor)arg;
         conn = getConnection();
         // transaction begin
         JDBCUtil.setAutoCommit(conn,false);
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ENTITY_FACTOR A values(?)");
         entityId = getSequenceNextVal();
         ps.setLong(1,entityId); // used for PK in entity table
         result = ps.executeUpdate();
         if (result == 1) {
            ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "FACTOR A values(?,?,?)");
            ps.setLong(1,entityId);
            ps.setString(2,factor.getIdentifier());
            ps.setLong(3,factor.getFactorType().getId());
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
         Factor factor = (Factor)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "FACTOR_ARCHIVE A values(?,?,?,?)");
         ps.setLong(1,getArchiveSequenceNextVal());
         ps.setLong(2,factor.getId());
         ps.setString(3,factor.getIdentifier());
         ps.setLong(4,factor.getFactorType().getId());
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
         Factor factor = (Factor)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "FACTOR A values(?,?,?)");
         ps.setLong(1,factor.getId());
         ps.setString(2,factor.getIdentifier());
         ps.setLong(3,factor.getFactorType().getId());
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
         ps = conn.prepareStatement("select A.FACTOR_ID, A.IDENTIFIER, A.FACTOR_TYPE_ID from " + getSchemaName() + "." + "FACTOR A where A.FACTOR_ID = ? order by A.FACTOR_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new Factor(), conn, rs);
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
         ps = conn.prepareStatement("select A.FACTOR_ID, A.IDENTIFIER, A.FACTOR_TYPE_ID from " + getSchemaName() + "." + "FACTOR A where A.FACTOR_ID = ? order by A.FACTOR_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadListEntity(new Factor(), conn, rs);
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.FACTOR_ID, A.IDENTIFIER, A.FACTOR_TYPE_ID from " + getSchemaName() + "." + "FACTOR_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntityArchive(new Factor(), conn, rs);
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
         ps = conn.prepareStatement("select A.FACTOR_ID, A.IDENTIFIER, A.FACTOR_TYPE_ID from " + getSchemaName() + "." + "FACTOR A order by A.FACTOR_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Factor>();
         while (rs.next()) {
            list.add(loadListEntity(new Factor(), conn, rs));
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.FACTOR_ID, A.IDENTIFIER, A.FACTOR_TYPE_ID from " + getSchemaName() + "." + "FACTOR_ARCHIVE A order by A.ARCHIVE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Factor>();
         while (rs.next()) {
            list.add(loadEntityArchive(new Factor(), conn, rs));
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
         Factor factor = (Factor)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "FACTOR A set A.IDENTIFIER = ?, A.FACTOR_TYPE_ID = ? where A.FACTOR_ID = ?");
         ps.setString(1,factor.getIdentifier());
         ps.setLong(2,factor.getFactorType().getId());
         ps.setLong(3,factor.getId());
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
      Factor factor;
      Connection conn = null;
      try {
         factor = (Factor) read(arg); // read based on the entity id
         if (null == factor) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createArchive(factor, conn)) {
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
      Factor factor = null;
      Connection conn = null;
      try {
         factor = (Factor) readArchive(arg); // read based on the archive id
         if (null == factor) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createFromArchive(factor, conn)) {
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "FACTOR A where A.FACTOR_ID = ?");
         ps.setLong(1,arg);
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "FACTOR_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
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
   private FactorType readFactorType(long arg, Connection conn) throws ReadException {
      try {
         return (FactorType)getDAO(FACTORTYPE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + FACTORTYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private FactorType readListFactorType(long arg, Connection conn) throws ReadException {
      try {
         return (FactorType)getDAO(FACTORTYPE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + FACTORTYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   protected DTO loadEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Factor factor = (Factor)entity;
      try {
         factor.setId(rs.getLong(1));
         factor.setIdentifier(rs.getString(2));
         factor.setFactorType(readFactorType(rs.getLong(3),conn));
         return factor;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Factor factor = (Factor)entity;
      try {
         factor.setId(rs.getLong(1));
         factor.setIdentifier(rs.getString(2));
         factor.setFactorType(readListFactorType(rs.getLong(3),conn));
         return factor;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Factor factor = (Factor)entity;
      try {
         factor.setId(rs.getLong(1));
         factor.setIdentifier(rs.getString(2));
         return factor;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadEntityArchive(ADTO entity, Connection conn, ResultSet rs) throws ReadException {
      Factor factor = (Factor)entity;
      try {
         factor.setArchiveId(rs.getLong(1));
         factor.setId(rs.getLong(2));
         factor.setIdentifier(rs.getString(3));
         factor.setFactorType(readFactorType(rs.getLong(4),conn));
         return factor;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
}
