package com.speproject.ppmtool.services;
import com.speproject.ppmtool.domain.Backlog;
import com.speproject.ppmtool.domain.Project;
import com.speproject.ppmtool.domain.ProjectTask;
import com.speproject.ppmtool.exceptions.ProjectNotFoundException;
import com.speproject.ppmtool.repositories.BacklogRepository;
import com.speproject.ppmtool.repositories.ProjectRepository;
import com.speproject.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;


    public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask , String username){
        //Exceptions project not found
        // if no project exists then it should not create the project task
        // it should be like /* Project Not Found : project not fouond */


            // PTS to be added to a specific project , project != null , BL exists
            Backlog backlog = projectService.findProjectByidentifier(projectIdentifier,username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);
            // we will set BL to PTask
            projectTask.setBacklog(backlog);
            // we want our Project sequence like IDPRO-1   IDPRO-2 .....   100  101   design decision to make eg.
            Integer BacklogSequence = backlog.getPTSequence();
            // update the BL sequence once the task is created
            BacklogSequence++;
            // update it in database
            backlog.setPTSequence(BacklogSequence);

            // add sequence toproject task
            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);




            // INITIAL status when status is = null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            // fix the bug first check null then check priority
            // set INITIAL priority when priority == null else each task LOW,MEDIUM,HIGH
            if(projectTask.getPriority() == null || projectTask.getPriority() == 0){
                projectTask.setPriority(3); // LOW PRIORITY
            }

            return projectTaskRepository.save(projectTask);




    }

    public Iterable<ProjectTask> findBacklogById(String id , String username){

            projectService.findProjectByidentifier(id,username);

            return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id , String username){
        // Edge cases
        // make sure we are searching in right backlog like we search in that project which belongs to me
        projectService.findProjectByidentifier(backlog_id,username);
        // that task we are searching it should exist
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found");
        }

        //make sure the backloog/project id in the path corresponds to the right project

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exists in project with id: '"+backlog_id+"'");

        }

        return projectTask;
    }


    // update project Task
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask , String backlog_id , String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id , String pt_id , String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
        // deletion didnt work initially so we did this
//        Backlog backlog = projectTask.getBacklog();
//        List<ProjectTask> pts = backlog.getProjectTasks();
//        pts.remove(projectTask);
//        backlogRepository.save(backlog);

        projectTaskRepository.delete(projectTask);

    }
    // find existing project task

    // replace it with updated task


    // save update

}
