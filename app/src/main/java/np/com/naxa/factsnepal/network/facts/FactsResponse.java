package np.com.naxa.factsnepal.network.facts;

public class FactsResponse {
    private Category[] category;

    private String version;

    private Home[] home;

    public Category[] getCategory ()
    {
        return category;
    }

    public void setCategory (Category[] category)
    {
        this.category = category;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    public Home[] getHome ()
    {
        return home;
    }

    public void setHome (Home[] home)
    {
        this.home = home;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+", version = "+version+", home = "+home+"]";
    }
}