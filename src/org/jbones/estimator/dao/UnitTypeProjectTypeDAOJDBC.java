package org.jbones.estimator.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import org.jbones.estimator.model.dto.UnitType;
import org.jbones.estimator.model.dto.ProjectType;
import org.jbones.estimator.model.dto.UnitTypeProjectType;
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class UnitTypeProjectTypeDAOJDBC extends BridgeDAOJDBC {
   protected static final String UNIT_TYPE_DAO_NAME = "UnitType";
   protected static final String PROJECT_TYPE_DAO_NAME = "ProjectType";
   protected String getEntityName() {
      return "Unit_Type_Project_Type";
   }
   /**
   * Simple create function for a persisted object based on the DTO.
   */
   public boolean create(DTO arg) throws CreateException, UniqueException {
      Connection conn = null;
      PreparedStatement ps = null;
      int result = -1;
      try {
         UnitTypeProjectType unitTypeProjectType = (UnitTypeProjectType)arg;
         conn = getConnection();
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "UNIT_TYPE_PROJECT_TYPE A values(?,?,?)");
         ps.setLong(1,getSequenceNextVal()); // used for PK in entity table
         ps.setLong(2,unitTypeProjectType.getUnitType().getId());
         ps.setLong(3,unitTypeProjectType.getProjectType().getId());
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
         ps = conn.prepareStatement("select A.UNIT_TYPE_ID, A.PROJECT_TYPE_ID from " + getSchemaName() + "." + "UNIT_TYPE_PROJECT_TYPE A where A.UNIT_TYPE_PROJECT_TYPE_ID = ? order by A.UNIT_TYPE_PROJECT_TYPE_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new UnitTypeProjectType(),conn,rs);
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
         ps = conn.prepareStatement("select A.UNIT_TYPE_ID, A.PROJECT_TYPE_ID from " + getSchemaName() + "." + "UNIT_TYPE_PROJECT_TYPE A where A.UNIT_TYPE_PROJECT_TYPE_ID = ? order by A.UNIT_TYPE_PROJECT_TYPE_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadListEntity(new UnitTypeProjectType(),conn,rs);
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
      throw new ListException(new IFException().getMessage());
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
         ps = conn.prepareStatement("select A.UNIT_TYPE_PROJECT_TYPE_ID, A.UNIT_TYPE_ID, A.PROJECT_TYPE_ID from " + getSchemaName() + "." + "UNIT_TYPE_PROJECT_TYPE A order by A.UNIT_TYPE_PROJECT_TYPE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<UnitTypeProjectType>();
         while (rs.next()) {
            list.add(loadEntity(new UnitTypeProjectType(),conn,rs));
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
         UnitTypeProjectType unitTypeProjectType = (UnitTypeProjectType)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "UNIT_TYPE_PROJECT_TYPE A set A.UNIT_TYPE_ID = ?, A.PROJECT_TYPE_ID = ? where A.UNIT_TYPE_PROJECT_TYPE_ID = ?");
         ps.setLong(1,unitTypeProjectType.getUnitType().getId());
         ps.setLong(2,unitTypeProjectType.getProjectType().getId());
         ps.setLong(3,unitTypeProjectType.getId());
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "UNIT_TYPE_PROJECT_TYPE A where A.UNIT_TYPE_PROJECT_TYPE_ID = ?");
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
   private ProjectType readProjectType(long arg, Connection conn) throws ReadException {
      try {
         return (ProjectType)getDAO(PROJECT_TYPE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + PROJECT_TYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private UnitType readUnitType(long arg, Connection conn) throws ReadException {
      try {
         return (UnitType)getDAO(UNIT_TYPE_DAO_NAME).read(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + UNIT_TYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private ProjectType readListProjectType(long arg, Connection conn) throws ReadException {
      try {
         return (ProjectType)getDAO(PROJECT_TYPE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + PROJECT_TYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   private UnitType readListUnitType(long arg, Connection conn) throws ReadException {
      try {
         return (UnitType)getDAO(UNIT_TYPE_DAO_NAME).readList(arg,conn);
      } catch (Exception e) {
         Log.getLog(Log.ERR).log("problem looking up DAO:" + UNIT_TYPE_DAO_NAME);
         Log.getLog(Log.ERR).log(e.getMessage());
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         throw new ReadException(CoreException.getStackTrace(e));
      }
   }
   protected DTO loadEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      UnitTypeProjectType unitTypeProjectType = (UnitTypeProjectType)entity;
      try {
            unitTypeProjectType.setId(rs.getLong(1));
            unitTypeProjectType.setUnitType(readUnitType(rs.getLong(2),conn));
            unitTypeProjectType.setProjectType(readProjectType(rs.getLong(3),conn));
            return unitTypeProjectType;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      UnitTypeProjectType unitTypeProjectType = (UnitTypeProjectType)entity;
      try {
            unitTypeProjectType.setId(rs.getLong(1));
            unitTypeProjectType.setUnitType(readListUnitType(rs.getLong(2),conn));
            unitTypeProjectType.setProjectType(readListProjectType(rs.getLong(3),conn));
            return unitTypeProjectType;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      UnitTypeProjectType unitTypeProjectType = (UnitTypeProjectType)entity;
      try {
            unitTypeProjectType.setId(rs.getLong(1));
            unitTypeProjectType.setUnitType(readListUnitType(rs.getLong(2),conn));
            unitTypeProjectType.setProjectType(readListProjectType(rs.getLong(3),conn));
            return unitTypeProjectType;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
}
