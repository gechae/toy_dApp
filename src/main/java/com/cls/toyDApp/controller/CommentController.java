package com.cls.toyDApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cls.toyDApp.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {
	
	private final CommentService commentService ;
	
	@RequestMapping(value="/setCmt", method = RequestMethod.POST)
	public void setComment() {
		
		commentService.setComment();
	}
	
	@RequestMapping(value="/getCmt", method = RequestMethod.POST)
	public void getComment() {
		
		commentService.getComment();
	}
}
