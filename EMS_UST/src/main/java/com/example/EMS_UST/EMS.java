package com.example.EMS_UST;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component

@Entity 
// This tells the JPA that this class contains all the details of Tables

@Table (name="INDUST")

public class EMS 
{
	@Id
	@Column(name="EMPID")
	private String eID;
	
	@Column (name="EMPNAME")
	private String eNAME;
	
	@Column (name="SAL")
	private String eSAL;
	
	
	public String geteID() 
	{
		return eID;
	}
	public void seteID(String eID) 
	{
		this.eID = eID;
	}
	public String geteNAME() 
	{
		return eNAME;
	}
	public void seteNAME(String eNAME) 
	{
		this.eNAME = eNAME;
	}
	public String geteSAL() 
	{
		return eSAL;
	}
	public void seteSAL(String eSAL) 
	{
		this.eSAL = eSAL;
	}
	
	

}
