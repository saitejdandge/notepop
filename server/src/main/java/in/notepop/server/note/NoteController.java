package in.notepop.server.note;

import in.notepop.server.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/createnote")
    public ResponseWrapper<Note> createNote(@RequestBody Note note) {
        note.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseWrapper.success(noteService.createNote(note));
    }

    @RequestMapping("/getnotes")
    public ResponseWrapper<List<Note>> getNotes() {
        try {
            return ResponseWrapper.success(noteService.getNotesOfUser(SecurityContextHolder.getContext().getAuthentication().getName()));
        } catch (Exception e) {
            return ResponseWrapper.error(e.getLocalizedMessage());
        }
    }

    @RequestMapping("/updatenote")
    public ResponseWrapper<Note> updateNote(
            @RequestBody UpdateNoteRequest updateNoteRequest) {
        try {
            updateNoteRequest.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseWrapper.success(noteService.updateNote(updateNoteRequest));
        } catch (Exception e) {
            return ResponseWrapper.error(e.getLocalizedMessage());
        }
    }


}
