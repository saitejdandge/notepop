package in.notepop.server.note;

import in.notepop.server.ResponseWrapper;
import in.notepop.server.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "note")
public class NoteController {

    private final NoteService noteService;
    private final JwtUtil jwtUtil;

    @Autowired
    NoteController(NoteService noteService, JwtUtil jwtUtil) {
        this.noteService = noteService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping("/createnote")
    public ResponseWrapper<Note> createNote(@RequestHeader("accessToken") String accessToken, @RequestBody Note note) {
        note.setUserId(accessToken);
        return ResponseWrapper.success(noteService.createNote(note));
    }

    @RequestMapping("/getnotes")
    public ResponseWrapper<List<Note>> getNotes(@RequestHeader("accessToken") String accessToken) {
        try {
            return ResponseWrapper.success(noteService.getNotesOfUser(accessToken));
        } catch (Exception e) {
            return ResponseWrapper.error(e.getLocalizedMessage());
        }
    }

    @RequestMapping("/updatenote")
    public ResponseWrapper<Note> updateNote(@RequestHeader("accessToken") String accessToken,
                                            @RequestBody UpdateNoteRequest updateNoteRequest) {
        try {
            updateNoteRequest.setUserId(accessToken);
            return ResponseWrapper.success(noteService.updateNote(updateNoteRequest));
        } catch (Exception e) {
            return ResponseWrapper.error(e.getLocalizedMessage());
        }
    }


}
