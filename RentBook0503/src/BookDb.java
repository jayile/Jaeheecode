import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BookDb
{
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	// 멤버변수
	Connection con;
	PreparedStatement pstmt;
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	PreparedStatement pstmt3;
	PreparedStatement pstmt4;
	PreparedStatement pstmt5;
	PreparedStatement pstmt6;
	
	Scanner sc = new Scanner(System.in);
	
	
	public void connectDatabase() {
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott",
					"tiger");
			String sql = "select * from login";
			pstmt = con.prepareStatement(sql);
			
			String sql1 = "select * from bookdb where title = ?";
			pstmt1 = con.prepareStatement(sql1);
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
	}
	
	public void bookMenu() {
		connectDatabase();
		
		int choice;
		while(true) {
			showBookMenu();
			
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice)
			{
			case 1:
				reserchBook();
				break;
			case 2:
				totalBook();
				break;
			case 3:
				bestBook();
				break;
			case 4:
				rentalBook();
				break;
			case 5:
				extendBookDate();
				break;
			case 6:
				rtBook();
				break;
			case 0:
				return;
			default:
				System.out.println("      잘 못 입력하셨습니다.");
				break;
			}
		}
	}
	
	public void showBookMenu() {
		System.out.println("     ┌────────────────────────┐");
		System.out.println("     │   [ 도서 대여 메뉴 ]   │");
		System.out.println("     │                        │");
		System.out.println("     │     ① 도서 검색       │");
		System.out.println("     │     ② 전체 도서       │ ");
		System.out.println("     │     ③ 인기 도서       │");
		System.out.println("     │     ④ 도서 대여       │");
		System.out.println("     │     ⑤ 대여 연장       │");
		System.out.println("     │     ⑥ 도서 반납       │");
		System.out.println("     │     ⓞ 메인 메뉴       │");
		System.out.println("     └────────────────────────┘");
		System.out.print  ("              선택 : ");
	}
	// 도서 검색
	public void reserchBook () {
		System.out.print("   검색할 도서 이름 : ");
		String key = sc.nextLine();
		
		String sql = "select * from bookdb where title like ?";
		try { 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,"%" + key + "%");
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String sql1 = "select * from bookdb where title like ?";
				pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1,"%" + key + "%");
				ResultSet rs1 = pstmt1.executeQuery();
				System.out.println("  ────────────────────────────────");
				System.out.println("          [ 검색된 도서 ]         ");
				while(rs1.next()) {
					System.out.println("   도서 코드 : " + rs1.getString(1));
					System.out.println("   도서 이름 : " + rs1.getString(2));
					System.out.println("   저자 이름 : " + rs1.getString(6));
					System.out.println("   도서 수량 : " + rs1.getString(3) + "권이 있습니다.");
					System.out.println("  ────────────────────────────────");
				}
				rs1.close();
			} else {
				System.out.println(" ┌────────────────────────────────┐");
				System.out.println(" │       미등록 도서입니다.       │");
				System.out.println(" └────────────────────────────────┘");
			}
			rs.close();
		} catch (Exception e) {
		}
	}
	// 전체 도서
	public void totalBook() {
		String sql = "select * from bookdb";
		try { 
			pstmt2 = con.prepareStatement(sql);
			ResultSet rs = pstmt2.executeQuery();
			System.out.println("────────[ 전체 도서 리스트 ]──────────────");
			while (rs.next()) {
				System.out.print("도서 코드 : " + rs.getString(1) + ", ");
				System.out.print("도서 이름 : " + rs.getString(2) + ", ");
				System.out.println("저자 이름 : " + rs.getString(6));
			}
			rs.close();
			System.out.println("──────────────────────────────────────────");
		} catch (Exception e) {
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
	}
	// 인기 도서
	public void bestBook() {
		String sql = "select bno, title, popular from bookdb order by popular desc";
		try { 
			pstmt2 = con.prepareStatement(sql);
			ResultSet rs = pstmt2.executeQuery();
			System.out.println("────────[ 인기 도서 리스트 ]──────────────");
			while (rs.next()) {
				System.out.print("도서 코드 : " + rs.getString(1) + ", ");
				System.out.print("도서 이름 : " + rs.getString(2) + ", ");
				System.out.println("대여 횟수 : " + rs.getString(3));
			}
			System.out.println("──────────────────────────────────────────");
			rs.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
	}
	// 도서 대여
	public void rentalBook() {
		
		try {
			ResultSet login = pstmt.executeQuery();
			if(login.next()) {
			} else {
				System.out.println("┌──────────────────────────────────┐");
				System.out.println("│ * 회원 로그인 후 이용해주세요. * │");
				System.out.println("│   회원이 아니라면 회원 등록 후   │");
				System.out.println("│        로그인을 해주세요.        │");
				System.out.println("└──────────────────────────────────┘");
				return;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.print("   대여할 도서의 이름 : ");
		String title = sc.nextLine();
		
		try {
			// 로그인한 회원
			ResultSet login = pstmt.executeQuery();
			
			// 도서 정보
			String sql2 = "select * from bookdb where many <> 0 and title = ?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setString(1, title);
			ResultSet rs2 = pstmt2.executeQuery();
			
			// 블랙경고
			String sql = "select * from userdb where userid = ?";
			pstmt3 = con.prepareStatement(sql);
			if(login.next()) {
				String userid = login.getString(1);
				pstmt3.setString(1, userid);
				ResultSet rs3 = pstmt3.executeQuery();
				if(rs3.next()) {
					String name = rs3.getString(3);
					int black = rs3.getInt(5);
					int bmany = rs3.getInt(4);
					if(bmany == 2) {
						System.out.println();
						System.out.println("   이미 대여하고 계신 도서가 많아");
						System.out.println("   대여가 불가능합니다.");
						return;
					} else if(black > 0) {
						System.out.println();
						System.out.println("    [" + name + "] 님은 블랙리스트로");
						System.out.println("       도서 대여가 불가능합니다.");
						return;
					}
				}	
				
				if(rs2.next()) {
					System.out.println("───────[ 대여를 신청한 도서 ]─────────────");
					String bno = rs2.getString(1);
					String btitle = rs2.getString(2);
					String bmany = rs2.getString(3);
					if(rs2.getInt(3) > 0) {
						System.out.println("도서 코드 : " + bno);
						System.out.println("도서 이름 : " + btitle);
						System.out.println("도서 수량 : " + bmany);
					}else {
						System.out.println("잔여 도서가 존재하지 않으므로 대여가 불가능합니다.");
						return;
					}
					System.out.println("──────────────────────────────────────────");
				} else {
					System.out.println("        * 도서가 없습니다. *");
					return;
				}
				login.close();
				rs2.close();
				rs3.close();
			}
		} catch (Exception e) {
			System.out.println("     도서 정보가 없습니다.");
			return;
		}
		
		// rentdb에 유저와 대여한 도서 데이터를 넣음
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql2 = "select * from bookdb where many <> 0 and title = ?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setString(1, title);
			ResultSet rs5 = pstmt2.executeQuery();
			
			String sql3 = "insert into rentdb (bno, title, userid) values (?, ?, ?)";
			pstmt3 = con.prepareStatement(sql3);
			if(login.next()) {
				String userid = login.getString(1);
				if(rs5.next()) {
					pstmt3.setString(1, rs5.getString(1));
					pstmt3.setString(2, rs5.getString(2));
					pstmt3.setString(3, userid);
					int updateCount = pstmt3.executeUpdate();
				}
			}
			login.close();
			rs5.close();
		} catch (Exception e) {
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		// bookdb에서 대여한 도서 권 수 차감, 대여횟수 증가
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql2 = "select * from bookdb where many <> 0 and title = ?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setString(1, title);
			ResultSet rs2 = pstmt2.executeQuery();
			
			String sql3 = "update bookdb set many = many-1 where bno = ?";
			pstmt3 = con.prepareStatement(sql3);
			if(rs2.next()) {
				pstmt3.setString(1, rs2.getString(1));
				int updateCount3 = pstmt3.executeUpdate();
			}
			
			// 같은 책 인기+
			String sql4 = "update bookdb set popular = popular+1 where title = ?";
			pstmt4 = con.prepareStatement(sql4);
			pstmt4.setString(1, title);
			int updateCount = pstmt4.executeUpdate();
			
			// userdb에서 대여하고 있는 책 수 +
			String sql5 = "update userdb set bmany = bmany+1 where userid = ?";
			pstmt5 = con.prepareStatement(sql5);
			if(login.next()) {
				pstmt5.setString(1, login.getString(1));
				int updateCount5 = pstmt5.executeUpdate();
			}
			login.close();
			rs2.close();
		} catch (Exception e) {
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		// 대여일
		try {
			ResultSet login = pstmt.executeQuery();
			
			pstmt1.setString(1, title);
			ResultSet rs1 = pstmt1.executeQuery();
			
			String sql2 = "select * from rentdb where userid = ?";
			pstmt2 = con.prepareStatement(sql2);
			if(login.next()) {
				pstmt2.setString(1, login.getString(1));
				ResultSet rs2 = pstmt2.executeQuery();
				if(rs2.next()) {
					System.out.println("대여일 : " + rs2.getString(4).substring(0, 10));
					System.out.println("반납일 : " + rs2.getString(5).substring(0, 10));
					System.out.println("대여 리스트에 기록되었습니다.");
					System.out.println("──────────────────────────────────────────");
				}
			rs2.close();
			}
			login.close();
			rs1.close();
		} catch (Exception e) {
			System.out.println("도서 정보가 없습니다.");
		}
	}
	// 도서 반납
	public void rtBook() {
		// 회원이 대여한 도서 정보
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql2 = "select * from rentdb where userid = ?";
			pstmt2 = con.prepareStatement(sql2);
			if(login.next()) {
				String userid = login.getString(1);
				pstmt2.setString(1, userid);
				ResultSet rs2 = pstmt2.executeQuery();
				
				// 회원이 대여한 도서 정보
				System.out.println("───────[ 대여한 도서 리스트 ]─────────────");
				while(rs2.next()) {
					System.out.println("도서 코드 : " + rs2.getString(1));
					System.out.println("도서 이름: " + rs2.getString(2));
				}
				rs2.close();
			} else {
				System.out.println("      회원 로그인을 해주세요.");
				return;
			}
			
			String sql3 = "select to_char(to_date(sysdate)) from dual";
			pstmt3 = con.prepareStatement(sql3);
			ResultSet rs3 = pstmt3.executeQuery();
			if(rs3.next()) {
				System.out.println("도서 반납일 : " + rs3.getString(1).substring(0, 8));
			}
			System.out.println("──────────────────────────────────────────");
			rs3.close();
			login.close();
		} catch (Exception e) {
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		System.out.print("     반납할 책 코드 : ");
		String bno = sc.nextLine();
		
		// 반납일 지난 도서 알림
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql2 = "select * from rentdb where to_date(rtdate) < to_date(sysdate) and bno = ?";
			pstmt2 = con.prepareStatement(sql2);
			if(login.next()) {
				pstmt2.setString(1, bno);
				ResultSet rs2 = pstmt2.executeQuery();
				if(rs2.next()) {
					System.out.println("     반납일이 지난 도서입니다.");
					System.out.println("     기존 반납일 : " + rs2.getString(5).substring(0, 10));	
				}
			}
		} catch(Exception e) {
		}
		
		// 반납 기간이 지났으면 블랙
		try {
			ResultSet login = pstmt.executeQuery();
			if(login.next()) {
				String userid = login.getString(1);
				
				String sql3 = "select * from rentdb "
				        + "where to_char(to_date(sysdate)) > to_char(to_date(rtdate)) and bno = ?";
				pstmt3 = con.prepareStatement(sql3);
				pstmt3.setString(1, bno);
				ResultSet rs3 = pstmt3.executeQuery();
				
				String sql2 = "select * from userdb where userid = ?";
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, userid);
				ResultSet rs2 = pstmt2.executeQuery();
				if(rs2.next()) {
					int black = rs2.getInt(5);
					if(black == 0) {
						if(rs3.next()) {
							String userid1 = rs3.getString(3);
							String sql = "update userdb "
										  + "set black = black+1, bdate = to_date(sysdate+10) where userid = ?";
							pstmt4 = con.prepareStatement(sql);
							pstmt4.setString(1, userid1);
							int updateCount = pstmt4.executeUpdate();
						}
					rs2.close();
					rs3.close();
					} else {
						String sql = "update userdb "
								  + "set bdate = to_date(sysdate+10) where userid = ?";
					pstmt4 = con.prepareStatement(sql);
					pstmt4.setString(1, userid);
					int updateCount = pstmt4.executeUpdate();
					}
				}
			}
			login.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		// 반납 리스트에 추가
		try {
			String sql2 = "select * from rentdb where bno = ?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setString(1, bno);
			ResultSet rs2 = pstmt2.executeQuery();
			
			if(rs2.next()) {
				String sql3 = "insert into returndb (bno, title, userid) values (?, ?, ?)";
				pstmt3 = con.prepareStatement(sql3);
				pstmt3.setString(1, rs2.getString(1));
				pstmt3.setString(2, rs2.getString(2));
				pstmt3.setString(3, rs2.getString(3));
				int updateCount = pstmt3.executeUpdate();
			}
				rs2.close();
			System.out.println("     반납 리스트에 추가되었습니다." );
			System.out.println("──────────────────────────────────────────");
		} catch (Exception e) {
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		// 반납한 책의 갯수 증가
		try {
			String sql2 = "select * from rentdb where bno = ?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setString(1, bno);
			ResultSet rs2 = pstmt2.executeQuery();
			
			String sql3 = "update bookdb set many = many+1 where bno = ?";
			pstmt3 = con.prepareStatement(sql3);
			pstmt3.setString(1, bno);
			int updateCount = pstmt3.executeUpdate();	 
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		// userdb에 bmany-1
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql2 = "update userdb set bmany = bmany-1 where userid = ?";
			pstmt2 = con.prepareStatement(sql2);
			if(login.next()) {
				pstmt2.setString(1, login.getString(1));
				int updateCount = pstmt2.executeUpdate();
			}
			login.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		// 대여 리스트에서 제거
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql2 = "select * from rentdb where userid = ?";
			pstmt2 = con.prepareStatement(sql2);
			if(login.next()) {
				pstmt2.setString(1, login.getString(1));
				ResultSet rs2 = pstmt2.executeQuery();
				
				String sql3 = "delete from rentdb where bno = ?";
				pstmt3 = con.prepareStatement(sql3);
				while(rs2.next()) {
					pstmt3.setString(1, rs2.getString(1));
					int updateCount = pstmt3.executeUpdate();
				}
				rs2.close();
			}
			login.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		try {
			ResultSet login = pstmt.executeQuery();
			
			if(login.next()) {
				String userid = login.getString(1);
				
				String sql2 = "select * from userdb where userid = ?";
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, userid);
				ResultSet rs2 = pstmt2.executeQuery();
				if(rs2.next()) {
					int black = rs2.getInt(5);
					String bdate = rs2.getString(6);
					if(black == 1) {
						System.out.println(userid + "님은 블랙리스트로 전환되었습니다.");
						System.out.println(bdate.substring(0, 10) + "까지 대여가 불가능 합니다.");
					}
				}
				rs2.close();
			}
			login.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 도서 대여 연장
	public void extendBookDate() {
		// 로그인한 회원
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql = "select * from rentdb where userid = ?";
			pstmt2 = con.prepareStatement(sql);
			if(login.next()) {
				pstmt2.setString(1, login.getString(1));
				ResultSet rs1 = pstmt2.executeQuery();
				
				System.out.println("──────[ 대여중인 도서 리스트 ]──────");
				// 대여 정보 출력
				while(rs1.next()) {
					System.out.println("도서 코드 : " + rs1.getString(1));
					System.out.println("도서 이름 : " + rs1.getString(2));
					System.out.println("기존 반납일 : " + rs1.getString(5).substring(0, 10));
				}
				System.out.println("────────────────────────────────────");
				rs1.close();
			} else {
			login.close();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		// 연장 가능 유무 확인
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql1 = "select * from rentdb where userid = ?";
			pstmt2 = con.prepareStatement(sql1);
			if(login.next()) {
				pstmt2.setString(1, login.getString(1));
				ResultSet rs1 = pstmt2.executeQuery();
				if(rs1.next()) {
					if(rs1.getInt(6) > 0) {
						System.out.println("        연장 가능한 횟수 : " + rs1.getString(6));
						System.out.println("        연장이 가능합니다.");
					} else {
						System.out.println("        연장 가능한 횟수 : " + rs1.getString(6));
						System.out.println("        연장이 불가능합니다.");
						return;
					}
				}
			}
		} catch(Exception e) {
			//e.printStackTrace();
		}
		
		// 대여 기간 연장
		try {
			System.out.print("연장할 도서 코드 : ");
			String code = sc.nextLine();
			
			ResultSet login = pstmt.executeQuery();
			
			String sql3 = "update rentdb set ex = ex-1, rtdate = rtdate+3 where bno = ?";
			pstmt2 = con.prepareStatement(sql3);
			pstmt2.setString(1, code);
			int updateCount = pstmt2.executeUpdate();
		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		try {
			ResultSet login = pstmt.executeQuery();
			
			String sql4 = "select * from rentdb where userid = ?";
			pstmt3 = con.prepareStatement(sql4);
			if(login.next()) {
				pstmt3.setString(1, login.getString(1));
				ResultSet rs1 = pstmt3.executeQuery();
				if(rs1.next()) {
					System.out.println("연장된 반납일 : " + rs1.getString(5).substring(0, 10));
				}
				rs1.close();
			}
			login.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
	}
}
