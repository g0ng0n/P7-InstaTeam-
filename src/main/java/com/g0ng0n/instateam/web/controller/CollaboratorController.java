package com.g0ng0n.instateam.web.controller;

import com.g0ng0n.instateam.model.Collaborator;
import com.g0ng0n.instateam.service.CollaboratorService;
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
public class CollaboratorController {


    @Autowired
    private CollaboratorService collaboratorService;

    @RequestMapping(value = "/collaborators")
    public String listCollaborators(ModelMap modelMap){

        List<Collaborator> collaborators = collaboratorService.findAll();

        modelMap.addAttribute("collaborators", collaborators);

        return "collaborators/index";
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/collaborators/{id}")
    public String getCollaborator(@PathVariable Long collaboratorId, ModelMap modelMap){

        Collaborator collaborator = collaboratorService.findById(collaboratorId);

        modelMap.addAttribute("collaborator", collaborator);
        return "collaborator/details";
    }


    @RequestMapping(value = "/collaborators/{collaboratorId}", method = RequestMethod.PUT)
    public String updateCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while updating the new Collaborator", FlashMessage.Status.FAILURE));

            return String.format("redirect:/collaborators/%s/edit", collaborator.getId());

        }

        // Update Collaborator if valid data was received
        collaboratorService.save(collaborator);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Collaborator was Successfully Updated", FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";
    }

    @RequestMapping(value = "/collaborators", method = RequestMethod.POST)
    public String addCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes ){

        if(result.hasErrors()){

            // Include Validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while adding the new Collaborator", FlashMessage.Status.FAILURE));

            // Redirect back to the form
            return String.format("redirect:/collaborators/%s/add", collaborator.getId());

        }

        collaboratorService.save(collaborator);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Collaborator was Successfully Added", FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";
    }

    @RequestMapping(value = "/collaborators/{collaboratorId}/delete", method = RequestMethod.DELETE)
    public String deleteCollaborator(@PathVariable Long collaboratorId, BindingResult result, RedirectAttributes redirectAttributes ){

        // Getting the collaboratorI that we want to delete

        Collaborator collaborator = collaboratorService.findById(collaboratorId);

        collaboratorService.delete(collaborator);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Collaborator Deleted", FlashMessage.Status.SUCCESS));


        return "redirect:/collaborators";
    }

    @RequestMapping("collaborators/add")
    public String formNewCollaborator(Model model){

        if(!model.containsAttribute("collaborator")){

            model.addAttribute("collaborator", new Collaborator());
        }

        model.addAttribute("action", "/collaborators");
        model.addAttribute("heading", " New Collaborator");
        model.addAttribute("submit", "Add");

        return "collaborator/form";

    }

    @RequestMapping("collaborators/{collaboratorId}/edit")
    public String formEditCollaborator(@PathVariable Long collaboratorId, Model model){

        if (!model.containsAttribute("collaborator")){
            model.addAttribute("collaborator", collaboratorService.findById(collaboratorId));
        }

        model.addAttribute("action", String.format("/collaborators/%s", collaboratorId));
        model.addAttribute("heading", " Edit Collaborator");
        model.addAttribute("submit", "Update");

        return "collaborator/form";
    }


}
