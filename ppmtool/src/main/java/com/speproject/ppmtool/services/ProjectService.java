package com.speproject.ppmtool.services;

import com.speproject.ppmtool.domain.Backlog;
import com.speproject.ppmtool.domain.Project;
import com.speproject.ppmtool.domain.User;
import com.speproject.ppmtool.exceptions.ProjectIdException;
import com.speproject.ppmtool.exceptions.ProjectNotFoundException;
import com.speproject.ppmtool.repositories.BacklogRepository;
import com.speproject.ppmtool.repositories.ProjectRepository;
import com.speproject.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveorUpdateProject(Project project , String username){

        // PROJECT.GETiD != NULL
        // FIND BY DB ID --> NULL

        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject !=null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }
        // logic for save or update
        // applying custom validation using our custom project ID exception way via try catch block
        try{
            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findProjectByidentifier(String projectId , String username){
        Project project =  projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' does not exists");
        }
        // checking its the users project only

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("project not found in your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByprojectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username){

        projectRepository.delete(findProjectByidentifier(projectId,username));
    }
}
