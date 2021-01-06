package org.jbones.estimator.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import org.jbones.estimator.model.dto.Client;
import org.jbones.estimator.model.dto.Estimate;
import org.jbones.estimator.model.dto.Factor;
import org.jbones.estimator.model.dto.Project;
import org.jbones.estimator.model.dto.Phase;
import org.jbones.estimator.model.dto.Resource;
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class EstimateDAOJDBC extends ADAOJDBC {
   protected static final String CLIENT_DAO_NAME = "Client";
   protected static final String FACTOR_DAO_NAME = "Factor";
   protected static final String PHASE_DAO_NAME = "Phase";
   protected static final String PROJECT_DAO_NAME = "Project";
   protected static final String RESOURCE_DAO_NAME = "Resource";

   protected String getEntityName() {
      return "Estimate";
   }
   protected String getArchiveEntityName() {
      return "Estimate_Archive";
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
         Estimate estimate = (Estimate)arg;
         conn = getConnection();
         // transaction begin
         JDBCUtil.setAutoCommit(conn,false);
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ENTITY_ESTIMATE A values(?)");
         entityId = getSequenceNextVal();
         ps.setLong(1,entityId); // used for PK in entity table
         result = ps.executeUpdate();
         if (result == 1) {
            ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ESTIMATE A values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setLong(1,entityId);
            ps.setString(2,estimate.getIdentifier());
            ps.setLong(3,estimate.getClient().getId());
            ps.setLong(4,estimate.getProject().getId());
            ps.setLong(5,estimate.getEstimator().getId());
            ps.setLong(6,estimate.getPhase().getId());
            ps.setLong(7,estimate.getTimeProducedMillisEpoch());
            ps.setInt(8,estimate.getRawUnitEstimate());
            ps.setInt(9,estimate.getAdjustedEstimate());
            ps.setInt(10,estimate.getHighEstimate());
            ps.setInt(11,estimate.getLowEstimate());
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
         Estimate estimate = (Estimate)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ESTIMATE_ARCHIVE A values(?,?,?,?,?,?,?,?,?,?,?,?)");
         ps.setLong(1,getArchiveSequenceNextVal());
         ps.setLong(2,estimate.getId());
         ps.setString(3,estimate.getIdentifier());
         ps.setLong(4,estimate.getClient().getId());
         ps.setLong(5,estimate.getProject().getId());
         ps.setLong(6,estimate.getEstimator().getId());
         ps.setLong(7,estimate.getPhase().getId());
         ps.setLong(8,estimate.getTimeProducedMillisEpoch());
         ps.setInt(9,estimate.getRawUnitEstimate());
         ps.setInt(10,estimate.getAdjustedEstimate());
         ps.setInt(11,estimate.getHighEstimate());
         ps.setInt(12,estimate.getLowEstimate());
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
         Estimate estimate = (Estimate)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ESTIMATE A values(?,?,?,?,?,?,?,?,?,?,?)");
         ps.setLong(1,estimate.getId());
         ps.setString(2,estimate.getIdentifier());
         ps.setLong(3,estimate.getClient().getId());
         ps.setLong(4,estimate.getProject().getId());
         ps.setLong(5,estimate.getEstimator().getId());
         ps.setLong(6,estimate.getPhase().getId());
         ps.setLong(7,estimate.getTimeProducedMillisEpoch());
         ps.setInt(8,estimate.getRawUnitEstimate());
         ps.setInt(9,estimate.getAdjustedEstimate());
         ps.setInt(10,estimate.getHighEstimate());
         ps.setInt(11,estimate.getLowEstimate());
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
         ps = conn.prepareStatement("select A.ESTIMATE_ID, A.IDENTIFIER, A.CLIENT_ID, A.PROJECT_ID, A.ESTIMATOR_ID, A.PHASE_ID, A.TIME_PRODUCED_MILLIS_EPOCH, A.RAW_UNIT_ESTIMATE, A.ADJUSTED_ESTIMATE, A.HIGH_ESTIMATE, A.LOW_ESTIMATE from " + getSchemaName() + "." + "ESTIMATE A where A.ESTIMATE_ID = ? order by A.ESTIMATE_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new Estimate(),conn,rs);
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
         ps = conn.prepareStatement("select A.ESTIMATE_ID, A.IDENTIFIER, A.CLIENT_ID, A.PROJECT_ID, A.ESTIMATOR_ID, A.PHASE_ID, A.TIME_PRODUCED_MILLIS_EPOCH, A.RAW_UNIT_ESTIMATE, A.ADJUSTED_ESTIMATE, A.HIGH_ESTIMATE, A.LOW_ESTIMATE from " + getSchemaName() + "." + "ESTIMATE A where A.ESTIMATE_ID = ? order by A.ESTIMATE_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadListEntity (new Estimate(),conn,rs);
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.ESTIMATE_ID, A.IDENTIFIER, A.CLIENT_ID, A.PROJECT_ID, A.ESTIMATOR_ID, A.PHASE_ID, A.TIME_PRODUCED_MILLIS_EPOCH, A.RAW_UNIT_ESTIMATE, A.ADJUSTED_ESTIMATE, A.HIGH_ESTIMATE, A.LOW_ESTIMATE from " + getSchemaName() + "." + "ESTIMATE_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntityArchive(new Estimate(),conn,rs);
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
         ps = conn.prepareStatement("select A.ESTIMATE_ID, A.IDENTIFIER, A.CLIENT_ID, A.PROJECT_ID, A.ESTIMATOR_ID, A.PHASE_ID, A.TIME_PRODUCED_MILLIS_EPOCH, A.RAW_UNIT_ESTIMATE, A.ADJUSTED_ESTIMATE, A.HIGH_ESTIMATE, A.LOW_ESTIMATE from " + getSchemaName() + "." + "ESTIMATE A order by A.ESTIMATE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Estimate>();
         while (rs.next()) {
            list.add(loadListEntity(new Estimate(),conn,rs));
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.ESTIMATE_ID, A.IDENTIFIER, A.CLIENT_ID, A.PROJECT_ID, A.ESTIMATOR_ID, A.PHASE_ID, A.TIME_PRODUCED_MILLIS_EPOCH, A.RAW_UNIT_ESTIMATE, A.ADJUSTED_ESTIMATE, A.HIGH_ESTIMATE, A.LOW_ESTIMATE from " + getSchemaName() + "." + "ESTIMATE_ARCHIVE A order by A.ARCHIVE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Estimate>();
         while (rs.next()) {
            list.add(loadEntityArchive(new Estimate(),conn,rs));
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
         Estimate estimate = (Estimate)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "ESTIMATE A set A.IDENTIFIER = ?, A.CLIENT_ID = ?, A.PROJECT_ID = ?, A.ESTIMATOR_ID = ?, A.PHASE_ID = ?, A.TIME_PRODUCED_MILLIS_EPOCH = ?, A.RAW_UNIT_ESTIMATE = ?, A.ADJUSTED_ESTIMATE = ?, A.HIGH_ESTIMATE = ?, A.LOW_ESTIMATE = ? where A.ESTIMATE_ID = ?");
         ps.setString(1,estimate.getIdentifier());
         ps.setLong(2,estimate.getClient().getId());
         ps.setLong(3,estimate.getProject().getId());
         ps.setLong(4,estimate.getEstimator().getId());
         ps.setLong(5,estimate.getPhase().getId());
         ps.setLong(6,estimate.getTimeProducedMillisEpoch());
         ps.setLong(7,estimate.getRawUnitEstimate());
         ps.setLong(8,estimate.getAdjustedEstimate());
         ps.setLong(9,estimate.getHighEstimate());
         ps.setLong(10,estimate.getLowEstimate());
         ps.setLong(11,estimate.getId());

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
      Estimate estimate;
      Connection conn = null;
      try {
         estimate = (Estimate) read(arg); // read based on the entity id
         if (null == estimate) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createArchive(estimate, conn)) {
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
      Estimate estimate = null;
      Connection conn = null;
      try {
         estimate = (Estimate) readArchive(arg); // read based on the archive id
         if (null == estimate) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createFromArchive(estimate, conn)) {
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "ESTIMATE A where A.ESTIMATE_ID = ?");
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "ESTIMATE_ARCHIVE A where A.ARCHIVE_ID = ?");
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
   private Client readClient(long arg, Connection conn) throws ReadException {
      try {
         return (Client)getDAO(CLIENT_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + CLIENT_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Factor readFactor(long arg, Connection conn) throws ReadException {
      try {
         return (Factor)getDAO(FACTOR_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + FACTOR_DAO_NAME);
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
   private Project readProject(long arg, Connection conn) throws ReadException {
      try {
         return (Project)getDAO(PROJECT_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + PROJECT_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
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
   private Client readListClient(long arg, Connection conn) throws ReadException {
      try {
         return (Client)getDAO(CLIENT_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + CLIENT_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private Factor readListFactor(long arg, Connection conn) throws ReadException {
      try {
         return (Factor)getDAO(FACTOR_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + FACTOR_DAO_NAME);
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
   private Project readListProject(long arg, Connection conn) throws ReadException {
      try {
         return (Project)getDAO(PROJECT_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + PROJECT_DAO_NAME);
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
   protected DTO loadEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Estimate estimate = (Estimate)entity;
      try {
         estimate.setId(rs.getLong(1));
         estimate.setIdentifier(rs.getString(2));
         estimate.setClient(readClient(rs.getLong(3), conn));
         estimate.setProject(readProject(rs.getLong(4), conn));
         estimate.setEstimator(readResource(rs.getLong(5), conn));
         estimate.setPhase(readPhase(rs.getLong(6), conn));
         estimate.setTimeProducedMillisEpoch(rs.getLong(7));
         estimate.setRawUnitEstimate(rs.getInt(8));
         estimate.setAdjustedEstimate(rs.getInt(9));
         estimate.setHighEstimate(rs.getInt(10));
         estimate.setLowEstimate(rs.getInt(11));
         return estimate;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Estimate estimate = (Estimate)entity;
      try {
         estimate.setId(rs.getLong(1));
         estimate.setIdentifier(rs.getString(2));
         estimate.setClient(readListClient(rs.getLong(3), conn));
         estimate.setProject(readListProject(rs.getLong(4), conn));
         estimate.setEstimator(readListResource(rs.getLong(5), conn));
         estimate.setPhase(readListPhase(rs.getLong(6), conn));
         estimate.setTimeProducedMillisEpoch(rs.getLong(7));
         estimate.setRawUnitEstimate(rs.getInt(8));
         estimate.setAdjustedEstimate(rs.getInt(9));
         estimate.setHighEstimate(rs.getInt(10));
         estimate.setLowEstimate(rs.getInt(11));
         return estimate;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Estimate estimate = (Estimate)entity;
      try {
         estimate.setId(rs.getLong(1));
         estimate.setIdentifier(rs.getString(2));
         return estimate;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadEntityArchive(ADTO entity, Connection conn, ResultSet rs) throws ReadException {
      Estimate estimate = (Estimate)entity;
      try {
         estimate.setArchiveId(rs.getLong(1));
         estimate.setId(rs.getLong(2));
         estimate.setIdentifier(rs.getString(3));
         estimate.setClient(readClient(rs.getLong(4), conn));
         estimate.setProject(readProject(rs.getLong(5), conn));
         estimate.setEstimator(readResource(rs.getLong(6), conn));
         estimate.setPhase(readPhase(rs.getLong(7), conn));
         estimate.setTimeProducedMillisEpoch(rs.getLong(8));
         estimate.setRawUnitEstimate(rs.getInt(9));
         estimate.setAdjustedEstimate(rs.getInt(10));
         estimate.setHighEstimate(rs.getInt(11));
         estimate.setLowEstimate(rs.getInt(12));
         return estimate;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
}
