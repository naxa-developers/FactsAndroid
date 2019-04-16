package np.com.naxa.factsnepal.feed;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import np.com.naxa.factsnepal.network.facts.Category;

@Entity(tableName = "facts")
public class Fact implements Serializable {

    @PrimaryKey
    @NonNull
    private int id;

    @SerializedName("like_count")
    private String likeCount;

    @SerializedName("category_id")
    private String catgoryId;

    private String categoryName;

    @SerializedName("image_url")
    private String imagePath;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("status")
    private String status;

    @SerializedName("order")
    private String order;


    private boolean isBookmarked;

    public Fact() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCatgoryId() {
        return catgoryId;
    }

    public void setCatgoryId(String catgoryId) {
        this.catgoryId = catgoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fact fact = (Fact) o;

        if (id != fact.id) return false;
        if (isBookmarked != fact.isBookmarked) return false;
        if (likeCount != null ? !likeCount.equals(fact.likeCount) : fact.likeCount != null)
            return false;
        if (catgoryId != null ? !catgoryId.equals(fact.catgoryId) : fact.catgoryId != null)
            return false;
        if (categoryName != null ? !categoryName.equals(fact.categoryName) : fact.categoryName != null)
            return false;
        if (imagePath != null ? !imagePath.equals(fact.imagePath) : fact.imagePath != null)
            return false;
        if (title != null ? !title.equals(fact.title) : fact.title != null) return false;
        if (slug != null ? !slug.equals(fact.slug) : fact.slug != null) return false;
        if (status != null ? !status.equals(fact.status) : fact.status != null) return false;
        return order != null ? order.equals(fact.order) : fact.order == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (likeCount != null ? likeCount.hashCode() : 0);
        result = 31 * result + (catgoryId != null ? catgoryId.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (isBookmarked ? 1 : 0);
        return result;
    }
}
