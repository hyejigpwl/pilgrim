package com.lec.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.lec.db.JDBCUtility;
import com.lec.reservation.vo.FacilityReservationVO;
import com.lec.reservation.vo.RoomReservationVO;
import com.lec.reservation.vo.SeminarReservationVO;

public class ReservationDAO {

    // Singleton
    private static ReservationDAO reservationDAO;

    private ReservationDAO() {}

    public static ReservationDAO getInstance() {
        if (reservationDAO == null)
        	reservationDAO = new ReservationDAO();
        return reservationDAO;
    }
    
    Connection conn = null;
	DataSource ds = null;

	// DB Connection
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

    
	// 1. 객실예약
 	public int reserveRoom(RoomReservationVO room) {
 		PreparedStatement pstmt = null;
 		ResultSet rs = null;
 		String sql = "insert into room_reservation(reservation_id, member_id, guest_count, checkin_date, checkout_date, room_type, reg_date) "
 				+ " values(?,?,?,?,?,?,now())";
 		int insertCount = 0;
 		int reservation_id=0;

 		try {
 			pstmt = conn.prepareStatement("select max(reservation_id) from room_reservation");
			rs = pstmt.executeQuery();
			if (rs.next())
				reservation_id = rs.getInt(1) + 1;
			
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, reservation_id);
            pstmt.setString(2, room.getMember_id());
 			pstmt.setInt(3, room.getGuest_count());
 			pstmt.setDate(4, new java.sql.Date(room.getCheckin_date().getTime()));
 			pstmt.setDate(5, new java.sql.Date(room.getCheckout_date().getTime()));
 			pstmt.setString(6, room.getRoom_type());
 			insertCount = pstmt.executeUpdate();
 		
 			
 		} catch (Exception e) {
 			System.out.println("객실예약실패!!!");
 			insertCount=0;
 		} finally {
 			JDBCUtility.close(null, pstmt, null);
 		}
 		return insertCount;
 	}
 	
 // 2. 시설예약
  	public int reserveFacility(FacilityReservationVO facility) {
  		PreparedStatement pstmt = null;
  		ResultSet rs = null;
  		String sql = "insert into facility_reservation(reservation_id, member_id, facility_type, checkin_date, checkout_date, reg_date) "
  				+ " values(?,?,?,?,?,now())";
  		int insertCount = 0;
  		int reservation_id=0;

  		try {
  			pstmt = conn.prepareStatement("select max(reservation_id) from facility_reservation");
 			rs = pstmt.executeQuery();
 			if (rs.next())
 				reservation_id = rs.getInt(1) + 1;
 			
             pstmt = conn.prepareStatement(sql);
             
             pstmt.setInt(1, reservation_id);
             pstmt.setString(2, facility.getMember_id());
  			pstmt.setString(3, facility.getFacility_type());
  			pstmt.setDate(4, new java.sql.Date(facility.getCheckin_date().getTime()));
  			pstmt.setDate(5, new java.sql.Date(facility.getCheckout_date().getTime()));
  			insertCount = pstmt.executeUpdate();
  		
  			
  		} catch (Exception e) {
  			System.out.println("객실예약실패!!!");
  			insertCount=0;
  		} finally {
  			JDBCUtility.close(null, pstmt, null);
  		}
  		return insertCount;
  	}

  	
 // 3. 세미나예약
   	public int reserveSeminar(SeminarReservationVO seminar) {
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		String sql = "insert into seminar_reservation(reservation_id, member_id, seminar_type, guest_count, seminar_date, reg_date) "
   				+ " values(?,?,?,?,?,now())";
   		int insertCount = 0;
   		int reservation_id=0;

   		try {
   			pstmt = conn.prepareStatement("select max(reservation_id) from seminar_reservation");
  			rs = pstmt.executeQuery();
  			if (rs.next())
  				reservation_id = rs.getInt(1) + 1;
  			
              pstmt = conn.prepareStatement(sql);
              
              pstmt.setInt(1, reservation_id);
              pstmt.setString(2, seminar.getMember_id());
   			pstmt.setString(3, seminar.getSeminar_type());
   			pstmt.setInt(4, seminar.getGuest_count());
   			pstmt.setDate(5, new java.sql.Date(seminar.getSeminar_date().getTime()));
   			insertCount = pstmt.executeUpdate();
   		
   			
   		} catch (Exception e) {
   			System.out.println("세미나예약실패!!!");
   			insertCount=0;
   			e.printStackTrace();
   		} finally {
   			JDBCUtility.close(null, pstmt, null);
   		}
   		return insertCount;
   	}
 	
   	// 4. 일별 가능한 숙소 개수 파악
   	public List<Map<String, Object>> getAvailableRoomsByDate(String date) {
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;
   	    List<Map<String, Object>> availableRoomsList = new ArrayList<>();

   	    try {
   	        String sql = "SELECT ri.room_type, room_price,  (ri.total_rooms - COALESCE(COUNT(rr.reservation_id), 0)) AS available_rooms " +
   	                     "FROM room_info ri " +
   	                     "LEFT JOIN room_reservation rr ON ri.room_type = rr.room_type " +
   	                     "AND ? >= rr.checkin_date AND ? < rr.checkout_date " +
   	                     "GROUP BY ri.room_type";
   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        pstmt.setString(1, date);
   	        pstmt.setString(2, date);
   	        rs = pstmt.executeQuery();

   	        while (rs.next()) {
   	            Map<String, Object> roomData = new HashMap<>();
   	            roomData.put("room_type", rs.getString("room_type"));
   	            roomData.put("available_rooms", rs.getInt("available_rooms"));
   	         roomData.put("room_price", rs.getInt("room_price"));
   	            availableRoomsList.add(roomData);
   	        }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableRoomsList;
   	}
   	
   	public int getLastDayOfMonth(int year, int month) {
   	    Calendar calendar = Calendar.getInstance();
   	    calendar.set(Calendar.YEAR, year);
   	    calendar.set(Calendar.MONTH, month - 1); // Calendar의 월은 0부터 시작하므로 -1 필요
   	    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월의 마지막 날 반환
   	}
   	
 // 날짜를 yyyy-MM-dd 형식으로 포맷팅하기 위한 SimpleDateFormat 설정
   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   	
   	
   	// 5. 월별 가능한 룸 총 개수
   	public Map<String, Integer> getAvailableRoomsByMonth(int year, int month) {
   	    Map<String, Integer> availableRoomsMap = new HashMap<>();
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;

   	    try {
   	        // SQL 쿼리: 지정된 월의 각 날짜별 예약 가능한 방 수 계산
   	        String sql = "SELECT a.date, SUM(ri.total_rooms - IFNULL(b.reserved_rooms, 0)) AS total_available_rooms " +
   	                     "FROM room_info ri " +
   	                     "JOIN ( " +
   	                     "    SELECT DATE_ADD(?, INTERVAL seq DAY) AS date " +
   	                     "    FROM seq_table " +  // seq_table은 0부터 30까지의 숫자를 가진 임시 테이블로 각 날짜를 생성하기 위해 사용
   	                     "    WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?) " +
   	                     ") a ON 1=1 " +
   	                     "LEFT JOIN ( " +
   	                     "    SELECT rr.room_type, DATE(a.date) AS date, COUNT(rr.room_type) AS reserved_rooms " +
   	                     "    FROM room_reservation rr " +
   	                     "    JOIN ( " +
   	                     "        SELECT DATE_ADD(?, INTERVAL seq DAY) AS date " +
   	                     "        FROM seq_table " +
   	                     "        WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?) " +
   	                     "    ) a ON a.date BETWEEN rr.checkin_date AND DATE_SUB(rr.checkout_date, INTERVAL 1 DAY) " +
   	                     "    GROUP BY rr.room_type, DATE(a.date) " +
   	                     ") b ON ri.room_type = b.room_type AND a.date = b.date " +
   	                     "GROUP BY a.date";

   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        
   	        // 날짜를 기준으로 날짜 범위 지정
   	     String firstDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, 1).getTime());
   	  String lastDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, getLastDayOfMonth(year, month)).getTime());
   	        
   	        // 첫 번째 날짜와 마지막 날짜 설정
   	        pstmt.setString(1, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(2, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(3, firstDateOfMonth); // 첫 번째 날짜
   	        pstmt.setString(4, lastDateOfMonth);  // 마지막 날짜
   	        
   	        // 날짜 범위 설정을 위한 추가 파라미터 (서브 쿼리 내에서 사용)
   	        pstmt.setString(5, firstDateOfMonth);
   	        pstmt.setString(6, firstDateOfMonth);
   	        pstmt.setString(7, firstDateOfMonth);
   	        pstmt.setString(8, lastDateOfMonth);

   	        rs = pstmt.executeQuery();

   	        // 쿼리 결과에서 날짜별로 예약 가능한 방 수를 Map에 저장
   	        while (rs.next()) {
   	            String date = sdf.format(rs.getDate("date")); // 날짜 (yyyy-MM-dd 형식)
   	            int totalAvailableRooms = rs.getInt("total_available_rooms");

   	            availableRoomsMap.put(date, totalAvailableRooms);
   	            // System.out.println("Date: " + date + ", Available Rooms: " + totalAvailableRooms);
   	        }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableRoomsMap;
   	}




   	
 // 6. 일별 가능한 시설 개수 파악
   	public List<Map<String, Object>> getAvailableFacilitesByDate(String date) {
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;
   	    List<Map<String, Object>> availableFacilitiesList = new ArrayList<>();

   	    try {
   	        String sql = "SELECT ri.facility_type, facility_price,  (ri.total_facilities - COALESCE(COUNT(rr.reservation_id), 0)) AS available_facilities FROM facility_info ri LEFT JOIN facility_reservation rr ON ri.facility_type = rr.facility_type AND ? >= rr.checkin_date AND ? < rr.checkout_date GROUP BY ri.facility_type";
   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        pstmt.setString(1, date);
   	        pstmt.setString(2, date);
   	        rs = pstmt.executeQuery();

   	        while (rs.next()) {
   	            Map<String, Object> facilityData = new HashMap<>();
   	            facilityData.put("facility_type", rs.getString("facility_type"));
   	         facilityData.put("available_facilities", rs.getInt("available_facilities"));
   	      facilityData.put("facility_price", rs.getInt("facility_price"));
   	         availableFacilitiesList.add(facilityData);
   	        }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableFacilitiesList;
   	}
   	
   	//  7. 월별 가능한 시설 총 갯수
   	public Map<String, Integer> getAvailableFacilitiesByMonth(int year, int month) {
   	    Map<String, Integer> availableFacilitiesMap = new HashMap<>();
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;

   	    try {
   	        // SQL 쿼리: 지정된 월의 각 날짜별 예약 가능한 시설 수 계산
   	        String sql = "SELECT DATE_FORMAT(a.date, '%Y-%m-%d') AS date, " +
   	                     "SUM(fi.total_facilities) - IFNULL(SUM(b.reserved_facilities), 0) AS total_available_facilities " +
   	                     "FROM facility_info fi " +
   	                     "JOIN ( " +
   	                     "    SELECT DATE_ADD(?, INTERVAL seq DAY) AS date " +
   	                     "    FROM seq_table " +  // seq_table은 0부터 30까지의 숫자를 가진 임시 테이블로 각 날짜를 생성하기 위해 사용
   	                     "    WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?) " +
   	                     ") a ON 1=1 " +
   	                     "LEFT JOIN ( " +
   	                     "    SELECT fr.facility_type, a.date AS date, COUNT(fr.facility_type) AS reserved_facilities " +
   	                     "    FROM facility_reservation fr " +
   	                     "    JOIN ( " +
   	                     "        SELECT DATE_ADD(?, INTERVAL seq DAY) AS date " +
   	                     "        FROM seq_table " +
   	                     "        WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?) " +
   	                     "    ) a ON a.date BETWEEN fr.checkin_date AND DATE_SUB(fr.checkout_date, INTERVAL 1 DAY) " +
   	                     "    GROUP BY fr.facility_type, a.date " +
   	                     ") b ON fi.facility_type = b.facility_type AND a.date = b.date " +
   	                     "GROUP BY a.date";

   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        
   	        // 날짜를 기준으로 날짜 범위 지정
   	     String firstDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, 1).getTime());
   	  String lastDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, getLastDayOfMonth(year, month)).getTime());
   	        
   	        // 첫 번째 날짜와 마지막 날짜 설정
   	        pstmt.setString(1, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(2, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(3, firstDateOfMonth); // 첫 번째 날짜
   	        pstmt.setString(4, lastDateOfMonth);  // 마지막 날짜
   	        
   	        // 날짜 범위 설정을 위한 추가 파라미터 (서브 쿼리 내에서 사용)
   	        pstmt.setString(5, firstDateOfMonth);
   	        pstmt.setString(6, firstDateOfMonth);
   	        pstmt.setString(7, firstDateOfMonth);
   	        pstmt.setString(8, lastDateOfMonth);

   	        rs = pstmt.executeQuery();

   	        // 쿼리 결과에서 날짜별로 예약 가능한 시설 수를 Map에 저장
   	        while (rs.next()) {
   	        	String date = sdf.format(rs.getDate("date")); // 날짜 (yyyy-MM-dd 형식)
   	            int totalAvailableFacilities = rs.getInt("total_available_facilities");

   	            availableFacilitiesMap.put(date, totalAvailableFacilities);
   	         System.out.println("Adding to map - Date: " + date + ", Available Facilities: " + totalAvailableFacilities);


   	            
   	            // System.out.println(availableFacilitiesMap); 
   	            
   	         // System.out.println("Date: " + date + ", Available Facilities: " + totalAvailableFacilities);
   	         }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableFacilitiesMap;
   	}
   	
   	
   	
   	// 8. 선택한 시설의 예약 가능한 날짜
   	public List<String> getAvailableDatesByFacility(String facilityType) {
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;
   	    List<String> availableDates = new ArrayList<>();

   	    try {
   	        // SQL 쿼리: 특정 시설의 예약 가능한 날짜를 조회
   	        String sql = "SELECT available_dates.date AS available_date\r\n"
   	        		+ "FROM (\r\n"
   	        		+ "    SELECT CURDATE() + INTERVAL seq DAY AS date\r\n"
   	        		+ "    FROM (\r\n"
   	        		+ "        SELECT 0 AS seq UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\r\n"
   	        		+ "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL\r\n"
   	        		+ "        SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL\r\n"
   	        		+ "        SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL\r\n"
   	        		+ "        SELECT 20 UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL\r\n"
   	        		+ "        SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 29 UNION ALL\r\n"
   	        		+ "        SELECT 30 UNION ALL SELECT 31 UNION ALL SELECT 32 UNION ALL SELECT 33 UNION ALL SELECT 34 UNION ALL\r\n"
   	        		+ "        SELECT 35 UNION ALL SELECT 36 UNION ALL SELECT 37 UNION ALL SELECT 38 UNION ALL SELECT 39 UNION ALL\r\n"
   	        		+ "        SELECT 40 UNION ALL SELECT 41 UNION ALL SELECT 42 UNION ALL SELECT 43 UNION ALL SELECT 44 UNION ALL\r\n"
   	        		+ "        SELECT 45 UNION ALL SELECT 46 UNION ALL SELECT 47 UNION ALL SELECT 48 UNION ALL SELECT 49 UNION ALL\r\n"
   	        		+ "        SELECT 50 UNION ALL SELECT 51 UNION ALL SELECT 52 UNION ALL SELECT 53 UNION ALL SELECT 54 UNION ALL\r\n"
   	        		+ "        SELECT 55 UNION ALL SELECT 56 UNION ALL SELECT 57 UNION ALL SELECT 58 UNION ALL SELECT 59 UNION ALL\r\n"
   	        		+ "        SELECT 60 UNION ALL SELECT 61 UNION ALL SELECT 62 UNION ALL SELECT 63 UNION ALL SELECT 64 UNION ALL\r\n"
   	        		+ "        SELECT 65 UNION ALL SELECT 66 UNION ALL SELECT 67 UNION ALL SELECT 68 UNION ALL SELECT 69 UNION ALL\r\n"
   	        		+ "        SELECT 70 UNION ALL SELECT 71 UNION ALL SELECT 72 UNION ALL SELECT 73 UNION ALL SELECT 74 UNION ALL\r\n"
   	        		+ "        SELECT 75 UNION ALL SELECT 76 UNION ALL SELECT 77 UNION ALL SELECT 78 UNION ALL SELECT 79 UNION ALL\r\n"
   	        		+ "        SELECT 80 UNION ALL SELECT 81 UNION ALL SELECT 82 UNION ALL SELECT 83 UNION ALL SELECT 84 UNION ALL\r\n"
   	        		+ "        SELECT 85 UNION ALL SELECT 86 UNION ALL SELECT 87 UNION ALL SELECT 88 UNION ALL SELECT 89 UNION ALL\r\n"
   	        		+ "        SELECT 90 UNION ALL SELECT 91 UNION ALL SELECT 92\r\n"
   	        		+ "    ) seq_table\r\n"
   	        		+ ") available_dates\r\n"
   	        		+ "LEFT JOIN (\r\n"
   	        		+ "    SELECT DATE_ADD(fr.checkin_date, INTERVAL n.num DAY) AS reserved_date, fr.facility_type\r\n"
   	        		+ "    FROM facility_reservation fr\r\n"
   	        		+ "    JOIN (\r\n"
   	        		+ "        SELECT 0 AS num UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL\r\n"
   	        		+ "        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL\r\n"
   	        		+ "        SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL\r\n"
   	        		+ "        SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL\r\n"
   	        		+ "        SELECT 20 UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL\r\n"
   	        		+ "        SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 29 UNION ALL\r\n"
   	        		+ "        SELECT 30 UNION ALL SELECT 31 UNION ALL SELECT 32 UNION ALL SELECT 33 UNION ALL SELECT 34 UNION ALL\r\n"
   	        		+ "        SELECT 35 UNION ALL SELECT 36 UNION ALL SELECT 37 UNION ALL SELECT 38 UNION ALL SELECT 39 UNION ALL\r\n"
   	        		+ "        SELECT 40 UNION ALL SELECT 41 UNION ALL SELECT 42 UNION ALL SELECT 43 UNION ALL SELECT 44 UNION ALL\r\n"
   	        		+ "        SELECT 45 UNION ALL SELECT 46 UNION ALL SELECT 47 UNION ALL SELECT 48 UNION ALL SELECT 49 UNION ALL\r\n"
   	        		+ "        SELECT 50 UNION ALL SELECT 51 UNION ALL SELECT 52 UNION ALL SELECT 53 UNION ALL SELECT 54 UNION ALL\r\n"
   	        		+ "        SELECT 55 UNION ALL SELECT 56 UNION ALL SELECT 57 UNION ALL SELECT 58 UNION ALL SELECT 59 UNION ALL\r\n"
   	        		+ "        SELECT 60 UNION ALL SELECT 61 UNION ALL SELECT 62 UNION ALL SELECT 63 UNION ALL SELECT 64 UNION ALL\r\n"
   	        		+ "        SELECT 65 UNION ALL SELECT 66 UNION ALL SELECT 67 UNION ALL SELECT 68 UNION ALL SELECT 69 UNION ALL\r\n"
   	        		+ "        SELECT 70 UNION ALL SELECT 71 UNION ALL SELECT 72 UNION ALL SELECT 73 UNION ALL SELECT 74 UNION ALL\r\n"
   	        		+ "        SELECT 75 UNION ALL SELECT 76 UNION ALL SELECT 77 UNION ALL SELECT 78 UNION ALL SELECT 79 UNION ALL\r\n"
   	        		+ "        SELECT 80 UNION ALL SELECT 81 UNION ALL SELECT 82 UNION ALL SELECT 83 UNION ALL SELECT 84 UNION ALL\r\n"
   	        		+ "        SELECT 85 UNION ALL SELECT 86 UNION ALL SELECT 87 UNION ALL SELECT 88 UNION ALL SELECT 89 UNION ALL\r\n"
   	        		+ "        SELECT 90 UNION ALL SELECT 91 UNION ALL SELECT 92\r\n"
   	        		+ "    ) n ON DATE_ADD(fr.checkin_date, INTERVAL n.num DAY) < fr.checkout_date\r\n"
   	        		+ "    WHERE fr.facility_type = ?\r\n"
   	        		+ ") reservations ON available_dates.date = reservations.reserved_date\r\n"
   	        		+ "JOIN facility_info fi ON fi.facility_type = ?\r\n"
   	        		+ "WHERE reservations.reserved_date IS NULL OR (\r\n"
   	        		+ "    (SELECT COUNT(*) FROM facility_reservation fr2\r\n"
   	        		+ "     WHERE fr2.facility_type = fi.facility_type\r\n"
   	        		+ "     AND available_dates.date BETWEEN fr2.checkin_date AND DATE_SUB(fr2.checkout_date, INTERVAL 1 DAY)\r\n"
   	        		+ "    ) < fi.total_facilities\r\n"
   	        		+ ")\r\n"
   	        		+ "AND available_dates.date >= CURRENT_DATE\r\n"
   	        		+ "GROUP BY available_dates.date;\r\n"
   	        		+ "";

   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        
   	        // 시설 유형 설정
   	        pstmt.setString(1, facilityType); // 시설 유형
   	        pstmt.setString(2, facilityType); // 시설 유형

   	        rs = pstmt.executeQuery();

   	        // 결과 처리
   	        while (rs.next()) {
   	            availableDates.add(rs.getString("available_date"));
   	        }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    System.out.println("Queried facility type: " + facilityType);
   	    System.out.println("Available Dates from DB: " + availableDates);

   	    return availableDates;
   	}



   	
   	

   	// 8. 월별 가능한 세미나A 갯수
   	public Map<String, Integer> getAvailableSeminarAByMonth(int year, int month) {
   	    Map<String, Integer> availableSeminarAMap = new HashMap<>();
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;

   	    try {
   	        // SQL 쿼리: 지정된 월의 각 날짜별 예약 가능한 세미나A 수 계산
   	        String sql = "SELECT DATE_FORMAT(a.date, '%Y-%m-%d') AS date, MIN(fi.available_people) - IFNULL(b.guest_count, 0) AS available_seminarA FROM seminar_info fi JOIN (SELECT DATE_ADD(?, INTERVAL seq DAY) AS date FROM seq_table WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?) ) a ON 1=1 LEFT JOIN (SELECT fr.seminar_type, a.date AS date, SUM(fr.guest_count) AS guest_count FROM seminar_reservation fr JOIN (SELECT DATE_ADD(?, INTERVAL seq DAY) AS date FROM seq_table  WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?)) a ON a.date = fr.seminar_date WHERE fr.seminar_type = '천로역정 A타임' GROUP BY fr.seminar_type, a.date) b ON fi.seminar_type = b.seminar_type AND a.date = b.date WHERE fi.seminar_type = '천로역정 A타임' GROUP BY a.date";

   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        
   	        // 날짜를 기준으로 날짜 범위 지정
   	     String firstDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, 1).getTime());
   	  String lastDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, getLastDayOfMonth(year, month)).getTime());
   	        
   	        // 첫 번째 날짜와 마지막 날짜 설정
   	        pstmt.setString(1, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(2, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(3, firstDateOfMonth); // 첫 번째 날짜
   	        pstmt.setString(4, lastDateOfMonth);  // 마지막 날짜
   	        
   	        // 날짜 범위 설정을 위한 추가 파라미터 (서브 쿼리 내에서 사용)
   	        pstmt.setString(5, firstDateOfMonth);
   	        pstmt.setString(6, firstDateOfMonth);
   	        pstmt.setString(7, firstDateOfMonth);
   	        pstmt.setString(8, lastDateOfMonth);

   	        rs = pstmt.executeQuery();

   	        // 쿼리 결과에서 날짜별로 예약 가능한 시설 수를 Map에 저장
   	        while (rs.next()) {
   	        	String date = sdf.format(rs.getDate("date")); // 날짜 (yyyy-MM-dd 형식)
   	            int availableSeminarA = rs.getInt("available_seminarA");

   	         availableSeminarAMap.put(date, availableSeminarA);
   	         System.out.println("Adding to map - Date: " + date + ", available_seminarA: " + availableSeminarA);


   	            
   	            // System.out.println(availableFacilitiesMap); 
   	            
   	         // System.out.println("Date: " + date + ", Available Facilities: " + totalAvailableFacilities);
   	         }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableSeminarAMap;
   	}
   	
   	
   	
	 // 9. 월별 가능한 세미나B 갯수
   	public Map<String, Integer> getAvailableSeminarBByMonth(int year, int month) {
   	    Map<String, Integer> availableSeminarBMap = new HashMap<>();
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;

   	    try {
   	        // SQL 쿼리: 지정된 월의 각 날짜별 예약 가능한 세미나A 수 계산
   	        String sql = "SELECT DATE_FORMAT(a.date, '%Y-%m-%d') AS date, MIN(fi.available_people) - IFNULL(b.guest_count, 0) AS available_seminarB FROM seminar_info fi JOIN (SELECT DATE_ADD(?, INTERVAL seq DAY) AS date FROM seq_table WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?) ) a ON 1=1 LEFT JOIN (SELECT fr.seminar_type, a.date AS date, SUM(fr.guest_count) AS guest_count FROM seminar_reservation fr JOIN (SELECT DATE_ADD(?, INTERVAL seq DAY) AS date FROM seq_table  WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?)) a ON a.date = fr.seminar_date WHERE fr.seminar_type = '천로역정 B타임' GROUP BY fr.seminar_type, a.date) b ON fi.seminar_type = b.seminar_type AND a.date = b.date WHERE fi.seminar_type = '천로역정 B타임' GROUP BY a.date";

   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        
   	        // 날짜를 기준으로 날짜 범위 지정
   	     String firstDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, 1).getTime());
   	  String lastDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, getLastDayOfMonth(year, month)).getTime());
   	        
   	        // 첫 번째 날짜와 마지막 날짜 설정
   	        pstmt.setString(1, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(2, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(3, firstDateOfMonth); // 첫 번째 날짜
   	        pstmt.setString(4, lastDateOfMonth);  // 마지막 날짜
   	        
   	        // 날짜 범위 설정을 위한 추가 파라미터 (서브 쿼리 내에서 사용)
   	        pstmt.setString(5, firstDateOfMonth);
   	        pstmt.setString(6, firstDateOfMonth);
   	        pstmt.setString(7, firstDateOfMonth);
   	        pstmt.setString(8, lastDateOfMonth);

   	        rs = pstmt.executeQuery();

   	        // 쿼리 결과에서 날짜별로 예약 가능한 시설 수를 Map에 저장
   	        while (rs.next()) {
   	        	String date = sdf.format(rs.getDate("date")); // 날짜 (yyyy-MM-dd 형식)
   	            int availableSeminarB = rs.getInt("available_seminarB");

   	         availableSeminarBMap.put(date, availableSeminarB);
   	         System.out.println("Adding to map - Date: " + date + ", available_seminarB: " + availableSeminarB);


   	            
   	            // System.out.println(availableFacilitiesMap); 
   	            
   	         // System.out.println("Date: " + date + ", Available Facilities: " + totalAvailableFacilities);
   	         }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableSeminarBMap;
   	}
	   	
	 // 10. 월별 가능한 세미나C 갯수
   	public Map<String, Integer> getAvailableSeminarCByMonth(int year, int month) {
   	    Map<String, Integer> availableSeminarCMap = new HashMap<>();
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;

   	    try {
   	        // SQL 쿼리: 지정된 월의 각 날짜별 예약 가능한 세미나A 수 계산
   	        String sql = "SELECT DATE_FORMAT(a.date, '%Y-%m-%d') AS date, MIN(fi.available_people) - IFNULL(b.guest_count, 0) AS available_seminarC FROM seminar_info fi JOIN (SELECT DATE_ADD(?, INTERVAL seq DAY) AS date FROM seq_table WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?) ) a ON 1=1 LEFT JOIN (SELECT fr.seminar_type, a.date AS date, SUM(fr.guest_count) AS guest_count FROM seminar_reservation fr JOIN (SELECT DATE_ADD(?, INTERVAL seq DAY) AS date FROM seq_table  WHERE DATE_ADD(?, INTERVAL seq DAY) BETWEEN ? AND LAST_DAY(?)) a ON a.date = fr.seminar_date WHERE fr.seminar_type = '천로역정 C타임' GROUP BY fr.seminar_type, a.date) b ON fi.seminar_type = b.seminar_type AND a.date = b.date WHERE fi.seminar_type = '천로역정 C타임' GROUP BY a.date";

   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        
   	        // 날짜를 기준으로 날짜 범위 지정
   	     String firstDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, 1).getTime());
   	  String lastDateOfMonth = sdf.format(new GregorianCalendar(year, month - 1, getLastDayOfMonth(year, month)).getTime());
   	        
   	        // 첫 번째 날짜와 마지막 날짜 설정
   	        pstmt.setString(1, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(2, firstDateOfMonth); // 날짜 생성 시작점
   	        pstmt.setString(3, firstDateOfMonth); // 첫 번째 날짜
   	        pstmt.setString(4, lastDateOfMonth);  // 마지막 날짜
   	        
   	        // 날짜 범위 설정을 위한 추가 파라미터 (서브 쿼리 내에서 사용)
   	        pstmt.setString(5, firstDateOfMonth);
   	        pstmt.setString(6, firstDateOfMonth);
   	        pstmt.setString(7, firstDateOfMonth);
   	        pstmt.setString(8, lastDateOfMonth);

   	        rs = pstmt.executeQuery();

   	        // 쿼리 결과에서 날짜별로 예약 가능한 시설 수를 Map에 저장
   	        while (rs.next()) {
   	        	String date = sdf.format(rs.getDate("date")); // 날짜 (yyyy-MM-dd 형식)
   	            int availableSeminarC = rs.getInt("available_seminarC");

   	         availableSeminarCMap.put(date, availableSeminarC);
   	         System.out.println("Adding to map - Date: " + date + ", available_seminarC: " + availableSeminarC);


   	            
   	            // System.out.println(availableFacilitiesMap); 
   	            
   	         // System.out.println("Date: " + date + ", Available Facilities: " + totalAvailableFacilities);
   	         }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableSeminarCMap;
   	}



}
