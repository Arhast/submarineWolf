package com.submarine.submarineWolf.submarineWolf.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class WolfInfo {
    @Getter
    @Setter
    private String wolfName;
    @Getter
    @Setter
    private String path;

//    public WolfInfo(String wolfName) {
//        this.wolfName = wolfName;
//    }
//
//    public WolfInfo() {
//    }
}
