public abstract class Person implements IPerson{
    private int id;
    private String name;
    private String PhoneNumber;
    private LinkedList<IRide> rideHistory;

    public Person(int id,String name,String PhoneNumber){
        this.id=id;
        this.name=name;
        setPhoneNumber(PhoneNumber);
        this.rideHistory=new LinkedList<>();
    }

    public int getId(){
        return id;}

    public String getName(){
        return name;}

    public void setName(String name){
        this.name=name;}

    public String getPhoneNumber(){
        return PhoneNumber;}

    public void setPhoneNumber(String PhoneNumber){
        if(PhoneNumber==null ||PhoneNumber.length()!=10)
        throw new IllegalArgumentException("phone must be 10 digits");
        for(int i=0;i<PhoneNumber.length();i++)
        if(!Character.isDigit(PhoneNumber.charAt(i)))
        throw new IllegalArgumentException("phone must contain digits onle");

        this.PhoneNumber=PhoneNumber;
    }

    public LinkedList<IRide> getRideHistory(){
        return rideHistory;
    }

    public String toString(){
        return "person{id = " +id+" name = "+name+" phone = "+PhoneNumber ;}
}