package general.comparator;

import java.util.Date;

public class Employee {
	private Integer empId;
	private Date hireDate;
	private String empName;
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public Date getHireDate() {
		return hireDate;
	}
	/**
	 * @param empId
	 * @param hireDate
	 * @param empName
	 */
	public Employee(Integer empId, Date hireDate, String empName) {
		super();
		this.empId = empId;
		this.hireDate = hireDate;
		this.empName = empName;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", hireDate=" + hireDate + ", empName=" + empName + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		result = prime * result + ((empName == null) ? 0 : empName.hashCode());
		result = prime * result + ((hireDate == null) ? 0 : hireDate.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee other = (Employee) obj;
		if (empId == null) {
			if (other.empId != null) {
				return false;
			}
		} else if (!empId.equals(other.empId)) {
			return false;
		}
		if (empName == null) {
			if (other.empName != null) {
				return false;
			}
		} else if (!empName.equals(other.empName)) {
			return false;
		}
		if (hireDate == null) {
			if (other.hireDate != null) {
				return false;
			}
		} else if (!hireDate.equals(other.hireDate)) {
			return false;
		}
		return true;
	}

}
