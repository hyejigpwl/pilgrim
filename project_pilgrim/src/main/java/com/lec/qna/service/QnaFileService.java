package com.lec.qna.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.lec.db.JDBCUtility;
import com.lec.qna.vo.QnaFilesVO;

public class QnaFileService {

	private static QnaFileService instance = new QnaFileService();

	public static QnaFileService getInstance() {
		return instance;
	}

	// ✅ 1. 파일 리스트를 한 번에 저장
	public void saveFiles(int bno, List<String> files) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO qna_files (qna_bno, file_name) VALUES (?, ?)";

		try {
			conn = JDBCUtility.getConnection();
			pstmt = conn.prepareStatement(sql);

			for (String fileName : files) {
				pstmt.setInt(1, bno);
				pstmt.setString(2, fileName);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtility.close(conn, pstmt, null);
		}
	}

	// ✅ 2. 특정 게시글(bno)의 파일 목록 조회
	public List<QnaFilesVO> getFilesByBno(int bno) {
		List<QnaFilesVO> fileList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, qna_bno, file_name FROM qna_files WHERE qna_bno = ?";

		try {
			conn = JDBCUtility.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				QnaFilesVO fileVO = new QnaFilesVO();
				fileVO.setId(rs.getInt("id"));
				fileVO.setQna_bno(rs.getInt("qna_bno"));
				fileVO.setFileName(rs.getString("file_name"));
				fileList.add(fileVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtility.close(conn, pstmt, rs);
		}
		return fileList;
	}

	// ✅ 3. 특정 게시글(bno)의 모든 파일 삭제
	public void deleteFilesByBno(int bno) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "DELETE FROM qna_files WHERE qna_bno = ?";

	    try {
	        conn = JDBCUtility.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, bno);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtility.close(conn, pstmt, null);
	    }
	}

}
