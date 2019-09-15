package com.fdmgroup.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "FDM_ACADEMY_ADMIN")
@NamedQueries({
	@NamedQuery(name="AcademyAdmin.findByEmail", query="SELECT a FROM AcademyAdmin a WHERE a.email LIKE ?1")
})
public class AcademyAdmin extends User implements IStorable {
	private LocalDate joinDate;

	public AcademyAdmin() {
		super();
	}

	public AcademyAdmin(String email, String firstName, String lastName, String password, String location,
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
