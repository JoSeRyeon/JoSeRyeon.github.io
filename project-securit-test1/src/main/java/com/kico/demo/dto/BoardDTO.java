package com.kico.demo.dto;


import com.kico.demo.entity.Board;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BoardDTO {

	private Long id;
	private String email;
	private String title;
	private String content;
	private String createdate;
	private Long fileid;
	private String filename;
	

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .email(email)
                .title(title)
                .content(content)
                .fileid(fileid)
                .filename(filename)
                .build();
        return build;
    }
    
    @Builder
    public BoardDTO(Long id, String email, String title, String content, String createdate, Long fileid, String filename) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.content = content;
        this.createdate = createdate;
        this.fileid = fileid;
        this.filename = filename;
    }
    
    
}
