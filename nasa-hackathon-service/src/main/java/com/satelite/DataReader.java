package com.satelite;

import com.satelite.models.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataReader {
    
    
    public static List<Data> getData() {
        List<Data> dataList = new ArrayList<>();
        try {
            String fileLocation = "/Users/122386/Documents/FIRMS/viirs/USA_contiguous_and_Hawaii";
            //String fileLocation = "/Users/122386/Downloads/DL_FIRE_V1_81350";
            File folder = new File(fileLocation);
            
            String[] files = folder.list();
            for (String file : files) {
                BufferedReader br = new BufferedReader(new FileReader(fileLocation + "/" + file));
                
                String st;
                while ((st = br.readLine()) != null) {
                    // order is
                    // [0]latitude
                    // [1]longitude
                    // [2]bright_ti4
                    // [3]scan
                    // [4]track
                    // [5]acq_date
                    // [6]acq_time
                    // [7]satellite
                    // [8]confidence
                    // [9]version
                    // [10]bright_ti5
                    // [11]frp
                    // [12]daynight
                    if (st.contains("latitude")) { // skip first line
                        continue;
                    }
                    
                    String[] dataArr = st.split(",");
                    dataList.add(Data.builder()
                            .latitude(Double.parseDouble(dataArr[0]))
                            .longitude(Double.parseDouble(dataArr[1]))
                            .brightTI4(Double.parseDouble(dataArr[2]))
                            .scan(Double.parseDouble(dataArr[3]))
                            .track(Double.parseDouble(dataArr[4]))
                            .acquiredDateTime(dataArr[5] + "T" + dataArr[6])
                            .satellite(dataArr[7])
                            .confidence(dataArr[8])
                            .version(dataArr[9])
                            .brightTI5(Double.parseDouble(dataArr[10]))
                            .frp(Double.parseDouble(dataArr[11]))
                            .daynight(dataArr[12])
                            .build());
                }
            }
        } catch (Exception e) {
            log.error("error", e);
        }
        
        return dataList;
    }
}
