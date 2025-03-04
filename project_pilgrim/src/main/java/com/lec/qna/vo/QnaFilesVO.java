package com.lec.qna.vo;

public class QnaFilesVO {
    private int id;          // 파일 ID (AUTO_INCREMENT)
    private int qna_bno;     // 게시글 번호 (FK)
    private String fileName; // 저장된 파일명

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getQna_bno() {
        return qna_bno;
    }
    public void setQna_bno(int qna_bno) {
        this.qna_bno = qna_bno;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "QnaFilesVO [id=" + id + ", qna_bno=" + qna_bno + ", fileName=" + fileName + "]";
    }
}
