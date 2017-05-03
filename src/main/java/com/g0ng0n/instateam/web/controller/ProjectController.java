package com.g0ng0n.instateam.web.controller;

import com.g0ng0n.instateam.model.Collaborator;
import com.g0ng0n.instateam.model.Project;
import com.g0ng0n.instateam.model.Role;
import com.g0ng0n.instateam.service.ProjectService;
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
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @RequestMapping(value = "/projects")
    public String listProjects(ModelMap modelMap){

        List<Project> projects = projectService.findAll();

        modelMap.addAttribute("projects", projects);

        return "projects/index";
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/projects/{id}")
    public String getProjects(@PathVariable Long projectId, ModelMap modelMap){

        Project project = projectService.findById(projectId);

        modelMap.addAttribute("project", project);
        return "projects/details";
    }


    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.PUT)
    public String updateProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while updating the new Project", FlashMessage.Status.FAILURE));

            return String.format("redirect:/projects/%s/edit", project.getId());

        }

        // Update Collaborator if valid data was received
        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Project was Successfully Updated", FlashMessage.Status.SUCCESS));

        return "redirect:/projects";
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes ){

        if(result.hasErrors()){

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while adding the new Project", FlashMessage.Status.FAILURE));

            return String.format("redirect:/projects/%s/add", project.getId());

        }

        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Project was Successfully Added", FlashMessage.Status.SUCCESS));

        return "redirect:/projects";
    }

    @RequestMapping(value = "/collaborators/{collaboratorId}/delete", method = RequestMethod.DELETE)
    public String deleteProject(@PathVariable Long projectId, BindingResult result, RedirectAttributes redirectAttributes ){

        // Getting the role that we want to delete

        Project project = projectService.findById(projectId);

        projectService.delete(project);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Project Deleted", FlashMessage.Status.SUCCESS));


        return "redirect:/projects";
    }

    @RequestMapping("projects/add")
    public String formNewProject(Model model){

        if(!model.containsAttribute("project")){

            model.addAttribute("project", new Project());
        }

        model.addAttribute("action", "/projects");
        model.addAttribute("heading", " New Project");
        model.addAttribute("submit", "Add");

        return "project/form";

    }

    @RequestMapping("projects/{projectId}/edit")
    public String formEditProject(@PathVariable Long projectId, Model model){

        if (!model.containsAttribute("project")){
            model.addAttribute("project", projectService.findById(projectId));
        }

        model.addAttribute("action", String.format("/projects/%s", projectId));
        model.addAttribute("heading", " Edit Project");
        model.addAttribute("submit", "Update");

        return "project/form";
    }


    /// assign and unassing collaborators to projects


    @RequestMapping(value = "/projects/{projectId}/collaborators", method = RequestMethod.POST)
    public String assignCollaborator(@Valid Project project, Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes ){

        if(result.hasErrors()){

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while assigning the new Collaborator", FlashMessage.Status.FAILURE));

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

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error while adding the new collaborator", FlashMessage.Status.FAILURE));

            return String.format("redirect:/projects/%s/", project.getId());

        }

        // Assign the collaborator to the project
        project.getCollaborators().remove(collaborator);

        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("The Collaborator was Successfully unassigned to the Project", FlashMessage.Status.SUCCESS));

        return "redirect:/projects";
    }



}
