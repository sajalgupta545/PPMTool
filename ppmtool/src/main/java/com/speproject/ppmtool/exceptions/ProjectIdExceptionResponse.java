package com.speproject.ppmtool.exceptions;

// making to handle if project identifire already exists and i cant re add it.
public class ProjectIdExceptionResponse {
    public String projectIdentifier;

    public ProjectIdExceptionResponse(String projectIdentifier){
        this.projectIdentifier = projectIdentifier;

    }
}
