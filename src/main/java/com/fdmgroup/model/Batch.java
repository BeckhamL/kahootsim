package com.fdmgroup.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FDM_BATCH")
@NamedQueries({
	@NamedQuery(name="Batch.countAll", query="SELECT COUNT(b) FROM Batch b")
})
public class Batch implements IStorable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_GEN")
	@SequenceGenerator(name = "BATCH_ID_GEN", sequenceName = "BATCH_ID_SEQ")
	private int id;
	// enums don't need a JPA annotation
	private Stream streamName;
	@OneToMany(mappedBy = "batch")
	@OrderBy
	private List<Trainee> trainees;

	public Batch() {
		super();
	}

	public Batch(List<Trainee> trainees, Stream streamName) {
		super();
		this.trainees = trainees;
		this.streamName = streamName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Batch other = (Batch) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addTrainee(Trainee trainee) {
		trainees.add(trainee);
	}
	
	public void removeTrainee(Trainee trainee) {
		trainees.remove(trainee);
	}
	
	public List<Trainee> getTrainees() {
		return trainees;
	}

	public void setTrainees(List<Trainee> trainees) {
		this.trainees = trainees;
	}

	public Stream getStreamName() {
		return streamName;
	}

	public void setStreamName(Stream streamName) {
		this.streamName = streamName;
	}

}
