package org.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.bridgelabz.fundoonotes.model.Label;
import org.bridgelabz.fundoonotes.model.Note;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Repository

@Slf4j
public class LabelRepositoryImpl implements LabelRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Label labelmodel) {
		log.info(" entered into note repository for creation ");
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(labelmodel);
	}

	@Override
	@Transactional
	public void saveNoteLabels(Note notes) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(notes);
		
	}

	@Override
	@Transactional
	public List<Label> getData(int userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Label WHERE userId=:userId";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("userId", userId);
		log.info("retrivinig ibased o nn note id "+userId);
		return query.getResultList();
	}

	@Override
	public void updateData(Label labelobject) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(labelobject);
		
	}

	@Override
	public void deleteData(Label labelobject) {
		int labelid=labelobject.getLabelid();
		Session currentSession = entityManager.unwrap(Session.class);
		log.info("delete the label data"+labelobject.toString());
		String deletequery="delete Label WHERE labelid=:labelid";
		Query query=currentSession.createQuery(deletequery);
		query.setParameter("labelid", labelid);
		query.executeUpdate();
		
	}

	@Override
	public List<Integer> getNoteIds(int labelid) {
		
		
		return null;
	}

	@Override
	public List<Label> getLabelsBasedId(int labelid){
		Session currentSession = entityManager.unwrap(Session.class);
		String retrivequery="from Label WHERE labelid=:labelid";
		Query query=currentSession.createQuery(retrivequery);
		query.setParameter("labelid", labelid);
		log.info("retrivinig ibased on labelid "+labelid);
		return query.getResultList();
	}
}
