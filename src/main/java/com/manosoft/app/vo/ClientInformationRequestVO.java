package com.manosoft.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformationRequestVO {

    private String clientID;
    private String clientName;
    private String filePath;
    private String fileName;
    private LocalDateTime dataReceivedDate;

}
