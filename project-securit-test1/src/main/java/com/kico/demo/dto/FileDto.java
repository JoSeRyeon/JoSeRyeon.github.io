package com.kico.demo.dto;


import com.kico.demo.entity.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
	
		private Long id;
	    private String orig_filename;
	    private String file_name;
	    private String file_path;
	    private String upload_time;
	    private String upload_user;
	    private String size;
	    
	    public File toEntity() {
	        File build = File.builder()
	                .id(id)
	                .orig_filename(orig_filename)
	                .file_name(file_name)
	                .file_path(file_path)
	                .upload_time(upload_time)
	                .upload_user(upload_user)
	                .size(size)
	                .build();
	        return build;
	    }
}
