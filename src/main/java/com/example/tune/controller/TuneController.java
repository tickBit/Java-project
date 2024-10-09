package com.example.tune.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

	@GetMapping("/play")
	public ResponseEntity<UrlResource> playSong(@RequestParam("filePath") String filePath) {
    try {
        // Muodosta tiedoston polku ja tarkista sen olemassaolo
        Path path = Paths.get(filePath).normalize();
        UrlResource resource = new UrlResource(path.toUri());

        if (resource.exists()) {
            // Palautetaan tiedosto toistettavaksi selaimessa
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg")) // Määritä tiedostotyyppi MP3:ksi
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    } catch (MalformedURLException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
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
	
	// delete post
	@PostMapping("/delete")
	public String deletePost(@RequestParam("postID") Long postId) {
		
		tunePostService.deleteTunePost(postId);
		
		return "redirect:/musiclist";
	}
	
	// tune
	@PostMapping("/addPost")
    public String addPost(@ModelAttribute("newTunePost") TunePost tunePost, @RequestParam("commentText") String commentText, @RequestParam("file") MultipartFile file, Model model) {

		final String uploadDirectory = System.getProperty("user.dir") + "/uploads";

		if (file.isEmpty()) {
			model.addAttribute("error", "Please upload a file!");
			return "musiclist"; // Näytä uudelleen sama sivu virheilmoituksella
		}
	
		try {
			// Luo hakemisto, jos sitä ei ole olemassa
			File dir = new File(uploadDirectory);
			if (!dir.exists()) {
				dir.mkdirs();
			}
	
			// Tallenna tiedosto palvelimelle
			String filePath = uploadDirectory + "/" + file.getOriginalFilename();
			file.transferTo(new File(filePath));
	
			tunePost.setFilePath(filePath);

			if (!commentText.isEmpty()) {
				TuneComment tuneComment = new TuneComment();
				tuneComment.setId(tunePost.getId());
				tuneComment.setText(commentText);
				commentService.saveTuneComment(tuneComment);
			
				tunePost.addComment(tuneComment);
			}
			
			// save new tune
			postService.saveTunePost(tunePost);

			// Onnistumisen jälkeen, ohjataan käyttäjä takaisin 'musiclist'-sivulle
			return "redirect:/musiclist"; // Päivittää ja ohjaa käyttäjän takaisin musiikkilistasivulle
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("error", "File upload failed!");
			return "musiclist"; // Näytä uudelleen sama sivu virheilmoituksella
		}

    }
}