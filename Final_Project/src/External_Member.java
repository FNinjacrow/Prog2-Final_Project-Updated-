
public class External_Member extends Person implements Payment
{
	String job;
	String organizationName;
	
	External_Member(int id, String name, String member_type, String job, String organizationName) 
	{
		super(id, name, member_type);
		this.job = job;
		this.organizationName = organizationName;
	}

	@Override
	public String info() 
	{
        return "Job: " + job + ", Organization: " + organizationName;
	}

	@Override
	public double calculate(int nights) 
	{
		if (nights <= 7)
		{
			return 10.0;
		}
		else
		{
			return 10.0 + (nights - 7) * 2.0;
		}
	}
	
	@Override
	public String toString() 
	{
        return super.toString() + ", " + info();
    }
}
