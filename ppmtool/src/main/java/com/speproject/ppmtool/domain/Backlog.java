package com.speproject.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0;
    private String projectIdentifier;

    // oneToone with project (each project has one backlog , each backlog belongs to one unique project)

    @OneToOne(fetch = FetchType.EAGER )          // adding referesh here to delete using on liner
    @JoinColumn(name = "project_id",nullable = false)
    @JsonIgnore
    private Project project;                  // take care of inifinite recursoion


    //oneTomany with project task ( one backlog can have on eor more project task ,
    // and a project task only belong to one backlog


    // initialize the any data structure when you use else it will give error
    @OneToMany(cascade = CascadeType.REFRESH , fetch = FetchType.EAGER ,mappedBy = "backlog" , orphanRemoval = true)
    private List<ProjectTask> projectTasks = new ArrayList<>();

    public Backlog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPTSequence() {
        return PTSequence;
    }

    public void setPTSequence(Integer PTSequence) {
        this.PTSequence = PTSequence;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectTask> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<ProjectTask> projectTasks) {
        this.projectTasks = projectTasks;
    }
}