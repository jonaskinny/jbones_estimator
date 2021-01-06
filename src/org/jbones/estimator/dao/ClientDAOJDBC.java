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
import org.jbones.core.*;
import org.jbones.core.util.JDBCUtil;
import org.jbones.core.dao.*;
import org.jbones.datastore.*;

/**
	Class for accessing datasources to populate data transfer objects with data
*/
   public class ClientDAOJDBC extends ADAOJDBC {
   protected String getEntityName() {
      return "Client";
   }
   protected String getArchiveEntityName() {
      return "Client_Archive";
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
         Client client = (Client)arg;
         conn = getConnection();
         // transaction begin
         JDBCUtil.setAutoCommit(conn,false);
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "ENTITY_CLIENT A values(?)");
         entityId = getSequenceNextVal();
         ps.setLong(1,entityId); // used for PK in entity table
         result = ps.executeUpdate();
         if (result == 1) {
            ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "CLIENT A values(?,?,?)");
            ps.setLong(1,entityId);
            ps.setString(2,client.getIdentifier());
            ps.setString(3,client.getName());
            result = ps.executeUpdate();
         }
         return result == 1;
         // improve exception throwing here
      } catch (SequenceException sE) {
         throw new CreateException(sE.getMessage());
      } catch (ConnectionException cE) {
         throw new CreateException(cE.getMessage());
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new CreateException(sqlE.getMessage());
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
         Client client = (Client)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "CLIENT_ARCHIVE A values(?,?,?,?)");
         ps.setLong(1,getArchiveSequenceNextVal());
         ps.setLong(2,client.getId());
         ps.setString(3,client.getIdentifier());
         ps.setString(4,client.getName());
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SequenceException sE) {
         throw new CreateException(sE.getMessage());
      } catch (ConnectionException cE) {
         throw new CreateException(cE.getMessage());
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new CreateException(sqlE.getMessage());
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
         Client client = (Client)arg;
         ps = conn.prepareStatement("insert into " + getSchemaName() + "." + "CLIENT A values(?,?,?)");
         ps.setLong(1,client.getId());
         ps.setString(2,client.getIdentifier());
         ps.setString(3,client.getName());
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new CreateException(sqlE.getMessage());
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
         ps = conn.prepareStatement("select A.CLIENT_ID, A.IDENTIFIER, A.NAME from " + getSchemaName() + "." + "CLIENT A where A.CLIENT_ID = ? order by A.CLIENT_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntity(new Client(),conn,rs);
         }
         return null;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ReadException(sqlE.getMessage());
      } finally {
         JDBCUtil.release(null,rs,null,ps);
      }
   }
   public DTO readList(long arg, Connection conn) throws ReadException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      try {
         ps = conn.prepareStatement("select A.CLIENT_ID, A.IDENTIFIER from " + getSchemaName() + "." + "CLIENT A where A.CLIENT_ID = ? order by A.CLIENT_ID ASC");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadIdentifierEntity(new Client(),conn,rs);
         }
         return null;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ReadException(sqlE.getMessage());
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.CLIENT_ID, A.IDENTIFIER, A.NAME from " + getSchemaName() + "." + "CLIENT_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         rs = ps.executeQuery();
         if (rs.next()) {
            return loadEntityArchive(new Client(),conn,rs);
         }
         return null;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new ReadException(sqlE.getMessage());
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
         ps = conn.prepareStatement("select A.CLIENT_ID, A.IDENTIFIER, A.NAME from " + getSchemaName() + "." + "CLIENT A order by A.CLIENT_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Client>();
         while (rs.next()) {
            list.add(loadListEntity(new Client(), conn, rs));
         }
         return list;
         // improve exception throwing here
      } catch (ReadException cE) {
         throw new ListException(cE.getMessage());
      } catch (SQLException sqlE) {
         throw new ListException(sqlE.getMessage());
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
         ps = conn.prepareStatement("select A.ARCHIVE_ID, A.CLIENT_ID, A.IDENTIFIER, A.NAME from " + getSchemaName() + "." + "CLIENT_ARCHIVE A order by A.ARCHIVE_ID ASC");
         rs = ps.executeQuery();
         List list = new ArrayList<Client>();
         while (rs.next()) {
            list.add((Client)loadEntityArchive(new Client(),conn,rs));
         }
         return list;
         // improve exception throwing here
      } catch (ReadException re) {
         throw new ListException(re.getMessage());
      } catch (SQLException sqlE) {
         throw new ListException(sqlE.getMessage());
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
         Client client = (Client)arg;
         conn = getConnection();
         ps = conn.prepareStatement("update " + getSchemaName() + "." + "CLIENT A set A.IDENTIFIER = ?, A.NAME = ? where A.CLIENT_ID = ?");
         ps.setString(1,client.getIdentifier());
         ps.setString(2,client.getName());
         ps.setLong(3,client.getId());
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (ConnectionException cE) {
         throw new UpdateException(cE.getMessage());
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new UpdateException(sqlE.getMessage());
      } finally {
        JDBCUtil.release(conn,null,null,ps);
      }
   }
	/**
   * Archives all data based on the long used.
   * Subclasses should implement this function.
   */
	public boolean archive(long arg) throws ArchiveException, UniqueException {
      Client client;
      Connection conn = null;
      try {
         client = (Client) read(arg); // read based on the entity id
         if (null == client) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createArchive(client, conn)) {
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
      } catch (ReadException rE) {
         throw new ArchiveException(rE.getMessage());
      } catch (DeleteException dE) {
         throw new ArchiveException(dE.getMessage());
      } catch (CreateException cE) {
         throw new ArchiveException(cE.getMessage());
      } catch (ConnectionException coE) {
         throw new ArchiveException(coE.getMessage());
      } catch (SQLIntegrityConstraintViolationException sqlICVE) {
         throw new UniqueException(sqlICVE.getMessage());
      } catch (SQLException sqlE) {
         throw new ArchiveException(sqlE.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   /**
   * Unarchives all data based on the long used.
   * Subclasses should implement this function.
   */
	public boolean unarchive(long arg) throws CreateException, UniqueException {
      Client client = null;
      Connection conn = null;
      try {
         client = (Client) readArchive(arg); // read based on the archive id
         if (null == client) {
            return false;
         }
         // transaction begins
         conn = getConnection();
         conn.setAutoCommit(false);
         if (createFromArchive(client, conn)) {
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
      } catch (ReadException re) {
         throw new CreateException(re.getMessage());
      } catch (DeleteException de) {
         throw new CreateException(de.getMessage());
      } catch (ConnectionException ce) {
         throw new CreateException(ce.getMessage());
      }  finally {
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "CLIENT A where A.CLIENT_ID = ?");
         ps.setLong(1,arg);
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new DeleteException(sqlE.getMessage());
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
         ps = conn.prepareStatement("delete from " + getSchemaName() + "." + "CLIENT_ARCHIVE A where A.ARCHIVE_ID = ?");
         ps.setLong(1,arg);
         result = ps.executeUpdate();
         return result == 1;
         // improve exception throwing here
      } catch (SQLException sqlE) {
         throw new DeleteException(sqlE.getMessage());
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
      Client client = (Client)entity;
      try {
         client.setId(rs.getLong(1));
         client.setIdentifier(rs.getString(2));
         client.setName(rs.getString(3));
         return client;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadListEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Client client = (Client)entity;
      try {
         client.setId(rs.getLong(1));
         client.setIdentifier(rs.getString(2));
         client.setName(rs.getString(3));
         return client;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadIdentifierEntity(DTO entity, Connection conn, ResultSet rs) throws ReadException {
      Client client = (Client)entity;
      try {
         client.setId(rs.getLong(1));
         client.setIdentifier(rs.getString(2));
         return client;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
   protected DTO loadEntityArchive(ADTO entity, Connection conn, ResultSet rs) throws ReadException {
      Client client = (Client)entity;
      try {
         client.setArchiveId(rs.getLong(1));
         client.setId(rs.getLong(2));
         client.setIdentifier(rs.getString(3));
         client.setName(rs.getString(4));
         return client;
      }  catch (SQLException sqlE) {
            throw new ReadException(sqlE.getMessage());
      }
   }
}
