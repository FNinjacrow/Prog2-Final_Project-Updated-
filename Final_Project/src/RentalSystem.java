import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class RentalSystem 
{

	ArrayList<Students> students = new ArrayList();
	ArrayList<External_Member> members = new ArrayList();
    ArrayList<Movie> movies = new ArrayList();
    ArrayList<Rental> rentals = new ArrayList();
    Scanner in = new Scanner(System.in);
    
    public RentalSystem(ArrayList<Movie> movies, ArrayList<Students> students, ArrayList<External_Member> members) 
    {
    	this.movies = movies;
		this.students = students;
        this.members = members;
	}

	public void addMember() 
    {
    	boolean f = true;
        while(f) 
        {
        	try 
            {
        		System.out.print("Enter a new Member ID: ");
        		int id = in.nextInt();
        		for (Students s : students) 
                {
                    if (s.getId() == id)
                    {
                    	throw new DuplicateMemberException("Member ID " + id + " is already used.");
                    }
                }
        		for (External_Member m : members) 
                {
                    if (m.getId() == id)
                    {
                    	throw new DuplicateMemberException("Member ID " + id + " is already used.");
                    }
                }
        		System.out.print("Enter name: ");
        		String name = in.next();
        		System.out.println("What is the member type? (Student or External)");
        		String member_type = in.next();
        		if(member_type.equalsIgnoreCase("Student"))
        		{
        			System.out.print("Enter school name: ");
        			String schoolName = in.next();
        			System.out.print("Enter grade: ");
        			int grade = in.nextInt();
        			Students s = new Students(id, name, member_type, schoolName, grade);
            		students.add(s);
            		System.out.println("Member added: " + s);
                    f=false;
        		}
        		else if(member_type.equalsIgnoreCase("External"))
        		{
        			System.out.print("Enter job: ");
        			String job = in.next();
        			System.out.print("Enter organization name: ");
        			String organizationName = in.next();
        			External_Member m = new External_Member(id, name, member_type, job, organizationName);
            		members.add(m);
            		System.out.println("Member added: " + m);
                    f=false;
        		}
        		else
        		{
        			System.out.println("Invalid input. Please try again.");
        			f=true;
        		}
            } 
            catch (DuplicateMemberException e) 
            {
                System.out.println("Error: " + e.getMessage());
                f=true;
            }
        }
    }
    
    public void addMovie() 
    {
    	boolean f = true;
    	while(f)
    	{
    		try 
            {
    			System.out.print("Enter a new Movie ID: ");
    			int id = in.nextInt();
                for (Movie m : movies) 
                {
                    if (id == m.getId()) 
                    {
                    	throw new DuplicateMovieException("Movie ID " + id + " is already used.");
                    }
                }
                System.out.print("Enter movie name: ");
                String name = in.next();
                for (Movie m : movies) 
                {
                    if (name == m.getTitle()) 
                    {
                    	System.out.println("There's a movie of the same name. Please try again.");
                    	f=false;;
                    }
                }
                Movie m = new Movie(id, name);
                movies.add(m);
                System.out.println("A movie has been successfully added into the catalog.");
                f=false;
            } 
    		catch (DuplicateMovieException e) 
            {
                System.out.println("Error: " + e.getMessage());
                f=true;
            }
    	}
    }

    public void addRental() 
    {
        try 
        {
        	System.out.println("Available movies:");
            boolean anyAvailable = false;
            for (Movie m : movies) 
            {
                if (m.isAvailable()) 
                {
                    System.out.println(m.getId() + "- [" + m.getTitle() + "]");
                    anyAvailable = true;
                }
            }
            if (!anyAvailable) 
            {
                System.out.println("No movies are currently available for rent.");
                return;
            }
            System.out.print("Enter Movie ID: ");
            int movieID = in.nextInt();
            Movie movie = null;
            for (Movie m : movies) 
            {
                if (m.getId()==movieID) 
                {
                    movie = m;
                    break;
                }
            }
            if (movie == null) 
            {
                System.out.println("That movie does not exist. Please try again.");
                return;
            }
            if (!movie.isAvailable()) 
            {
                System.out.println("That movie has already rented. Please try again.");
                return;
            }
            System.out.print("Enter Member ID: ");
            int memberID = in.nextInt();
            Person member = null;
            for (Person p : students) 
            {
                if (p.getId() == memberID) 
                {
                    member = p;
                    break;
                }
            }
            for (Person p : members) 
            {
                if (p.getId() == memberID) 
                {
                    member = p;
                    break;
                }
            }
            if (member == null) 
            {
                System.out.println("Member with ID " + memberID + " does not exist!");
                return;
            }
            System.out.print("Enter Rent Date (YYYY-MM-DD): ");
            String rentDateStr = in.next();
            LocalDate date = LocalDate.parse(rentDateStr);
            Rental r = new Rental(movie, member, date);
            rentals.add(r);
            movie.setAvailable(false);
            System.out.println("A rental has been successfully added.");
        } 
        catch (Exception ex) 
        {
            System.out.println("Error renting movie: " + ex.getMessage());
        }
    }
    
    public void listMovies() 
    {
        try 
        {
            for (Movie movie : movies) 
            {
                System.out.println(movie.getId() + " " + movie.getTitle());
            }
        } 
        catch (Exception ex) 
        {
            System.out.println("Error listing movies: " + ex.getMessage());
        }
    }
    
    public void listMembers() 
    {
        try 
        {
            for (Students stu : students) 
            {
                System.out.println(stu);
            }
            for (External_Member em : members) 
            {
                System.out.println(em);
            }
        } 
        catch (Exception ex) 
        {
            System.out.println("Error listing members: " + ex.getMessage());
        }
    }
    
    public void checkAvailability() 
    {
        try {
        	System.out.print("Enter Movie ID: ");
        	int movieId = in.nextInt();
            Movie movie = findMovieById(movieId);
            if (movie == null) 
            {
            	throw new MovieNotFoundException("Movie not found!");
            } 
            else 
            {
                if(movie.isAvailable()==true) 
                {
                	System.out.println("The movie '" + movie.getTitle() + "' is available for rent. ");
                }
                else
                {
                	System.out.println("The movie '" + movie.getTitle() + " is not available for rent. ");
                }            }
        } 
        catch (MovieNotFoundException ex) 
        {
            System.out.println("Error checking availability: " + ex.getMessage());
        }
    }
    
    public void returnRentedMovie() 
    {
        try 
    	{
            System.out.print("Enter the ID of the movie that will be returned: ");
            int movieId = in.nextInt();
            System.out.print("Enter Return Date (YYYY-MM-DD): ");
            String returnDateStr = in.next();
            LocalDate returnDate = LocalDate.parse(returnDateStr);
            for (Rental rental : rentals) 
            {
                if (rental.getMovie().getId() == movieId && rental.getReturnDate() == null) 
                {
                	double fee = rental.returnMovie(returnDate);
                    System.out.println("The rented movie has been returned.");
                    System.out.println("Receipt: " + fee + "$");
                    rental.getMovie().setAvailable(true);
                    rentals.remove(rental);
                    return;
                }
            }
            System.out.println("No active rental found for movie ID " + movieId);
        } 
        catch (Exception ex) 
        {
            System.out.println("Error returning movie: " + ex.getMessage());
        }
    }
    
    private Movie findMovieById(int id) 
    {
        
    	for (Movie m : movies) 
        {
            if (m.getId() == id) 
            {
            	return m;
            }
            else
            {
            	return null;
            }
        }
        return null;
    }
}
