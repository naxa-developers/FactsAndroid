package np.com.naxa.factsnepal.network.facts;

public class Home {
    private String like_count;

    private String category_id;

    private String image_url;

    private String description;

    private String id;

    private String short_desc;

    private String title;

    private String slug;

    private String status;

    private String order;

    public String getLike_count ()
    {
        return like_count;
    }

    public void setLike_count (String like_count)
    {
        this.like_count = like_count;
    }

    public String getCategory_id ()
    {
        return category_id;
    }

    public void setCategory_id (String category_id)
    {
        this.category_id = category_id;
    }

    public String getImage_url ()
    {
        return image_url;
    }

    public void setImage_url (String image_url)
    {
        this.image_url = image_url;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getShort_desc ()
    {
        return short_desc;
    }

    public void setShort_desc (String short_desc)
    {
        this.short_desc = short_desc;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getSlug ()
    {
        return slug;
    }

    public void setSlug (String slug)
    {
        this.slug = slug;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getOrder ()
    {
        return order;
    }

    public void setOrder (String order)
    {
        this.order = order;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [like_count = "+like_count+", category_id = "+category_id+", image_url = "+image_url+", description = "+description+", id = "+id+", short_desc = "+short_desc+", title = "+title+", slug = "+slug+", status = "+status+", order = "+order+"]";
    }
}