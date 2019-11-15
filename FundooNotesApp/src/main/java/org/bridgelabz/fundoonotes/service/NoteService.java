package org.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Set;

import org.bridgelabz.fundoonotes.dto.NoteDTO;
import org.bridgelabz.fundoonotes.model.Note;
import org.bridgelabz.fundoonotes.model.User;

public interface NoteService {
boolean noteCreation(NoteDTO note,String token);

List<Note> getAllNotes(String token);

boolean noteUpdation(int noteId, Note modifynote, String token);

List<Note> getAllNotes(int noteId);

void pinNotes(int noteId, boolean ispinned);

List<Note> sortNoteByDate(String token);

List<Note> sortNoteByName(String token);

void deleteNote(Note modifynote);

List<Note> searchNotes(String token, String keyword, String field);

List<Note> getTashNotes(String token);

void unTashNotes(Note modifynote);

void archiveNote(Note modifynote);

List<Note> getArchiveNotes(String token);

void updateReminder(Note modifynote);

List<Note> getReminderNotes(String token);

List<Note> getAlltheNotes(String token);

void CollabarateNote(int noteId, String token);

Set<User> getCollabaratedUsers(int noteId);

void deleteNoteOnLabel(int noteId, int labelid);

void deleteCollabaratedNote(int noteId, String email);

}
