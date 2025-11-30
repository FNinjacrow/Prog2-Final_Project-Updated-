import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileSystem 
{

	ArrayList<Movie> movies = new ArrayList();
	ArrayList<Students> students = new ArrayList();
	ArrayList<External_Member> members = new ArrayList();
	
	public FileSystem(ArrayList<Movie> movies, ArrayList<Students> students, ArrayList<External_Member> members) 
	{
		this.movies = movies;
		this.students = students;
        this.members = members;
	}

	public void fileCheck() {
	    try 
	    {
	        String filename = "System_List";
	        File file = new File("C:\\Users\\Fninj\\Desktop\\SystemList.txt"); //Change directory to the appropriate one
	        if (file.exists()) 
	        {
	            System.out.println("File '" + filename + "' already exists.");
	            return;
	        }
	        if (file.createNewFile()) 
	        {
	            System.out.println("File '" + filename + "' created successfully at " + file.getAbsolutePath());
	        } 
	        else 
	        {
	            System.out.println("Failed to create file '" + filename + "'.");
	        }
	    } 
	    catch (IOException e) 
	    {
	        System.out.println("Error checking/creating file: " + e.getMessage());
	    }
	}
	
	public void fileSave() 
	{
		try 
		{
			String filename = "System_List";
			File file = new File("C:\\Users\\Fninj\\Desktop\\SystemList.txt"); //Change directory to the appropriate one
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			writer.println("Movie List: ");
			for (Movie m : movies) 
			{
				writer.println("ID: " + m.getId() + ", Title: " + m.getTitle());
			}
			writer.println();
			writer.println("Member List: ");
			writer.println("Students: ");
			for (Students s : students) 
			{
				 writer.println("ID: " + s.getId() + ", Name: " + s.getName() + ", Type: Student, " + s.info());
			}
			writer.println("External Members: ");
			for (External_Member m : members) 
			{
				writer.println("ID: " + m.getId() + ", Name: " + m.getName() + ", Type: External, Job: " + m.info());
			}
			writer.close();
			System.out.println("All data has been successfully saved to " + filename);
		} 
		catch (IOException e) 
		{
			System.out.println("Error exporting data: " + e.getMessage());
		}
	}

	public static void fileGet(ArrayList<Movie> movies, ArrayList<Students> students, ArrayList<External_Member> members)
	{
        Path path = Path.of("C:\\Users\\Fninj\\Desktop\\SystemList.txt"); // adjust path as needed
        String logicalName = "System_List";
        if (!Files.exists(path)) 
        {
            System.out.println("File '" + logicalName + "' does not exist at: " + path.toAbsolutePath());
            return;
        }
        boolean readingMovies = false;
        boolean readingStu = false;
        boolean readingMem = false;
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String low = line.toLowerCase();
                if (low.startsWith("movie list:") || low.startsWith("movies:")) {
                    readingMovies = true;
                    readingStu = false;
                    readingMem = false;
                    continue;
                }
                if (low.startsWith("member list:") || low.startsWith("members:")) {
                    readingMovies = false;
                    readingStu = false;
                    readingMem = false;
                    continue;
                }
                if (low.startsWith("students:")) {
                    readingMovies = false;
                    readingStu = true;
                    readingMem = false;
                    continue;
                }
                if (low.startsWith("external members:") || low.startsWith("external:")) {
                    readingMovies = false;
                    readingStu = false;
                    readingMem = true;
                    continue;
                }
                Map<String, String> kv = parseKeyValues(line);
                try {
                    if (readingMovies) {
                        if (kv.containsKey("id") && (kv.containsKey("title") || kv.containsKey("name"))) {
                            int id = Integer.parseInt(kv.get("id"));
                            String title = kv.containsKey("title") ? kv.get("title") : kv.get("name");
                            movies.add(new Movie(id, title));
                        } else {
                            String[] parts = line.split(",", 2);
                            if (parts.length < 2) {
                                System.out.println("Skipping malformed movie line: " + line);
                                continue;
                            }
                            int id = Integer.parseInt(parts[0].trim());
                            String title = parts[1].trim();
                            movies.add(new Movie(id, title));
                        }
                    } else if (readingStu) {
                        if (kv.containsKey("id") && kv.containsKey("name")) {
                            int id = Integer.parseInt(kv.get("id"));
                            String name = kv.get("name");
                            String school = kv.getOrDefault("school", kv.getOrDefault("institution", ""));
                            int grade = 0;
                            if (kv.containsKey("grade")) {
                                grade = Integer.parseInt(kv.get("grade"));
                            } else if (kv.containsKey("class")) {
                                grade = Integer.parseInt(kv.get("class"));
                            }
                            students.add(new Students(id, name, "Student", school, grade));
                        } else {
                            String[] parts = line.split(",", 4);
                            if (parts.length < 4) {
                                System.out.println("Skipping malformed student line: " + line);
                                continue;
                            }
                            int id = Integer.parseInt(parts[0].trim());
                            String name = parts[1].trim();
                            String school = parts[2].trim();
                            int grade = Integer.parseInt(parts[3].trim());
                            students.add(new Students(id, name, "Student", school, grade));
                        }
                    } else if (readingMem) {
                        if (kv.containsKey("id") && kv.containsKey("name")) {
                            int id = Integer.parseInt(kv.get("id"));
                            String name = kv.get("name");
                            String job = kv.getOrDefault("job", "");
                            String org = kv.getOrDefault("organization", kv.getOrDefault("org", ""));
                            String type = kv.getOrDefault("type", "External");
                            members.add(new External_Member(id, name, type, job, org));
                        } else {
                            String[] parts = line.split(",", 4);
                            if (parts.length < 4) {
                                System.out.println("Skipping malformed external member line: " + line);
                                continue;
                            }
                            int id = Integer.parseInt(parts[0].trim());
                            String name = parts[1].trim();
                            String job = parts[2].trim();
                            String org = parts[3].trim();
                            members.add(new External_Member(id, name, "External", job, org));
                        }
                    } else {
                        if (kv.containsKey("type") && kv.containsKey("id") && kv.containsKey("name")) {
                            String type = kv.get("type").toLowerCase();
                            if (type.contains("external")) {
                                int id = Integer.parseInt(kv.get("id"));
                                String name = kv.get("name");
                                String job = kv.getOrDefault("job", "");
                                String org = kv.getOrDefault("organization", kv.getOrDefault("org", ""));
                                members.add(new External_Member(id, name, "External", job, org));
                                continue;
                            } else if (type.contains("student")) {
                                int id = Integer.parseInt(kv.get("id"));
                                String name = kv.get("name");
                                String school = kv.getOrDefault("school", "");
                                int grade = kv.containsKey("grade") ? Integer.parseInt(kv.get("grade")) : 0;
                                students.add(new Students(id, name, "Student", school, grade));
                                continue;
                            }
                        }
                    }
                } 
                catch (Exception e) 
                {
                    System.out.println("Skipping line due to unexpected error: " + line + " -> " + e.getMessage());
                }
            }
            System.out.println("Data has been imported successfully from " + logicalName);
        } 
        catch (IOException ioe) 
        {
            System.out.println("Error reading file: " + ioe.getMessage());
        }
    }
    
	private static Map<String, String> parseKeyValues(String line) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] tokens = line.split(",");
        for (String token : tokens) {
            String t = token.trim();
            if (t.isEmpty()) continue;
            String[] kv = t.split(":", 2);
            if (kv.length < 2) {
                continue;
            }
            String key = kv[0].trim().toLowerCase();
            String val = kv[1].trim();
            String lowerVal = val.toLowerCase();
            if (lowerVal.startsWith(key + ":")) {
                val = val.substring(key.length() + 1).trim();
            }
            for (String commonKey : new String[]{"id", "name", "type", "job", "organization", "org", "title", "school", "grade"}) {
                if (val.toLowerCase().startsWith(commonKey + ":")) {
                    val = val.substring(commonKey.length() + 1).trim();
                }
            }
            map.put(key, val);
        }
        return map;
	}
	
}
