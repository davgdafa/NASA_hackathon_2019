package com.satelite;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.satelite.models.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("satelite")
public class DataController {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    
    @GetMapping(value = "/health")
    public String healthCheck() {
        return "dale pipo!";
    }
    
    @GetMapping(value = "/read")
    public JsonNode readData(@RequestParam(required = false) String fromDateTime,
                             @RequestParam(required = false) String toDateTime,
                             @RequestParam Double nwLatitude,
                             @RequestParam Double nwLongitude,
                             @RequestParam Double seLatitude,
                             @RequestParam Double seLongitude) {
        ArrayNode arrayNode = OBJECT_MAPPER.createArrayNode();
        List<Data> dataList = DataReader.getData();
        
        Stream<Data> stream = dataList
                .stream()
                .filter(data -> data.getLatitude() < nwLatitude
                        && data.getLatitude() > seLatitude
                        && data.getLongitude() < seLongitude
                        && data.getLongitude() > nwLongitude);
        
        if (fromDateTime != null || toDateTime != null) {
            
            if (fromDateTime != null) {
                stream = stream.filter(data -> {
                    LocalDateTime converted = LocalDateTime.parse(fromDateTime, FORMATTER);
                    LocalDateTime convertedData = LocalDateTime.parse(data.getAcquiredDateTime(), FORMATTER);
                    return (converted.isBefore(convertedData) || converted.isEqual(convertedData));
                });
            }
            
            if (toDateTime != null) {
                stream = stream.filter(data -> {
                    LocalDateTime converted = LocalDateTime.parse(toDateTime, FORMATTER);
                    LocalDateTime convertedData = LocalDateTime.parse(data.getAcquiredDateTime(), FORMATTER);
                    return (converted.isAfter(convertedData) || converted.isEqual(convertedData));
                });
            }
        }
        
        List<Data> filteredList = stream.collect(Collectors.toList());
        
        filteredList.forEach(data -> {
            arrayNode.add(OBJECT_MAPPER.valueToTree(data));
        });
        
        // call SmsNotification.sendMessage("phone number", "message"); as you see fit.
        return arrayNode;
    }
    
    @GetMapping("/test")
    public String test() {
        System.out.println(ClassWithoutNameKt
                .isThereFireInThisRegion("/Path/to/data/DL_FIRE_M6_81349", 25.6643619F, -81.3646979F));
        return "";
    }
}
