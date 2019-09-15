package com.fdmgroup.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "FDM_TRAINEE")
@NamedQueries({
	@NamedQuery(name="Trainee.findByEmail", query="SELECT t FROM Trainee t WHERE t.email LIKE ?1"),
	@NamedQuery(name="Trainee.countAll", query="SELECT COUNT(t) FROM Trainee t")
})
public class Trainee extends User implements IStorable {
	private LocalDate startDate;
	private LocalDate endDate;
	// enums do not need a JPA annotation
	private TraineeStatus status;
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private Batch batch;
	@OneToMany(mappedBy = "trainee")
	@OrderBy
	private List<Result> results;

	public Trainee() {
		super();
	}

	public Trainee(String email, String firstName, String lastName, String password, String location,
			String designation, List<Quiz> quizzes, TraineeStatus status, LocalDate startDate, LocalDate endDate,
			Batch batch, List<Result> results) {
		super(email, firstName, lastName, password, location, designation, quizzes);
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.batch = batch;
		this.results = results;
	}
	
	public void addResult(Result result) {
		results.add(result);
	}
	
	public void removeResult(Result result) {
		results.remove(result);
	}

	public TraineeStatus getStatus() {
		return status;
	}

	public void setStatus(TraineeStatus status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

}
