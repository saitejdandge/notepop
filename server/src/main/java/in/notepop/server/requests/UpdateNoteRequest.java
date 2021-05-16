package in.notepop.server.requests;

import lombok.Data;

@Data
public class UpdateNoteRequest {
    private Long noteId;
    private String userId;
    private String newValue;

}
