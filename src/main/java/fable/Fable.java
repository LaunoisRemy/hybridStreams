package fable;


import java.util.Objects;

/**
 * Class to represent a fable in a gzip file
 */
public class Fable {
    /**
     * Name of the fable
     */
    private String name;
    /**
     * the byte starting from which we find the text
     */
    private int offSet;
    /**
     * the fable as a number of bytes
     */
    private int lenght;

    /**
     * Content of the fable
     */
    private String content;

    /**
     * Constructor of fable
     * @param name
     */
    public Fable(String name) {
        this.name = name;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Fable{" + name+ offSet + lenght + content;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fable fable = (Fable) o;
        return Objects.equals(name, fable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
