package com.example.Interface.Impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.example.Interface.FileRequired;

public class FileRequiredValidator implements ConstraintValidator<FileRequired, MultipartFile> {
    @Override
    public void initialize(FileRequired constraint) {}

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile != null
                && !multipartFile.getOriginalFilename().isEmpty();
    }
}