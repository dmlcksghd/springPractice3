package com.shinhan.myapp.board;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
	Long board_no;
	String title;
	String content;
	String writer;
	Date regdate;
	String pic;
}
