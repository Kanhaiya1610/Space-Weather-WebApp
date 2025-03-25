package com.spaceweather.controller;

import com.spaceweather.service.SpaceWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private SpaceWeatherService weatherService;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            model.addAttribute("riskLevel", weatherService.calculateCurrentRiskLevel());
            model.addAttribute("solarFlares", weatherService.getRecentSolarFlares());
            model.addAttribute("radiationStorms", weatherService.getRecentRadiationStorms());
            model.addAttribute("alerts", weatherService.getActiveAlerts());
            return "dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/alerts")
    public String alerts(Model model) {
        model.addAttribute("alerts", weatherService.getActiveAlerts());
        return "alerts";
    }

    // Add error page mapping
    @GetMapping("/error")
    public String error() {
        return "error";
    }
} 