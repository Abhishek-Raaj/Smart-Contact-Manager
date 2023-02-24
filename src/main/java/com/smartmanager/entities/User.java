package com.smartmanager.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {
//	 @Override
//	public String toString() {
//		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
//				+ ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + contacts
//				+ "]";
//	}
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
      private int id;
	@NotBlank(message="please enter your name")
	@Size(min=3, max=30,message="length should between 3-30")
      private String name;
      @Column(unique=true)
      @NotBlank(message="please enter your email")
      private String email;
     @NotBlank(message="please enter your password")
	private String password;
      private String role;
      private String enabled;
      private String imageUrl;
      @Column(length=500)
      @NotBlank(message="about section sholud not be empty")
      private String about;
      @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
      private List<Contact>  contacts=new ArrayList<>();
      
      
      
      public List<Contact> getContacts() {
  		return contacts;
  	}
  	public void setContacts(List<Contact> contacts) {
  		this.contacts = contacts;
  	}
      public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
}
