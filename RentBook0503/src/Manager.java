import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Manager
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void managerMenu() {
		connectDatabase();
		
		int choice;
		while(true) {
			showManagerMenu();
			
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice)
			{
			case 1:
				delMember();
				break;
			case 2:
				addBook();
				break;
			case 3:
				delBook();
				break;
			case 4:
				delOldBook();
				break;
			case 0:
				return;
			default:
				System.out.println("      잘 못 입력하셨습니다.");
				break;
			}
		}
	}
	
	public void showManagerMenu() {
		System.out.println("      ┌─────────────────────┐");
		System.out.println("      │   [ 관리자 메뉴 ]   │");
		System.out.println("      │                     │");
		System.out.println("      │    ① 회원 삭제     │");
		System.out.println("      │    ② 도서 추가     │");
		System.out.println("      │    ③ 도서 삭제     │");
		System.out.println("      │    ④ 낡은 도서     │");
		System.out.println("      │    ⓞ 메인 메뉴     │");
		System.out.println("      └─────────────────────┘");
		System.out.print  ("             선택 : ");
	}
	// 회원 삭제
	public void delMember() {
		System.out.print("   삭제할 회원 ID : ");
		String userid = sc.nextLine();
		
		String sql = "delete from userdb where userid = ?";
		try {
			pstmt3 = con.prepareStatement(sql);
			pstmt3.setString(1, userid);
			int updateCount = pstmt3.executeUpdate();
			System.out.println("      회원이 삭제되었습니다.");
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("      잘못된 정보입니다.");
		}
	}
	// 도서 추가
	public void addBook() {
		System.out.print  ("  추가하는 도서 이름 : ");
		String title = sc.nextLine();
		System.out.print  ("  추가하는 도서 저자 : ");
		String writer = sc.nextLine();
		System.out.println();
		
		String sql = "insert into bookdb(bno, title, writer) VALUES (bookdb_seq.nextval, ?, ?)";
		try {
			pstmt3 = con.prepareStatement(sql);
			pstmt3.setString(1, title);
			pstmt3.setString(2, writer);
			int updateCount = pstmt3.executeUpdate();
			System.out.println("    ┌─────────────────────────┐");
			System.out.println("    │  도서가 추가되었습니다. │");
			System.out.println("    └─────────────────────────┘");
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("      잘못된 정보입니다.");
		}
	}
	// 도서 정보 삭제
	public void delBook() {
		System.out.print("   삭제할 도서 번호 : ");
		String title = sc.nextLine();
		
		String sql = "delete from bookdb where bno = ?";
		try {
			pstmt3 = con.prepareStatement(sql);
			pstmt3.setString(1, title);
			int updateCount = pstmt3.executeUpdate();
			System.out.println("      도서가 삭제되었습니다.");
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("      잘못된 정보입니다.");
		}
	}
	// 낡은 책 삭제
	public void delOldBook() {
		try {
			String sql1 = "select * from bookdb "
									 + "where to_char(to_date(stdate) + interval '2' YEAR) < to_char(to_date(sysdate))"
							      + "order by title asc";
			pstmt1 = con.prepareStatement(sql1);
			ResultSet rs = pstmt1.executeQuery();
			if(rs.next()) {
			} else {
				System.out.println(" ┌────────────────────────────────┐");
				System.out.println(" │  삭제할 도서 정보가 없습니다.  │");
				System.out.println(" └────────────────────────────────┘");
				rs.close();
				return;	
			}
			
			ResultSet rs1 = pstmt1.executeQuery();
			System.out.println("───────[ 처분할 도서 리스트 ]─────────────");
			while(rs1.next()) {
				System.out.println("도서 번호 :" + rs1.getString(1));
				System.out.println("도서 이름 :" + rs1.getString(2));
				System.out.println("도서 입고일 :" + rs1.getString(5).substring(0, 10));
			}
			System.out.println("──────────────────────────────────────────");
			rs1.close();
			
			ResultSet rs2 = pstmt1.executeQuery();
			if(rs2.next()) {
				String sql2 = "delete from bookdb "
						  + "where to_char(to_date(stdate) + interval '2' YEAR) < to_char(to_date(sysdate))";
				pstmt2 = con.prepareStatement(sql2);
				int updateCount = pstmt2.executeUpdate();
				
				System.out.println(" ┌────────────────────────────────┐");
				System.out.println(" │   도서 정보가 삭제되었습니다.  │");
				System.out.println(" └────────────────────────────────┘");
				rs2.close();
				return;
			}	
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("     알 수 없는 에러가 발생했습니다.");
		}
	}
}
