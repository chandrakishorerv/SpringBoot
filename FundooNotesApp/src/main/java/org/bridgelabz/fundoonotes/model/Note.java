package org.bridgelabz.fundoonotes.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity


@Table(name="note")
public class Note {
	@Id
	@Column(name = "note_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int noteId;
	private String title;
	private String description;
	private String colour;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private boolean inTrash;
	private boolean isArchive;
	private boolean isPinned;
	private int userId;
	private LocalDateTime remainder;

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	public List<Label> listOfLabels;

	
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "label_note", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "label_id") })
	@JsonManagedReference
	@JsonIgnore
	private Set<Label> listOfLabels;

	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "user_note", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	@JsonIgnore
@JsonManagedReference
	private Set<User> listOfusers;


}
