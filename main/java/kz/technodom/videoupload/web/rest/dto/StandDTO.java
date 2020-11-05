package kz.technodom.videoupload.web.rest.dto;

import kz.technodom.videoupload.domain.Stand;
import kz.technodom.videoupload.domain.VideoFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StandDTO {
    private Long id;
    private String ipAddress;
    private String storeName;
    private String city;
    private Set<VideoFileDTO> videoFileDTOS;

    public static StandDTO fromStand(Stand stand){
        StandDTO standDTO = new StandDTO();
        standDTO.id = stand.getId();
        standDTO.ipAddress = stand.getIpAddress();
        standDTO.storeName = stand.getStoreName();
        standDTO.city = stand.getCity();

        return standDTO;
    }

    public static Stand toStand(StandDTO standDTO){
        Stand stand = new Stand();
        return stand;
    }

    public static Set<StandDTO> fromStands(Set<Stand> stands) {
        return stands.stream().map(u -> fromStand(u)).collect(Collectors.toSet());
    }
}
