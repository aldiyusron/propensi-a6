package mosing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mosing.model.UserAdmisiModel;
import mosing.service.UserAdmisiService;

@Controller
public class UserAdmisiController {

	@Autowired
	UserAdmisiService userDAO;
	
	@RequestMapping("/pendaftar/register")
	public String add()
	{
		return "form-registrasi1";
	}
	
	@RequestMapping("/pendaftar/register/next")
	public String addUser(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "email", required = false) String email)
	{
		UserAdmisiModel user = new UserAdmisiModel(username, password, email, "PEN");
		userDAO.addUser(user);	
		return "form-registrasi2";
	}
}