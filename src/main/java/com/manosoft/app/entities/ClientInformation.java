package com.manosoft.app.entities;

import lombok.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientInformation {

    private String clientID;
    private String clientName;
    private String filePath;
    private String fileName;
    private LocalDateTime dataReceivedDate;
}
