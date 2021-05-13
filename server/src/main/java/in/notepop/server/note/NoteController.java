package in.notepop.server.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/createnote")
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @RequestMapping("/getnotes")
    public NotesResponse getNotes(@RequestHeader("accessToken") String accessToken) {
        return new NotesResponse(noteService.getNotesOfUser(accessToken), 1);
    }


}
