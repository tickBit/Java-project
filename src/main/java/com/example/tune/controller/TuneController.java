package com.example.tune.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tune.model.TunePost;
import com.example.tune.repo.*;
import com.example.tune.model.TuneComment;
import com.example.tune.service.*;


@Controller
public class TuneController {

	@Autowired
    private TunePostService tunePostService;

	@Autowired
	private TuneCommentService commentService;

	@Autowired
	private TunePostService postService;

	@Autowired
    private TunePostRepository postRepo;

	@RequestMapping({"/","/home"})
	public String home() {
		return "home";
	}

	// ************************************************************************

	/*
	@RequestMapping("/addtune")
	public String addTune() {
		return "addtune";
	}
	*/
	
	// ************************************************************************

	// controller method for getting all job posts
	@GetMapping("/musiclist")
	public String viewTunes(Model model) {

		List<TunePost> tunePosts = postService.getAllTunePosts();
		model.addAttribute("tunePosts", tunePosts);

		// Tarkista Java-palvelinpuolella (esimerkiksi Controllerissa tai Service-luokassa)
		for (TunePost post : tunePosts) {
    		System.out.println(post.getPostName());
    		for (TuneComment comment : post.getComments()) {
        		System.out.println(comment.getText());
    		}
		}

		return "musiclist";
	}

	// ************************************************************************

	// kommentit
	@PostMapping("/handleForm")
    public String handleCommentForm(@RequestParam("postID") Long postId,
                                    @ModelAttribute("tuneComment") TuneComment tuneComment) {

		System.out.println(tuneComment);

        // Kutsutaan palvelua, joka lisää uuden kommentin oikeaan postaukseen
        tunePostService.addCommentToPost(postId, tuneComment);

        // Palataan takaisin sivulle
        return "redirect:/musiclist";
    }
	
	@PostMapping("/addPost")
    public String addPost(@ModelAttribute("newTunePost") TunePost tunePost, @RequestParam("commentText") String commentText) {

        // Jos lomakkeella annettiin kommentti, tallenna myös se
        if (!commentText.isEmpty()) {
            TuneComment tuneComment = new TuneComment();
            tuneComment.setId(tunePost.getId());
            tuneComment.setText(commentText);
            commentService.saveTuneComment(tuneComment);
        
			tunePost.addComment(tuneComment);
		}
		// Tallenna uusi kappale
        postService.saveTunePost(tunePost);

		
        // Uudelleenohjaus takaisin pääsivulle
        return "redirect:/musiclist";
    }
}