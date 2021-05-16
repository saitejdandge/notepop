package in.notepop.server.note;

import in.notepop.server.exceptions.BaseException;
import in.notepop.server.exceptions.ErrorCodes;
import in.notepop.server.requests.UpdateNoteRequest;
import in.notepop.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public Note createNote(Note note) {
        note.setCreatedDate(LocalDate.now());
        note.setUpdatedDate(LocalDate.now());
        return noteRepository.save(note);
    }

    public List<Note> getNotesOfUser(String userId) {
        Optional<List<Note>> notes = noteRepository.findNotesByUserId(userId);
        return notes.orElse(null);
    }

    //manages data through the object
    public Note updateNote(UpdateNoteRequest updateNoteRequest) {

        Note note = noteRepository
                .findById(updateNoteRequest.getNoteId())
                .orElseThrow(() -> new BaseException(ErrorCodes.NOTES_NOT_FOUND));

        if (userRepository.findByUsername(updateNoteRequest.getUserId()).isEmpty())
            throw new BaseException(ErrorCodes.NO_USER_FOUND);

        if (!note.getUserId().equals(updateNoteRequest.getUserId()))
            throw new BaseException(ErrorCodes.UNAUTHORIZED_ACCESS);

        if (updateNoteRequest.getNewValue() != null && updateNoteRequest.getNewValue().length() > 0 && !Objects.equals(updateNoteRequest.getNewValue(), note.getValue()))
            note.setValue(updateNoteRequest.getNewValue());

        note.setUpdatedDate(LocalDate.now());
        return noteRepository.save(note);
    }
}
