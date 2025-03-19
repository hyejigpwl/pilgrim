package com.lec.review.vo;

public class ReviewFilesVO {
    private int id;          // 파일 ID (AUTO_INCREMENT)
    private int review_bno;     // 게시글 번호 (FK)
    private String fileName; // 저장된 파일명

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getReview_bno() {
        return review_bno;
    }
    public void setReview_bno(int review_bno) {
        this.review_bno = review_bno;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ReviewFilesVO [id=" + id + ", review_bno=" + review_bno + ", fileName=" + fileName + "]";
    }
}
