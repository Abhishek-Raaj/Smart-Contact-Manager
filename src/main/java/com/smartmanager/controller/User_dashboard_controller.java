package com.smartmanager.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartmanager.dao.Contact_repo;
import com.smartmanager.dao.Repository;
import com.smartmanager.entities.Contact;
import com.smartmanager.entities.Message;
import com.smartmanager.entities.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class User_dashboard_controller {

	@Autowired
	private Repository repository;
	@Autowired
	private Contact_repo contact_repo;
	@GetMapping("/dashboard")
	public String user_dashboard(Model model,Principal principal,HttpSession session)
	{
		session.removeAttribute("message");
		String username=principal.getName();
		User user=repository.findByEmail(username);
		model.addAttribute("user", user);
		return "/normal/user_dashboard.html";
	}
	
	@GetMapping("/contact")
	public String contact(HttpSession session)
	{
		session.removeAttribute("message");
		return "normal/contact.html";
	}
	@PostMapping("/contact-submit")
	public String contactsubmit(@ModelAttribute("contact") Contact contact,@RequestParam("photo") MultipartFile file,Principal principal,HttpSession session)
	{
		
		session.removeAttribute("message");
		File savefile;
		try {
			
		if(file.isEmpty())
			contact.setImage("user.jpg");
		else
		{ 
			savefile = new ClassPathResource("/static/img").getFile();
			Path path=Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			System.out.println(path);
			Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
			contact.setImage(file.getOriginalFilename());
		}
		String username=principal.getName();
		User user=repository.findByEmail(username);
		user.getContacts().add(contact);
		
		contact.setUser(user);
		repository.save(user);
		System.out.println(contact);
		session.setAttribute("message", new Message("Contact Added Sucessfully","success"));
          } catch (Exception e) {
			
		
			session.setAttribute("message", "image size should be less than 1MB");
			return "normal/error.html";
		 } 
		return "normal/contact.html";
	}
	
	@GetMapping("/all_contacts/{page}")
	public String view_contact(Model model,@PathVariable("page") Integer page,Principal principal,HttpSession session)
	{
		session.removeAttribute("message");
		String username=principal.getName();
		User user=repository.findByEmail(username);
		Pageable pageable=PageRequest.of(page, 4);
		Page<Contact> contact=contact_repo.findByUser_id(user.getId(),pageable);
		if(page>contact.getTotalPages()-1)
		{
			model.addAttribute("message", " page not found");
			return "normal/error.html";
		}
		System.out.println(contact.get());
		model.addAttribute("user_c", contact.get());
		model.addAttribute("current_page", page);
		model.addAttribute("total_pages", contact.getTotalPages());
		
		return "normal/all_contacts.html";
	}
	@GetMapping("/{id}")
	public String single_contact(@PathVariable("id") Integer id,Principal principal,Model model,HttpSession session)
	{
		session.removeAttribute("message");
		User user=repository.findByEmail(principal.getName());
		Optional<Contact> contact=contact_repo.findById(id);
		if(contact.isEmpty() || user.getId()!=contact.get().getUser().getId())
		{
			model.addAttribute("message", "page not found");
			return "normal/error.html";
		}
		model.addAttribute("single", contact.get());
		return "normal/single_contact.html";
	}
	
	@PostMapping("/delete")
	public String delete_form(@RequestParam("hidden") Integer id,HttpSession session)
	{
		session.removeAttribute("message");
		Optional<Contact> contact=contact_repo.findById(id);
		contact_repo.delete(contact.get());
		File delfile;
		try {
			delfile = new ClassPathResource("/static/img").getFile();
			File f1= new File(delfile,contact.get().getImage());
			  f1.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	      
		
		System.out.println(id);
		return "redirect:/user/all_contacts/0";
	}
	
	@PostMapping("/update")
	public String update_form(@RequestParam("hidden") Integer id,Model model,HttpSession session)
	{
		session.removeAttribute("message");
		Optional<Contact> contact=contact_repo.findById(id);
		model.addAttribute("contact", contact.get());
		System.out.println(contact.get());
		return "normal/update_form";
	}
	@PostMapping("/update-submit")
	public String update_submit(@ModelAttribute("contact") Contact contact,@RequestParam("photo") MultipartFile file,@RequestParam("image") String image,@RequestParam("id") Integer id,Principal principal,HttpSession session)
	{
		session.removeAttribute("message");
		if(file.isEmpty())
			contact.setImage(image);
		else
		{
			try {
				File delfile = new ClassPathResource("/static/img").getFile();
			      File f1= new File(delfile, image);
				  f1.delete();
				 File savefile = new ClassPathResource("/static/img").getFile();
					Path path=Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		String username=principal.getName();
		User user=repository.findByEmail(username);
		contact.setUser(user);
		contact.setCid(id);
		System.out.println(contact);
		contact_repo.save(contact);
		return "redirect:/user/all_contacts/0";
	}
}

