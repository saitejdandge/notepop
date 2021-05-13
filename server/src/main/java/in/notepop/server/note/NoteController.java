package in.notepop.server.note;

import in.notepop.server.ResponseWrapper;
import in.notepop.server.Status;
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
    public ResponseWrapper<Note> createNote(@RequestBody Note note) {
        return new ResponseWrapper<>(noteService.createNote(note), 1, Status.SUCCESS);
    }

    @RequestMapping("/getnotes")
    public ResponseWrapper<List<Note>> getNotes(@RequestHeader("accessToken") String accessToken) {
        return new ResponseWrapper<>(noteService.getNotesOfUser(accessToken), 1, Status.SUCCESS);
    }


}
