package org.tw.app;

import org.tw.domain.Transcription;
import org.tw.service.STTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/tw/stt/")
public class STTController {

    @Autowired
    private STTService sttService;

    public STTController(STTService sttService) {
        this.sttService = sttService;
    }


    @Operation(summary = "Get Text for given Audio chunk", description = "Create and Pull Text from given Audio chunk")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful")})
    @PostMapping( value ="/v0/convert" ,headers = "Accept=multipart/form-data",produces = "application/json",  consumes ={MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public String convertOffline(@RequestParam  MultipartFile file, @RequestParam String language) {

        try {
            return sttService.convertOffline(file.getBytes(),language);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Operation(summary = "Get Text for given Audio chunk", description = "Create and Pull Text from given Audio chunk")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful")})
    @PostMapping( value ="/v1/convert" ,headers = "Accept=multipart/form-data",produces = "application/json",  consumes ={MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public String convertOnline(@RequestParam  MultipartFile file, @RequestParam String language) {

        try {
            return sttService.convertOnline(file.getBytes(),language);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
