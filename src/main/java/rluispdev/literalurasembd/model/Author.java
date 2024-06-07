package rluispdev.literalurasembd.model;

public class Author {
    private String name;
    private int birth_year;
    private int death_year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = this.birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = this.death_year;
    }

    @Override
    public String toString() {
        return
                "nameAuthor='" + name + '\'' +
                ", birth=" + birth_year +
                ", death=" + death_year;

    }
}
