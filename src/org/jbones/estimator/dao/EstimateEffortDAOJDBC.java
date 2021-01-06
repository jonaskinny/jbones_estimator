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
import org.jbones.estimator.model.dto.Estimate;
import org.jbones.estimator.model.dto.EstimateEffort;
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class EstimateEffortDAOJDBC extends BridgeDAOJDBC {
   protected static final String ESTIMATE_DAO_NAME = "Estimate";
   protected static final String EFFORT_DAO_NAME = "Effort";
   protected String getEntityName() {
      return "Estimate_Effort";
   }
   /**
   * Simple create function for a persisted object based on the DTO.
   */
   public boolean create(DTO arg) throws CreateException, UniqueException {
      Connection conn = null;
      PreparedStatement ps = null;
      int result = -1;
      try {
         EstimateEffort estimateEffort = (EstimateEffort)arg;
         conn = getConnection();
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ESTIMATE_EFFORT A values(?,?,?,?)");
         ps.setLong(1,getSequenceNextVal()); // used for PK in entity table
         ps.setString(2,estimateEffort.getIdentifier());
         //ps.setString(2,"" + estimateEffort.getEstimate().getId() + ":" + estimateEffort.getEffort().getId());
         ps.setLong(3,estimateEffort.getEstimate().getId());
         ps.setLong(4,estimateEffort.getEffort().getId());

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
         JDBCUtil.release(conn,null,null,ps);
      }
   }
   /**
   * Simple read function for a persisted object based on the long used.
   */
	public DTO read(long arg, Connection conn) throws ReadException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.ESTIMATE_EFFORT_ID, A.IDENTIFIER, A.ESTIMATE_ID, A.EFFORT_ID from " + getSchemaName() + "." + "ESTIMATE_EFFORT A where A.ESTIMATE_EFFORT_ID = ? order by A.ESTIMATE_EFFORT_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new EstimateEffort(),conn,rs);
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
         ps = conn.prepareStatement("select A.ESTIMATE_EFFORT_ID, A.IDENTIFIER, A.ESTIMATE_ID, A.EFFORT_ID from " + getSchemaName() + "." + "ESTIMATE_EFFORT A where A.ESTIMATE_EFFORT_ID = ? order by A.ESTIMATE_EFFORT_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadListEntity(new EstimateEffort(),conn,rs);
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
   public List listChildren(Connection conn, long arg) throws ListException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.ESTIMATE_EFFORT_ID, A.IDENTIFIER, A.ESTIMATE_ID, A.EFFORT_ID from " + getSchemaName() + "." + "ESTIMATE_EFFORT A where ESTIMATE_ID = ? order by A.ESTIMATE_EFFORT_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         List list = new ArrayList<EstimateEffort>();
         while (rs.next()) {
            list.add(loadListEntity(new EstimateEffort(),conn,rs));
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
   public List listParents(Connection conn, long arg) throws ListException {
      throw new ListException(new IFException().getMessage());
   }
	/**
   * Simple list function for a List of all persisted objects.
   * Subclasses should implement this function.
   */
	public List list(Connection conn) throws ListException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.ESTIMATE_EFFORT_ID, A.IDENTIFIER, A.ESTIMATE_ID, A.EFFORT_ID from " + getSchemaName() + "." + "ESTIMATE_EFFORT A order by A.ESTIMATE_EFFORT_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<EstimateEffort>();
         while (rs.next()) {
            list.add(loadListEntity(new EstimateEffort(),conn,rs));
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
         EstimateEffort estimateEffort = (EstimateEffort)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "ESTIMATE_EFFORT A set A.IDENTIFIER = ? where A.ESTIMATE_EFFORT_ID = ?");
         ps.setLong(1,estimateEffort.getId());
         ps.setString(2,estimateEffort.getIdentifier());
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
   * deletes all data based on the long used.
   * Subclasses should implement this function.
   */
	public boolean delete(long arg, Connection conn) throws DeleteException {
      PreparedStatement ps = null;
      int result = -1;
      try {
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "ESTIMATE_EFFORT A where A.ESTIMATE_EFFORT_ID = ?");
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
   private Estimate readEstimate(long arg, Connection conn) throws ReadException {
      try {
         return (Estimate)getDAO(ESTIMATE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + ESTIMATE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Effort readEffort(long arg, Connection conn) throws ReadException {
      try {
         return (Effort)getDAO(EFFORT_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + EFFORT_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Estimate readListEstimate(long arg, Connection conn) throws ReadException {
      try {
         return (Estimate)getDAO(ESTIMATE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + ESTIMATE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Effort readListEffort(long arg, Connection conn) throws ReadException {
      try {
         return (Effort)getDAO(EFFORT_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + EFFORT_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   protected DTO loadEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      EstimateEffort estimateEffort = (EstimateEffort)entity;
      try {
            estimateEffort.setId(rs.getLong(1));
            estimateEffort.setIdentifier(rs.getString(2));
            estimateEffort.setEstimate(readEstimate(rs.getLong(3),conn));
            estimateEffort.setEffort(readEffort(rs.getLong(4),conn));
            return estimateEffort;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      EstimateEffort estimateEffort = (EstimateEffort)entity;
      try {
            estimateEffort.setId(rs.getLong(1));
            estimateEffort.setIdentifier(rs.getString(2));
            estimateEffort.setEstimate(readListEstimate(rs.getLong(3),conn));
            estimateEffort.setEffort(readListEffort(rs.getLong(4),conn));
            return estimateEffort;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      EstimateEffort estimateEffort = (EstimateEffort)entity;
      try {
            estimateEffort.setId(rs.getLong(1));
            estimateEffort.setIdentifier(rs.getString(2));
            return estimateEffort;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
}
