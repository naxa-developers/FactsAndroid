package np.com.naxa.factsnepal.feed;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Fact implements Serializable {
    private String title;
    private String imagePath;
    private String category;

    public Fact(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }


    private Fact(String title, String imagePath, String category) {
        this.title = title;
        this.imagePath = imagePath;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static ArrayList<Fact> getDemoItems(int numbers, int offset) {

        Fact fact;
        ArrayList<Fact> facts = new ArrayList<>();
        for (int i = offset; i <= numbers; i++) {
            Pair p = getRandom();
            facts.add(new Fact(p.first.toString(), p.second.toString(), "Category " + 1));
        }

        return facts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fact fact = (Fact) o;

        if (title != null ? !title.equals(fact.title) : fact.title != null) return false;
        if (imagePath != null ? !imagePath.equals(fact.imagePath) : fact.imagePath != null)
            return false;
        return category != null ? category.equals(fact.category) : fact.category == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }


    private static ArrayList<Pair> getFacts() {

        ArrayList<Pair> list = new ArrayList<Pair>();

        list.add(Pair.create("Among 167 countries, Nepal ranks at 97th position with an overall score of 5.18 in the Democracy Index 2018.",
                "https://scontent.fktm8-1.fna.fbcdn.net/v/t1.0-9/52598128_2388654841158088_3154020005595578368_n.jpg?_nc_cat=108&_nc_ht=scontent.fktm8-1.fna&oh=c05dbe8435ba5f7d20a6e3abd2c4e585&oe=5D12307D"));

        list.add(Pair.create("According to the Metropolitan Traffic Police, a total of 10,802 vehicles entered Kathmandu Valley through different entry points, whereas 10,815 vehicles left the valley on B.S. 2075-11-11."
                , "https://scontent.fktm8-1.fna.fbcdn.net/v/t1.0-9/52638797_2396505700373002_4565827759254798336_n.png?_nc_cat=105&_nc_ht=scontent.fktm8-1.fna&oh=7bbb8ead8c153edfe8e2f1af17f8eef2&oe=5D134EF8"));

        list.add(Pair.create("In terms of population, Province 3 has the highest number of literate persons among the 7 provinces of Nepal.",
                "https://scontent.fktm8-1.fna.fbcdn.net/v/t1.0-9/51968913_2376859975670908_6870779992251301888_n.png?_nc_cat=102&_nc_ht=scontent.fktm8-1.fna&oh=c6a42de61f964c94f5442f4bfeed3533&oe=5D1DCA51"
        ));

        list.add(Pair.create("In terms of population, Province 3 has the highest number of literate persons among the 7 provinces of Nepal.",
                "https://scontent.fktm8-1.fna.fbcdn.net/v/t1.0-9/51968913_2376859975670908_6870779992251301888_n.png?_nc_cat=102&_nc_ht=scontent.fktm8-1.fna&oh=c6a42de61f964c94f5442f4bfeed3533&oe=5D1DCA51"
        ));


        list.add(Pair.create("FACTS of the Week | Cancer Statistics | Published on 10 February 2019",
                "https://scontent.fktm8-1.fna.fbcdn.net/v/t1.0-9/51623548_2373885115968394_5689074480115089408_n.jpg?_nc_cat=104&_nc_ht=scontent.fktm8-1.fna&oh=415fd918c4fd91adbdb78680e0789802&oe=5D245AF1"));
        return list;
    }

    private static Pair getRandom() {
        ArrayList<Pair> list = getFacts();
        int rnd = new Random().nextInt(list.size());
        return list.get(rnd);
    }


    public static ArrayList<Pair> getDemoCategories() {
        ArrayList<Pair> arrayList = new ArrayList<>();
        arrayList.add(Pair.create("1", "All"));
        arrayList.add(Pair.create("2", "Education"));
        arrayList.add(Pair.create("3", "Health"));
        arrayList.add(Pair.create("4", "Politics"));
        arrayList.add(Pair.create("5", "Economics"));

        return arrayList;
    }
}
