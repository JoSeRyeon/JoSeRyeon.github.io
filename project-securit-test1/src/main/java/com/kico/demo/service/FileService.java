package com.kico.demo.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kico.demo.dto.FileDto;
import com.kico.demo.entity.File;
import com.kico.demo.repository.FileRepository;


@Service
public class FileService {
	
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FileDto getFile(Long id) {
        File file = fileRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .id(id)
                .orig_filename(file.getOrig_filename())
                .file_name(file.getFile_name())
                .file_path(file.getFile_path())
                .build();
        return fileDto;
    }
}
