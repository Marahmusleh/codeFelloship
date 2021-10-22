package com.application2.codeFellowship.Controllers;

import com.application2.codeFellowship.Model.ApplicationUser;
import com.application2.codeFellowship.Repository.ApplicationUserRepository;
import com.application2.codeFellowship.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;


    @GetMapping("/allUsers")
    public String getUsers(@AuthenticationPrincipal ApplicationUser user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("Users", applicationUserRepository.findAll());
        ApplicationUser myApp = applicationUserRepository.findApplicationUserByUsername(user.getUsername());
        model.addAttribute("myfollowers", myApp.getFollowers());

        return "users";
    }

    @PostMapping("/addfollow")
    public RedirectView followUser(@AuthenticationPrincipal ApplicationUser user, @RequestParam int id) {
        ApplicationUser myApp = applicationUserRepository.findApplicationUserByUsername(user.getUsername());
        ApplicationUser followUser = applicationUserRepository.findById(id).get();
        myApp.getFollowers().add(followUser);
        applicationUserRepository.save(myApp);
        return new RedirectView("/feed");
    }

    @GetMapping("/feed")
    public String getUsersInfo(@AuthenticationPrincipal ApplicationUser user, Model model) {
        model.addAttribute("username", user.getUsername());
        ApplicationUser myApp = applicationUserRepository.findApplicationUserByUsername(user.getUsername());
        List<ApplicationUser> myfollowers = myApp.getFollowers();
        model.addAttribute("allfollowers",myfollowers);

        return "feed";
    }
}


