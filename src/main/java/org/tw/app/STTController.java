package org.tw.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tw.domain.SanitizedText;
import org.tw.domain.Transcription;
import org.tw.service.core.ProfanityService;
import org.tw.service.stt.STTService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/tw/stt/")
public class STTController {

    private static Logger logger = LoggerFactory.getLogger(STTController.class);

    private final STTService sttService;
    private final ProfanityService profanityService;

    @Autowired
    public STTController(final STTService sttService, final ProfanityService profanityService) {
        this.sttService = sttService;
        this.profanityService = profanityService;
    }


    @Operation(summary = "Get Text for given Audio chunk", description = "Create and Pull Text from given Audio chunk")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful")})
    @PostMapping(value = "/v0/convert", headers = "Accept=multipart/form-data", produces = "application/json", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public Transcription convertOffline(@RequestParam MultipartFile file, @RequestParam String language, @RequestParam boolean sanitize) {

        Transcription transcription =null;

        try {
            long start = System.currentTimeMillis();
             transcription = sttService.convertOffline(file.getBytes(), language,sanitize);
            long end = System.currentTimeMillis();
            logger.info("Total Time taken to convert and clean :: "+(end-start)/1000.0);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            transcription = new Transcription();
        }
        return transcription;
    }

    @Deprecated
    @Operation(summary = "Get Text for given Audio chunk", description = "Create and Pull Text from given Audio chunk")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful")})
    @PostMapping(value = "/v1/convert", headers = "Accept=multipart/form-data", produces = "application/json", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public Transcription convertOnline(@RequestParam MultipartFile file, @RequestParam String language,boolean sanitize) {

        try {
            return sttService.convertOnline(file.getBytes(), language,sanitize);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return new Transcription();
    }

    @Operation(summary = "Filter for cuss words", description = "filter cuss words")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful")})
    @PostMapping(value = "/v0/sanitize", headers = "Accept=Application/json", produces = "application/json", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public SanitizedText filter(@RequestBody String text) {

        return profanityService.censor(text);
    }


}
