
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental 
{
	private Movie movie;
    private Person member;
    private LocalDate rentDate;
    private LocalDate returnDate;
	
    public Rental(Movie movie, Person member, LocalDate rentDate)
    {
    	this.movie = movie;
    	this.member = member;
    	this.rentDate = rentDate;
    }
    
    public double returnMovie(LocalDate returnDate) 
    {
    	if (returnDate.isBefore(rentDate)) 
    	{
            throw new IllegalArgumentException("The Return Date cannot be before the Rent Date.");
        }
    	movie.setAvailable(true);
        this.returnDate = returnDate;
        long daysBetween = ChronoUnit.DAYS.between(rentDate, returnDate);
		int nights = (int) daysBetween;
		if (nights < 0) 
		{	
			 nights = 0;
		}
		double fee = ((Payment) member).calculate(nights);
		return fee;
    }
    
    public Movie getMovie() 
    {
        return movie;
    }
    
    public Person getMember() 
    {
        return member;
    }
    
	public LocalDate getReturnDate() 
	{
		return returnDate;
	}
	
	public int getNights() 
	{
        if (returnDate == null)
        {
        	return 0;
        }
        long nights = ChronoUnit.DAYS.between(rentDate, returnDate);
        return (int) nights;
    }
}
