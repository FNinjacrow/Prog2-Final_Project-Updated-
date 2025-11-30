
public abstract class Person 
{
	int id;
	String name;
	String member_type;
	
	Person(int id, String name, String member_type)
	{
		this.id = id;
		this.name = name;
		this.member_type = member_type;
	}
	
	public abstract String info();
	
	public String toString()
	{
		return "ID: " + id + ", Name: " + name + ", Type: " + member_type;
	}
	
	public int getId() 
	{ 
		return id; 
	}
	
	public String getName() 
	{ 
		return name; 
	}
}
