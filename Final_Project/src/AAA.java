import java.util.ArrayList;
import java.util.Scanner;

public class AAA //Driver
{

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		ArrayList<Movie> movies = new ArrayList<>();
		ArrayList<Students> students = new ArrayList();
		ArrayList<External_Member> members = new ArrayList();
		RentalSystem system = new RentalSystem(movies, students, members);
		FileSystem file = new FileSystem(movies, students, members);
		file.fileCheck();
		file.fileGet(movies, students, members);
		boolean f = true;
		while(f) 
		{
			System.out.println("Enter choice:\n1- Add Member\n2- Add Movie\n3- Add Rental\n4- List Movies\n5- List Members\n6- Check Availability\n7- Return Rented Movie\n8- Exit");
			System.out.print("Choice: ");
			int c = in.nextInt();
			switch(c) 
			{
				case 1:
					system.addMember();
					file.fileSave();
					break;
				
				case 2:
					system.addMovie();
					file.fileSave();
					break;
					
				case 3:
					system.addRental();
					break;
					
				case 4:
					system.listMovies();
					break;
					
				case 5:
					system.listMembers();
					break;
					
				case 6:
					system.checkAvailability();
					break;
					
				case 7:
					system.returnRentedMovie();
					break;
					
				case 8:
					f=false;
					break;
					
				default:
					System.out.println("Not a valid input.");
			}
		}
			
	}

}
