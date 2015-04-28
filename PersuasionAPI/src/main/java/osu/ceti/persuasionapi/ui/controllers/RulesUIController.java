package osu.ceti.persuasionapi.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/config")
public class RulesUIController {
	
	@RequestMapping("/rules")
	public String configureRules(ModelMap model) {
		return "rules";
	}
}