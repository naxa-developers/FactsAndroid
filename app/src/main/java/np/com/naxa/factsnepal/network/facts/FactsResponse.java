package np.com.naxa.factsnepal.network.facts;

import java.util.List;

import np.com.naxa.factsnepal.feed.Fact;

public class FactsResponse {
    private List<Category> category;

    private String version;

    private List<Fact> home;

    public List<Category> getCategory ()
    {
        return category;
    }

    public void setCategory (List<Category> category)
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

    public List<Fact> getHome ()
    {
        return home;
    }

    public void setHome (List<Fact> home)
    {
        this.home = home;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+", version = "+version+", home = "+home+"]";
    }
}