package com.manosoft.app.controller;

import com.manosoft.app.entities.ClientInformation;
import com.manosoft.app.services.ClientInformationStorageService;
import com.manosoft.app.vo.ClientInformationRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientInformationController {

    @Autowired
    private ClientInformationStorageService service;

    @GetMapping
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome For Application", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody ClientInformationRequestVO clientInformationVO) throws FileNotFoundException {
        ClientInformation clientInformation = new ClientInformation();
        clientInformation.setClientID(clientInformationVO.getClientID());
        clientInformation.setClientName(clientInformationVO.getClientName());
        clientInformation.setFileName(clientInformationVO.getFileName());
        clientInformation.setFilePath(clientInformationVO.getFilePath());
        Optional<LocalDateTime> dateTime = Optional.ofNullable(clientInformationVO.getDataReceivedDate());
        clientInformation.setDataReceivedDate(dateTime.isPresent() ? dateTime.get(): LocalDateTime.now());
        String returnMsg = service.uploadClientInfo(clientInformation)
                ? "File Uploaded to "+clientInformation.getClientName()+" Source Bucket."
                : "Either File Already Exists or File not in correct format.";
        return new ResponseEntity<>(returnMsg, HttpStatus.OK);
    }

    @GetMapping("/split/{clientID}/{fileName}")
    public ResponseEntity<List<String>> performSplitting(@PathVariable String clientID,@PathVariable String fileName) throws FileNotFoundException {
        Optional<List<String>> fileList = Optional.ofNullable(service.performSplitting(clientID,fileName));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }

}
