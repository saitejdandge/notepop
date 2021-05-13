package in.notepop.server.note;

import lombok.Data;

@Data
public class UpdateNoteRequest {
    private Long noteId;
    private Long userId;
    private String newValue;

}
