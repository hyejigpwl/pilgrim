package com.lec.review.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.lec.db.JDBCUtility;
import com.lec.review.vo.ReviewFilesVO;

public class ReviewFileService {

	private static ReviewFileService instance = new ReviewFileService();

	public static ReviewFileService getInstance() {
		return instance;
	}

	// ✅ 1. 파일 리스트를 한 번에 저장
	public void saveFiles(int bno, List<String> files, Connection conn) {
	    PreparedStatement pstmt = null;
	    String sql = "INSERT INTO review_files (review_bno, file_name) VALUES (?, ?)";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        if (files == null || files.isEmpty()) {
	            System.out.println("🚨 저장할 파일이 없습니다. (files 리스트가 비어 있음)");
	            return; // 파일이 없으면 더 이상 진행하지 않음
	        }

	        for (String fileName : files) {
	            System.out.println("▶ 파일 저장 중: " + fileName);
	            pstmt.setInt(1, bno);
	            pstmt.setString(2, fileName);
	            int result = pstmt.executeUpdate();
	            
	            if (result == 0) {
	                System.out.println("🚨 파일 저장 실패: " + fileName);
	            } else {
	                System.out.println("✅ 파일 저장 완료: " + fileName);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtility.close(null, pstmt, null); // 🔥 conn을 닫지 않음 (트랜잭션 유지)
	    }
	}


	// ✅ 2. 특정 게시글(bno)의 파일 목록 조회
	public List<ReviewFilesVO> getFilesByBno(int bno) {
		List<ReviewFilesVO> fileList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, review_bno, file_name FROM review_files WHERE review_bno = ?";

		try {
			conn = JDBCUtility.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReviewFilesVO fileVO = new ReviewFilesVO();
				fileVO.setId(rs.getInt("id"));
				fileVO.setReview_bno(rs.getInt("review_bno"));
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
	    String sql = "DELETE FROM review_files WHERE review_bno = ?";

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
	
	public void modifyFiles(int bno, List<String> files, Connection conn) {
	    PreparedStatement deletePstmt = null;
	    PreparedStatement insertPstmt = null;
	    
	    String deleteSql = "DELETE FROM review_files WHERE review_bno = ?";
	    String insertSql = "INSERT INTO review_files (review_bno, file_name) VALUES (?, ?)";

	    try {
	        // 1. 기존 파일 삭제
	        deletePstmt = conn.prepareStatement(deleteSql);
	        deletePstmt.setInt(1, bno);
	        int deleteCount = deletePstmt.executeUpdate();
	        System.out.println("🗑 기존 파일 삭제 완료 (삭제된 파일 수: " + deleteCount + ")");

	        // 2. 새로운 파일 추가
	        if (files != null && !files.isEmpty()) {
	            insertPstmt = conn.prepareStatement(insertSql);

	            for (String fileName : files) {
	                System.out.println("▶ 새 파일 저장 중: " + fileName);
	                insertPstmt.setInt(1, bno);
	                insertPstmt.setString(2, fileName);
	                int result = insertPstmt.executeUpdate();

	                if (result == 0) {
	                    System.out.println("🚨 파일 저장 실패: " + fileName);
	                } else {
	                    System.out.println("✅ 파일 저장 완료: " + fileName);
	                }
	            }
	        } else {
	            System.out.println("🚨 새로 저장할 파일이 없습니다.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtility.close(null, deletePstmt, null);
	        JDBCUtility.close(null, insertPstmt, null); // 🔥 conn을 닫지 않음 (트랜잭션 유지)
	    }
	}


}
