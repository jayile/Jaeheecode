import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UsberDb
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
	
	Scanner sc = new Scanner(System.in);
	
	public void connectDatabase() {
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott",
					"tiger");
			String sql = "select * from login";
			pstmt = con.prepareStatement(sql);
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
	}
	
	public void userMenu()
	{
		connectDatabase();
		
		int choice;
		while(true) {
			showUserMenu();
			
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice)
			{
			case 1:
				loginUser();
				break;
			case 2:
				logout();
				break;
			case 3:
				addUser();
				break;
			case 4:
				selUser();
				break;
			case 0:
				return;
			default:
				System.out.println("      잘 못 입력하셨습니다.");
				break;
			}
		}
	}
	
	public void showUserMenu() {
		System.out.println("      ┌─────────────────────┐");
		System.out.println("      │    [ 회원 메뉴 ]    │");
		System.out.println("      │                     │");
		System.out.println("      │  ① 회원 로그인     │");
		System.out.println("      │  ② 회원 로그아웃   │");
		System.out.println("      │  ③ 회원 등록       │");
		System.out.println("      │  ④ 회원 조회       │");
		System.out.println("      │  ⓞ 메인 메뉴로     │");
		System.out.println("      └─────────────────────┘");
		System.out.print  ("            선택 : ");
	}
	// 로그인
	public void loginUser() {
		try {
			ResultSet login = pstmt.executeQuery();
			if(login.next()) {
				System.out.println("    이미 로그인된 회원이 있습니다.");
				System.out.println("    로그아웃을 해주세요.");
				return;
			}
		} catch(Exception e) {
		}
		
		System.out.println("      ┌─────────────────────┐");
		System.out.println("      │     회원 로그인     │");
		System.out.println("      └─────────────────────┘");
		System.out.print  ("             ID : ");
		String userid = sc.nextLine();
		System.out.print  ("             PW : ");
		String userpw = sc.nextLine();
		System.out.println();
		
		try {
			String sql1 = "select * from userdb where userid = ?";
			pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1, userid);
			ResultSet rs1 = pstmt1.executeQuery();
			
			if(rs1.next()) {
				if(rs1.getString(1).equals(userid) && rs1.getString(2).equals(userpw)) {
					String sql2 = "insert into login (userid, userpw) values (?, ?)";
					pstmt2 = con.prepareStatement(sql2);
					pstmt2.setString(1, userid);
					pstmt2.setString(2, userpw);
					int updateCount = pstmt2.executeUpdate();
					
					System.out.println(" ──────────────────────────────────");
					System.out.println("    " + rs1.getString(3) + "님으로 로그인 되었습니다.");
					System.out.println(" ──────────────────────────────────");
				} else {
					System.out.println("     비밀번호가 맞지 않습니다.");
				}
			} else {
				System.out.println("        ID가 맞지 않습니다.");
			}			
			rs1.close();
		} catch (Exception e) {
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
	}
	// 로그아웃
	public void logout() {
		try {
			ResultSet login = pstmt.executeQuery();
			if(login.next()) {
				String sql2 = "delete from login";
				pstmt2 = con.prepareStatement(sql2);
				int updateCount = pstmt2.executeUpdate();
				System.out.println(" ┌────────────────────────────────┐");
				System.out.println(" │      로그아웃 되었습니다.      │");
				System.out.println(" └────────────────────────────────┘");
			} else {
				System.out.println(" ┌────────────────────────────────┐");
				System.out.println(" │    로그아웃할 ID가 없습니다.   │");
				System.out.println(" └────────────────────────────────┘");
			}
		login.close();
		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
	}
	// 회원 등록
	public void addUser() {
		try {
			String sql = "select * from login";
			pstmt = con.prepareStatement(sql);
			ResultSet login1 = pstmt.executeQuery();
			if(login1.next()) {
				System.out.println("      회원 로그아웃을 해주세요.");
				return;
			}
		} catch (Exception e) {
		}
		
		System.out.print("        회원 ID : ");
		String userid = sc.nextLine();
		System.out.print("       비밀번호 : ");
		String userpw = sc.nextLine();
		System.out.print("       회원이름 : ");
		String uName = sc.nextLine();
		System.out.println();
		
		String sql = "insert into userdb (userid, userpw, uName) values (?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpw);
			pstmt.setString(3, uName);
			int updateCount = pstmt.executeUpdate();
			System.out.println("    회원 등록이 완료되었습니다.");
		} catch (Exception e) {
			System.out.println("        중복되는 ID 입니다.");
		}
	}
	// 회원 조회
	public void selUser() {
		System.out.println("      ┌─────────────────────┐");
		System.out.println("      │    대여중인 도서    │");
		System.out.println("      └─────────────────────┘");
		
		try {
			String sql = "select * from login";
			pstmt = con.prepareStatement(sql);
			ResultSet login1 = pstmt.executeQuery();
			if(login1.next()) {
			} else {
				System.out.println("      회원 로그인을 해주세요.");
				return;
			}
			
			ResultSet login = pstmt.executeQuery();
			if(login.next()) {
				String userid = login.getString(1);
				System.out.println("      " + userid + "님의 도서 대여 정보");
				
				String sql1 = "select * from rentdb where userid = ?";
				pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1, userid);
				ResultSet info1 = pstmt1.executeQuery();
				System.out.println(" ────────────────────────────────── ");
				while(info1.next()) {
					String title = info1.getString(2);
					String rdate = info1.getString(4);
					String rtdate = info1.getString(5);
					System.out.println("      대여한 책 : " + title);
					System.out.println("      대여일 : " + rdate.substring(0, 10));
					System.out.println("      반납일 : " + rtdate.substring(0, 10));
					System.out.println(" ────────────────────────────────── ");
				}
				String sql2 = "select * from rentdb where userid = ?";
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, userid);
				ResultSet info2 = pstmt2.executeQuery();
				if(info2.next()) {
				} else {
					System.out.println("    대여한 도서 정보가 없습니다.");
				}
			}
		} catch(Exception e) {
			System.out.println("      회원 로그인을 해주세요.");
		}
	}
}