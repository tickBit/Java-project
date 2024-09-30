package com.example.tune.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tune.model.TunePost;
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

	@RequestMapping({"/","/home"})
	public String home() {
		return "home";
	}

	@GetMapping("/musiclist")
	public String viewTunes(Model model) {

		List<TunePost> tunePosts = postService.getAllTunePosts();
		model.addAttribute("tunePosts", tunePosts);

		return "musiclist";
	}

	// comments
	@PostMapping("/handleForm")
    public String handleCommentForm(@RequestParam("postID") Long postId,
                                    @ModelAttribute("tuneComment") TuneComment tuneComment) {

        // Kutsutaan palvelua, joka lisää uuden kommentin oikeaan postaukseen
        tunePostService.addCommentToPost(postId, tuneComment);

        return "redirect:/musiclist";
    }
	
	// tune
	@PostMapping("/addPost")
    public String addPost(@ModelAttribute("newTunePost") TunePost tunePost, @RequestParam("commentText") String commentText) {

        if (!commentText.isEmpty()) {
            TuneComment tuneComment = new TuneComment();
            tuneComment.setId(tunePost.getId());
            tuneComment.setText(commentText);
            commentService.saveTuneComment(tuneComment);
        
			tunePost.addComment(tuneComment);
		}
		// save new tune
        postService.saveTunePost(tunePost);

        return "redirect:/musiclist";
    }
}