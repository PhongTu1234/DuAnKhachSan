package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {

	@RequestMapping("blog")
	public String blog() {
		return "admin/Blog/index";
	}
	
	
}