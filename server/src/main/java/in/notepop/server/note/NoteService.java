package in.notepop.server.note;

import in.notepop.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
        return noteRepository.findNotesByUserId(userId).get();
    }

    //manages data through the object
    public Note updateNote(UpdateNoteRequest updateNoteRequest) {

        Note note = noteRepository
                .findById(updateNoteRequest.getNoteId())
                .orElseThrow(() -> new IllegalStateException("Notes is not found"));

        System.out.println(updateNoteRequest.getUserId());
        if (userRepository.findByUniqueId(updateNoteRequest.getUserId()).isEmpty())
            throw new IllegalStateException("No User found");

        if (!note.getUserId().equals(updateNoteRequest.getUserId()))
            throw new IllegalStateException("Access Denied");

        if (updateNoteRequest.getNewValue() != null && updateNoteRequest.getNewValue().length() > 0 && !Objects.equals(updateNoteRequest.getNewValue(), note.getValue()))
            note.setValue(updateNoteRequest.getNewValue());

        note.setUpdatedDate(LocalDate.now());
        return noteRepository.save(note);
    }
}
