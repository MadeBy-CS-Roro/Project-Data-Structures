class Rider extends Person implements IRider{
    private String email;
    private String homeCity;

    Rider(int id,String name,String phoneNumber,String email,String homeCity){
        super(id,name,phoneNumber);
        this.email=email;
        this.homeCity=homeCity;}

    public String getEmail(){
        return email;}

    public void setEmail(String email){
        this.email=email;}

    public String getHomeCity(){
        return homeCity;}

    public void setHomeCity(String homeCity){
        this.homeCity=homeCity;}

    @Override
    public int compareTo(IRider other){
        return this.getId()-other.getId();
    }

    public String toString(){
        return " Rider{ id= " +getId()+" name = "+getName()+" email = "+email+" homeCity + "+ homeCity ; }
}
