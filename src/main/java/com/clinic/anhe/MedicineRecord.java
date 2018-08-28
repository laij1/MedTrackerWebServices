package com.clinic.anhe;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="medicine_record")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt"}, 
allowGetters = true)
public class MedicineRecord {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private Integer rid;
	
	@Column(name="create_on", nullable=false)
    @Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date createAt;
	
	@Column(name="charge_on")
    @Temporal(TemporalType.DATE)
	private Date chargeAt;
	
	private Integer mid;
	
	@Column(name="medicine_name", nullable=false)
	private String medicineName;
	
	private Integer quantity;
	
	@Column(name="create_by",nullable=false)
	private Integer createBy;
	
	@Column(name="charge_by")
	private Integer chargeBy;
	
	private Integer subtotal;
	
	@Column(nullable=false)
	private String payment;
	
	private Integer pid;

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getChargeAt() {
		return chargeAt;
	}

	public void setChargeAt(Date chargeAt) {
		this.chargeAt = chargeAt;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicine_name) {
		this.medicineName = medicine_name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer create_by) {
		this.createBy = create_by;
	}

	public Integer getChargeBy() {
		return chargeBy;
	}

	public void setChargeBy(Integer charge_by) {
		this.chargeBy = charge_by;
	}

	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	


}
