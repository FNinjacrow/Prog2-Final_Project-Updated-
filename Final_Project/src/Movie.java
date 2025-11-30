
public class Movie 
{
	int movie_id;
	String movie_name;
	boolean available = true;
	
	public Movie(int movie_id, String movie_name) 
	{
        this.movie_id = movie_id;
        this.movie_name = movie_name;
        this.available = true;
    }
	
	public boolean isAvailable() {
	    return available;
	}

	public void setAvailable(boolean available) 
	{
	    this.available = available;
	}

	public int getId() 
	{
		return movie_id;
	}

	
	public String getTitle() 
	{
	    return movie_name;
	}
}
