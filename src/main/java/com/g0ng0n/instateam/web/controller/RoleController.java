package com.g0ng0n.instateam.web.controller;

import com.g0ng0n.instateam.model.Role;
import com.g0ng0n.instateam.service.RoleService;
import com.g0ng0n.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by g0ng0n.
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/roles")
    public String listRoles(ModelMap modelMap){

        List<Role> roles = roleService.findAll();

        modelMap.addAttribute("roles", roles);

        return "role/index";
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/roles/{id}")
    public String getRole(@PathVariable Long roleId, ModelMap modelMap){

        Role role = roleService.findById(roleId);

        modelMap.addAttribute("role", role);
        return "role/details";
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.PUT)
    public String updateRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){

            // Include Validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);

            // Add Role if invalid was received
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while updating the new Role", FlashMessage.Status.FAILURE));

            // Redirect back to the form
            return String.format("redirect:/roles/%s/edit", role.getId());

        }

        // Update Role if valid data was received
        roleService.save(role);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Role was Successfully Updated", FlashMessage.Status.SUCCESS));

        return "redirect:/roles";
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes ){

        if(result.hasErrors()){

            // Include Validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);

            // Add Role if invalid was received
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while adding the new Role", FlashMessage.Status.FAILURE));

            // Redirect back to the form
            return String.format("redirect:/roles/%s/add", role.getId());

        }

        // Add Role if valid data was received
        roleService.save(role);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Role was Successfully Added", FlashMessage.Status.SUCCESS));

        return "redirect:/roles";
    }


    @RequestMapping(value = "/roles/{roleId}/delete", method = RequestMethod.DELETE)
    public String deleteRole(@PathVariable Long roleId, BindingResult result, RedirectAttributes redirectAttributes ){

        // Getting the role that we want to delete

        Role role = roleService.findById(roleId);

        if(role.getCollaborator() != null){
            redirectAttributes.addFlashAttribute("flash",

                    new FlashMessage("Only Empty Role can be deleted", FlashMessage.Status.FAILURE));

            return String.format("redirect:/roles/%s/edit", roleId);
        }

        roleService.delete(role);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Role Deleted", FlashMessage.Status.SUCCESS));


        return "redirect:/roles";
    }



    @RequestMapping("roles/add")
    public String formNewRole(Model model){

        if(!model.containsAttribute("role")){

            model.addAttribute("role", new Role());
        }

        model.addAttribute("action", "/roles");
        model.addAttribute("heading", " New Role");
        model.addAttribute("submit", "Add");

        return "role/form";

    }

    @RequestMapping("roles/{roleId}/edit")
    public String formEditRole(@PathVariable Long roleId, Model model){

        if (!model.containsAttribute("role")){
            model.addAttribute("role", roleService.findById(roleId));
        }

        model.addAttribute("action", String.format("/roles/%s", roleId));
        model.addAttribute("heading", " Edit Role");
        model.addAttribute("submit", "Update");

        return "role/form";
    }
}
