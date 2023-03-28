public class Customer 
{
    public String firstname;
    public String surname;
    public int dob;

    public Customer(String firstname, String surname, int dob) 
    {
        this.firstname = firstname;
        this.surname = surname;
        this.dob = dob;
    }

    public String getFirstname() 
    {
        return firstname;
    }

    public void setFirstname(String firstname) 
    {
        this.firstname = firstname;
    }
    
    public String getSurname() 
    {
        return surname;
    }
    
    public void setSurname(String surname) 
    {
        this.surname = surname;
    }

    public int getDob() 
    {
        return dob;
    }

    public void setDob(int dob) 
    {
        this.dob = dob;
    }
}