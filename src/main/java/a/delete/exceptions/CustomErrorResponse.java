package a.delete.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {

    private LocalDateTime time;
    private String message;
    private String details;
}
