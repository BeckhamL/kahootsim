package com.fdmgroup.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "FDM_TRAINER")
@NamedQueries({
	@NamedQuery(name="Trainer.findByEmail", query="SELECT t FROM Trainer t WHERE t.email LIKE ?1"),
	@NamedQuery(name="Trainer.countAll", query="SELECT COUNT(t) FROM Trainer t")
})
public class Trainer extends User implements IStorable {
	private LocalDate joinDate;

	public Trainer() {
		super();
	}

	public Trainer(String email, String firstName, String lastName, String password, String location,
			String designation, List<Quiz> quizzes, LocalDate joinDate) {
		super(email, firstName, lastName, password, location, designation, quizzes);
		this.joinDate = joinDate;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

}
