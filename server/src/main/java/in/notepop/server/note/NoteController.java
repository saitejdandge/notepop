package in.notepop.server.note;

import in.notepop.server.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/createnote")
    public ResponseWrapper<Note> createNote(@RequestHeader("accessToken") String accessToken, @RequestBody Note note) {
        //todo map with jwt and userid
        note.setUserId(Long.parseLong(accessToken));
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
            updateNoteRequest.setUserId(Long.parseLong(accessToken));
            return ResponseWrapper.success(noteService.updateNote(updateNoteRequest));
        } catch (Exception e) {
            return ResponseWrapper.error(e.getLocalizedMessage());
        }
    }


}
