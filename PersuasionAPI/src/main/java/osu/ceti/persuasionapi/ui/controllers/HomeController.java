package osu.ceti.persuasionapi.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping(method=RequestMethod.GET)
	public String renderHomePage(ModelMap model) {
		return "home";
	}
	
	@RequestMapping(value="/help", method=RequestMethod.GET)
	public String renderHelpPage(ModelMap model) {
		return "help";
	}

}
