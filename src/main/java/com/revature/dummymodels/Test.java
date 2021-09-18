package com.revature.dummymodels;

import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Getter;
import com.revature.annotations.Id;

@Entity(tableName="test_table")
public class Test {

	@Id(columnName="test_id")
	private int id;
	
	@Column(columnName="test_username")
	private String testUsername;
	
	@Column(columnName="test_password")
	private String testPassword;
	
	@Column(columnName="test_age")
	private int testAge;
	
	@Column(columnName="test_weight")
	private double testWeight;
	
	public Test() {
		super();
	}

	public Test(int id, String testUsername, String testPassword, int testAge, double testWeight) {
		super();
		this.id = id;
		this.testUsername = testUsername;
		this.testPassword = testPassword;
		this.testAge = testAge;
		this.testWeight = testWeight;
	}

	public Test(String testUsername, String testPassword, int testAge, double testWeight) {
		super();
		this.testUsername = testUsername;
		this.testPassword = testPassword;
		this.testAge = testAge;
		this.testWeight = testWeight;
	}
	
	@Getter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Getter
	public String getTestUsername() {
		return testUsername;
	}

	public void setTestUsername(String testUsername) {
		this.testUsername = testUsername;
	}

	@Getter
	public String getTestPassword() {
		return testPassword;
	}

	public void setTestPassword(String testPassword) {
		this.testPassword = testPassword;
	}

	@Getter
	public int getTestAge() {
		return testAge;
	}

	public void setTestAge(int testAge) {
		this.testAge = testAge;
	}

	@Getter
	public double getTestWeight() {
		return testWeight;
	}

	public void setTestWeight(double testWeight) {
		this.testWeight = testWeight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, testAge, testPassword, testUsername, testWeight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		return id == other.id && testAge == other.testAge && Objects.equals(testPassword, other.testPassword)
				&& Objects.equals(testUsername, other.testUsername)
				&& Double.doubleToLongBits(testWeight) == Double.doubleToLongBits(other.testWeight);
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", testUsername=" + testUsername + ", testPassword=" + testPassword + ", testAge="
				+ testAge + ", testWeight=" + testWeight + "]";
	}
	
	
	
}