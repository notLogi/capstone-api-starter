package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("profile")
public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao){
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Profile getProfileById(Principal principal){
        try{
            User user = userDao.getByUserName(principal.getName());
            if(user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            int userId = user.getId();
            return profileDao.getByUserId(userId);
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping
    @PreAuthorize(("isAuthenticated()"))
    public Profile updateProfile(Principal principal, @RequestBody Profile profile){
        try{
            User user = userDao.getByUserName(principal.getName());
            if(user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            int userId = user.getId();
            profileDao.update(userId, profile);
            return profile;
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
