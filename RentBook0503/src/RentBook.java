import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class RentBook
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
	
	Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		System.out.println(" ┌───────────────────────────────┐");
		System.out.println(" │          ♥ WELCOME ♥          │");
		System.out.println(" │      도서 대여점 입니다!      │");
		System.out.println(" └───────────────────────────────┘");
		
		RentBook rb = new RentBook();
		rb.selectMenu();
	}
	
	public void connectDatabase() {
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott",
					"tiger");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void selectMenu ()
	{
		
		int choice;
		
		while(true) {
			connectDatabase();
			black();
			showMenu();
			
			UsberDb ud = new UsberDb();
			BookDb bd = new BookDb();
			Manager m = new Manager();
			
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice)
			{
			case 1:
				ud.userMenu();
				break;
			case 2:
				bd.bookMenu();
				break;
			case 3:
				m.managerMenu();
				break;
			case 4:
				notice();
				break;
			case 0:
				logout();
				System.out.println("       프로그램을 종료합니다.");
				return;
			default:
				System.out.println("      잘 못 입력하셨습니다.");
				break;
			}
		}
	}
	
	public void showMenu()
	{
		System.out.println("      ┌─────────────────────┐");
		System.out.println("      │    [ 메인 메뉴 ]    │");
		System.out.println("      │                     │");
		System.out.println("      │    ① 회원 메뉴     │");
		System.out.println("      │    ② 도서 메뉴     │");
		System.out.println("      │    ③ 관리자 메뉴   │");
		System.out.println("      │    ④ 공지사항      │");
		System.out.println("      │    ⓞ 종료          │");
		System.out.println("      └─────────────────────┘");
		System.out.print  ("             선택 : ");
	}
	
	public void notice ()
	{
		try { 
			String sql = "select * from bookdb "
							   + "where to_char(to_date(sysdate) - interval '3' day) < to_char(to_date(stdate))";
			pstmt1 = con.prepareStatement(sql);
			ResultSet rs = pstmt1.executeQuery();
			
			System.out.println("─────────────────────────────────────────────────────────────");
			System.out.println("                          신간 알림                          ");
			System.out.println("                                                             ");
			while (rs.next()) {
				System.out.print("  도서 코드 : " + rs.getString(1) + ", ");
				System.out.println("  도서 이름 : " + rs.getString(2));
			}
			System.out.println("─────────────────────────────────────────────────────────────");
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
	}
	
	public void black() {
		// 반납 기간 지나면 블랙
		try {
			String sql1 = "select * from rentdb "
						        + "where to_char(to_date(sysdate)) > to_char(to_date(rtdate))";
			pstmt1 = con.prepareStatement(sql1);
			
			ResultSet rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				String userid = rs1.getString(3);
				String sql = "update userdb "
							  + "set black = black+1 where userid = ?";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setString(1, userid);
				int updateCount = pstmt2.executeUpdate();
			}
			rs1.close();
		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
		
		// 블랙 기간 지나면 풀림
		String sql2 = "select * from userdb "
							+ "where to_char(to_date(sysdate)) >= to_char(to_date(bdate))";
		try {
			pstmt1 = con.prepareStatement(sql2);
			ResultSet rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				String sql = "update userdb "
							  + "set black = black-1, bdate = null where userid = ?";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setString(1, rs1.getString(1));
				int updateCount = pstmt2.executeUpdate();
			}
			rs1.close();
		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 에러가 발생했습니다.");
		}
	}
	
	public void logout() {
		try {
			String sql = "select * from login";
			pstmt = con.prepareStatement(sql);
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
}