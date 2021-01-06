package org.jbones.estimator.model.dto;

import org.jbones.core.Copyable;
import org.jbones.core.ObjectCopyException;
import org.jbones.core.util.ClassName;

import java.util.List;

/**
   Class that represents a Estimate DTO.
*/
public class Estimate extends BaseADTO implements Copyable {
    
   private Client client;
   private Project project;
   private Resource estimator;
   private Phase phase;
   private List<Factor> factorList;
   private List<Effort> effortList;
   private long timeProducedMillisEpoch;
   private int rawUnitEstimate;
   private int adjustedEstimate;
   private int highEstimate;
   private int lowEstimate;

   public Client getClient() {
      return client;
   }
   public void setClient(Client client) {
      this.client = client;
   }
   public Project getProject() {
      return project;
   }
   public void setProject(Project project) {
      this.project = project;
   }
   public Resource getEstimator() {
      return estimator;
   }
   public void setEstimator(Resource estimator) {
      this.estimator = estimator;
   }
   public Phase getPhase() {
      return phase;
   }
   public void setPhase(Phase phase) {
      this.phase = phase;
   }
   public List<Factor> getFactorList() {
      return factorList;
   }
   public void setFactorList(List<Factor> factorList) {
      this.factorList = factorList;
   }
   public List<Effort> getEffortList() {
      return effortList;
   }
   public void setEffortList(List<Effort> effortList) {
      this.effortList = effortList;
   }
   public long getTimeProducedMillisEpoch() {
      return timeProducedMillisEpoch;
   }
   public void setTimeProducedMillisEpoch(long timeProducedMillisEpoch) {
      this.timeProducedMillisEpoch = timeProducedMillisEpoch;
   }
   public int getRawUnitEstimate() {
      return rawUnitEstimate;
   }
   public void setRawUnitEstimate(int rawUnitEstimate) {
      this.rawUnitEstimate = rawUnitEstimate;
   }
   public int getAdjustedEstimate() {
      return adjustedEstimate;
   }
   public void setAdjustedEstimate(int adjustedEstimate) {
      this.adjustedEstimate = adjustedEstimate;
   }
   public int getHighEstimate() {
      return highEstimate;
   }
   public void setHighEstimate(int highEstimate) {
      this.highEstimate = highEstimate;
   }
   public int getLowEstimate() {
      return lowEstimate;
   }
   public void setLowEstimate(int lowEstimate) {
      this.lowEstimate = lowEstimate;
   }


   /**
    copy may call clone if no dependency objects exist, or may perform
    a deep copy if dependency objects do exist.
   */
   public Object copy(Object o) throws ObjectCopyException, CloneNotSupportedException, NoSuchMethodException {
      if (o instanceof Client) {
         // this class has no dependency objects so just use clone
         return this.clone();
      }
      throw new ObjectCopyException("Object " + o + " of Class " + ClassName.name(o.getClass()) + " cannot be copied as a Estimate");
   }
}
