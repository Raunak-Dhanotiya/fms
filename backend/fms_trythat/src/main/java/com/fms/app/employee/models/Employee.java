package com.fms.app.employee.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fms.app.divisionDepartment.models.Department;
import com.fms.app.divisionDepartment.models.Division;
import com.fms.app.divisionDepartment.models.SubDepartment;
import com.fms.app.documents.models.Document;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Fl;
import com.fms.app.spaceManagement.models.Rm;
@Entity
@Table(name = "em")
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3728319301683428721L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "em_id")
	private Integer emId;

	@Column(name = "em_code", nullable = false)
	private String emCode;
	
	@Column(name = "initials")
	private String initials;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "maiden_name")
	private String maidenName;

	@Column(name = "alias_name")
	private String aliasName;

	@Column(name = "email")
	private String email;

	@Column(name = "em_status")
	private String emStatus;

	@Column(name = "id_number")
	private String idNumber;

	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@Column(name = "gender")
	private String gender;

	@Column(name = "phone_home")
	private String phoneHome;

	@Column(name = "phone_work")
	private String phoneWork;

	@Column(name = "phone_personal")
	private String phonePersonal;

	@Column(name = "fax_num")
	private String faxNum;

	@Column(name = "alt_contact_name")
	private String altContactName;

	@Column(name = "alt_contact_phone")
	private String altContactPhone;

	@Column(name = "comp_name")
	private String compName;

	@Column(name = "date_join")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateJoin;

	@Column(name = "date_leave")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateLeave;
	
	@Column(name="bl_id")
	private Integer blId;
	
	@Column(name="fl_id")
	private Integer flId;
	
	@Column(name="rm_id")
	private Integer rmId;
	
	@Column(name = "line_mngr")
	private Integer lineMngr;

	@Column(name = "em_photo", nullable = true)
	private Integer emPhoto;

	@Column(name = "work_type", nullable = false, length = 2)
	private String workType;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_id", referencedColumnName = "bl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Bl bl;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fl_id", referencedColumnName = "fl_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Fl fl;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rm_id", referencedColumnName = "rm_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Rm rm;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "em_photo", insertable = false, updatable = false)
	private Document doc;
	
	@Column(name="div_id")
	private Integer divId;
	
	@Column(name="dep_id")
	private Integer depId;
	
	@Column(name="emstd_id")
	private Integer emstdId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emstd_id", referencedColumnName = "emstd_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private EmStd emStd;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "div_id", referencedColumnName = "div_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Division division;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dep_id", referencedColumnName = "dep_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Department department;
	
	@Column(name="sub_dep_id")
	private Integer subDepId;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_dep_id", referencedColumnName = "sub_dep_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private SubDepartment subDepartment;
	/**
	 * @param emId
	 * @param emCode
	 * @param initials
	 * @param firstName
	 * @param lastName
	 * @param maidenName
	 * @param aliasName
	 * @param email
	 * @param emStd
	 * @param emStatus
	 * @param idNumber
	 * @param birthDate
	 * @param gender
	 * @param phoneHome
	 * @param phoneWork
	 * @param phonePersonal
	 * @param faxNum
	 * @param altContactName
	 * @param altContactPhone
	 * @param compName
	 * @param dateJoin
	 * @param dateLeave
	 * @param blId
	 * @param flId
	 * @param rmId
	 * @param costCentreId
	 * @param lineMngr
	 * @param emPhoto
	 * @param workType
	 * @param bl
	 * @param fl
	 * @param rm
	 * @param doc
	 * @param divId
	 * @param depId
	 * @param emstdId
	 */
	public Employee(Integer emId, String emCode, String initials, String firstName, String lastName, String maidenName,
			String aliasName, String email, String emStatus, String idNumber, Date birthDate,
			String gender, String phoneHome, String phoneWork, String phonePersonal, String faxNum,
			String altContactName, String altContactPhone, String compName, Date dateJoin, Date dateLeave, Integer blId,
			Integer flId, Integer rmId,  Integer lineMngr, Integer emPhoto, String workType, Bl bl, Fl fl, Rm rm,
			Document doc, Integer divId, Integer depId, Integer emstdId,EmStd emStd,Division division,Department department,
			Integer subDepId,SubDepartment subDepartment) {
		super();
		this.emId = emId;
		this.emCode = emCode;
		this.initials = initials;
		this.firstName = firstName;
		this.lastName = lastName;
		this.maidenName = maidenName;
		this.aliasName = aliasName;
		this.email = email;
		this.emStatus = emStatus;
		this.idNumber = idNumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phoneHome = phoneHome;
		this.phoneWork = phoneWork;
		this.phonePersonal = phonePersonal;
		this.faxNum = faxNum;
		this.altContactName = altContactName;
		this.altContactPhone = altContactPhone;
		this.compName = compName;
		this.dateJoin = dateJoin;
		this.dateLeave = dateLeave;
		this.blId = blId;
		this.flId = flId;
		this.rmId = rmId;
		this.lineMngr = lineMngr;
		this.emPhoto = emPhoto;
		this.workType = workType;
		this.bl = bl;
		this.fl = fl;
		this.rm = rm;
		this.doc = doc;
		this.divId = divId;
		this.depId = depId;
		this.emstdId = emstdId;
		this.emStd = emStd;
		this.division = division;
		this.department = department;
		this.subDepId = subDepId;
		this.subDepartment = subDepartment;
	}

	/**
	 * 
	 */
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the emId
	 */
	public Integer getEmId() {
		return emId;
	}

	/**
	 * @param emId the emId to set
	 */
	public void setEmId(Integer emId) {
		this.emId = emId;
	}

	/**
	 * @return the emCode
	 */
	public String getEmCode() {
		return emCode;
	}

	/**
	 * @param emCode the emCode to set
	 */
	public void setEmCode(String emCode) {
		this.emCode = emCode;
	}

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @param initials the initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the maidenName
	 */
	public String getMaidenName() {
		return maidenName;
	}

	/**
	 * @param maidenName the maidenName to set
	 */
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	/**
	 * @return the aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * @param aliasName the aliasName to set
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emStatus
	 */
	public String getEmStatus() {
		return emStatus;
	}

	/**
	 * @param emStatus the emStatus to set
	 */
	public void setEmStatus(String emStatus) {
		this.emStatus = emStatus;
	}

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the phoneHome
	 */
	public String getPhoneHome() {
		return phoneHome;
	}

	/**
	 * @param phoneHome the phoneHome to set
	 */
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	/**
	 * @return the phoneWork
	 */
	public String getPhoneWork() {
		return phoneWork;
	}

	/**
	 * @param phoneWork the phoneWork to set
	 */
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	/**
	 * @return the phonePersonal
	 */
	public String getPhonePersonal() {
		return phonePersonal;
	}

	/**
	 * @param phonePersonal the phonePersonal to set
	 */
	public void setPhonePersonal(String phonePersonal) {
		this.phonePersonal = phonePersonal;
	}

	/**
	 * @return the faxNum
	 */
	public String getFaxNum() {
		return faxNum;
	}

	/**
	 * @param faxNum the faxNum to set
	 */
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	/**
	 * @return the altContactName
	 */
	public String getAltContactName() {
		return altContactName;
	}

	/**
	 * @param altContactName the altContactName to set
	 */
	public void setAltContactName(String altContactName) {
		this.altContactName = altContactName;
	}

	/**
	 * @return the altContactPhone
	 */
	public String getAltContactPhone() {
		return altContactPhone;
	}

	/**
	 * @param altContactPhone the altContactPhone to set
	 */
	public void setAltContactPhone(String altContactPhone) {
		this.altContactPhone = altContactPhone;
	}

	/**
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @return the dateJoin
	 */
	public Date getDateJoin() {
		return dateJoin;
	}

	/**
	 * @param dateJoin the dateJoin to set
	 */
	public void setDateJoin(Date dateJoin) {
		this.dateJoin = dateJoin;
	}

	/**
	 * @return the dateLeave
	 */
	public Date getDateLeave() {
		return dateLeave;
	}

	/**
	 * @param dateLeave the dateLeave to set
	 */
	public void setDateLeave(Date dateLeave) {
		this.dateLeave = dateLeave;
	}

	/**
	 * @return the blId
	 */
	public Integer getBlId() {
		return blId;
	}

	/**
	 * @param blId the blId to set
	 */
	public void setBlId(Integer blId) {
		this.blId = blId;
	}

	/**
	 * @return the flId
	 */
	public Integer getFlId() {
		return flId;
	}

	/**
	 * @param flId the flId to set
	 */
	public void setFlId(Integer flId) {
		this.flId = flId;
	}

	/**
	 * @return the rmId
	 */
	public Integer getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	/**
	 * @return the lineMngr
	 */
	public Integer getLineMngr() {
		return lineMngr;
	}

	/**
	 * @param lineMngr the lineMngr to set
	 */
	public void setLineMngr(Integer lineMngr) {
		this.lineMngr = lineMngr;
	}

	/**
	 * @return the emPhoto
	 */
	public Integer getEmPhoto() {
		return emPhoto;
	}

	/**
	 * @param emPhoto the emPhoto to set
	 */
	public void setEmPhoto(Integer emPhoto) {
		this.emPhoto = emPhoto;
	}

	/**
	 * @return the workType
	 */
	public String getWorkType() {
		return workType;
	}

	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(String workType) {
		this.workType = workType;
	}

	/**
	 * @return the bl
	 */
	public Bl getBl() {
		return bl;
	}

	/**
	 * @param bl the bl to set
	 */
	public void setBl(Bl bl) {
		this.bl = bl;
	}

	/**
	 * @return the fl
	 */
	public Fl getFl() {
		return fl;
	}

	/**
	 * @param fl the fl to set
	 */
	public void setFl(Fl fl) {
		this.fl = fl;
	}

	/**
	 * @return the rm
	 */
	public Rm getRm() {
		return rm;
	}

	/**
	 * @param rm the rm to set
	 */
	public void setRm(Rm rm) {
		this.rm = rm;
	}

	/**
	 * @return the doc
	 */
	public Document getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	/**
	 * @return the divId
	 */
	public Integer getDivId() {
		return divId;
	}

	/**
	 * @param divId the divId to set
	 */
	public void setDivId(Integer divId) {
		this.divId = divId;
	}

	/**
	 * @return the depId
	 */
	public Integer getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	/**
	 * @return the emstdId
	 */
	public Integer getEmstdId() {
		return emstdId;
	}

	/**
	 * @param emstdId the emstdId to set
	 */
	public void setEmstdId(Integer emstdId) {
		this.emstdId = emstdId;
	}

	public EmStd getEmStd() {
		return emStd;
	}

	public void setEmStd(EmStd emStd) {
		this.emStd = emStd;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getSubDepId() {
		return subDepId;
	}

	public void setSubDepId(Integer subDepId) {
		this.subDepId = subDepId;
	}

	public SubDepartment getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(SubDepartment subDepartment) {
		this.subDepartment = subDepartment;
	}
	
}
