package com.g0ng0n.instateam.web.controller;

import com.g0ng0n.instateam.model.Collaborator;
import com.g0ng0n.instateam.model.Project;
import com.g0ng0n.instateam.model.Role;
import com.g0ng0n.instateam.service.CollaboratorService;
import com.g0ng0n.instateam.service.ProjectService;
import com.g0ng0n.instateam.service.RoleService;
import com.g0ng0n.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by g0ng0n.
 */
@Controller
public class CollaboratorController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/projects/{projectId}/collaborators", method = RequestMethod.POST)
    public String assignCollaborator(@Valid Project project, Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes ){

        if(result.hasErrors()){

            // Include Validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);

            // Add Role if invalid was received
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while assigning the new Collaborator", FlashMessage.Status.FAILURE));

            // Redirect back to the form /projects/{projectId}/
            return String.format("redirect:/projects/%s/", project.getId());

        }

        // Assign the collaborator to the project
        project.getCollaborators().add(collaborator);

        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Collaborator was Successfully Assigned to the Project", FlashMessage.Status.SUCCESS));

        return "redirect:/projects";
    }

    @RequestMapping("/projects/{projectId}/collaborators/add")
    public String formAssignCollaborator(Model model){

        if(!model.containsAttribute("collaborator")){

            model.addAttribute("collaborator", new Collaborator());
        }

        model.addAttribute("action", "/collaborator");
        model.addAttribute("heading", "Assigning new Collaborator");
        model.addAttribute("submit", "Assign");

        return "/projects/{projectId}/collaborators/form";

    }

    @RequestMapping(value = "/projects/{projectId}/collaborators", method = RequestMethod.DELETE)
    public String unassignCollaborator(@Valid Project project, Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes ){

        if(result.hasErrors()){

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while adding the new Role", FlashMessage.Status.FAILURE));

            return String.format("redirect:/projects/%s/", project.getId());

        }

        // Assign the collaborator to the project
        project.getCollaborators().remove(collaborator);

        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Collaborator was Successfully unassigned to the Project", FlashMessage.Status.SUCCESS));

        return "redirect:/projects";
    }

}
