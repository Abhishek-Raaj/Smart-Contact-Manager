package com.smartmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.smartmanager.dao.Repository;
import com.smartmanager.dao.contactuspeople;
import com.smartmanager.entities.Contactus;
import com.smartmanager.entities.User;



@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private Repository repository;
	@Autowired
	private contactuspeople contactus;
    @GetMapping("/home")
	public String home(Model model)
	{
    	model.addAttribute("tittle", "Home page");
		return "home.html";
	}
    
    @GetMapping("/about")
	public String about(Model model)
	{
    	model.addAttribute("tittle", "About page");
		return "about.html";
	}
    @GetMapping("/contact")
   	public String contact(Model model)
   	{
       	model.addAttribute("tittle", "contact page");
   		return "ContactUs.html";
   	}
    
    @PostMapping("/contact")
   	public String contactsubmit(@ModelAttribute("contact") Contactus contact,Model model)
   	{
    	contactus.save(contact);
    	model.addAttribute("msg", "Thanks for Your Response");
   		return "ContactUs.html";
   	}
    @GetMapping("/login")
   	public String login(Model model)
   	{
   		return "login.html";
   	}
    
  
    @GetMapping("/signup")
   	public String signup(Model model)
   	{
    	model.addAttribute("tittle", "Registration page");
    	model.addAttribute("user", new User());
   		return "signup.html";
   	}
    
    @PostMapping("/do_register")
   	public String registeruser(@Validated  @ModelAttribute("user") User user,BindingResult binding, @RequestParam(name="agreement",defaultValue = "false")  boolean agreement,  Model model)
   	{

         System.out.println(binding);
//    	if(!agreement)
//    	{   
//    		session.setAttribute("message",new Msgstr("Please check the agreement ","alert-danger"));
//    	
//    		return "signup.html";
//    	} 
    	
    	if(binding.hasErrors())
    	{
    		model.addAttribute("user", user);
    		return "signup.html";
    	}
    	user.setRole("ROLE_USER");
    	user.setEnabled("true");
    	user.setPassword(encoder.encode(user.getPassword()));
    	
    	this.repository.save(user);
         model.addAttribute("user", new User());
//         session.setAttribute("message",new Msgstr("Sucessfully Registered","alert-success"));
   		return "signup.html";
    	
    	
   	}
}
