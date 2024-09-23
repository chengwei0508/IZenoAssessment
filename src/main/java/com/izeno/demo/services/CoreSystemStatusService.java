/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.services;

import org.springframework.stereotype.Service;

/**
 *
 * @author cw
 */
@Service
public class CoreSystemStatusService {
    private volatile boolean coreSystemUp = true;

    public boolean isCoreSystemUp() {
        return coreSystemUp;
    }

    public void setCoreSystemStatus(boolean isUp) {
        this.coreSystemUp = isUp;
    }
}