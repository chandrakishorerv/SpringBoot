package org.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.bridgelabz.fundoonotes.model.Note;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository

@Slf4j
public class NoteRepositoryImpl implements NoteRepository{
	@Autowired
	private EntityManager entityManager;
	@Override
	public void createNote(Note notemodel) {
		log.info(" entered into note repository for creation "); 
		Session currentSession = entityManager.unwrap(Session.class);
		int id =(int) currentSession.save(notemodel);
	}

	@Override
	public Note getNoteById(Note modifynote) {
		log.info(" entered into note repository  for deletion");
		Session currentSession = entityManager.unwrap(Session.class);
		return (Note) currentSession.createQuery("from Note where note_id="+modifynote.getNoteId()).uniqueResult();
	}

	@Override
	public List<Note> getAllNotes(int userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Note WHERE userId=:userId and inTrash=false and isArchive=false ";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("userId", userId);
		//and inTrash=false and isArchive=false
		return query.getResultList();
	}
	
	@Override
	public List<Note> getNote(int noteid) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Note WHERE noteId=:noteId";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("noteId", noteid);
		log.info("retrivinig ibased o nn note id "+noteid);
		return query.getResultList();
	}

	@Override
	public void updateNote( Note note) {
//		log.info(" entered into note repository  for updation");
//		System.out.println(note.getColour()+" colour of the note");
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(note);
	}

	@Override
	public void pinNote(int noteId, boolean ispinned) {
		Session currentSession = entityManager.unwrap(Session.class);
		String updatequery="update Note set isPinned=:isPinned where noteId=:noteId";
		Query query=currentSession.createQuery(updatequery);
		query.setParameter("noteId", noteId);
		query.setParameter("isPinned", ispinned);
		System.out.println("entereed into repo     "+ispinned);
	query.executeUpdate();
		
	}

	@Override
	public List<Note> getNoteId(String title) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Note WHERE title=:title";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("title", title);
		return query.getResultList();
	}

	@Override
	public List<Note> getTashNotes(int userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Note WHERE userId=:userId and inTrash=true";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("userId", userId);
		
		return query.getResultList();
	}

	@Override
	public void unTashNotes(int noteId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String inTrash=" ";
		String updatequery="update Note set inTrash =:inTrash where noteId=:noteId";
		Query query=currentSession.createQuery(updatequery);
		query.setParameter("noteId", noteId);
		query.setParameter("inTrash", inTrash);
		query.executeUpdate();
	}

	@Override
	public void deleteNote(Note notemodel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Note> getArchiveNotes(int userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Note WHERE userId=:userId and isArchive=true";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("userId", userId);
		
		return query.getResultList();
	}

	@Override
	public List<Note> getNotesBasedId(int noteId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Note WHERE noteId=:noteId ";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("noteId", noteId);
		
		return query.getResultList();

	}

	@Override
	public List<Note> getAllTheNote(int userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Note WHERE userId=:userId ";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("userId", userId);
		//and inTrash=false and isArchive=false
		return query.getResultList();
	}
}
