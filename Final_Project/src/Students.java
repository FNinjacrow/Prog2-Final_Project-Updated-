
public class Students extends Person implements Payment
{
	String schoolName;
	int grade;
	
	Students(int id, String name, String member_type, String schoolName, int grade) 
	{
		super(id, name, member_type);
		this.schoolName = schoolName;
        this.grade = grade;
	}

	@Override
	public String info() {
        return "Student at " + schoolName + ", Grade: " + grade;
    }

	@Override
	public double calculate(int nights) 
	{
		if (nights <= 7)
		{
			return 5.0;
		}
		else
		{
			return 5.0 + (nights - 7) * 1.0;
		}
	}
	
	@Override
    public String toString() 
	{
        return super.toString() + ", " + info();
    }
}
