package com.application2.codeFellowship.Controllers;

import com.application2.codeFellowship.Model.ApplicationUser;
import com.application2.codeFellowship.Model.Post;
import com.application2.codeFellowship.Repository.ApplicationUserRepository;
import com.application2.codeFellowship.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.util.List;

@Controller
public class MainController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "SignUp";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "SignIn";
    }

    @GetMapping("/")
    public String getHomePage(Model model, @AuthenticationPrincipal ApplicationUser applicationUser) {
        if(applicationUser != null) {
            ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(applicationUser.getUsername());
            model.addAttribute("userId", user.getId());
            model.addAttribute("username", user.getUsername());
        }
        return"home";
    }

    @PostMapping("/signup")
    public RedirectView signUp(@RequestParam String bio, @RequestParam int dateOfBirth,
                               @RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam String password, @RequestParam String username){
        ApplicationUser applicationUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName,dateOfBirth,bio);
        applicationUser = applicationUserRepository.save(applicationUser);
        return new RedirectView("/login");
    }
    @GetMapping("/users")
    public String showUser(@RequestParam int id, Model model){
        model.addAttribute("info", applicationUserRepository.findById(id).get());
        return "Info";
    }
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal ApplicationUser user , Model model){
        if (user != null) {
            ApplicationUser userPost = applicationUserRepository.findApplicationUserByUsername(user.getUsername());
            model.addAttribute("username", user.getUsername());
            List<Post> posts = postRepository.findAllByUser(userPost);
            model.addAttribute("posts" , posts );
        }
        return "post";
    }

    @PostMapping("/post")
    public RedirectView addPost(@AuthenticationPrincipal ApplicationUser user, @RequestParam String body){
        ApplicationUser userInfo = applicationUserRepository.findApplicationUserByUsername((user.getUsername()));
        Post addNewPost = new Post(body, userInfo);
        postRepository.save(addNewPost);
        return new RedirectView("/profile");
    }
}
