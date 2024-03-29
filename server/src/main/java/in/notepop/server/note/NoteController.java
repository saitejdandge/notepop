package in.notepop.server.note;

import in.notepop.server.requests.UpdateNoteRequest;
import in.notepop.server.responses.ResponseWrapper;
import in.notepop.server.shared.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "note")
public class NoteController extends BaseController {

    private final NoteService noteService;

    @Autowired
    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/createnote")
    public ResponseWrapper<Note> createNote(@RequestBody Note note) {
        note.setUserId(getLoggedInUserId());
        return ResponseWrapper.success(noteService.createNote(note));
    }

    @RequestMapping("/getnotes")
    public ResponseWrapper<List<Note>> getNotes() {
        return ResponseWrapper.success(noteService.getNotesOfUser(getLoggedInUserId()));
    }

    @RequestMapping("/updatenote")
    public ResponseWrapper<Note> updateNote(
            @RequestBody UpdateNoteRequest updateNoteRequest) {
        updateNoteRequest.setUserId(getLoggedInUserId());
        return ResponseWrapper.success(noteService.updateNote(updateNoteRequest));

    }
}
